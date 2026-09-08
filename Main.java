import java.io.*;
import java.util.*;

public class Main {
    private static final ArrayList<CountryRegion> regions = new ArrayList<>();

    private static class CountryRegion {
        String country;
        String region;
        long population;
        double area;

        CountryRegion(String country, String region, long population, double area) {
            this.country = country;
            this.region = region;
            this.population = population;
            this.area = area;
        }
    }

    public static void main(String[] args) {
        File csvFile = new File("java-learning-lab", "countries.csv");

        try (BufferedReader reader = new BufferedReader(new FileReader(csvFile))) {
            reader.readLine();
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length < 4) {
                    continue;
                }
                String country = data[0].trim();
                String regionName = data[1].trim();
                long population = Long.parseLong(data[2].trim());
                double area = Double.parseDouble(data[3].trim());
                regions.add(new CountryRegion(country, regionName, population, area));
            }
        } catch (Exception e) {
            System.out.println("Error reading CSV file: " + e.getMessage());
            return;
        }
        if (regions.isEmpty()) {
            System.out.println("No records found in the CSV file.");
            return;
        }
        ArrayList<CountryRegion> filtered = new ArrayList<>();
        for (CountryRegion r : regions) {
            if (r.population > 50_000_000 && r.area > 100_000) {
                filtered.add(r);
            }
        }

        Collections.sort(regions, (a, b) -> Long.compare(b.population, a.population));
        long totalPopulation = 0;
        double totalArea = 0;
        for (CountryRegion r : regions) {
            totalPopulation += r.population;
            totalArea += r.area;
        }

        double averagePopulation = (double) totalPopulation / regions.size();
        double averageArea = totalArea / regions.size();

        CountryRegion largest = regions.get(0);
        for (CountryRegion r : regions) {
            if (r.area > largest.area) {
                largest = r;
            }
        }

        System.out.println("RESULTS : -");
        System.out.println("Total Population: " + totalPopulation);
        System.out.println("Average Population: " + averagePopulation);
        System.out.println("Total Area: " + totalArea + " km2");
        System.out.println("Average Area: " + averageArea + " km2");
        System.out.println("Largest Region: " + largest.region + " (" + largest.country + ")");
        System.out.println("Filtered Records: " + filtered.size());

        File outputFile = new File("java-learning-lab", "results.json");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {
            writer.write("{\n");
            writer.write("  \"totalPopulation\": " + totalPopulation + ",\n");
            writer.write("  \"averagePopulation\": " + averagePopulation + ",\n");
            writer.write("  \"totalArea\": " + totalArea + ",\n");
            writer.write("  \"averageArea\": " + averageArea + ",\n");
            writer.write("  \"largestRegion\": {\n");
            writer.write("    \"country\": \"" + largest.country + "\",\n");
            writer.write("    \"region\": \"" + largest.region + "\",\n");
            writer.write("    \"population\": " + largest.population + ",\n");
            writer.write("    \"area\": " + largest.area + "\n");
            writer.write("  }\n");
            writer.write("}\n");

            System.out.println("results.json created successfully.");
        } catch (Exception e) {
            System.out.println("Error writing JSON file: " + e.getMessage());
        }
    }
}
