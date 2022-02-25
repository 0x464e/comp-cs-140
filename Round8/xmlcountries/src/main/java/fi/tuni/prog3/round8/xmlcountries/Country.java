package fi.tuni.prog3.round8.xmlcountries;

public record Country(String name, double area, long population, double gdp) implements Comparable<Country>
{
    @Override
    public int compareTo(Country o)
    {
        return name.compareTo(o.name);
    }

    @Override
    public String toString()
    {
        return String.format("%s%n  Area: %.1f km2%n  Population: %d%n  GDP: %.1f (2015 USD)",
                             name, area, population, gdp);
    }

    public String getName()
    {
        return name;
    }

    public double getArea()
    {
        return area;
    }

    public long getPopulation()
    {
        return population;
    }

    public double getGdp()
    {
        return gdp;
    }
}
