
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

public class CountryDataAnalyzer {

    private static final long MIN_POPULATION = 50_000_000L;
    private static final long HIGH_POPULATION = 100_000_000L;
    private static final double MIN_AREA = 100_000.0;

    /*
     * TASK A: LOAD AND VALIDATE CSV DATA
     */

    public List<CountryRegion> loadData(String fileName)
            throws IOException {

        List<CountryRegion> regions = new ArrayList<>();

        try (BufferedReader reader =
                     new BufferedReader(new FileReader(fileName))) {

            String header = reader.readLine();

            if (header == null) {
                System.out.println("CSV file is empty.");
                return regions;
            }

            String line;
            int lineNumber = 1;

            while ((line = reader.readLine()) != null) {
                lineNumber++;

                if (line.trim().isEmpty()) {
                    continue;
                }

                String[] data = line.split(",", -1);

                if (data.length != 4) {
                    System.out.println(
                            "Skipping invalid CSV line: " + lineNumber
                                    + " (expected 4 columns).");
                    continue;
                }

                try {
                    String country = data[0].trim();
                    String region = data[1].trim();
                    long population =
                            Long.parseLong(data[2].trim());
                    double area =
                            Double.parseDouble(data[3].trim());

                    if (country.isEmpty()
                            || region.isEmpty()
                            || population < 0
                            || !Double.isFinite(area)
                            || area < 0) {

                        System.out.println(
                                "Skipping invalid CSV line: "
                                        + lineNumber
                                        + " (invalid field value).");
                        continue;
                    }

                    regions.add(new CountryRegion(
                            country, region, population, area));

                } catch (NumberFormatException e) {
                    System.out.println(
                            "Skipping invalid CSV line: "
                                    + lineNumber
                                    + " (invalid number).");
                }
            }
        }

        return regions;
    }

    /*
     * TASK A: BASIC STATISTICS
     */

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
            total = Math.addExact(
                    total, region.getPopulation());
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

    public CountryRegion getLargestRegion(
            List<CountryRegion> regions) {

        if (regions.isEmpty()) {
            return null;
        }

        CountryRegion largest = regions.get(0);

        for (CountryRegion region : regions) {
            if (region.getArea() > largest.getArea()) {
                largest = region;
            }
        }

        return largest;
    }

    /*
     * TASK B: ARRAYS
     */

    public CountryRegion[] getRegionsAsArray(
            List<CountryRegion> regions) {

        return regions.toArray(new CountryRegion[0]);
    }

    public void displayFirstFiveRegions(
            CountryRegion[] regions) {

        System.out.println("\nFirst Five Regions:");

        int limit = Math.min(regions.length, 5);

        for (int i = 0; i < limit; i++) {
            System.out.println(
                    (i + 1) + ". "
                            + regions[i].getRegion()
                            + " (" + regions[i].getCountry() + ")");
        }
    }

    public CountryRegion getMostPopulatedRegion(
            CountryRegion[] regions) {

        if (regions.length == 0) {
            return null;
        }

        CountryRegion mostPopulated = regions[0];

        for (CountryRegion region : regions) {
            if (region.getPopulation()
                    > mostPopulated.getPopulation()) {
                mostPopulated = region;
            }
        }

        return mostPopulated;
    }

    public CountryRegion[] getRegionsSortedByName(
            CountryRegion[] regions) {

        CountryRegion[] sortedRegions =
                Arrays.copyOf(regions, regions.length);

        Arrays.sort(
                sortedRegions,
                Comparator.comparing(
                        CountryRegion::getRegion,
                        String.CASE_INSENSITIVE_ORDER));

        return sortedRegions;
    }

    public long getTotalPopulationFromArray(
            CountryRegion[] regions) {

        long total = 0;

        for (CountryRegion region : regions) {
            total = Math.addExact(
                    total, region.getPopulation());
        }

        return total;
    }

    /*
     * TASK B: SETS
     *
     * Names are compared without regard to letter case.
     */

