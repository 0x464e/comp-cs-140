public class GameStateException extends Exception
{
    GameStateException()
    {
        super("There is currently no active word game!");
    }
}
