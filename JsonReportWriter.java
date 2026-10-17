
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class JsonReportWriter {

    public static void writeReport(
            String fileName,
            CountryDataAnalyzer analyzer,
            List<CountryRegion> regions) throws IOException {

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

            // Include every country's regions in JSON
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
