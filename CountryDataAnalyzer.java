import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class CountryDataAnalyzer {

    private static final long MIN_POPULATION = 50_000_000L;
    private static final double MIN_AREA = 100_000.0;

    public List<CountryRegion> loadData(String fileName)
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
                            "Skipping invalid CSV line: " + lineNumber);
                    continue;
                }

                try {
                    String country = data[0].trim();
                    String region = data[1].trim();
                    long population = Long.parseLong(data[2].trim());
                    double area = Double.parseDouble(data[3].trim());

                    if (country.isEmpty() || region.isEmpty()
                            || population < 0 || area < 0
                            || !Double.isFinite(area)) {
                        throw new IllegalArgumentException(
                                "Invalid country data on line "
                                        + lineNumber);
                    }

                    regions.add(new CountryRegion(
                            country, region, population, area));

                } catch (NumberFormatException e) {
                    System.out.println(
                            "Skipping invalid CSV line: " + lineNumber);
                }
            }
        }

        return regions;
    }

    public List<CountryRegion> getFilteredRegions(
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

    public long getTotalPopulation(List<CountryRegion> regions) {
        long total = 0;

        for (CountryRegion region : regions) {
            total += region.getPopulation();
        }

        return total;
    }

    public double getTotalArea(List<CountryRegion> regions) {
        double total = 0;

        for (CountryRegion region : regions) {
            total += region.getArea();
        }

        return total;
    }

    public CountryRegion getLargestRegion(List<CountryRegion> regions) {
        if (regions.isEmpty()) {
            throw new IllegalArgumentException(
                    "Cannot find the largest region in an empty list.");
        }

        CountryRegion largest = regions.get(0);

        for (CountryRegion region : regions) {
            if (region.getArea() > largest.getArea()) {
                largest = region;
            }
        }

        return largest;
    }

    public Map<String, List<String>> getRegionsPerCountry(
            List<CountryRegion> regions) {

        Map<String, List<String>> regionsPerCountry = new TreeMap<>();

        for (CountryRegion region : regions) {
            regionsPerCountry
                    .computeIfAbsent(
                            region.getCountry(),
                            key -> new ArrayList<>())
                    .add(region.getRegion());
        }

        // Sort each country's regions alphabetically
        for (List<String> countryRegions : regionsPerCountry.values()) {
            Collections.sort(countryRegions);
        }

        return regionsPerCountry;
    }

    public void displayResults(List<CountryRegion> regions) {
        if (regions.isEmpty()) {
            System.out.println("No records found in the CSV file.");
            return;
        }

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