    public Set<String> getUniqueCountryNames(
            List<CountryRegion> regions) {

        Set<String> countries =
                new TreeSet<>(String.CASE_INSENSITIVE_ORDER);

        for (CountryRegion region : regions) {
            countries.add(region.getCountry());
        }

        return countries;
    }

    public Set<String> getUniqueRegionNames(
            List<CountryRegion> regions) {

        Set<String> names =
                new TreeSet<>(String.CASE_INSENSITIVE_ORDER);

        for (CountryRegion region : regions) {
            names.add(region.getRegion());
        }

        return names;
    }

    public Set<String> getRepeatedRegionNames(
            List<CountryRegion> regions) {

        Map<String, Integer> frequencies =
                new TreeMap<>(String.CASE_INSENSITIVE_ORDER);

        for (CountryRegion region : regions) {
            frequencies.merge(region.getRegion(), 1, Integer::sum);
        }

        Set<String> repeated =
                new TreeSet<>(String.CASE_INSENSITIVE_ORDER);

        for (Map.Entry<String, Integer> entry
                : frequencies.entrySet()) {

            if (entry.getValue() > 1) {
                repeated.add(entry.getKey());
            }
        }

        return repeated;
    }

    /*
     * TASK B: MAPS
     */

    public Map<String, List<String>> getRegionsPerCountry(
            List<CountryRegion> regions) {

        Map<String, List<String>> regionsPerCountry =
                new TreeMap<>(String.CASE_INSENSITIVE_ORDER);

        for (CountryRegion region : regions) {
            regionsPerCountry
                    .computeIfAbsent(
                            region.getCountry(),
                            key -> new ArrayList<>())
                    .add(region.getRegion());
        }

        for (List<String> countryRegions
                : regionsPerCountry.values()) {

            countryRegions.sort(String.CASE_INSENSITIVE_ORDER);
        }

        return regionsPerCountry;
    }

    public Map<String, Integer> getRegionCountPerCountry(
            List<CountryRegion> regions) {

        Map<String, Integer> counts =
                new TreeMap<>(String.CASE_INSENSITIVE_ORDER);

        for (CountryRegion region : regions) {
            counts.merge(region.getCountry(), 1, Integer::sum);
        }

        return counts;
    }

    public Map<String, Double> getAveragePopulationPerCountry(
            List<CountryRegion> regions) {

        Map<String, Long> totals =
                new TreeMap<>(String.CASE_INSENSITIVE_ORDER);

        Map<String, Integer> counts =
                new TreeMap<>(String.CASE_INSENSITIVE_ORDER);

        for (CountryRegion region : regions) {
            totals.merge(
                    region.getCountry(),
                    region.getPopulation(),
                    Math::addExact);

            counts.merge(region.getCountry(), 1, Integer::sum);
        }

        Map<String, Double> averages =
                new TreeMap<>(String.CASE_INSENSITIVE_ORDER);

        for (String country : totals.keySet()) {
            averages.put(
                    country,
                    (double) totals.get(country) / counts.get(country));
        }

        return averages;
    }

    public Map<String, Long> getCombinedPopulationPerCountry(
            List<CountryRegion> regions) {

        Map<String, Long> totals =
                new TreeMap<>(String.CASE_INSENSITIVE_ORDER);

        for (CountryRegion region : regions) {
            totals.merge(
                    region.getCountry(),
                    region.getPopulation(),
                    Math::addExact);
        }

        return totals;
    }

    public String getCountryWithHighestCombinedPopulation(
            List<CountryRegion> regions) {

        Map<String, Long> totals =
                getCombinedPopulationPerCountry(regions);

        String highestCountry = null;
        long highestPopulation = -1;

        for (Map.Entry<String, Long> entry : totals.entrySet()) {
            if (entry.getValue() > highestPopulation) {
                highestPopulation = entry.getValue();
                highestCountry = entry.getKey();
            }
        }

        return highestCountry;
    }

    /*
     * TASK B: QUEUES
     */

