import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

//class to test Board methods based on the standard 3x3 tic-tac-toe board
class TestTTTBoard
{
    private TTTBoard test;

    @BeforeEach
    void init()
    {

        test = new TTTBoard();
    }

    //test getDiagonal
    /*
    @Test
    void testGetDiagonal()
    {
        //test to ensure the correctness of getDiagonal with the standard 3x3 tic-tac-toe board
        //you will have to changed the access of getDiagonal from private to package or above to test

        int[] expectedValues = new int[]{1,0,2,0,3,0,2,0,1}; //expected values from getDiagonal
        int exVal = 0; //used to traverse the array

        for(int i = 0; i < G.N; i++)
        {
            for(int j = 0; j < G.N; j++)
            {
                assertEquals(expectedValues[exVal], TTTBoard.getDiagonal(i,j)); //test
                exVal++; //go to next expected value
            }
        }
    }
     */

    //test getDiagonal with a 4x4, note that enabling this test will cause those based on a 3x3 to fail
    //to do this test, do both making getDiagonal package access and change N in G to be 4
    /*
    @Test
    void testGetDiagonal4By4()
    {
        int[] expectedValues = {1, 0, 0, 2, 0, 1, 2, 0, 0, 2, 1, 0, 2, 0, 0, 1}; //set the expected values
        int exVal = 0; //start them at index 0
        for (int i = 0; i < G.N; i++)
        {
            for (int j = 0; j < G.N; j++)
                assertEquals(expectedValues[exVal++], TTTBoard.getDiagonal(i, j), "Issue at " + i + "," + j);
        }
    }
     */

    @Test
    void testTTTBoardDefaultConstructor()
    {
        int[][] goalBoard = {{0,0,0},{0,0,0},{0,0,0}};
        assertEquals("TTTBoard", test.getClass().getName(), "TTTBoard objects are not known as TTTBoard");
        assertEquals(G.N * G.N, test.getOpenSpaces().size(),
                "Default constructed board doesn't have as many open spaces as there are squares");
        assertEquals("java.util.ArrayList", test.getOpenSpaces().getClass().getName(), "Does not have an ArrayList");
        assertTrue(Arrays.deepEquals(goalBoard, test.getBoard()));
    }

    @Test
    void testTTTBoardCopyConstructor()
    {
        //create it with a board that will ensure other doesn't have default values
        test.setBoard(new int[][]{ {1,0,0} , {-1,-1,0} , {1,-1,1}});

        TTTBoard other = new TTTBoard(test);
        assertTrue(Arrays.deepEquals(other.getBoard(), test.getBoard()), "Copy constructor doesn't create identical board");
        assertEquals(other.getOpenSpaces(), test.getOpenSpaces(), "Copy Constructor doesn't create identical openSpaces");

        assertNotSame(other.getBoard(), test.getBoard(), "Copy Constructor creates shallow copies of board.");
        assertNotSame(other.getOpenSpaces(), test.getOpenSpaces(), "Copy constructor creates shallows copies of openSpaces");
    }

    @Test
    void testGetOpenSpaces()
    {
        ArrayList<Coordinate> other = test.getOpenSpaces();

        assertSame(test.getOpenSpaces(), other, "getOpenSpaces does not return reference to member var");
        assertEquals(other, test.getOpenSpaces(), "Values from openSpaces not accurate when gotten");
    }

    @Test
    void testGetOpenSpacesDeepCopy()
    {
        ArrayList<Coordinate> other = test.getOpenSpacesDeepCopy();
        assertNotSame(other, test.getOpenSpaces(), "deep copy not returned");
        assertEquals(other, test.getOpenSpaces(), "deep copy not accurate with its values");
    }

    @Test
    void testSetBoardAndGet()
    {
        //sets the board and then gets each position
        //while these values should not be used in a real use case, having unique values assures correctness
        int[][] newBoard = new int[][]{{1,2,3},{4,5,6},{7,8,9}};
        test.setBoard(newBoard);
        for(int i = 0; i < G.N; i++)
        {
            for(int j = 0; j < G.N; j++)
                assertEquals(newBoard[i][j], test.get(i,j), "Incorrect value with " + i + "," + j);
        }
    }

