package fi.tuni.prog3.standings;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Pattern;

/**
 * A class for maintaining team statistics and standings. Team standings are determined by the following rules:
 * <ul>
 * <li>Primary rule: points total. Higher points come first.</li>
 * <li>Secondary rule: goal difference (scored minus allowed). Higher difference comes first.</li>
 * <li>Tertiary rule: number of goals scored. Higher number comes first.</li>
 * <li>Last rule: natural String order of team names.</li>
 * </ul>
 */
public class Standings
{
    private final ArrayList<Team> teams = new ArrayList<>();

    /**
     * A class for storing statistics of a single team.
     * The class offers only public getter functions.
     * The enclosing class Standings is responsible for setting and updating team statistics.
     */
    public static class Team
    {
        private final String name;
        private int wins;
        private int ties;
        private int losses;
        private int scored;
        private int allowed;
        private int points;
        private int playedGames;

        /**
         * Constructs a Team object for storing statistics of the named team.
         * @param name the name of the team whose statistics the new team object stores.
         */
        public Team(String name)
        {
            this.name = name;
        }

        /**
         * Returns the name of the team.
         * @return the name of the team.
         */
        public String getName()
        {
            return name;
        }

        /**
         * Returns the number of wins of the team.
         * @return the number of wins of the team.
         */
        public int getWins()
        {
            return wins;
        }

        /**
         * Returns the number of ties of the team.
         * @return the number of ties of the team.
         */
        public int getTies()
        {
            return ties;
        }

        /**
         * Returns the number of losses of the team.
         * @return the number of losses of the team.
         */
        public int getLosses()
        {
            return losses;
        }

        /**
         * Returns the number of goals scored by the team.
         * @return the number of goals scored by the team.
         */
        public int getScored()
        {
            return scored;
        }

        /**
         * Returns the number of goals allowed (conceded) by the team.
         * @return the number of goals allowed (conceded) by the team.
         */
        public int getAllowed()
        {
            return allowed;
        }

        /**
         * Returns the overall number of points of the team.
         * @return the overall number of points of the team.
         */
        public int getPoints()
        {
            return points;
        }



    }

    /**
     * Constructs an empty Standings object.
     */
    public Standings() {}

    /**
     * Constructs a Standings object that is initialized with the game data read from the specified file.
     * The result is identical to first constructing an empty Standing object and then calling {@link #readMatchData(String fileName) readMatchData(filename)}.
     * @param fileName the name of the game data file to read.
     * @throws IOException if there is some kind of an IO error (e.g. if the specified file does not exist).
     */
    public Standings(String fileName) throws IOException
    {
        readMatchData(fileName);
    }


    /**
     * Reads game data from the specified file and updates the team statistics and standings accordingly.<p>
     * The match data file is expected to contain lines of form "teamNameA\tgoalsA-goalsB\tteamNameB". Note that the '\t' are tabulator characters.<p>
     * E.g. the line "Iceland\t3-2\tFinland" would describe a match between Iceland and Finland where Iceland scored 3 and Finland 2 goals.
     * @param fileName the name of the game data file to read.
     * @throws IOException if there is some kind of an IO error (e.g. if the specified file does not exist).
     */
    public final void readMatchData(String fileName) throws IOException
    {
        try (var file = new BufferedReader(new FileReader(fileName)))
        {
            String line;
            while ((line = file.readLine()) != null)
            {
                var pattern = Pattern.compile("(.+?)\\t(\\d)-(\\d)\\t(.+)");
                var matcher = pattern.matcher(line);
                matcher.find();

                addMatchResult(matcher.group(1), Integer.parseInt(matcher.group(2)), Integer.parseInt(matcher.group(3)), matcher.group(4));
            }
        }
    }

    /**
     * Updates the team statistics and standings according to the match result described by the parameters.
     * @param teamNameA the name of the first ("home") team.
     * @param goalsA the number of goals scored by the first team.
     * @param goalsB the number of goals scored by the second team.
     * @param teamNameB the name of the second ("away") team.
     */
    public void addMatchResult(String teamNameA, int goalsA, int goalsB, String teamNameB)
    {
        var teamA = teams.stream()
                .filter(x -> x.getName().equals(teamNameA))
                .findFirst()
                .orElseGet(() -> {
                    var team = new Team(teamNameA);
                    teams.add(team);
                    return team;
                });
        var teamB = teams.stream()
                .filter(x -> x.getName().equals(teamNameB))
                .findFirst()
                .orElseGet(() -> {
                    var team = new Team(teamNameB);
                    teams.add(team);
                    return team;
                });

        teamA.playedGames++;
        teamB.playedGames++;

        teamA.scored += goalsA;
        teamB.scored += goalsB;

        teamA.allowed += goalsB;
        teamB.allowed += goalsA;

        if (goalsA > goalsB)
        {
            teamA.wins++;
            teamA.points += 3;
            teamB.losses++;
        }
        else if (goalsB > goalsA)
        {
            teamB.wins++;
            teamB.points += 3;
            teamA.losses++;
        }
        else
        {
            teamA.ties++;
            teamB.ties++;
            teamA.points++;
            teamB.points++;
        }
    }

    /**
     * Returns a list of the teams in the same order as they would appear in a standings table.
     * @return a list of the teams in the same order as they would appear in a standings table.
     */
    public List<Team> getTeams()
    {
        return teams.stream()
                .sorted(Comparator.comparing(Team::getPoints).reversed()
                                .thenComparing((a, b) -> (b.getScored() - b.getAllowed()) - (a.getScored() - a.getAllowed()))
                                .thenComparing((a, b) -> b.getScored() - a.getScored())
                                .thenComparing(Team::getName)).toList();
    }

    /**
     * Prints a formatted standings table to the provided output stream.
     * @param out the output stream to use when printing the standings table.
     */
    public void printStandings(PrintStream out)
    {
        var sortedTeams = this.teams.stream().sorted(Comparator.comparing(Team::getPoints).reversed()
                                                             .thenComparing((a, b) -> (b.getScored() - b.getAllowed()) - (a.getScored() - a.getAllowed()))
                                                             .thenComparing((a, b) -> b.getScored() - a.getScored())
                                                             .thenComparing(Team::getName)).toList();

        var nameWidth = sortedTeams.stream().max(Comparator.comparingInt(x -> x.name.length())).get().name.length();
        for (var team : sortedTeams)
            System.out.format("%-" + nameWidth + "s %3d %3d %3d %3d %6s %3d%n", team.name, team.playedGames, team.wins,
                              team.ties, team.losses, String.format("%d-%d", team.scored, team.allowed), team.points);
    }



}
