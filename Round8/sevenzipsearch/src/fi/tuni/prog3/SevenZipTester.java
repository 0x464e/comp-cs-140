package fi.tuni.prog3;

import org.apache.commons.compress.archivers.sevenz.SevenZFile;

import java.io.*;
import java.util.Locale;
import java.util.regex.Pattern;

public class SevenZipTester
{
    public static void main(String[] args) throws IOException
    {
        var fileName = args[0];
        var searchWord = args[1];

        try (var sevenZFile = new SevenZFile(new File(fileName)))
        {
            var entries = sevenZFile.getEntries();
            for (var entry : entries)
            {
                var name = entry.getName();
                if (!name.matches(".+?\\.txt$"))
                    continue;

                System.out.format("%s%n", name);

                try (var reader = new BufferedReader(new StringReader(new String(sevenZFile.getInputStream(entry).readAllBytes()))))
                {
                    String line;
                    var i = 1;
                    while ((line = reader.readLine()) != null)
                    {
                        var matcher = Pattern.compile("(" + searchWord + ")", Pattern.CASE_INSENSITIVE).matcher(line);
                        if (matcher.find())
                            System.out.format("%d: %s%n", i, matcher.replaceAll(x -> x.group().toUpperCase(Locale.ROOT)));

                        i++;
                    }
                    System.out.println();
                }
            }
        }
    }
}
