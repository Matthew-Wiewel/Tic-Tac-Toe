import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class TicTacToeGame extends Application
{
    private Stage primaryStage;
    private int boardSize;
    private int numWon;
    private int numDrawn;
    private int numLost;

    //Image constants
    final private Image blankImage = new Image("blank.png");
    final private Image xImage = new Image("X.png");
    final private Image oImage = new Image("O.png");
    final private Image xTransition = new Image("halfX.png");
    final private Image oTransition = new Image("halfO.png");

    private Scene homeScene; //scene for selecting difficulty, playing, and seeing history
    private HBox homeSceneBox;
    private MenuBar topMenu; //used for menu to go across top of the screen
    private Menu options; //menu to hold various options for quitting or clearing history
    private MenuItem freshStart;
    private MenuItem quit;
    private Menu boardSizeOptions; //menu to select board size
    private MenuItem size3x3;
    private MenuItem size4x4;
    private MenuItem size5x5;
    private Menu difficultyOptions; //menu to select difficulty
    private MenuItem selectEasy;
    private MenuItem selectMedium;
    private MenuItem selectHard;
    private MenuItem selectExpert;
    private VBox selectionAndPlayHolder; //used to hold displays of current selections and play button
    private TextField currentBoardSizeDisplay;
    private TextField currentDifficultyDisplay;
    private Button playButton;

    private Scene playScene; //used for playing the game of Tic Tac Toe
    private BorderPane playBox; //used to hold the GUI in this scene
    private TextField mostRecentMoveDisplay;
    private GridPane board; //will hold the squares of the tic tac toe board
    private ImageView[][] boardImages; //will have clickable images for making moves
    private HBox bottomInfo; //will hold nodes at bottom of border pane
    private Button backToHome;
    private TextField whoWonDisplay;
    private Button exitGame; //used to quit game in progress
    private Button confirmExitGame; //used to save user from misclicks mid-game

    private void remakeBoard()
    {
        boardImages = new ImageView[G.N][G.N];

    }

    private void createScenes()
    {
        //TODO, make home scene

        //TODO, make play scene
        remakeBoard(); //will initially be for the 3x3
    }

    //main function to allow mvn exec:main and mvn compile to launch program with the given plugins
    public static void main(String[] args)
    {
        //TODO: uncomment this line once you're ready to add the GUI.
        //launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        this.primaryStage = primaryStage; //set equal so we can reference the primary stage outside of start
        //TODO: add GUI
    }

}
