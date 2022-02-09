import java.util.Arrays;

public class Mean
{
    public static void main(String[] args)
    {
        System.out.println("Mean: " + Arrays.stream(Arrays.stream(args).mapToDouble(Double::parseDouble).toArray()).average().getAsDouble());
    }
}