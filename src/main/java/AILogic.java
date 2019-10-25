import java.util.ArrayList;
import java.util.Random;

//a class to handle the AI's logic
class AILogic
{
    static int skillLevelEasy = 1; //used to denote when the AI is set to Easy
    static int skillLevelMedium; //medium skill level for AI
    static int skillLevelHard; //hard skill level for AI
    static int skillLevelExpert; //expert skill level for AI
    private static int maxDepth; //keeps track of how many moves to consider
    private static int player;
    private static Random random = new Random();

    //getters and setters for AI's skill level and which player it is
    static void setSkillLevel(int skillLevel)
    { maxDepth = skillLevel; }
    static void setPlayer(int isPlayer)
    { player = isPlayer; }
    static int getSkillLevel()
    { return maxDepth; }
    static int getPlayer()
    { return player; }

    //method to allow run-time setting of the skill levels in case I want to allow 4x4 or 5x5 boards
    static void setSkillLevels()
    {
        skillLevelMedium = G.N;
        skillLevelHard = 2*G.N;
        skillLevelExpert = G.N * G.N - 1;
    }


    //method called in GUI to find the AI's move
    static TTTBoard.Coordinate findMove(TTTBoard gameBoard)
    {
        ArrayList<TTTBoard.Coordinate> moveOptions =  findMoveHelper(gameBoard, 0);
        return moveOptions.get(random.nextInt(moveOptions.size())); //return a random Coordinate that the AI thinks is okay to make
    }

    //method to find a list of moves the AI may make
    private static ArrayList<TTTBoard.Coordinate> findMoveHelper(TTTBoard gameBoard, int depthTraversed)
    {
       // if(depthTraversed > maxDepth)
            //gonna return TODO

        //TODO
        return null;
    }
}
