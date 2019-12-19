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
                maxDepth = 1;
                break;
            }
            case MEDIUM:
            {
                maxDepth = G.N;
                break;
            }
            case HARD:
            {
                maxDepth = 2*G.N;
                break;
            }
            case EXPERT:
            {
                maxDepth = G.N * G.N - 1;
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
    TTTBoard.Coordinate findMove(TTTBoard gameBoard)
    {
        ArrayList<TTTBoard.Coordinate> moveOptions =  findMoveHelperMax(gameBoard, 0);
        return moveOptions.get(random.nextInt(moveOptions.size())); //return a random Coordinate that the AI thinks is okay to make
    }

    //method to find a list of moves the AI may make, this is the max function of minimax
    private ArrayList<TTTBoard.Coordinate> findMoveHelperMax(TTTBoard gameBoard, int depthTraversed)
    {
       if(depthTraversed > maxDepth) //if we're deeper than the depth we want to traverse down, any open moves are fair game
           return gameBoard.getOpenSpaces();

       //TODO holder
        return null;
    }

    private ArrayList<TTTBoard.Coordinate> findMoveHelperMin(TTTBoard gameBoard, int depthTraversed)
    {
        if(depthTraversed > maxDepth) //we've gone beyond the limits we're allowed to explore
            return gameBoard.getOpenSpaces(); //any legal move is therefore equal in the eyes of this AI

        //TODO holder
        return null;
    }
}