    @Test
    void testSetBoardOpenSpaces()
    {
        test.setBoard(new int[][]{ {1,0,0} , {-1,-1,0} , {1,-1,1}}); //set the board

        //create an ArrayList of what we're expecting
        ArrayList<Coordinate> compareTo = new ArrayList<Coordinate>(Arrays.asList(
                new Coordinate(0,1), new Coordinate(0,2), new Coordinate(1,2)));

        assertEquals(compareTo, test.getOpenSpaces(), "setBoard not reflecting openSpaces correctly");
    }

    @Test
    void testResetSquareValues()
    {
        //give the board a bunch of random values
        int[][] newBoard = new int[][]{{1,2,3},{4,5,6},{7,8,9}};
        test.setBoard(newBoard);

        //reset board
        test.reset();

        //go through the board
        for(int i = 0; i < G.N; i++)
        {
            for(int j = 0; j < G.N; j++)
                assertEquals(G.BLANK, test.get(i,j), "Incorrect value at " + i + "," + j);
        }
    }

    @Test
    void testResetOpenSpaces()
    {
        //mess up the board then reset
        test.setBoard(new int[][]{{1,2,3},{4,5,6},{6,7,8}});
        test.reset();

        //see the openSpaces has the right length
        assertEquals(G.N*G.N, test.getOpenSpaces().size(), "Reset does not bring openSpaces to right size.");

        //see the openSpaces has the right values
        ArrayList<Coordinate> compareTo = new ArrayList<Coordinate>();
        for(int i = 0; i < G.N; i++)
            for(int j = 0; j < G.N; j++)
                compareTo.add(new Coordinate(i,j));

        assertEquals(compareTo, test.getOpenSpaces(), "Reset does not give openSpaces the right coordinates");
    }

    @Test
    void testSetAndCheckWinValues()
    {
        //test to ensure that setAndCheckWin is putting the right value into the right coordinate
        //does not check about wins here
        int[][] newBoard = new int[][]{{1,2,3},{4,5,6},{7,8,9}};
        for(int i = 0; i < G.N; i++)
        {
            for(int j = 0; j < G.N; j++)
                test.setAndCheckWin(newBoard[i][j], i, j);
        }

        //and now verify
        for(int i = 0; i < G.N; i++)
        {
            for(int j = 0; j < G.N; j++)
                assertEquals(newBoard[i][j], test.get(i,j), "Incorrect value at " + i + "," + j);
        }
    }

    @Test
    void testSetAndCheckWinValuesModifiesOpenSpaces()
    {
        //tests that when adding elements via setAndCheckWin, we remove those coordinates from the
        int openSpacesLengthBefore = 0;
        ArrayList<Coordinate> expectedList = new ArrayList<Coordinate>();
        for(int i = 0; i < G.N; i++)
            for(int j = 0; j < G.N; j++)
                expectedList.add(new Coordinate(i,j));

        for(int i = 0; i < G.N; i++)
        {
            for(int j = 0; j < G.N; j++)
            {
                openSpacesLengthBefore = test.getOpenSpaces().size();
                test.setAndCheckWin(1, i, j);
                expectedList.remove(expectedList.get(0)); //remove from of list as it is same value openSpaces should be removing
                assertEquals(openSpacesLengthBefore-1, test.getOpenSpaces().size(),
                        "setting doesn't remove elements from openSpaces");
                assertEquals(expectedList, test.getOpenSpaces(), "ArrayList is not removing the correct coordinates");
            }
        }
    }

