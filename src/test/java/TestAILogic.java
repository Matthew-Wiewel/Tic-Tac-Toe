import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class TestAILogic
{
    private TTTBoard board;
    static private AILogic ai;

    @BeforeAll
    static void init()
    {

        AILogic.setSkillLevels();
        ai = new AILogic();
    }

    @BeforeEach
    void initBoard()
    {

        board = new TTTBoard();
    }

    @Test
    void testSetSkillLevel()
    {
        ai.setSkillLevel(AILogic.skillLevelEasy);
        assertEquals(1, ai.getSkillLevel(), "Easy is not 1");

        ai.setSkillLevel(AILogic.skillLevelMedium);
        assertEquals(G.N, ai.getSkillLevel(), "Medium is not G.N=" + G.N);

        ai.setSkillLevel(AILogic.skillLevelHard);
        assertEquals(2*G.N, ai.getSkillLevel(), "Hard is not 2*G.N=" + 2*G.N);

        ai.setSkillLevel(AILogic.skillLevelExpert);
        assertEquals(G.N*G.N-1, ai.getSkillLevel(), "Expert is not G.N*G.N-1=" + G.N*G.N*-1);
    }

    @Test
    void testSetSkillLevel2()
    {
        ai.setSkillLevel(Difficulty.EASY);
        assertEquals(1, ai.getSkillLevel(), "Easy is not 1");

        ai.setSkillLevel(Difficulty.MEDIUM);
        assertEquals(G.N, ai.getSkillLevel(), "Medium is not G.N=" + G.N);

        ai.setSkillLevel(Difficulty.HARD);
        assertEquals(2*G.N, ai.getSkillLevel(), "Hard is not 2*G.N=" + 2*G.N);

        ai.setSkillLevel(Difficulty.EXPERT);
        assertEquals(G.N*G.N-1, ai.getSkillLevel(), "Expert is not G.N*G.N-1=" + G.N*G.N*-1);
    }

    @Test
    void testSetPlayer()
    {
        ai.setPlayer(G.X);
        assertEquals(G.X, ai.getPlayer(), "Setting AI to player X does not set AI to player X");
        assertEquals(G.O, ai.getOpponent(), "Setting AI to X does not make opponent to O");

        ai.setPlayer(G.O);
        assertEquals(G.O, ai.getPlayer(), "Setting AI to player O does not set AI to player O");
        assertEquals(G.X, ai.getOpponent(), "Setting AI to O does not make opponent X");
    }

    /*
    Test of the search function will be done by creating boards and then asserting that an ArrayList of
    coordinates contains the returned coordinate. (Those coordinates that are acceptable will be determined by me
    looking at what options it should return given a particular board. These tests should be run in a for loop due to
    the randomness expected from the search to make sure we get all possible results.
     */
}
