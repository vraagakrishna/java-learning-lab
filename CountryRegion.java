
public class CountryRegion {

    private final String country;
    private final String region;
    private final long population;
    private final double area;

    public CountryRegion(String country, String region,
                         long population, double area) {
        this.country = country;
        this.region = region;
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
}
