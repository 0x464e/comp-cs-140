import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Pattern;

public class Standings
{
    private final ArrayList<Team> teams = new ArrayList<>();

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

        public String getName()
        {
            return name;
        }

        public int getWins()
        {
            return wins;
        }

        public int getTies()
        {
            return ties;
        }

        public int getLosses()
        {
            return losses;
        }

        public int getScored()
        {
            return scored;
        }

        public int getAllowed()
        {
            return allowed;
        }

        public int getPoints()
        {
            return points;
        }


        Team(String teamName)
        {
            this.name = teamName;
        }
    }

    public Standings(String fileName) throws IOException
    {
        readMatchData(fileName);
    }

    public Standings() {}

    public void readMatchData(String fileName) throws IOException
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

    public void printStandings()
    {
        var sortedTeams = this.teams.stream().sorted(Comparator.comparing(Team::getPoints).reversed()
                                                             .thenComparing((a, b) -> (b.getScored() - b.getAllowed()) - (a.getScored() - a.getAllowed()))
                                                             .thenComparing((a, b) -> b.getScored() - a.getScored())
                                                             .thenComparing(Team::getName)).toList();

        var nameWidth = sortedTeams.stream().max(Comparator.comparingInt(x -> x.name.length())).get().name.length();
        for (var team : sortedTeams)
            System.out.format("%1$-" + nameWidth + "s %2$3d %3$3d %4$3d %5$3d %6$6s %7$3d%n", team.name, team.playedGames, team.wins, team.ties,
                              team.losses, String.format("%d-%d", team.scored, team.allowed), team.points);
    }

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

    public List<Team> getTeams()
    {
        return teams.stream()
                .sorted(Comparator.comparing(Team::getPoints).reversed()
                                .thenComparing((a, b) -> (b.getScored() - b.getAllowed()) - (a.getScored() - a.getAllowed()))
                                .thenComparing((a, b) -> b.getScored() - a.getScored())
                                .thenComparing(Team::getName)).toList();
    }
}
