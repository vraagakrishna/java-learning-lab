
import java.util.Objects;

public class CountryRegion {

    private final String country;
    private final String region;
    private final long population;
    private final double area;

    public CountryRegion(String country, String region,
                         long population, double area) {

        if (country == null || country.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "Country name cannot be empty.");
        }

        if (region == null || region.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "Region name cannot be empty.");
        }

        if (population < 0) {
            throw new IllegalArgumentException(
                    "Population cannot be negative.");
        }

        if (!Double.isFinite(area) || area < 0) {
            throw new IllegalArgumentException(
                    "Area must be finite and cannot be negative.");
        }

        this.country = country.trim();
        this.region = region.trim();
        this.population = population;
        this.area = area;
    }

    public String getCountry() {
        return country;
    }

    public String getRegion() {
        return region;
    }

    public long getPopulation() {
        return population;
    }

    public double getArea() {
        return area;
    }

    // Calculate population density as people per unit of area.
    public double getPopulationDensity() {
        if (area == 0) {
            return 0.0;
        }

        return population / area;
    }

    // Provide a readable representation of a record.
    @Override
    public String toString() {
        return "CountryRegion{" +
                "country='" + country + '\'' +
                ", region='" + region + '\'' +
                ", population=" + population +
                ", area=" + area +
                '}';
    }

    // Compare records by their data values.
    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }

        if (!(object instanceof CountryRegion)) {
            return false;
        }

        CountryRegion other = (CountryRegion) object;

        return population == other.population
                && Double.compare(area, other.area) == 0
                && country.equals(other.country)
                && region.equals(other.region);
    }

    @Override
    public int hashCode() {
        return Objects.hash(country, region, population, area);
    }
}
