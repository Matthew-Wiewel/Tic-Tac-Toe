import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Random;

enum Difficulty { EASY, MEDIUM, HARD, EXPERT };

//a class to handle the AI's logic
class AILogic
{
    //TODO, this will get removed. The calculations will form the basis for difficulty later
    //package private static variables to give AI's skill levels meaningful names
    static int skillLevelEasy = 1; //used to denote when the AI is set to Easy
    static int skillLevelMedium; //medium skill level for AI
    static int skillLevelHard; //hard skill level for AI
    static int skillLevelExpert; //expert skill level for AI


    private int maxDepth; //keeps track of how many moves to consider
    private int player;
    private int opponent;
    private Random random;

    //constructor
    AILogic()
    {
        random = new Random();
        skillLevelEasy = 1;
    }
    //getters and setters for AI's skill level and which player it is
    void setSkillLevel(int skillLevel)
    { maxDepth = skillLevel; }
    void setSkillLevel(Difficulty chosenDifficulty)
    {
        switch(chosenDifficulty)
        {
            case EASY:
            {
                maxDepth = 1; //easy only looks 1 move ahead
                break;
            }
            case MEDIUM:
            {
                maxDepth = G.N - G.N/2; //medium will look half of N moves ahead
                break;
            }
            case HARD:
            {
                maxDepth = G.N; //hard looks N ahead
                break;
            }
            case EXPERT:
            {
                maxDepth = G.N * G.N - 1; //expert considers all move until end of game
                break;
            }
        }
    }
    void setPlayer(int isPlayer)
    {
        player = isPlayer; //set player to given player

        //set opponent to the opposite
        if(player == G.O)
            opponent = G.X;
        else
            opponent = G.O;
    }
    int getSkillLevel()
    { return maxDepth; }
    int getPlayer()
    { return player; }
    int getOpponent()
    { return opponent; }

    //method to allow run-time setting of the skill levels in case I want to allow 4x4 or 5x5 boards
    static void setSkillLevels()
    {
        skillLevelMedium = G.N;
        skillLevelHard = 2*G.N;
        skillLevelExpert = G.N * G.N - 1;
    }


    //method called in GUI to find the AI's move
    Coordinate findMove(TTTBoard gameBoard)
    {
        ArrayList<Pair<Coordinate,Integer>> moveOptions = new ArrayList<>();
        for(Coordinate c : gameBoard.getOpenSpaces())
        {
            //make move for AI to start the search and find its value
            TTTBoard tempBoard = new TTTBoard(gameBoard);
            int tempStateValue = tempBoard.setAndCheckWin(player, c.getX(), c.getY());
            int valueOfState = min(tempBoard, tempStateValue, 1);

            //create pair based on this coordinate and value and add it to our list
            Pair<Coordinate, Integer> tempPair = new Pair<>(c, valueOfState);
            moveOptions.add(tempPair);
        }

        //now find the max valued move
        Coordinate moveChoice = null;
        int bestValue = Integer.MIN_VALUE;
        for(Pair<Coordinate, Integer> p : moveOptions)
        {
            //if we find a better move than what we've had before, change our choice
            if(p.getValue() > bestValue)
            {
                bestValue = p.getValue();
                moveChoice = p.getKey();
            }
        }

        return moveChoice;
    }

    private int max(TTTBoard gameBoard, int stateStatus, int depthTraversed)
    {
        //if we are in a terminal state, return its value
        if(isTerminalState(stateStatus, depthTraversed))
            return getUtility(stateStatus);

        int maxValue = Integer.MIN_VALUE; //declare value initialized to min at first

        //go through all the possible moves of the gameBoard
        for(Coordinate c : gameBoard.getOpenSpaces())
        {
            //make a possible move for AI
            TTTBoard tempBoard = new TTTBoard(gameBoard);
            int tempStateValue = tempBoard.setAndCheckWin(player, c.getX(), c.getY());
            //store value of this possible move into a temporary value
            int temp = min(tempBoard, tempStateValue, depthTraversed + 1);
            if(temp > maxValue) //if we've found a better state, choose that value
                maxValue = temp;
        }

        return maxValue;
    }

    private int min(TTTBoard gameBoard, int stateStatus, int depthTraversed)
    {
        //if we are in a terminal state, return its value
        if(isTerminalState(stateStatus, depthTraversed))
            return getUtility(stateStatus);

        int minValue = Integer.MAX_VALUE; //declare value initialized to max at first

        //go through all the possible moves of the gameBoard
        for(Coordinate c : gameBoard.getOpenSpaces())
        {
            //make a possible move for opponent
            TTTBoard tempBoard = new TTTBoard(gameBoard);
            int tempStateValue = tempBoard.setAndCheckWin(player == G.X ? G.O : G.X, c.getX(), c.getY());
            //store value of this possible move into a temporary value
            int temp = max(tempBoard, tempStateValue, depthTraversed + 1);
            if(temp < minValue) //if we've found a better state, choose that value
                minValue = temp;
        }

        return minValue;
    }

    //method to test if we have reached a terminal state, either because a result has been reached or the depth traversed
    //is at the max we're allowing
    private boolean isTerminalState(int stateStatus, int depthTraversed)
    {
        return depthTraversed >= maxDepth || stateStatus == G.X || stateStatus == G.O || stateStatus == G.DRAW;
    }

    //method that looks at the status of a state and returns an integer value for it
    //wins for the AI are high values, losses are low values, draws and continuing states
    //are medium values. A random number is also added to each value to help randomize the
    //resulting move chosen between equal values so that the AI doesn't always make the same move
    private int getUtility(int stateStatus)
    {
        if(stateStatus == G.X && player == G.X || stateStatus == G.O && player == G.O) //AI wins
            return 1000 + random.nextInt(100); //base value of 1000 plus a random number from 0-99
        else if(stateStatus == G.DRAW ) //draw case
            return 200 + random.nextInt(100);
        else if(stateStatus == G.BLANK) //game continues case
            return random.nextInt(100);
        else //AI loses
            return -1000 + random.nextInt(100);
    }

}
