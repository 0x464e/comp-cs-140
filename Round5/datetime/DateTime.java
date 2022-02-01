public class DateTime extends Date
{
    private final int hour;
    private final int minute;
    private final int second;

    private static boolean isIllegalTime(int hour, int minute, int second)
    {
        return hour < 0 || hour > 23 || minute < 0 || minute > 59 || second < 0 || second > 59;
    }

    DateTime(int year, int month, int day, int hour, int minute, int second) throws DateException
    {
        super(year, month, day);
        if (isIllegalTime(hour, minute, second))
            throw new DateException(String.format("Illegal time %02d:%02d:%02d", hour, minute, second));

        this.hour = hour;
        this.minute = minute;
        this.second = second;
    }

    @Override
    public String toString()
    {
        return String.format("%s %02d:%02d:%02d", super.toString(), hour, minute, second);
    }

    public int getHour()
    {
        return hour;
    }

    public int getMinute()
    {
        return minute;
    }

    public int getSecond()
    {
        return second;
    }
}
