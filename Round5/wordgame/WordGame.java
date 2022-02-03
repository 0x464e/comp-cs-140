import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class WordGame
{
    private final ArrayList<String> words;
    private String word;
    private boolean gameActive;
    private WordGameState gameState;

    public static class WordGameState
    {
        private ArrayList<String> word;
        private final ArrayList<String> flippedWord;
        private int mistakes;
        private final int mistakeLimit;
        private int missingChars;

        private WordGameState(String word, int mistakeLimit)
        {
            this.word = new ArrayList<>(Collections.nCopies(word.length(), "_"));
            this.flippedWord = new ArrayList<>(Arrays.stream(word.split("")).toList());
            this.mistakeLimit = mistakeLimit;
            this.missingChars = word.length();
        }

        public String getWord()
        {
            return String.join("", word);
        }

        public int getMistakes()
        {
            return mistakes;
        }

        public int getMistakeLimit()
        {
            return mistakeLimit;
        }

        public int getMissingChars()
        {
            return missingChars;
        }
    }

    public WordGame(String wordFilename) throws IOException
    {
        words = new ArrayList<>(Files.readAllLines(Path.of(wordFilename)));
    }

    void initGame(int wordIndex, int mistakeLimit)
    {
        this.word = words.get(wordIndex % words.size());
        gameState = new WordGameState(word, mistakeLimit);
        gameActive = true;
    }

    boolean isGameActive()
    {
        return gameActive;
    }

    WordGameState getGameState() throws GameStateException
    {
        if (!gameActive)
            throw new GameStateException();
        return gameState;
    }

    WordGameState guess(char guessChar) throws GameStateException
    {
        if (!gameActive)
            throw new GameStateException();

        var c = Character.toString(guessChar).toLowerCase();
        var found = false;

        for (var i = 0; i < gameState.flippedWord.size(); i++)
        {
            var charAt = gameState.flippedWord.get(i);
            if (charAt.equals(c))
            {
                found = true;
                gameState.flippedWord.set(i, "");
                gameState.word.set(i, charAt);
                if (--gameState.missingChars == 0)
                {
                    gameActive = false;
                    break;
                }
            }
        }

        if (!found && ++gameState.mistakes > gameState.mistakeLimit)
            gameActive = false;

        return gameState;
    }

    WordGameState guess(String guessWord) throws GameStateException
    {
        if (!gameActive)
            throw new GameStateException();

        if (word.equals(guessWord))
        {
            gameState.missingChars = 0;
            gameState.word = new ArrayList<>(Arrays.stream(word.split("")).toList());
            gameActive = false;
        }
        else if (++gameState.mistakes > gameState.mistakeLimit)
            gameActive = false;

        return gameState;
    }
}
