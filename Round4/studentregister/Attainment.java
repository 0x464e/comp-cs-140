public record Attainment(String courseCode, String studentNumber, int grade)
{
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
}
