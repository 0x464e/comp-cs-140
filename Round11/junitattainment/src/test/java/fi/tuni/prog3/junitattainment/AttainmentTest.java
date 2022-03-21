package fi.tuni.prog3.junitattainment;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AttainmentTest
{
    @Test
    void testGetCourseCode()
    {
        var attainment = new Attainment("a", "b", 4);
        assertEquals("a", attainment.getCourseCode());
    }

    @Test
    void TestGetStudentNumber()
    {
        var attainment = new Attainment("a", "b", 4);
        assertEquals("b", attainment.getStudentNumber());
    }

    @Test
    void TestGetGrade()
    {
        var attainment = new Attainment("a", "b", 4);
        assertEquals(4, attainment.getGrade());
    }

    @Test
    void testToString()
    {
        var attainment = new Attainment("a", "b", 4);
        assertEquals("a b 4", attainment.toString());
    }

    @Test
    void whenExceptionThrown()
    {
        assertThrows(IllegalArgumentException.class, () -> new Attainment(null, null, 69));
    }


    @Test
    void testCompareTo()
    {
        var attainment1 = new Attainment("a", "b", 4);
        var attainment2 = new Attainment("b", "c", 4);

        var cmp = attainment1.compareTo(attainment2);
        assertTrue(cmp < 0);
    }
}