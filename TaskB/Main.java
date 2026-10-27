
import java.io.IOException;
import java.util.List;

public class Main {

    // Input and output files for Task B.
    private static final String INPUT_FILE = "countries.csv";
    private static final String OUTPUT_FILE = "results.json";

    public static void main(String[] args) {

        CountryDataAnalyzer analyzer = new CountryDataAnalyzer();

        try {
            // Step 1: Load and validate the CSV records.
            List<CountryRegion> regions =
                    analyzer.loadData(INPUT_FILE);

            System.out.println("CSV file loaded successfully.");
            System.out.println("Valid records loaded: " + regions.size());

            // Step 2: Display Task A and Task B analysis.
            // The analyzer handles empty lists safely.
            analyzer.displayResults(regions);

            // Step 3: Generate the JSON report.
            // The report writer must serialize Task B fields too.
            JsonReportWriter.writeReport(
                    OUTPUT_FILE,
                    analyzer,
                    regions
            );

            System.out.println(
                    "\nJSON report created successfully: "
                            + OUTPUT_FILE);

        } catch (IOException e) {
            System.err.println(
                    "File error: " + e.getMessage());
            System.err.println(
                    "Check that countries.csv exists in "
                            + "D:\\JAVA\\TaskB and is readable.");

        } catch (ArithmeticException e) {
            System.err.println(
                    "Calculation error: a numeric total "
                            + "exceeded its supported range.");

        } catch (IllegalArgumentException e) {
            System.err.println(
                    "Invalid input or argument: "
                            + e.getMessage());
        }
    }
}
