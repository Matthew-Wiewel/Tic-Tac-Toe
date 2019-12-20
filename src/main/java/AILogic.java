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
                maxDepth = G.N; //medium will look N moves ahead
                break;
            }
            case HARD:
            {
                maxDepth = 2*G.N; //hard looks 2N ahead
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
        ArrayList<Coordinate> moveOptions =  findMoveHelperMax(gameBoard, 0);
        return moveOptions.get(random.nextInt(moveOptions.size())); //return a random Coordinate that the AI thinks is okay to make
    }

    //method to find a list of moves the AI may make, this is the max function of minimax
    private ArrayList<Coordinate> findMoveHelperMax(TTTBoard gameBoard, int depthTraversed)
    {
       if(depthTraversed > maxDepth) //if we're deeper than the depth we want to traverse down, any open moves are fair game
           return gameBoard.getOpenSpaces();

       //TODO holder
        return null;
    }

    private Coordinate max(TTTBoard gameBoard, int stateStatus, int depthTraversed)
    {
        //TODO
        return null;
    }

    private Coordinate min(TTTBoard gameBoard, int stateStatus, int depthTraversed)
    {
        //TODO
        return null;
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
