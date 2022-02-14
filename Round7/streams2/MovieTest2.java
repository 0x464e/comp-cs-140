import java.util.stream.Collectors;
import java.util.stream.*;
import java.util.Map;

public class MovieTest2{


    public static void main(String[] args){    

        MovieAnalytics2  ma = new MovieAnalytics2();

        ma.populateWithData(args[0]);

        System.out.println("\nTop ten directors with highest number of movies:\n");

        ma.getMoviesByDirectors(10);

        System.out.println("\nAverage score by genre:\n");
        
        ma.getScoreByGenre();    

        System.out.println("\nAverage duration by genre:\n");
        
        ma.getDurationByGenre();

    }

}