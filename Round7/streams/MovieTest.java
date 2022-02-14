


import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.*;

public class MovieTest{


    public static void main(String[] args){

        
        MovieAnalytics  ma = new MovieAnalytics();

        ma.populateWithData(args[0]);


        // Movies after 2020
        System.out.println("\nMovies after 2020:\n");
        ma.moviesAfter(2020).forEach(ma.showInfo());

        // Movies before 1930
        System.out.println("\nMovies before 1930:\n");
        ma.moviesBefore(1930).forEach(ma.showInfo());

        // Between 1971 and 1972
        System.out.println("\nMovies between 1971-1972:\n");
        ma.moviesBetween(1971,1972).forEach(ma.showInfo());

        // Movies by M.S
        System.out.println("\nMovies by Martin Scorsese:\n");
        ma.moviesByDirector("Martin Scorsese").forEach(ma.showInfo());

    }

}