    public int processQueueAndDisplay(
            List<CountryRegion> regions) {

        Queue<CountryRegion> queue = new ArrayDeque<>(regions);

        int processed = 0;
        boolean fifoVerified = true;

        System.out.println("\nQueue Processing (CSV Order):");

        while (!queue.isEmpty()) {
            CountryRegion current = queue.remove();

            if (processed >= regions.size()
                    || current != regions.get(processed)) {
                fifoVerified = false;
            }

            System.out.println(
                    current.getCountry() + " - "
                            + current.getRegion()
                            + " | Population: "
                            + current.getPopulation());

            if (current.getPopulation() > MIN_POPULATION) {
                System.out.println(
                        "  Population exceeds 50 million.");
            }

            processed++;
        }

        System.out.println("Total records processed: " + processed);
        System.out.println("FIFO order verified: " + fifoVerified);

        return processed;
    }

    public List<CountryRegion> getRegionsAbovePopulation(
            List<CountryRegion> regions, long threshold) {

        Queue<CountryRegion> queue = new ArrayDeque<>();

        for (CountryRegion region : regions) {
            if (region.getPopulation() > threshold) {
                queue.add(region);
            }
        }

        List<CountryRegion> results = new ArrayList<>();

        while (!queue.isEmpty()) {
            results.add(queue.remove());
        }

        return results;
    }

    /*
     * TASK B: RESULTS FOR THE JSON REPORT
     *
     * These results can be passed to JsonReportWriter.
     * The writer itself will need to serialize these new fields.
     */

    public Map<String, Object> getTaskBReport(
            List<CountryRegion> regions) {

        CountryRegion[] array = getRegionsAsArray(regions);

        Map<String, Object> report = new LinkedHashMap<>();

        Set<String> countries = getUniqueCountryNames(regions);
        Set<String> regionNames = getUniqueRegionNames(regions);
        Set<String> repeatedNames = getRepeatedRegionNames(regions);

        report.put("uniqueCountryCount", countries.size());
        report.put("uniqueCountryNames",
                new ArrayList<>(countries));

        report.put("uniqueRegionCount", regionNames.size());
        report.put("uniqueRegionNames",
                new ArrayList<>(regionNames));

        report.put("repeatedRegionNames",
                new ArrayList<>(repeatedNames));

        report.put("perCountryRegionCounts",
                getRegionCountPerCountry(regions));

        report.put("averageRegionalPopulationByCountry",
                getAveragePopulationPerCountry(regions));

        report.put("combinedPopulationByCountry",
                getCombinedPopulationPerCountry(regions));

        report.put("countryWithHighestCombinedPopulation",
                getCountryWithHighestCombinedPopulation(regions));

        report.put("queueProcessedRecordCount", array.length);
        report.put("queueFifoVerified", isQueueFifo(regions));

        CountryRegion mostPopulated =
                getMostPopulatedRegion(array);

        if (mostPopulated != null) {
            report.put("mostPopulatedRegion",
                    mostPopulated.getRegion());
            report.put("mostPopulatedRegionCountry",
                    mostPopulated.getCountry());
            report.put("mostPopulatedRegionPopulation",
                    mostPopulated.getPopulation());
        }

        report.put("regionsAbove50Million",
                getRegionDescriptions(
                        getRegionsAbovePopulation(
                                regions, MIN_POPULATION)));

        report.put("regionsAbove100Million",
                getRegionDescriptions(
                        getRegionsAbovePopulation(
                                regions, HIGH_POPULATION)));

        report.put("totalPopulationFromArray",
                getTotalPopulationFromArray(array));

        return report;
    }

    private boolean isQueueFifo(List<CountryRegion> regions) {
        Queue<CountryRegion> queue = new ArrayDeque<>(regions);
        int index = 0;

        while (!queue.isEmpty()) {
            if (queue.remove() != regions.get(index)) {
                return false;
            }
            index++;
        }

        return index == regions.size();
    }

    private List<String> getRegionDescriptions(
            List<CountryRegion> regions) {

        List<String> descriptions = new ArrayList<>();

        for (CountryRegion region : regions) {
            descriptions.add(
                    region.getRegion() + " ("
                            + region.getCountry() + ")");
        }

        return descriptions;
    }

    /*
     * DISPLAY ALL TASK A AND TASK B RESULTS
     */

