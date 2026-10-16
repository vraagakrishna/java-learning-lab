import java.io.*;
import java.util.*;

public class Main {

    // Constants: keep configuration values in one place
    private static final String INPUT_FILE = "countries.csv";
    private static final String OUTPUT_FILE = "results.json";

    private static final long MIN_POPULATION = 50_000_000L;
    private static final double MIN_AREA = 100_000.0;

    public static void main(String[] args) {
        CountryDataAnalyzer analyzer = new CountryDataAnalyzer();

        try {
            List<CountryRegion> regions =
                    analyzer.loadData(INPUT_FILE);

            if (regions.isEmpty()) {
                System.out.println("No records found in the CSV file.");
                return;
            }

            analyzer.displayResults(regions);
            JsonReportWriter.writeReport(
                    OUTPUT_FILE, analyzer, regions);

            System.out.println(OUTPUT_FILE + " created successfully.");

        } catch (IOException | IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // Model class: represents one country-region record
    private static class CountryRegion {
        private final String country;
        private final String region;
        private final long population;
        private final double area;

        CountryRegion(String country, String region,
                      long population, double area) {
            this.country = country;
            this.region = region;
            this.population = population;
            this.area = area;
        }

        String getCountry() {
            return country;
        }

        String getRegion() {
            return region;
        }

        long getPopulation() {
            return population;
        }

        double getArea() {
            return area;
        }
    }

    // Handles loading, analysis, filtering and displaying data
    private static class CountryDataAnalyzer {

        List<CountryRegion> loadData(String fileName)
                throws IOException {

            List<CountryRegion> regions = new ArrayList<>();

            try (BufferedReader reader = new BufferedReader(
                    new FileReader(fileName))) {

                // Skip the CSV header
                reader.readLine();

                String line;
                int lineNumber = 1;

                while ((line = reader.readLine()) != null) {
                    lineNumber++;

                    if (line.trim().isEmpty()) {
                        continue;
                    }

                    String[] data = line.split(",", -1);

                    if (data.length < 4) {
                        System.out.println(
                                "Skipping invalid CSV line: "
                                        + lineNumber);
                        continue;
                    }

                    try {
                        String country = data[0].trim();
                        String region = data[1].trim();
                        long population =
                                Long.parseLong(data[2].trim());
                        double area =
                                Double.parseDouble(data[3].trim());

                        if (country.isEmpty() || region.isEmpty()
                                || population < 0 || area < 0
                                || !Double.isFinite(area)) {
                            throw new IllegalArgumentException(
                                    "Invalid country data");
                        }

                        regions.add(new CountryRegion(
                                country, region, population, area));

                    } catch (NumberFormatException e) {
                        System.out.println(
                                "Skipping invalid CSV line: "
                                        + lineNumber);
                    }
                }
            }

            return regions;
        }

        List<CountryRegion> getFilteredRegions(
                List<CountryRegion> regions) {

            List<CountryRegion> filtered = new ArrayList<>();

            for (CountryRegion region : regions) {
                if (region.getPopulation() > MIN_POPULATION
                        && region.getArea() > MIN_AREA) {
                    filtered.add(region);
                }
            }

            return filtered;
        }

        long getTotalPopulation(List<CountryRegion> regions) {
            long total = 0;

            for (CountryRegion region : regions) {
                total += region.getPopulation();
            }

            return total;
        }

        double getTotalArea(List<CountryRegion> regions) {
            double total = 0;

            for (CountryRegion region : regions) {
                total += region.getArea();
            }

            return total;
        }

        CountryRegion getLargestRegion(List<CountryRegion> regions) {
            CountryRegion largest = regions.get(0);

            for (CountryRegion region : regions) {
                if (region.getArea() > largest.getArea()) {
                    largest = region;
                }
            }

            return largest;
        }

        // Task 5: group every region by country alphabetically
        Map<String, List<String>> getRegionsPerCountry(
                List<CountryRegion> regions) {

            Map<String, List<String>> regionsPerCountry =
                    new TreeMap<>();

            for (CountryRegion region : regions) {
                regionsPerCountry
                        .computeIfAbsent(
                                region.getCountry(),
                                key -> new ArrayList<>())
                        .add(region.getRegion());
            }

            // Sort each country's region names alphabetically
            for (List<String> countryRegions :
                    regionsPerCountry.values()) {
                Collections.sort(countryRegions);
            }

            return regionsPerCountry;
        }

        void displayResults(List<CountryRegion> regions) {
            long totalPopulation = getTotalPopulation(regions);
            double totalArea = getTotalArea(regions);

            double averagePopulation =
                    (double) totalPopulation / regions.size();
            double averageArea = totalArea / regions.size();

            CountryRegion largest = getLargestRegion(regions);
            List<CountryRegion> filtered = getFilteredRegions(regions);

            System.out.println("RESULTS :-");
            System.out.println("Total Population: " + totalPopulation);
            System.out.println("Average Population: " + averagePopulation);
            System.out.println("Total Area: " + totalArea + " km2");
            System.out.println("Average Area: " + averageArea + " km2");
            System.out.println("Largest Region: "
                    + largest.getRegion() + " ("
                    + largest.getCountry() + ")");
            System.out.println("Filtered Records: " + filtered.size());

            System.out.println("\nRegions Per Country:");

            for (Map.Entry<String, List<String>> entry :
                    getRegionsPerCountry(regions).entrySet()) {

                System.out.println(entry.getKey() + ":");

                for (String region : entry.getValue()) {
                    System.out.println("  - " + region);
                }
            }
        }
    }

    // Handles JSON output separately from data analysis
    private static class JsonReportWriter {

        static void writeReport(String fileName,
                                CountryDataAnalyzer analyzer,
                                List<CountryRegion> regions)
                throws IOException {

            long totalPopulation =
                    analyzer.getTotalPopulation(regions);
            double totalArea = analyzer.getTotalArea(regions);

            double averagePopulation =
                    (double) totalPopulation / regions.size();
            double averageArea = totalArea / regions.size();

            CountryRegion largest =
                    analyzer.getLargestRegion(regions);

            List<CountryRegion> filtered =
                    analyzer.getFilteredRegions(regions);

            Map<String, List<String>> regionsPerCountry =
                    analyzer.getRegionsPerCountry(regions);

            try (BufferedWriter writer = new BufferedWriter(
                    new FileWriter(fileName))) {

                writer.write("{\n");
                writer.write("  \"totalPopulation\": "
                        + totalPopulation + ",\n");
                writer.write("  \"averagePopulation\": "
                        + averagePopulation + ",\n");
                writer.write("  \"totalArea\": " + totalArea + ",\n");
                writer.write("  \"averageArea\": " + averageArea + ",\n");

                writer.write("  \"largestRegion\": {\n");
                writer.write("    \"country\": \""
                        + escapeJson(largest.getCountry()) + "\",\n");
                writer.write("    \"region\": \""
                        + escapeJson(largest.getRegion()) + "\",\n");
                writer.write("    \"population\": "
                        + largest.getPopulation() + ",\n");
                writer.write("    \"area\": "
                        + largest.getArea() + "\n");
                writer.write("  },\n");

                writer.write("  \"filteredRecords\": "
                        + filtered.size() + ",\n");

                // Task 5: include every country's regions in JSON
                writer.write("  \"regionsPerCountry\": {\n");

                int countryIndex = 0;

                for (Map.Entry<String, List<String>> entry :
                        regionsPerCountry.entrySet()) {

                    writer.write("    \""
                            + escapeJson(entry.getKey()) + "\": [");

                    List<String> countryRegions = entry.getValue();

                    for (int i = 0; i < countryRegions.size(); i++) {
                        if (i > 0) {
                            writer.write(", ");
                        }

                        writer.write("\""
                                + escapeJson(countryRegions.get(i))
                                + "\"");
                    }

                    writer.write("]");

                    if (++countryIndex < regionsPerCountry.size()) {
                        writer.write(",");
                    }

                    writer.write("\n");
                }

                writer.write("  }\n");
                writer.write("}\n");
            }
        }

        private static String escapeJson(String value) {
            return value.replace("\\", "\\\\")
                    .replace("\"", "\\\"")
                    .replace("\n", "\\n")
                    .replace("\r", "\\r")
                    .replace("\t", "\\t");
        }
    }
}
