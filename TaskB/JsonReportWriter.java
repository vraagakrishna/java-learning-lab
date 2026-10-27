
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class JsonReportWriter {

    public static void writeReport(
            String fileName,
            CountryDataAnalyzer analyzer,
            List<CountryRegion> regions) throws IOException {

        // Store all report information in insertion order.
        Map<String, Object> report = new LinkedHashMap<>();

        // =====================================================
        // TASK A - ORIGINAL STATISTICS
        // =====================================================

        long totalPopulation =
                analyzer.getTotalPopulation(regions);

        double totalArea =
                analyzer.getTotalArea(regions);

        int recordCount = regions.size();

        double averagePopulation = recordCount == 0
                ? 0.0
                : (double) totalPopulation / recordCount;

        double averageArea = recordCount == 0
                ? 0.0
                : totalArea / recordCount;

        CountryRegion largest = recordCount == 0
                ? null
                : analyzer.getLargestRegion(regions);

        List<CountryRegion> filtered =
                analyzer.getFilteredRegions(regions);

        Map<String, List<String>> regionsPerCountry =
                analyzer.getRegionsPerCountry(regions);

        report.put("totalRecords", recordCount);
        report.put("totalPopulation", totalPopulation);
        report.put("averagePopulation", averagePopulation);
        report.put("totalArea", totalArea);
        report.put("averageArea", averageArea);

        // Handle empty input without causing a NullPointerException.
        if (largest == null) {
            report.put("largestRegion", null);
        } else {
            Map<String, Object> largestRegion =
                    new LinkedHashMap<>();

            largestRegion.put("country", largest.getCountry());
            largestRegion.put("region", largest.getRegion());
            largestRegion.put("population", largest.getPopulation());
            largestRegion.put("area", largest.getArea());

            report.put("largestRegion", largestRegion);
        }

        report.put("filteredRecords", filtered.size());

        // Keep the original Task A country-to-regions result.
        report.put("regionsPerCountry", regionsPerCountry);

        // =====================================================
        // TASK B - ARRAYS, SETS, MAPS AND QUEUES
        // =====================================================

        Map<String, Object> taskBReport =
                analyzer.getTaskBReport(regions);

        report.put("taskB", taskBReport);

        // =====================================================
        // WRITE THE COMPLETE REPORT AS JSON
        // =====================================================

        try (BufferedWriter writer = new BufferedWriter(
                new FileWriter(fileName))) {

            writeJson(report, writer, 0);
            writer.newLine();
        }
    }

    // =========================================================
    // GENERIC JSON WRITER
    // Supports maps, lists, arrays, strings, numbers,
    // booleans and null values.
    // =========================================================

    private static void writeJson(
            Object value,
            BufferedWriter writer,
            int indent) throws IOException {

        if (value == null) {
            writer.write("null");
            return;
        }

        if (value instanceof String
                || value instanceof Character) {
            writer.write("\"");
            writer.write(escapeJson(value.toString()));
            writer.write("\"");
            return;
        }

        if (value instanceof Number) {
            Number number = (Number) value;

            if ((number instanceof Double
                    && !Double.isFinite(number.doubleValue()))
                    || (number instanceof Float
                    && !Float.isFinite(number.floatValue()))) {
                writer.write("null");
            } else {
                writer.write(number.toString());
            }
            return;
        }

        if (value instanceof Boolean) {
            writer.write(value.toString());
            return;
        }

        if (value instanceof CountryRegion) {
            CountryRegion region = (CountryRegion) value;

            Map<String, Object> regionDetails =
                    new LinkedHashMap<>();

            regionDetails.put("country", region.getCountry());
            regionDetails.put("region", region.getRegion());
            regionDetails.put("population", region.getPopulation());
            regionDetails.put("area", region.getArea());

            writeJson(regionDetails, writer, indent);
            return;
        }

        if (value instanceof Map<?, ?>) {
            Map<?, ?> map = (Map<?, ?>) value;

            writer.write("{");

            if (!map.isEmpty()) {
                writer.newLine();
            }

            int index = 0;

            for (Map.Entry<?, ?> entry : map.entrySet()) {
                writeIndent(writer, indent + 1);

                writer.write("\"");
                writer.write(escapeJson(
                        String.valueOf(entry.getKey())));
                writer.write("\": ");

                writeJson(entry.getValue(), writer, indent + 1);

                if (++index < map.size()) {
                    writer.write(",");
                }

                writer.newLine();
            }

            if (!map.isEmpty()) {
                writeIndent(writer, indent);
            }

            writer.write("}");
            return;
        }

        if (value instanceof Iterable<?>) {
            Iterable<?> items = (Iterable<?>) value;

            java.util.Iterator<?> iterator = items.iterator();

            writer.write("[");

            if (iterator.hasNext()) {
                writer.newLine();
            }

            while (iterator.hasNext()) {
                writeIndent(writer, indent + 1);
                writeJson(iterator.next(), writer, indent + 1);

                if (iterator.hasNext()) {
                    writer.write(",");
                }

                writer.newLine();
            }

            if (items.iterator().hasNext()) {
                writeIndent(writer, indent);
            }

            writer.write("]");
            return;
        }

        if (value.getClass().isArray()) {
            int length = Array.getLength(value);

            writer.write("[");

            if (length > 0) {
                writer.newLine();
            }

            for (int i = 0; i < length; i++) {
                writeIndent(writer, indent + 1);
                writeJson(Array.get(value, i), writer, indent + 1);

                if (i < length - 1) {
                    writer.write(",");
                }

                writer.newLine();
            }

            if (length > 0) {
                writeIndent(writer, indent);
            }

            writer.write("]");
            return;
        }

        // Fallback: represent unsupported objects as strings.
        writer.write("\"");
        writer.write(escapeJson(value.toString()));
        writer.write("\"");
    }

    private static void writeIndent(
            BufferedWriter writer,
            int indent) throws IOException {

        for (int i = 0; i < indent; i++) {
            writer.write("  ");
        }
    }

    // Escape special characters so the generated JSON stays valid.
    private static String escapeJson(String value) {
        StringBuilder escaped = new StringBuilder();

        for (int i = 0; i < value.length(); i++) {
            char character = value.charAt(i);

            switch (character) {
                case '"':
                    escaped.append("\\\"");
                    break;

                case '\\':
                    escaped.append("\\\\");
                    break;

                case '\b':
                    escaped.append("\\b");
                    break;

                case '\f':
                    escaped.append("\\f");
                    break;

                case '\n':
                    escaped.append("\\n");
                    break;

                case '\r':
                    escaped.append("\\r");
                    break;

                case '\t':
                    escaped.append("\\t");
                    break;

                default:
                    if (character < 0x20) {
                        escaped.append(String.format(
                                "\\u%04x", (int) character));
                    } else {
                        escaped.append(character);
                    }
            }
        }

        return escaped.toString();
    }
}