    public void displayResults(List<CountryRegion> regions) {
        if (regions.isEmpty()) {
            System.out.println("No records found in the CSV file.");
            System.out.println("Task B results are empty.");
            return;
        }

        long totalPopulation = getTotalPopulation(regions);
        double totalArea = getTotalArea(regions);

        double averagePopulation =
                (double) totalPopulation / regions.size();

        double averageArea = totalArea / regions.size();

        CountryRegion largest = getLargestRegion(regions);
        List<CountryRegion> filtered = getFilteredRegions(regions);

        System.out.println("========== TASK A RESULTS ==========");
        System.out.println("Total records: " + regions.size());
        System.out.println("Total population: " + totalPopulation);
        System.out.println("Average population: " + averagePopulation);
        System.out.println("Total area: " + totalArea + " km2");
        System.out.println("Average area: " + averageArea + " km2");
        System.out.println("Largest region by area: "
                + largest.getRegion() + " ("
                + largest.getCountry() + ")");
        System.out.println("Filtered records: " + filtered.size());

        System.out.println("\nRegions Per Country:");

        for (Map.Entry<String, List<String>> entry
                : getRegionsPerCountry(regions).entrySet()) {

            System.out.println(entry.getKey() + ":");

            for (String region : entry.getValue()) {
                System.out.println("  - " + region);
            }
        }

        System.out.println("\n========== TASK B: ARRAYS ==========");

        CountryRegion[] array = getRegionsAsArray(regions);

        displayFirstFiveRegions(array);

        CountryRegion mostPopulated = getMostPopulatedRegion(array);

        if (mostPopulated != null) {
            System.out.println("\nMost populated region: "
                    + mostPopulated.getRegion() + " ("
                    + mostPopulated.getCountry() + "), population: "
                    + mostPopulated.getPopulation());
        }

        System.out.println("\nRegions Sorted Alphabetically (Copy):");

        for (CountryRegion region : getRegionsSortedByName(array)) {
            System.out.println(
                    region.getRegion() + " (" + region.getCountry() + ")");
        }

        System.out.println("Array population total: "
                + getTotalPopulationFromArray(array));

        System.out.println("\n========== TASK B: SETS ==========");

        Set<String> countries = getUniqueCountryNames(regions);
        Set<String> regionNames = getUniqueRegionNames(regions);
        Set<String> repeatedNames = getRepeatedRegionNames(regions);

        System.out.println("Unique country count: " + countries.size());
        System.out.println("Unique countries (alphabetical): " + countries);
        System.out.println("Unique region-name count: " + regionNames.size());
        System.out.println("Unique region names: " + regionNames);
        System.out.println("Repeated region names: " + repeatedNames);

        System.out.println("\n========== TASK B: MAPS ==========");

        System.out.println("Region count per country:");

        for (Map.Entry<String, Integer> entry
                : getRegionCountPerCountry(regions).entrySet()) {
            System.out.println(
                    entry.getKey() + ": " + entry.getValue());
        }

        System.out.println("\nAverage regional population by country:");

        for (Map.Entry<String, Double> entry
                : getAveragePopulationPerCountry(regions).entrySet()) {
            System.out.println(
                    entry.getKey() + ": " + entry.getValue());
        }

        System.out.println("\nCombined population by country:");

        for (Map.Entry<String, Long> entry
                : getCombinedPopulationPerCountry(regions).entrySet()) {
            System.out.println(
                    entry.getKey() + ": " + entry.getValue());
        }

        System.out.println("Country with highest combined population: "
                + getCountryWithHighestCombinedPopulation(regions));

        System.out.println("\n========== TASK B: QUEUES ==========");

        processQueueAndDisplay(regions);

        List<CountryRegion> above100Million =
                getRegionsAbovePopulation(regions, HIGH_POPULATION);

        System.out.println("\nRegions with population above 100 million:");

        for (CountryRegion region : above100Million) {
            System.out.println(
                    region.getRegion() + " ("
                            + region.getCountry() + "), population: "
                            + region.getPopulation());
        }
    }
}
