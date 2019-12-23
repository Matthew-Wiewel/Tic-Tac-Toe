import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

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
        assertEquals(2, ai.getSkillLevel(), "Medium is not G.N=" + G.N);

        ai.setSkillLevel(Difficulty.HARD);
        assertEquals(3, ai.getSkillLevel(), "Hard is not 2*G.N=" + 2*G.N);

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

    @Test
    void testExpert1()
    {
        //test to see that expert realizes that when the opponent takes a corner as the first move, the center is necessary
        ai.setPlayer(G.O);
        ai.setSkillLevel(Difficulty.EXPERT);
        int[][] gameBoard = new int[][]{
                {G.X, G.BLANK, G.BLANK},
                {G.BLANK, G.BLANK, G.BLANK},
                {G.BLANK, G.BLANK, G.BLANK}};
        board.setBoard(gameBoard);
        ArrayList<Coordinate> expectedResults = new ArrayList<Coordinate>(Arrays.asList(new Coordinate(1, 1)));

        Coordinate move = ai.findMove(board);

        assertTrue(expectedResults.contains(move), "Incorrect move of " + move.getX() + "," + move.getY() + " returned.");
    }

    @Test
    void testExpert2()
    {
        //a test to see ai uses same logic and gets same results as both X or O
        ai.setPlayer(G.X);
        ai.setSkillLevel(Difficulty.EXPERT);
        int[][] gameBoard = new int[][]{
                {G.O, G.BLANK, G.BLANK},
                {G.BLANK, G.BLANK, G.BLANK},
                {G.BLANK, G.BLANK, G.BLANK}};
        board.setBoard(gameBoard);
        ArrayList<Coordinate> expectedResults = new ArrayList<Coordinate>(Arrays.asList(new Coordinate(1, 1)));

        //do multiple tests to ensure correct result is always returned
        for(int i = 0; i < 15; i++)
        {
            Coordinate move = ai.findMove(board);
            assertTrue(expectedResults.contains(move), "Incorrect move of " + move.getX() + "," + move.getY() + " returned.");
        }
    }

    @Test
    void testExpert3()
    {
        ai.setPlayer(G.O);
        ai.setSkillLevel(Difficulty.EXPERT);
        int[][] gameBoard = new int[][]{
                {G.O, G.BLANK, G.BLANK},
                {G.BLANK, G.X, G.BLANK},
                {G.X, G.BLANK, G.O}};
        board.setBoard(gameBoard);
        ArrayList<Coordinate> expectedResults = new ArrayList<Coordinate>(Arrays.asList(new Coordinate(0, 2)));

        //run the test multiple times to ensure it continuously gives results within the valid range
        for (int i = 0; i < 20; i++)
        {
            Coordinate move = ai.findMove(board);
            assertTrue(expectedResults.contains(move), "Incorrect move of " + move.getX() + "," + move.getY() + " returned." +
                    "\nIssue in iteration " + i);
        }
    }

    @Test
    void testMedium1()
    {
        ai.setPlayer(G.O);
        ai.setSkillLevel(Difficulty.MEDIUM);
        int[][] gameBoard = new int[][]{
                {G.X, G.BLANK, G.BLANK},
                {G.BLANK, G.BLANK, G.BLANK},
                {G.BLANK, G.BLANK, G.BLANK}};
        board.setBoard(gameBoard);
        ArrayList<Coordinate> results = new ArrayList<>(); //list to hold what find move returns

        //run tests a bunch of times to ensure odds of each possible move showing up at least once
        for (int i = 0; i < 100; i++)
        {
            Coordinate move = ai.findMove(board);
            if(!results.contains(move))
                results.add(move);
        }

        //now test to see that some good and some bad choices are made
        assertTrue(results.contains(new Coordinate(0,2)), "Bad move: Coordinate 0,2 not contained");
        assertTrue(results.contains(new Coordinate(2,0)), "Bad move: 2,0 not contained");
        assertTrue(results.contains(new Coordinate(1,1)), "Good move: 1,1 not contained");
    }

    @Test
    void testMedium2()
    {
        ai.setPlayer(G.O);
        ai.setSkillLevel(Difficulty.MEDIUM);
        int[][] gameBoard = new int[][]{
                {G.X, G.BLANK, G.BLANK},
                {G.BLANK, G.O, G.BLANK},
                {G.BLANK, G.BLANK, G.X}};
        board.setBoard(gameBoard);
        ArrayList<Coordinate> results = new ArrayList<>(); //list to hold what find move returns

        //run tests a bunch of times to ensure odds of each possible move showing up at least once
        for (int i = 0; i < 100; i++)
        {
            Coordinate move = ai.findMove(board);
            if(!results.contains(move))
                results.add(move);
        }

        //now test to see that some good and some bad choices are made
        assertTrue(results.contains(new Coordinate(0,2)), "Bad move: Coordinate 0,2 not contained");
        assertTrue(results.contains(new Coordinate(2,0)), "Bad move: 2,0 not contained");
        assertTrue(results.contains(new Coordinate(1,0)), "Good move: 1,1 not contained");
    }

    @Test
    void testHard1()
    {
        ai.setPlayer(G.X);
        ai.setSkillLevel(Difficulty.HARD);
        int[][] gameBoard = new int[][]{
                {G.X, G.BLANK, G.BLANK},
                {G.BLANK, G.O, G.BLANK},
                {G.BLANK, G.BLANK, G.X}};
        board.setBoard(gameBoard);
        ArrayList<Coordinate> results = new ArrayList<>(); //list to hold what find move returns

        //run tests a bunch of times to ensure odds of each possible move showing up at least once
        for (int i = 0; i < 100; i++)
        {
            Coordinate move = ai.findMove(board);
            if(!results.contains(move))
                results.add(move);
        }

        //now test to see that some good and some bad choices are made
        assertTrue(results.contains(new Coordinate(0,2)), "Good move: Coordinate 0,2 not contained");
        assertTrue(results.contains(new Coordinate(2,0)), "Good move: 2,0 not contained");
        assertFalse(results.contains(new Coordinate(1,0)), "Bad move: 1,1 contained");
    }

    @Test
    void testEasy1()
    {
        ai.setPlayer(G.O);
        ai.setSkillLevel(Difficulty.EASY);
        int[][] gameBoard = new int[][]{
                {G.X, G.BLANK, G.BLANK},
                {G.BLANK, G.O, G.BLANK},
                {G.BLANK, G.BLANK, G.X}};
        board.setBoard(gameBoard);
        ArrayList<Coordinate> results = new ArrayList<>(); //list to hold what find move returns

        //run tests a bunch of times to ensure odds of each possible move showing up at least once
        for (int i = 0; i < 100; i++)
        {
            Coordinate move = ai.findMove(board);
            if(!results.contains(move))
                results.add(move);
        }

        //now test to see that every single option, whether it's good or bad in the long run, is seen
        assertTrue(results.contains(new Coordinate(0,1)), "0,1 not contained");
        assertTrue(results.contains(new Coordinate(0,2)), "0,2 not contained");
        assertTrue(results.contains(new Coordinate(1,0)), "1,0 not contained");
        assertTrue(results.contains(new Coordinate(1,2)), "1,2 not contained");
        assertTrue(results.contains(new Coordinate(2,0)), "2,0 not contained");
        assertTrue(results.contains(new Coordinate(2,1)), "2,1 not contained");
    }

    @Test
    void testEasy2()
    {
        //test that Easy sees immediate wins
        ai.setPlayer(G.O);
        ai.setSkillLevel(Difficulty.EASY);
        int[][] gameBoard = new int[][]{
                {G.X, G.BLANK, G.BLANK},
                {G.BLANK, G.O, G.X},
                {G.O, G.BLANK, G.X}};
        board.setBoard(gameBoard);

        for(int i = 0; i < 10; i++)
        {
            Coordinate move = ai.findMove(board);
            assertEquals(new Coordinate(0,2), move, "Issue in iteration " + i + "\n" +
                    "move found was " + move.getX() + "," + move.getY());
        }
    }
}
