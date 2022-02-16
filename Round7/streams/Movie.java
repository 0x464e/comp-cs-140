public record Movie(String title, int releaseYear, int duration, String genre, double score,
                    String director)
{
    public String getTitle()
    {
        return title;
    }

    public int getReleaseYear()
    {
        return releaseYear;
    }

    public int getDuration()
    {
        return duration;
    }

    public String getGenre()
    {
        return genre;
    }

    public double getScore()
    {
        return score;
    }

    public String getDirector()
    {
        return director;
    }

}