    @Test
    void testSetAndCheckWinPlayerXWinsAndTestFindWin()
    {
        //a test to see that when an X is played to cause a win for Player X, player X wins
        //win with center, win with diagonal, with with other diagonal, win for row, win for column
        int[][] xWinPos1 = new int[][]{{G.X,G.BLANK,G.O},{G.BLANK,G.X,G.O},{G.BLANK, G.BLANK,G.BLANK}}; //place X, 2,2
        int[][] xWinPos2 = new int[][]{{G.O,G.BLANK,G.BLANK},{G.BLANK,G.X,G.O},{G.X,G.BLANK,G.BLANK}}; //place X 0,2
        int[][] xWinPos3 = new int[][]{{G.X,G.O,G.X},{G.O,G.BLANK,G.O},{G.X,G.O,G.X}}; //place X 1,1
        int[][] xWinPos4 = new int[][]{{G.X,G.O,G.O},{G.BLANK,G.BLANK,G.BLANK},{G.X,G.BLANK,G.BLANK}}; //place X 1,0
        int[][] xWinPos5 = new int[][]{{G.O,G.O,G.BLANK},{G.O,G.X,G.BLANK},{G.X,G.BLANK,G.X}}; //place X 2,1

        //set ArrayLists with the expected winning tiles of each position
        ArrayList<Coordinate> xWinTiles1 = new ArrayList<Coordinate>(Arrays.asList(
                new Coordinate(0,0), new Coordinate(1,1), new Coordinate(2,2)));
        ArrayList<Coordinate> xWinTiles2 = new ArrayList<Coordinate>(Arrays.asList(
                new Coordinate(0,2), new Coordinate(1,1), new Coordinate(2,0)));
        ArrayList<Coordinate> xWinTiles3 = new ArrayList<Coordinate>(Arrays.asList(
                new Coordinate(0,0), new Coordinate(1,1), new Coordinate(2,2)));
        ArrayList<Coordinate> xWinTiles4 = new ArrayList<Coordinate>(Arrays.asList(
                new Coordinate(0,0), new Coordinate(1,0), new Coordinate(2,0)));
        ArrayList<Coordinate> xWinTiles5 = new ArrayList<Coordinate>(Arrays.asList(
                new Coordinate(2,0), new Coordinate(2,1), new Coordinate(2,2)));


        //test win and tiles on top-left to bottom-right diagonal
        test.setBoard(xWinPos1);
        assertEquals(G.X, test.setAndCheckWin(G.X, 2,2),"Issue in xWinPos1");
        assertEquals(xWinTiles1, test.getWinningTiles(2,2), "Winning tiles are incorrect with xWinTiles1");

        //test win on bottom-left to top-right diagonal
        test.setBoard(xWinPos2);
        assertEquals(G.X, test.setAndCheckWin(G.X, 0,2),"Issue in xWinPos2");
        assertEquals(xWinTiles2, test.getWinningTiles(0,2), "Winning tiles are incorrect with xWinTiles2");

        //test win with center and both diagonals
        test.setBoard(xWinPos3);
        assertEquals(G.X, test.setAndCheckWin(G.X, 1,1),"Issue in xWinPos3");
        assertEquals(xWinTiles3, test.getWinningTiles(1,1), "Winning tiles are incorrect with xWinTiles3");

        //test win with a column
        test.setBoard(xWinPos4);
        assertEquals(G.X, test.setAndCheckWin(G.X, 1,0),"Issue in xWinPos4");
        assertEquals(xWinTiles4, test.getWinningTiles(1,0), "Winning tiles are incorrect with xWinTiles4");

        //test win with a row
        test.setBoard(xWinPos5);
        assertEquals(G.X, test.setAndCheckWin(G.X, 2,1),"Issue in xWinPos5");
        assertEquals(xWinTiles5, test.getWinningTiles(2,1), "Winning tiles are incorrect with xWinTiles5");
    }

