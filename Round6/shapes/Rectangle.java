public record Rectangle(double height, double width)  implements IShapeMetrics
{
    @Override
    public String toString()
    {
        return String.format("Rectangle with height %.2f and width %.2f", height, width);
    }

    @Override
    public String name()
    {
        return "rectangle";
    }

    @Override
    public double area()
    {
        return width * height;
    }

    @Override
    public double circumference()
    {
        return 2 * width + 2 * height;
    }
}
