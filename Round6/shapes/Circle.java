public record Circle(double radius) implements IShapeMetrics
{
    @Override
    public String toString()
    {
        return String.format("Circle radius: %.2f", radius);
    }

    @Override
    public String name()
    {
        return "circle";
    }

    @Override
    public double area()
    {
        return PI * radius * radius;
    }

    @Override
    public double circumference()
    {
        return 2 * PI * radius;
    }
}
