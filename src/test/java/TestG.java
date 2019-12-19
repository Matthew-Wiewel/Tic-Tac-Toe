import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestG
{
    @Test
    private void testToString()
    {
        assertEquals("X", G.toString(G.X));

        assertEquals("O", G.toString(G.O));

        assertEquals("Draw", G.toString(G.DRAW));

        assertEquals("Blank", G.toString(G.BLANK));
    }
}
