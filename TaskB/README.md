# Java Data Processing Exercise — Task B

## Overview

Task B extends the Java Data Processing Exercise from Task A. It practises Java arrays, Sets, Maps, and Queues using country and region records loaded from `countries.csv`.

The application reads real CSV data, performs data-processing operations, displays results in the terminal, and generates an extended JSON report named `results.json`.

Task A focuses on general dataset analysis, while Task B focuses on applying different Java data structures to solve data-processing problems.

## Features

### 1. Arrays

- Converts the region collection into an array.
- Displays the first five regions, or all available regions when fewer than five exist.
- Finds the most populated region using an array.
- Sorts a copy of the array alphabetically by region name without modifying the original collection.
- Calculates total population using the array.

### 2. Sets

- Identifies unique country names and region names.
- Displays the number of unique countries and regions.
- Identifies region names that occur more than once.
- Displays unique country names alphabetically.
- Uses Set-based collection behaviour to manage duplicate values.

### 3. Maps

- Associates countries with their region names.
- Calculates the number of regions recorded for each country.
- Calculates average regional population for each country.
- Calculates combined regional population by country.
- Identifies the country with the highest combined regional population.
- Displays country names and their corresponding results alphabetically.

### 4. Queues

- Adds region records to a queue in CSV order.
- Removes and processes records one at a time.
- Displays each region during processing.
- Identifies regions with populations exceeding 50 million.
- Displays the total number of processed records.
- Verifies FIFO (First In, First Out) processing order.
- Identifies regions with populations exceeding 100 million using a separate threshold-based queue operation.

### 5. JSON Report

The generated `results.json` contains the original Task A results and additional Task B information, including:

- Total records, population, and area statistics.
- Largest region by area.
- Regions grouped by country.
- Unique country and region counts and names.
- Repeated region names.
- Region counts by country.
- Average and combined regional population by country.
- Country with the highest combined regional population.
- Most populated region.
- Queue processing count and FIFO verification.
- Regions exceeding 50 million and 100 million population thresholds.
- Total population calculated using the array.

## Project Structure

```text
Java_Task_B/
├── Main.java
├── CountryRegion.java
├── CountryDataAnalyzer.java
├── JsonReportWriter.java
├── countries.csv
├── results.json          # Generated when the application runs
└── README.md
```

### Responsibilities of Each File

| File                       | Responsibility                                                                |
| -------------------------- | ----------------------------------------------------------------------------- |
| `Main.java`                | Entry point that loads data, displays results, and invokes report generation. |
| `CountryRegion.java`       | Represents a country-region record and its associated data.                   |
| `CountryDataAnalyzer.java` | Loads CSV records and performs Task A and Task B analysis.                    |
| `JsonReportWriter.java`    | Writes the analysis results to a JSON file.                                   |
| `countries.csv`            | Input dataset containing country, region, population, and area fields.        |
| `results.json`             | Generated report containing the analysis results.                             |

## Requirements

- Java Development Kit (JDK 21 or a compatible JDK).
- `countries.csv` located in the project directory.
- A CSV header followed by country, region, population, and area values.
- PowerShell, Command Prompt, or another terminal for compiling and running the application.

## How to Run

Open a terminal in the project directory:

```powershell
cd D:\JAVA\Java_Task_B
```

Compile the Java source files:

```powershell
javac *.java
```

Run the application:

```powershell
java Main
```

The application displays the analysis in the terminal and writes `results.json` in the current working directory.

**Windows compiler note:** If `javac` does not work because of your Java PATH configuration, use the JDK compiler directly:

```powershell
& "C:\Program Files\Java\jdk-21\bin\javac.exe" -d . Main.java CountryDataAnalyzer.java CountryRegion.java JsonReportWriter.java
```

Then run:

```powershell
& "C:\Program Files\Java\jdk-21\bin\java.exe" -cp "." Main
```

The direct-path commands above match the development environment used to run this project.

## Sample Execution Results

The supplied dataset produced the following results during testing:

| Result                                            |         Value |
| ------------------------------------------------- | ------------: |
| Valid records loaded                              |            49 |
| Unique countries                                  |            11 |
| Unique region names                               |            49 |
| Total population                                  | 2,138,800,000 |
| Regions above 50 million population               |            16 |
| Regions above 100 million population              |             7 |
| Queue records processed                           |            49 |
| FIFO verification                                 |        `true` |
| Country with highest combined regional population |         India |

