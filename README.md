# Java Data Processing Exercise

## Overview

A Java application that reads country and region data from a CSV file, performs statistical analysis, organizes records, and exports the results to a JSON file.

## Features

- **CSV Processing:** Reads country, region, population, and area data from `countries.csv`.
- **Data Validation:** Skips blank or malformed rows and handles invalid numeric values.
- **Statistical Analysis:** Calculates total population, average population, total area, and average area.
- **Region Analysis:** Identifies the largest region by area.
- **Data Filtering:** Filters regions with a population greater than 50 million and an area greater than 100,000.
- **Country-wise Grouping:** Groups all regions under their respective countries.
- **Alphabetical Sorting:** Sorts country names and region names alphabetically.
- **JSON Export:** Generates `results.json` containing statistics, largest-region details, filtered record count, and regions per country.
- **Error Handling:** Handles file access errors and selected data-validation errors.
- **OOP Design:** Separates the data model, analysis logic, and JSON report generation into dedicated classes.
- **Reusable Constants:** Stores filenames and filtering thresholds as constants.
- **JSON Escaping:** Handles common special characters in country and region names.

## Project Structure

```text
java-learning-lab/
│
├── Main.java                 # Main entry point of the application
├── CountryRegion.java        # Represents country and region data
├── CountryDataAnalyzer.java  # Loads, validates, filters, and analyzes data
├── JsonReportWriter.java     # Generates the JSON report
├── countries.csv             # Input dataset containing country and region records
├── results.json              # Generated JSON report
└── README.md                 # Project documentation
```

### File Descriptions

- **Main.java:** Starts the application and coordinates data processing and report generation.
- **CountryRegion.java:** Defines the data model for country-region records.
- **CountryDataAnalyzer.java:** Reads CSV data, validates records, calculates statistics, filters regions, and groups and sorts regions alphabetically by country.
- **JsonReportWriter.java:** Exports statistical results and country-wise region lists to `results.json`.
- **countries.csv:** Contains the input data used by the application.
- **results.json:** Stores the generated analysis results in JSON format.
- **README.md:** Documents the project, its features, and instructions for running it.

## Requirements

- Java Development Kit (JDK).
- `countries.csv` in the project directory.

## How to Run

Compile the program:

```bash
javac Main.java
```

Execute the application:

```bash
java Main
```

The program displays the analysis results in the terminal and generates `results.json` in the current working directory.

## Output Summary

The generated report includes:

- Total and average population.
- Total and average area.
- Largest region by area, including its country, population, and area.
- Number of records matching the filtering conditions.
- All regions grouped by country, with countries and region names sorted alphabetically.

## Java Concepts Demonstrated

- Object-Oriented Programming (OOP).
- Encapsulation and abstraction.
- Classes, objects, constructors, and getter methods.
- Java Collections: `List`, `ArrayList`, `Map`, and `TreeMap`.
- Iteration, filtering, grouping, and sorting.
- File handling using `BufferedReader` and `BufferedWriter`.
- Exception handling and try-with-resources.
- Constants using `static final`.
- JSON report generation and character escaping.

## Future Improvements

- Calculate population density and identify the most populated region.
- Add unit tests for calculations and data processing.
- Use dedicated CSV and JSON libraries.
- Add configurable filtering and sorting options.
