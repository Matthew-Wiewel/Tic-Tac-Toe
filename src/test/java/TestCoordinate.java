import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class TestCoordinate
{
    private Coordinate test;

    @BeforeEach
    void init()
    {
        test = new Coordinate(1,3);
    }

    @Test
    void testClassName()
    {
        assertEquals("TTTBoard$Coordinate", test.getClass().getName(), "TTTBoard.Coordinate class name is not as expected");
    }

    @Test
    void testGetters()
    {
        //test to see getters return right value
        assertEquals(1, test.getX(), "Get X does not return proper x value but instead " + test.getX());
        assertEquals(3, test.getY(), "Get Y does not return proper y value but instead " + test.getY());

        //test to see getters do not return wrong value
        assertFalse(test.getX() != 1, "getX return a random value of " + test.getX());
        assertFalse(test.getY() != 3, "getY return a random value of " + test.getY());
    }

    @ParameterizedTest
    @ValueSource( ints = {0,1,2,3,4,5,6,7})
    void testSetters(int arg)
    {
        //test the setters with a variety of values
        test.setX(arg);
        test.setY(arg +1); //adding one here to ensure x and y are unique to avoid false positives

        assertEquals(arg, test.getX(), "x value not set properly. Is " + test.getX());
        assertEquals(arg +1, test.getY(), "y value not set properly. Is " + test.getY());
    }

    @Test
    void testEquals()
    {
        Coordinate theEquals = new Coordinate(1,3);
        Coordinate theNotEquals = new Coordinate(4,5);
        Integer integer = 0;

        //do tests of equality and inequality, making sure results are commutaive

        //equal TTTBoard.Coordinates
        assertTrue(test.equals(theEquals), "test.equals(theEquals) failed");
        assertTrue(theEquals.equals(test), "theEquals.equals(test) failed");

        //inequal TTTBoard.Coordinates
        assertFalse(test.equals(theNotEquals), "test.equals(theNotEquals) failed");
        assertFalse(theNotEquals.equals(test), "theNotEquals.equals(test) failed");

        //comparing a TTTBoard.Coordinate with a non-TTTBoard.Coordinate
        assertFalse(test.equals(integer), "Equality with a non-TTTBoard.Coordinate failed");
    }

    @Test
    void testCopyConstructor()
    {
        Coordinate other = new Coordinate(test); //created copy-constructed coordinate

        //now that we know the equals works, use it to determine we've truly copied the board
        assertEquals(other, test, "Copy constructed Coordinate not equal to what it copied");
    }
}
