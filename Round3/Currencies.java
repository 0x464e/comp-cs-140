import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class Currencies
{
    public static void main(String[] args) throws IOException
    {
        var reader = new BufferedReader(new InputStreamReader(System.in));
        var rates = new TreeMap<String, Double>();
        for (;;)
        {
            System.out.print("Enter command: ");
            var input = reader.readLine();
            var inputs = input.split(" +");

            if (input.isEmpty())
                continue;

            System.out.println(input);


            var command = inputs[0];

            if (command.equals("rate") && inputs.length == 3)
            {
                var rateId = inputs[1].toUpperCase();
                var rate = Double.parseDouble(inputs[2]);
                rates.put(rateId, rate);
                System.out.format("Stored the rate 1 EUR = %.3f %s%n", rate, rateId);
            }
            else if (command.equals("convert") && inputs.length == 3)
            {
                var amount = Double.parseDouble(inputs[1]);
                var rateId = inputs[2].toUpperCase();

                if (!rates.containsKey(rateId))
                {
                    System.out.format("No rate for %s has been stored!%n", rateId);
                    continue;
                }

                var rate = rates.get(rateId);
                System.out.format("%.3f %s = %.3f EUR%n", amount, rateId, amount/rate);
            }
            else if (command.equals("rates") && inputs.length == 1)
            {
                System.out.format("Stored euro rates:%n  ");
                System.out.println(rates.entrySet().stream().map(x -> String.format("%s %.3f", x.getKey(), x.getValue())).collect(Collectors.joining(String.format("%n  "))));
            }
            else if (command.equals("quit") && inputs.length == 1)
            {
                System.out.println("Quit-command received, exiting...");
                break;
            }
            else
                System.err.println("Unknown or illegal command!");
        }
    }
}