    @Test
    void testSetAndCheckWinPlayerOWinsAndTestFindWin()
    {
        //do tests for player O winning
        int[][] oWinPos1 = new int[][]{{G.O,G.X,G.BLANK},{G.X,G.BLANK,G.X},{G.BLANK,G.BLANK,G.O}}; //add O at 1,1
        int[][] oWinPos2 = new int[][]{{G.X,G.BLANK,G.BLANK},{G.O,G.O,G.X},{G.O,G.X,G.BLANK}}; //add O at 0,2
        int[][] oWinPos3 = new int[][]{{G.O,G.X,G.O},{G.X,G.BLANK,G.X},{G.O,G.X,G.O}}; //add O 1,1
        int[][] oWinPos4 = new int[][]{{G.X,G.BLANK,G.X},{G.O,G.O,G.BLANK},{G.X,G.BLANK,G.BLANK}}; //add O 1,2
        int[][] oWinPos5 = new int[][]{{G.X,G.X,G.BLANK},{G.X,G.BLANK,G.O},{G.BLANK,G.BLANK,G.O}}; //add O 0,2

        //set ArrayLists with the expected winning tiles of each position
        ArrayList<Coordinate> oWinTiles1 = new ArrayList<Coordinate>(Arrays.asList(
                new Coordinate(0,0), new Coordinate(1,1), new Coordinate(2,2)));
        ArrayList<Coordinate> oWinTiles2 = new ArrayList<Coordinate>(Arrays.asList(
                new Coordinate(0,2), new Coordinate(1,1), new Coordinate(2,0)));
        ArrayList<Coordinate> oWinTiles3 = new ArrayList<Coordinate>(Arrays.asList(
                new Coordinate(0,0), new Coordinate(1,1), new Coordinate(2,2)));
        ArrayList<Coordinate> oWinTiles4 = new ArrayList<Coordinate>(Arrays.asList(
                new Coordinate(1,0), new Coordinate(1,1), new Coordinate(1,2)));
        ArrayList<Coordinate> oWinTiles5 = new ArrayList<Coordinate>(Arrays.asList(
                new Coordinate(0,2), new Coordinate(1,2), new Coordinate(2,2)));


        //test top-left to bottom-right diagonal win
        test.setBoard(oWinPos1);
        assertEquals(G.O, test.setAndCheckWin(G.O, 1,1), "Issue with oWinPos1");
        assertEquals(oWinTiles1, test.getWinningTiles(1,1), "Winning tiles incorrect for oWinTiles1");

        //tet bottom-left to top-rigth diagonal win
        test.setBoard(oWinPos2);
        assertEquals(G.O, test.setAndCheckWin(G.O, 0,2), "Issue with oWinPos2");
        assertEquals(oWinTiles2, test.getWinningTiles(0,2), "Winning tiles incorrect for oWinTiles2");

        //test center win with both diagonals
        test.setBoard(oWinPos3);
        assertEquals(G.O, test.setAndCheckWin(G.O, 1,1), "Issue with oWinPos3");
        assertEquals(oWinTiles3, test.getWinningTiles(1,1), "Winning tiles incorrect for oWinTiles3");

        //test row win
        test.setBoard(oWinPos4);
        assertEquals(G.O, test.setAndCheckWin(G.O, 1,2), "Issue with oWinPos4");
        assertEquals(oWinTiles4, test.getWinningTiles(1,2), "Winning tiles incorrect for wWinTiles4");

        //test column win
        test.setBoard(oWinPos5);
        assertEquals(G.O, test.setAndCheckWin(G.O, 0,2), "Issue with oWinPos5");
        assertEquals(oWinTiles5, test.getWinningTiles(0,2), "Winning tiles incorrect for oWinTiles5");
    }

    @Test
    void testSetAndCheckWinSeesGameContinue()
    {
        //create positions that should be game continues as registered
        int[][] contPos1 = new int[][]{{G.X, G.X, G.BLANK},{G.O, G.O, G.BLANK},{G.X, G.O, G.BLANK}}; //add X at 2,2
        int[][] contPos2 = new int[][]{{G.X, G.O, G.X},{G.O, G.X, G.O}, {G.BLANK, G.BLANK, G.BLANK}}; //add O at 2,1
        int[][] contPos3 = new int[][]{{G.O, G.X, G.O},{G.O, G.X, G.BLANK},{G.X, G.O, G.BLANK}}; //add X at 2,2
        int[][] contPos4 = new int[][]{{G.X, G.O, G.BLANK},{G.BLANK, G.BLANK, G.BLANK},{G.BLANK, G.BLANK, G.BLANK}}; //add X to 1,1

        //first, check when adding 1 to an empty board
        for(int i = 0; i < G.N; i++)
        {
            for(int j = 0; j < G.N; j++)
            {
                //add and check it's a draw result
                assertEquals(G.BLANK, test.setAndCheckWin(G.X, i, j), "Issue at " + i + "," + j + " in loop");
                test.reset(); //then reset board
            }
        }

        //now do the tests with larger positions, no need for reset as we do setBoard calls
        test.setBoard(contPos1);
        assertEquals(G.BLANK, test.setAndCheckWin(G.X, 2, 2), "Failure with contPos1");

        test.setBoard(contPos2);
        assertEquals(G.BLANK, test.setAndCheckWin(G.O, 2, 1), "Failure with contPos2");

        test.setBoard(contPos3);
        assertEquals(G.BLANK, test.setAndCheckWin(G.X, 2, 2), "Failure with contPos3");

        test.setBoard(contPos4);
        assertEquals(G.BLANK, test.setAndCheckWin(G.X, 1, 1), "Failure with contPos4");
    }

