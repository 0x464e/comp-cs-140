import java.util.Arrays;

public class Median
{
    public static void main(String[] args)
    {
        var inputArray = Arrays.stream(args).mapToDouble(Double::parseDouble).sorted().toArray();
        System.out.println("Median: " + (inputArray.length % 2 == 0 ? (inputArray[inputArray.length/2 - 1] + inputArray[inputArray.length/2])/2 : inputArray[inputArray.length/2]));
    }
}
