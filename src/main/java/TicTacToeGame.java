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
    private Difficulty chosenDifficulty;

    //Image constants
    final private Image blankImage = new Image("blank.png");
    final private Image xImage = new Image("X.png");
    final private Image oImage = new Image("O.png");
    final private Image xTransition = new Image("halfX.png");
    final private Image oTransition = new Image("halfO.png");

    private Scene homeScene; //scene for selecting difficulty, playing, and seeing history
    private VBox homeSceneBox;
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
    private HBox bottomOfHomePane; //used to hold the two VBoxes below
    private VBox selectionAndPlayHolder; //used to hold displays of current selections and play button
    private TextField currentBoardSizeDisplay;
    private TextField currentDifficultyDisplay;
    private Button playButton;
    private VBox infoHolder;
    private ListView<String> priorGames;
    private TextField winDrawLossDisplay;

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

    private void createHomeScene()
    {
        //initialize menu bar for home scene
        topMenu = new MenuBar();

        options = new Menu("File"); //options menu
        freshStart = new MenuItem("Fresh Start");
        quit = new MenuItem("Quit");
        freshStart.setOnAction(e->{} /*TODO, clear prior results*/);
        quit.setOnAction(e->System.exit(0)); //quit buttons exits game
        options.getItems().addAll(freshStart, quit);

        boardSizeOptions = new Menu("Board Size"); //menu for board sizes
        size3x3 = new MenuItem("Standard (3x3)");
        size4x4 = new MenuItem("Large (4x4)");
        size5x5 = new MenuItem("Huge (5x5)");
        /*TODO, when the play button is picked, that'll compare this to N to see if board needs recreation*/;
        size3x3.setOnAction(e->{
            boardSize = 3;
            currentBoardSizeDisplay.setText("3x3 Board");
        });
        size4x4.setOnAction(e->{
            boardSize = 4;
            currentBoardSizeDisplay.setText("4x4 Board");
        });
        size5x5.setOnAction(e->{
            boardSize = 5;
            currentBoardSizeDisplay.setText("5x5 Board");
        });
        boardSizeOptions.getItems().addAll(size3x3, size4x4, size5x5);

        difficultyOptions = new Menu("Difficulty"); //menu for difficulty level
        //TODO, we'll calculate difficulty when play is pressed
        selectEasy = new MenuItem("Easy");
        selectMedium = new MenuItem("Medium");
        selectHard = new MenuItem("Hard");
        selectExpert = new MenuItem("Expert");
        selectEasy.setOnAction(e->{
            chosenDifficulty = Difficulty.EASY;
            currentDifficultyDisplay.setText("Easy");
        }); //on action, corresponding difficulty is chosen
        selectMedium.setOnAction(e->{
            chosenDifficulty = Difficulty.MEDIUM;
            currentDifficultyDisplay.setText("Medium");
        });
        selectHard.setOnAction(e->{
            chosenDifficulty = Difficulty.HARD;
            currentDifficultyDisplay.setText("Hard");
        });
        selectExpert.setOnAction(e->{
            chosenDifficulty = Difficulty.EXPERT;
            currentDifficultyDisplay.setText("Expert");
        });
        difficultyOptions.getItems().addAll(selectEasy, selectMedium, selectHard, selectExpert);

        topMenu.getMenus().addAll(options, boardSizeOptions, difficultyOptions);



        
        //create left VBox with current settings and play button

        //text fields for displaying size and difficulty, disabled and style to not be greyed out
        currentDifficultyDisplay = new TextField("Easy");
        currentBoardSizeDisplay = new TextField("3x3 Board");
        currentDifficultyDisplay.setDisable(true);
        currentBoardSizeDisplay.setDisable(true);
        currentDifficultyDisplay.setStyle("-fx-opacity: 1.0");
        currentBoardSizeDisplay.setStyle("-fx-opacity: 1.0");

        playButton = new Button("Play A Game!"); //button for playing a game
        playButton.setOnAction(e->{} /*TODO, start a game*/);

        selectionAndPlayHolder = new VBox(20, currentDifficultyDisplay, currentBoardSizeDisplay, playButton);

        priorGames = new ListView<>(); //create variables to display results of previous games and current stats
        winDrawLossDisplay = new TextField("          ");
        infoHolder = new VBox(20, priorGames, winDrawLossDisplay);

        //combine the two VBoxes into an HBox
        bottomOfHomePane = new HBox(40, selectionAndPlayHolder, infoHolder);






    }

    private void createPlayScene()
    {

    }

    private void createScenes()
    {
        createHomeScene();
        createPlayScene();

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
        //initial values made
        boardSize = 3;
        chosenDifficulty = Difficulty.EASY;
        numDrawn = 0;
        numLost = 0;
        numWon = 0;

        this.primaryStage = primaryStage; //set equal so we can reference the primary stage outside of start
        createScenes();
        //TODO: add GUI
    }

}
