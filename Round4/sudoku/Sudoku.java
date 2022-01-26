import java.util.ArrayList;
import java.util.Collections;
import java.util.stream.IntStream;

public class Sudoku
{
    private final ArrayList<ArrayList<Character>> board = new ArrayList<>();

    public Sudoku()
    {
        IntStream.range(0,9).forEach(x -> board.add(new ArrayList<>(Collections.nCopies(9, ' '))));
    }

    public void set(int i, int j, char c)
    {
        if (i < 0 || i > 8 || j < 0 || j > 8)
            System.out.format("Trying to access illegal cell (%d, %d)!%n", i, j);
        else if (!Character.toString(c).matches("[ 1-9]"))
            System.out.format("Trying to set illegal character %s to (%d, %d)!%n", c, i, j);
        else
            board.get(i).set(j, c);
    }

    public void print()
    {
        System.out.println("#####################################");
        var i = 0;
        for (var line : board)
        {
            System.out.format("# %s | %s | %s # %s | %s | %s # %s | %s | %s #%n", line.toArray());
            System.out.println(++i % 3 == 0 ? "#####################################" : "#---+---+---#---+---+---#---+---+---#");
        }
    }

    public boolean check()
    {
        var rowNumber = 0;

        //rows
        for (var row : board)
        {
            var sortedRow = row.stream().filter(x -> x != ' ').sorted().toList();
            char prevElem = 0;
            for (var elem : sortedRow)
            {
                if (elem == prevElem)
                {
                    System.out.format("Row %d has multiple %s's!%n", rowNumber, prevElem);
                    return false;
                }
                prevElem = elem;
            }
            rowNumber++;
        }

        //columns
        for (var i = 0; i < 9; i++)
        {
            var finalI = i;
            var sortedColumn = board.stream().map(x -> x.get(finalI)).filter(x -> x != ' ').sorted().toList();
            char prevElem = 0;
            for (var elem : sortedColumn)
            {
                if (elem == prevElem)
                {
                    System.out.format("Column %d has multiple %s's!%n", i, prevElem);
                    return false;
                }
                prevElem = elem;
            }
        }

        for (var section = 0; section < 9; section += 3)
        {
            for (var block = 0; block < 3; block++)
            {
                final var finalBlock = block;
                var sortedBlock = board.subList(section, section + 3)
                        .stream()
                        .flatMap(x -> x.subList(finalBlock * 3, finalBlock * 3 + 3).stream())
                        .filter(x -> x != ' ')
                        .sorted()
                        .toList();

                char prevElem = 0;
                for (var elem : sortedBlock)
                {
                    if (elem == prevElem)
                    {
                        System.out.format("Block at (%d, %d) has multiple %s's!%n", section, block * 3, prevElem);
                        return false;
                    }
                    prevElem = elem;
                }
            }
        }

        return true;
    }
}
