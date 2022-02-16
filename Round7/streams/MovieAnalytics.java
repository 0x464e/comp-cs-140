import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.function.Consumer;
import java.util.stream.Stream;

public class MovieAnalytics
{
    private final ArrayList<Movie> movies = new ArrayList<>();

    public MovieAnalytics() {}

    public static Consumer<Movie> showInfo()
    {
        return x -> System.out.format("%s (By %s, %s)%n", x.getTitle(), x.getDirector(), x.getReleaseYear());
    }

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

    public Stream<Movie> moviesAfter(int year)
    {
        return movies.stream().filter(x -> x.getReleaseYear() >= year).sorted(Comparator.comparing(Movie::getReleaseYear).thenComparing(Movie::getTitle));
    }

    public Stream<Movie> moviesBefore(int year)
    {
        return movies.stream().filter(x -> x.getReleaseYear() <= year).sorted(Comparator.comparing(Movie::getReleaseYear).thenComparing(Movie::getTitle));
    }

    public Stream<Movie> moviesBetween(int yearA, int yearB)
    {
        return movies.stream().filter(x -> x.getReleaseYear() >= yearA && x.getReleaseYear() <= yearB).sorted(Comparator.comparing(Movie::getReleaseYear).thenComparing(Movie::getTitle));
    }

    public Stream<Movie> moviesByDirector(String director)
    {
        return movies.stream().filter(x -> x.getDirector().equals(director)).sorted(Comparator.comparing(Movie::getReleaseYear).thenComparing(Movie::getTitle));
    }
}
