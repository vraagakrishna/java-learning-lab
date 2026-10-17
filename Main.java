
import java.io.IOException;
import java.util.List;

public class Main {

    // File names used by the program
    private static final String INPUT_FILE = "countries.csv";
    private static final String OUTPUT_FILE = "results.json";

    public static void main(String[] args) {

        CountryDataAnalyzer analyzer = new CountryDataAnalyzer();

        try {
            // Read country and region data from the CSV file
            List<CountryRegion> regions =
                    analyzer.loadData(INPUT_FILE);

            if (regions.isEmpty()) {
                System.out.println(
                        "No records found in the CSV file.");
                return;
            }

            // Display the analysis results
            analyzer.displayResults(regions);

            // Generate the JSON report
            JsonReportWriter.writeReport(
                    OUTPUT_FILE, analyzer, regions);

            System.out.println(
                    OUTPUT_FILE + " created successfully.");

        } catch (IOException | IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
