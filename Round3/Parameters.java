import java.util.Arrays;
import java.util.Comparator;
import java.util.TreeSet;


public class Parameters
{
    public static void main(String[] args)
    {
        var database = new TreeSet<>(Arrays.asList(args));
        var indexWidth = (int)(Math.log10(database.size()) + 1);
        var inputWidth = database.stream().max(Comparator.comparingInt(String::length)).get().length();
        System.out.println("###" + "#".repeat(indexWidth + inputWidth + 4));
        int i = 0;
        for (var input : database)
        {
            System.out.format("# %1$" + indexWidth + "d | %2$-" + inputWidth + "s #%n", ++i, input);
            if (i < database.size())
                System.out.println("#" + "-".repeat(indexWidth + 2) + "+" + "-".repeat(inputWidth + 2) + "#");
        }
        System.out.println("###" + "#".repeat(indexWidth + inputWidth + 4));

    }
}
