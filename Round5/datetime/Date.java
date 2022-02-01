public class Date
{
    static boolean isLeapYear(int year)
    {
        return (year % 4 == 0) && ((year % 100 != 0) || (year % 400 == 0));
    }

    private static int[][] mDays = {{31, 31}, {28, 29}, {31, 31}, {30, 30}, {31, 31}, {30, 30},
            {31, 31}, {31, 31}, {30, 30}, {31, 31}, {30, 30}, {31, 31}};

    static int monthDays(int month, int year)
    {
        int days = -1;
        if (1 <= month && month <= 12)
        {
            days = isLeapYear(year) ? mDays[month - 1][1] : mDays[month - 1][0];
        }
        return days;
    }

    static boolean isLegalDate(int day, int month, int year)
    {
        return (1 <= day) && (day <= monthDays(month, year));
    }

    private final int year;
    private final int month;
    private final int day;

    public Date(int year, int month, int day) throws DateException
    {
        if (!isLegalDate(day, month, year))
            throw new DateException(String.format("Illegal date %02d.%02d.%d", day, month, year));

        this.day = day;
        this.month = month;
        this.year = year;
    }

    @Override
    public String toString()
    {
       return String.format("%02d.%02d.%d", day, month, year);
    }

    public int getYear()
    {
        return year;
    }

    public int getMonth()
    {
        return month;
    }

    public int getDay()
    {
        return day;
    }

}
