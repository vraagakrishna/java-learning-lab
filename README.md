# Java Data Processing Exercise

## Goal

Build a small Java program that reads data from a CSV file, processes it, and writes the results to a JSON file.

The exercise focuses on:

- Reading data from a file
- Writing data to a file
- Using Java Collections
- Filtering and sorting data
- Performing numerical calculations
- Working with objects

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

## Tasks

Write a program that:

### 1. Read the data

Read all records from the CSV file and store them using appropriate Java objects and Collections.

### 2. Filter

Find regions based on conditions such as:

- Population greater than 50 million
- Area greater than 100,000 km²
- Regions belonging to a particular country

### 3. Sort

Sort the data by:

- Population
- Area
- Region name

Try sorting in both ascending and descending order.

### 4. Calculate

Use the data to calculate things such as:

- Total population
- Average population
- Total area
- Average area
- Population density (`population / area`)
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

## Notes

There is no single "correct" implementation.

Choose the Collections, object structure, sorting, filtering, and JSON structure that you think are appropriate.

The main requirement is that the program **reads real data, processes it, and produces a new file containing the results**.
