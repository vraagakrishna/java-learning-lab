# Java Data Processing Exercise

## Goal

Build a Java program that reads country and region data from a CSV file, processes the data using different Java data structures, and writes the results to a JSON file.

The exercise focuses on:

- Reading data from a file
- Writing data to a file
- Working with Java objects and Collections
- Using arrays, Sets, Maps, and Queues
- Filtering and sorting data
- Performing numerical calculations
- Removing duplicates and grouping data
- Processing records in a specific order
- Producing structured output in JSON format

---

## Input Data

You are given a `countries.csv` file containing information about countries and their regions.

Each row contains:

```csv
country,region,population,area_km2
```

Example:

```csv
India,Maharashtra,126000000,307713
India,Uttar Pradesh,241000000,243286
India,Bihar,131000000,94163
India,West Bengal,101000000,88752
Japan,Tokyo,14000000,2194
Japan,Osaka,8800000,1905
Japan,Aichi,7500000,5173
China,Guangdong,127000000,179800
China,Shandong,101000000,157100
China,Sichuan,84000000,486000
```

The full dataset is provided separately.

---

## Task A

Write a program that:

### 1. Read the data

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

### 5. Write the results

Write your processed data and/or calculated results to a JSON file.

For example:

```json
[
  {
    "country_name": "India",
    "regions": ["Bihar", "Maharashtra", "Uttar Pradesh", "West Bengal"]
  }
]
```

You can decide what additional calculated information would be useful to include in the JSON.

--- 

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

---

## Notes

There is no single "correct" implementation.

Choose the Collections, object structure, sorting, filtering, and JSON structure that you think are appropriate.

The main requirement is that the program **reads real data, processes it, and produces a new file containing the results**.