    @Test
    void testCheckWinSeesDraw()
    {
        int[][] drawPos1 = new int[][]{{G.O, G.O, G.X},{G.X, G.X, G.O},{G.O, G.BLANK, G.X}}; //add X at 2,1
        int[][] drawPos2 = new int[][]{{G.X, G.O, G.X},{G.X, G.O, G.X},{G.O, G.X, G.BLANK}}; //add O at 2,2
        int[][] drawPos3 = new int[][]{{G.O, G.X, G.O},{G.O, G.X, G.O},{G.X, G.O, G.BLANK}}; //add X at 2,2
        int[][] drawPos4 = new int[][]{{G.X, G.X, G.O},{G.O, G.O, G.X},{G.X, G.BLANK, G.O}}; //add O at 2,1

        test.setBoard(drawPos1);
        assertEquals(G.DRAW, test.setAndCheckWin(G.X, 2, 1), "Failure with drawPos1");

        test.setBoard(drawPos2);
        assertEquals(G.DRAW, test.setAndCheckWin(G.O, 2, 2), "Failure with drawPos2");

        test.setBoard(drawPos3);
        assertEquals(G.DRAW, test.setAndCheckWin(G.X, 2, 2), "Failure with drawPos3");

        test.setBoard(drawPos4);
        assertEquals(G.DRAW, test.setAndCheckWin(G.O, 2, 1), "Failure with drawPos4");
    }

    @Test
    void testGetBoard()
    {
        //test to get the board of a TTTBoard object
        int[][] newBoard = new int[][]{{1,2,3},{4,5,6},{7,8,9}};
        test.setBoard(newBoard);

        //assure getBoard returns reference
        assertTrue(test.getBoard() == test.getBoard(), "Reference to own board not equal");

        //assure getBoard return reference
        TTTBoard other = new TTTBoard(test);
        assertFalse(other.getBoard() == test.getBoard(), "Different TTTBoards' references to their boards should not be equal");

        //test getBoard's reference to board shares with a calling storing int[][]
        int[][] refToBoard = test.getBoard();
        assertTrue(refToBoard == test.getBoard(), "Reference isn't as expected");

        //test that values changed in the reference outside test are reflected in the test's board
        refToBoard[2][2] = 999;
        assertEquals(refToBoard[2][2], test.get(2,2), "Not as expected.");
    }

    @Test
    void testGetBoardDeepCopy()
    {
        test.setBoard(new int[][]{{1,2,3},{4,5,6},{7,8,9}}); //set a board
        int[][] copy = test.getBoardDeepCopy(); //get a deep copy

        //test that we do not return a reference
        assertFalse(copy == test.getBoard(), "Deep copy returns a reference, not a deep copy");

        //test that the deep copy is accurate
        assertTrue(Arrays.deepEquals(copy,test.getBoard()), "Deep copy does not return an accurate deep copy");
    }

    @Test
    void testEquals()
    {
        assertTrue(test.equals(test), "Self-equality not working");

        TTTBoard other = new TTTBoard(test);
        assertTrue(test.equals(other), "Equality with equal object not working");
        assertTrue(other.equals(test), "Equality not commutative");

        other.setAndCheckWin(999,2,2); //make other non-equal
        assertFalse(test.equals(other), "Inequality with non-equal object not working");
        assertFalse(other.equals(test), "Commutativity of inequality not working");

        Integer garbage = 5;
        assertFalse(test.equals(garbage), "Inequality with non-TTTBoard not working");
    }

    @Test
    void testEquals2()
    {
        //test to see two are the same even if their minMaxValues are different

        TTTBoard other = new TTTBoard(test);
        other.setMinMaxValue(500);
        test.setMinMaxValue(400);

        assertTrue(other.equals(test), "other does not equal test");
        assertTrue(test.equals(other), "test does not equal other");
    }
    
    @Test
    void testHashCode()
    {
        int[][] board = new int[][]{{G.X, G.O, G.X},{G.BLANK, G.BLANK, G.X},{G.BLANK, G.O, G.BLANK}};
        test.setBoard(board);

        assertEquals(Arrays.deepHashCode(board), test.hashCode(), "TTTBoard hash code differs from its board's hash code");
    }
}
