import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Dates
{
    private static final Pattern iso8601Date = Pattern.compile("(?<year>\\d{4})-(?<month>\\d{2})-(?<day>\\d{2})");
    private static final Pattern finnishDate = Pattern.compile("(?<day>\\d{1,2})\\.(?<month>\\d{1,2})\\.(?<year>\\d{4})");

    private Dates()
    {
        throw new IllegalStateException("Utility class");
    }

    public static class DateDiff
    {
        private final LocalDate start;
        private final LocalDate end;
        private final long diff;

        private DateDiff(LocalDate startDate, LocalDate endDate)
        {
            this.start = startDate;
            this.end = endDate;
            this.diff = startDate.until(endDate, ChronoUnit.DAYS);
        }

        @Override
        public String toString()
        {
            return String.format("%s --> %s: %d day%s", start.format(DateTimeFormatter.ofPattern("EEEE dd.MM.yyyy", Locale.ENGLISH)), end.format(DateTimeFormatter.ofPattern("EEEE dd.MM.yyyy", Locale.ENGLISH)), diff, diff == 1 ? "" : "s");
        }

        public String getStart()
        {
            return start.toString();
        }

        public String getEnd()
        {
            return end.toString();
        }

        public long getDiff()
        {
            return diff;
        }
    }

    public static DateDiff[] dateDiffs(String ...dateStrs)
    {
        if (dateStrs.length < 2)
            return new DateDiff[0];

        var dates = new ArrayList<LocalDate>();

        for (var dateStr : dateStrs)
        {
            var isoMatcher = iso8601Date.matcher(dateStr);
            var finnishMatcher = finnishDate.matcher(dateStr);
            var found = false;
            Matcher matcher = null;

            if (isoMatcher.find())
            {
                found = true;
                matcher = isoMatcher;
            }
            else if (finnishMatcher.find())
            {
                found = true;
                matcher = finnishMatcher;
            }


            if (!found)
            {
                System.out.format("The date \"%s\" is illegal!%n", dateStr);
                continue;
            }

            var day = Integer.parseInt(matcher.group("day"));
            var month = Integer.parseInt(matcher.group("month"));
            var year = Integer.parseInt(matcher.group("year"));

            try
            {
                dates.add(LocalDate.of(year, month, day));
            }
            catch (DateTimeException e)
            {
                System.out.format("The date %s is illegal!%n", dateStr);
            }
        }

        dates.sort(Comparator.naturalOrder());

        var dateDiffs = new DateDiff[dates.size() - 1];

        for (var i = 0; i < dates.size() - 1; i++)
            dateDiffs[i] = new DateDiff(dates.get(i), dates.get(i+1));

        return dateDiffs;
    }
}
