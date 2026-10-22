# Java Data Processing Exercise

## Overview

A Java application that reads country and region records from `countries.csv`, calculates statistics, groups records by country, and writes the results to `results.json`.

## Features

- Reads country, region, population, and area values from `countries.csv`.
- Skips blank rows, rows with fewer than four comma-separated fields, invalid numeric values, and records with invalid or missing country/region names or negative population/area values.
- Calculates total and average population and total and average area.
- Identifies the largest region by area and the most populated region as separate results.
- Counts records with population greater than 50 million **and** area greater than 100,000.
- Groups region names by country and sorts country and region names alphabetically.
- Writes the statistics, largest-region details, most-populated-region details, filtered-record count, and country-wise region lists to `results.json`.
- Handles file I/O errors and selected invalid-data conditions.
- Escapes backslashes, quotation marks, and common control characters when writing country and region names to JSON.

## Project Structure

```text
java-learning-lab/
├── Main.java                  # Application entry point
├── CountryRegion.java         # Country-region data model
├── CountryDataAnalyzer.java   # CSV loading and data analysis
├── JsonReportWriter.java      # JSON report generation
├── countries.csv              # Input data (provide this file)
├── results.json               # Generated report
└── README.md                  # Project documentation
```

## Requirements

- Java Development Kit (JDK)
- `countries.csv` in the project directory, with a header row followed by country, region, population, and area fields

## How to Run

Open a terminal in the project directory and compile all Java files:

```bash
javac *.java
```

Run the application:

```bash
java Main
```

The application prints the analysis to the terminal and writes `results.json` in the current working directory.

## Output Summary

`results.json` contains:

- Total and average population
- Total and average area
- Largest region by area
- Most populated region
- Number of records matching both filtering conditions
- Region names grouped by country and sorted alphabetically

The largest region by area and the most populated region are different measurements and are reported separately.

## Java Concepts Demonstrated

- Classes, objects, constructors, and getter methods
- Encapsulation through private fields in `CountryRegion`
- Separation of responsibilities across the data model, analyzer, and report writer
- Abstraction through `Main` calling high-level loading, analysis, and report-writing methods without implementing their internal steps
- Collections: `List`, `ArrayList`, `Map`, and `TreeMap`
- Loops, filtering, grouping, and sorting
- File handling with `BufferedReader` and `BufferedWriter`
- Exception handling and try-with-resources
- Constants declared with `static final`
- JSON output and character escaping

## Future Improvements

- Calculate population density.
- Add unit tests for calculations and data processing.
- Use dedicated CSV and JSON libraries.
- Add configurable filtering and sorting options.