These values describe the current supplied dataset. Results may differ if the CSV input changes.

## Java Concepts Demonstrated

- Classes, objects, constructors, and encapsulation.
- Separation of responsibilities across Java classes.
- Arrays and array copying.
- Sorting with a comparator.
- Sets and duplicate handling.
- Maps for grouping and aggregation.
- Queues and FIFO processing.
- Loops, filtering, and calculations.
- CSV file reading and JSON file writing.
- Exception handling and try-with-resources.
- Collections and generic data structures.

## Optional Challenges

The assignment lists several optional extensions. They should be treated separately from the core requirements.

- Use a `LinkedHashMap` to preserve the order in which countries first appear in the CSV file.
- Use a `PriorityQueue` to process regions from highest to lowest population.
- Find the five regions with the highest population density.
- Allow users to specify a country name or population threshold when running the program.

These features are not claimed as completed in this README. They can be added later if required.

---

# Original Task B Assignment

The following section preserves the original assignment questions and requirements for future reference.

## Task B

Extend your existing Java program to practise working with **arrays, Sets, Maps, and Queues** using the country and region data.

Task A focused on processing the dataset and analysing its contents. Task B focuses on applying different Java data structures to solve data-processing problems.

Some of the results produced in Task B may overlap with Task A. This is intentional. Focus on how the choice of data structure affects the way you store, access, and process the data.

### 1. Arrays

Use arrays to perform the following operations:

- Convert the collection of regions into an array.
- Display the first five regions in the array, or all regions if fewer than five exist.
- Find the most populated region using the array.
- Sort a copy of the array by region name in alphabetical order without modifying the original collection.
- Calculate the total population using the array.

### 2. Sets

Use Sets to analyse unique values in the dataset:

- Find all unique country names.
- Find all unique region names.
- Display the number of unique countries and regions.
- Identify region names that appear more than once in the dataset.
- Display unique country names in alphabetical order.

Consider how a Set handles duplicate elements and how you can identify duplicates before adding elements to a Set. Remember that the same region name may legitimately occur in different countries.

### 3. Maps

Use Maps to organise and analyse the data:

- Associate each country with its regions.
- Calculate the number of regions recorded for each country.
- Calculate the average regional population for each country.
- Identify the country with the highest combined regional population.
- Display country names alongside their corresponding region counts in alphabetical order.

Consider which Map implementation is appropriate for each operation and how the choice affects ordering and data access.

### 4. Queues

Use a Queue to process region records in the order they appear in the CSV file.

- Add region records to a queue.
- Remove and process the records one at a time.
- Display each region as it is processed.
- Identify regions whose population exceeds 50 million.
- Display the total number of records processed.
- Verify that records are processed in FIFO (First In, First Out) order.

**Additional challenge:** Create a separate queue containing only regions with a population greater than 100 million and process those records separately.

### 5. Update the JSON Output

Extend the JSON output from Task A to include relevant results from Task B.

Include information such as:

- The number of unique countries and regions.
- Unique country names.
- Region names that appear more than once.
- The number of regions recorded for each country.
- Results from queue processing, including the total number of records processed.

You may organise the output as you see fit, provided that the JSON is valid and the results are meaningful.

### 6. Additional Challenges (Optional)

- Use a `TreeSet` to maintain unique country names in alphabetical order.
- Use a `LinkedHashMap` to preserve the order in which countries first appear in the CSV file.
- Use a `PriorityQueue` to process regions from highest to lowest population.
- Find the five regions with the highest population density.
- Allow the user to specify a country name or population threshold when running the program.

### Requirements

- Reuse your existing classes and data wherever appropriate.
- Demonstrate the practical use of arrays, Sets, Maps, and Queues.
- Extend the JSON output with relevant results.
- Handle empty datasets and invalid records appropriately.
- Be prepared to explain why each data structure was selected and how it behaves.

There is no single correct implementation. Choose appropriate data structures and demonstrate their practical application to the supplied dataset.

## Notes

There is no single "correct" implementation.

Choose the Collections, object structure, sorting, filtering, and JSON structure that you think are appropriate.

The main requirement is that the program **reads real data, processes it, and produces a new file containing the results**.
