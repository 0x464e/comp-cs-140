import javax.swing.plaf.ColorUIResource;
import java.util.*;

public class StudentRegister
{
    private final HashMap<String, Student> studends = new HashMap<>();
    private final HashMap<String, Course> courses = new HashMap<>();
    private final HashMap<String, ArrayList<Attainment>> attainments = new HashMap<>();

    public StudentRegister() { }

    public List<Student> getStudents()
    {
        return this.studends.values().stream().sorted(Comparator.comparing(Student::getName)).toList();
    }

    public void addStudent(Student student)
    {
        this.studends.put(student.getStudentNumber(), student);
    }

    public void addCourse(Course course)
    {
        this.courses.put(course.getCode(), course);
    }

    public List<Course> getCourses()
    {
        return this.courses.values().stream().sorted(Comparator.comparing(Course::getName)).toList();
    }

    public void addAttainment(Attainment attainment)
    {
        final var studentNumber = attainment.getStudentNumber();
        if (attainments.containsKey(studentNumber))
            attainments.get(studentNumber).add(attainment);
        else
            attainments.put(studentNumber, new ArrayList<>(List.of(attainment)));
    }

    public void printStudentAttainments(String studentNumber, String order)
    {
        if (!attainments.containsKey(studentNumber))
            System.out.println("Unknown student number: " + studentNumber);
        else
        {
            var student = studends.get(studentNumber);
            System.out.format("%s (%s):%n", student.getName(), student.getStudentNumber());
            var sortedAttainments = attainments.get(studentNumber).stream().sorted((a,b) ->
                                                                                           order.equals("by code") ?
                                                                                                   a.getCourseCode().compareTo(b.getCourseCode())
                                                                                                   :
                                                                                                   courses.get(a.getCourseCode()).getName().compareTo(courses.get(b.getCourseCode()).getName())).toList();
            sortedAttainments.forEach(x -> System.out.format("  %s %s: %d%n", x.getCourseCode(), courses.get(x.getCourseCode()).getName(), x.getGrade()));
        }
    }

    public void printStudentAttainments(String studentNumber)
    {
        if (!attainments.containsKey(studentNumber))
            System.out.println("Unknown student number: " + studentNumber);
        else
        {
            var student = studends.get(studentNumber);
            System.out.format("%s (%s):%n", student.getName(), student.getStudentNumber());
            attainments.get(studentNumber).forEach(x -> System.out.format("  %s %s: %d%n", x.getCourseCode(), courses.get(x.getCourseCode()).getName(), x.getGrade()));
        }
    }
}
