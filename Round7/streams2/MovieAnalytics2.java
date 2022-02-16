import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Map;
import java.util.stream.Collectors;

public class MovieAnalytics2
{
    private final ArrayList<Movie> movies = new ArrayList<>();

    public MovieAnalytics2() {}

    public void populateWithData(String fileName) throws IOException
    {
        try (var reader = new BufferedReader(new FileReader(fileName)))
        {
            movies.addAll(
                    reader.lines()
                            .map(x ->
                                 {
                                     var lines = x.split(";");
                                     return new Movie(lines[0], Integer.parseInt(lines[1]), Integer.parseInt(lines[2]), lines[3], Double.parseDouble(lines[4]), lines[5]);
                                 }
                            ).toList());
        }
    }

    public void printCountByDirector(int n)
    {
        movies.stream().collect(Collectors.groupingBy(Movie::getDirector, Collectors.counting()))
                .entrySet()
                .stream()
                .sorted(Comparator.comparingLong(Map.Entry<String, Long>::getValue).reversed().thenComparing(Map.Entry::getKey))
                .limit(n)
                .forEach(x -> System.out.format("%s: %d movies%n", x.getKey(), x.getValue()));
    }

    public void printAverageDurationByGenre()
    {
        movies.stream().collect(Collectors.groupingBy(Movie::getGenre, Collectors.averagingDouble(Movie::getDuration)))
                .entrySet()
                .stream()
                .sorted(Comparator.comparingDouble(Map.Entry<String, Double>::getValue).thenComparing(Map.Entry::getKey))
                .forEach(x -> System.out.format("%s: %.2f%n", x.getKey(), x.getValue()));
    }

    public void printAverageScoreByGenre()
    {
        movies.stream().collect(Collectors.groupingBy(Movie::getGenre, Collectors.averagingDouble(Movie::getScore)))
                .entrySet()
                .stream()
                .sorted(Comparator.comparingDouble(Map.Entry<String, Double>::getValue).reversed().thenComparing(Map.Entry::getKey))
                .forEach(x -> System.out.format("%s: %.2f%n", x.getKey(), x.getValue()));
    }
}
