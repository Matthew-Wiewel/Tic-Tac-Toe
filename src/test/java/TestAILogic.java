import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class TestAILogic
{
    private TTTBoard board;

    @BeforeAll
    static void init()
    {
        AILogic.setSkillLevels();
    }

    @BeforeEach
    void initBoard()
    {
        board = new TTTBoard();
    }

    @Test
    void testSetSkillLevel()
    {
        AILogic.setSkillLevel(AILogic.skillLevelEasy);
        assertEquals(1, AILogic.getSkillLevel(), "Easy is not 1");

        AILogic.setSkillLevel(AILogic.skillLevelMedium);
        assertEquals(G.N, AILogic.getSkillLevel(), "Medium is not G.N=" + G.N);

        AILogic.setSkillLevel(AILogic.skillLevelHard);
        assertEquals(2*G.N, AILogic.getSkillLevel(), "Hard is not 2*G.N=" + 2*G.N);

        AILogic.setSkillLevel(AILogic.skillLevelExpert);
        assertEquals(G.N*G.N-1, AILogic.getSkillLevel(), "Expert is not G.N*G.N-1=" + G.N*G.N*-1);
    }

    @Test
    void testSetPlayer()
    {
        AILogic.setPlayer(G.X);
        assertEquals(G.X, AILogic.getPlayer(), "Setting AI to player X does not set AI to player X");

        AILogic.setPlayer(G.O);
        assertEquals(G.O, AILogic.getPlayer(), "Setting AI to player O does not set AI to player O");
    }

    /*
    Test of the search function will be done by creating boards and then asserting that an ArrayList of
    coordinates contains the returned coordinate. (Those coordinates that are acceptable will be determined by me
    looking at what options it should return given a particular board. These tests should be run in a for loop due to
    the randomness expected from the search to make sure we get all possible results.
     */
}
