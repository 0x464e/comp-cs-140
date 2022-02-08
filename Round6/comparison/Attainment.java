import java.util.Comparator;

public record Attainment(String courseCode, String studentNumber, int grade) implements Comparable<Attainment>
{
    public static final Comparator<? super Attainment> CODE_STUDENT_CMP = Comparator.comparing((Attainment a) -> a.courseCode).thenComparing(a -> a.studentNumber);
    public static final Comparator<? super Attainment> CODE_GRADE_CMP = (a, b) -> {
        var cmp = a.courseCode.compareTo(b.courseCode);
        if (cmp == 0)
            cmp = b.grade - a.grade;
        return cmp;
    };

    public String getCourseCode()
    {
        return courseCode;
    }

    public String getStudentNumber()
    {
        return studentNumber;
    }

    public int getGrade()
    {
        return grade;
    }

    @Override
    public String toString()
    {
        return String.format("%s %s %s%n", courseCode, studentNumber, grade);
    }

    @Override
    public int compareTo(Attainment o)
    {
        var cmp = studentNumber.compareTo(o.getStudentNumber());
        if (cmp == 0)
            cmp = courseCode.compareTo(o.getCourseCode());
        return cmp;
    }
}
