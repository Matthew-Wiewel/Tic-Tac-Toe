import javafx.animation.PauseTransition;
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
import javafx.util.Duration;

public class TicTacToeGame extends Application
{
    private Stage primaryStage;
    private int boardSize;
    private int numWon;
    private int numDrawn;
    private int numLost;
    private Difficulty chosenDifficulty;
    private boolean hasGameInProgress;
    private AILogic ai;
    private TTTBoard gameBoard;
    private int player;

    //Image constants
    final private Image blankImage = new Image("blank.png");
    final private Image xImage = new Image("X.png");
    final private Image oImage = new Image("O.png");
    final private Image xTransition = new Image("halfX.png");
    final private Image oTransition = new Image("halfO.png");

    //title constants
    final private String homeTitle = "Tic-Tac-Toe Menu";
    final private String playTitle = "Play Tic-Tac-Toe";
    final private String emptyTextField = "             ";

    //pause constant
    final private int pauseTime = 3;

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
    private Menu playAsOptions;
    private MenuItem playX;
    private MenuItem playO;
    private HBox bottomOfHomePane; //used to hold the two VBoxes below
    private VBox selectionAndPlayHolder; //used to hold displays of current selections and play button
    private TextField currentPlayerDisplay;
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
    private Button cancelExitGame; //used to not quit mid-game
    private Button confirmExitGame; //used to save user from misclicks mid-game

    private String getDifficulty()
    {
        switch(chosenDifficulty)
        {
            case EASY:
                return " on easy mode.";
            case MEDIUM:
                return " on medium mode.";
            case HARD:
                return " on hard mode.";
            case EXPERT:
                return " on expert mode.";
            default:
                return null;
        }
    }

    //method to enable or disable all the squares of the board
    private void setBoardDisable(boolean enablement)
    {
        for(int i = 0; i < G.N; i++)
            for(int j = 0; j < G.N; j++)
                boardImages[i][j].setDisable(enablement);
    }

    private void remakeBoard()
    {
        if(boardSize == G.N) //case where we can reuse the memory, just reset the images
        {
            for(int i = 0; i < G.N; i++)
                for (int j = 0; j < G.N; j++)
                    boardImages[i][j].setImage(blankImage);

            setBoardDisable(false);
        }
        else //need to create a new board
        {
            G.N = boardSize; //update board size
            boardImages = new ImageView[G.N][G.N];

            for(int i = 0; i < G.N; i++)
            {
                for(int j = 0; j < G.N; j++)
                {
                    boardImages[i][j] = new ImageView(blankImage);
                    //boardImages[i][j].setImage(blankImage);

                    int row = i; //variables used so that they can be in lambda
                    int column = j;
                    boardImages[i][j].setOnMouseClicked(e->{

                        //make the move visually
                        boardImages[row][column].setImage(player == G.X ? xTransition : oTransition);
                        PauseTransition p = new PauseTransition(Duration.seconds(pauseTime));
                        p.play();
                        boardImages[row][column].setImage(player == G.X ? xImage : oImage);
                        boardImages[row][column].setDisable(true);
                        //TODO, make it not disabled visually though it's disabled in truth


                        //make the move in TTTBoard member
                        int currentResult = gameBoard.setAndCheckWin(player, row, column); //TODO, modify this method
                        if(currentResult == player) //win was found
                        {
                            numWon++;
                            hasGameInProgress = false;
                            whoWonDisplay.setText("Congratulations! You win!");
                            priorGames.getItems().add("You won a game on a " + G.N + "x" + G.N + " board as " + G.toString(player) + getDifficulty());
                            winDrawLossDisplay.setText("Won: " + numWon + "\tDrawn: " + numDrawn + "\tLost: " + numLost);
                            setBoardDisable(true); //with the game being over, disable the board
                        }
                        else if(currentResult == G.DRAW) //drawn game
                        {
                            numDrawn++;
                            hasGameInProgress = false;
                            whoWonDisplay.setText("The field is a draw!");
                            priorGames.getItems().add("You drew on game on a " + G.N + "x" + G.N + " board as " + G.toString(player) + getDifficulty());
                            winDrawLossDisplay.setText("Won: " + numWon + "\tDrawn: " + numDrawn + "\tLost: " + numLost);
                            setBoardDisable(true);
                        }
                        else //game is still going on
                        {
                            doAIMove();
                        }
                    });

                    //add ImageView to gridPane
                    board.add(boardImages[i][j], i, j);
                }
            }
        }

        //also make sure we start with a blank slate for info
        mostRecentMoveDisplay.setText(emptyTextField);
        whoWonDisplay.setText(emptyTextField);
        cancelExitGame.setVisible(false);
        confirmExitGame.setVisible(false);

        //and we'll have a game in progress
        hasGameInProgress = true;

        //and create a TTTBoard class for this game
        gameBoard = new TTTBoard();
    }

    private void doAIMove()
    {
        Coordinate aiMove = ai.findMove(gameBoard);

        //visually make move
        PauseTransition p = new PauseTransition(Duration.seconds(pauseTime));
        boardImages[aiMove.getX()][aiMove.getY()].setImage(ai.getPlayer() == G.X ? xTransition : oTransition);
        p.play();
        boardImages[aiMove.getX()][aiMove.getY()].setImage(ai.getPlayer() == G.X ? xImage : oImage);
        boardImages[aiMove.getX()][aiMove.getY()].setDisable(true); //ai claimed this square, so disable it

        //make move on board
        int aiResult = gameBoard.setAndCheckWin(ai.getPlayer(), aiMove.getX(), aiMove.getY());

        if(aiResult == ai.getPlayer()) //AI has won
        {
            numLost++;
            hasGameInProgress = false;
            whoWonDisplay.setText("Sorry. You lost to a computer.");
            priorGames.getItems().add("You lost a game on a " + G.N  + "x" + G.N + " board as " + G.toString(player) + getDifficulty());
            winDrawLossDisplay.setText("Won: " + numWon + "\tDrawn: " + numDrawn + "\tLost: " + numLost);
            setBoardDisable(true); //and with a game over, disable the board
        }
        else if(aiResult == G.DRAW) //draw result
        {
            numDrawn++;
            hasGameInProgress = false;
            whoWonDisplay.setText("The field is a draw!");
            priorGames.getItems().add("You drew a game on a " + G.N  + "x" + G.N + " board as " + G.toString(player) + getDifficulty());
            winDrawLossDisplay.setText("Won: " + numWon + "\tDrawn: " + numDrawn + "\tLost: " + numLost);
            setBoardDisable(true);
        }
    }

    private void createHomeScene()
    {
        //initialize menu bar for home scene
        topMenu = new MenuBar();

        options = new Menu("File"); //options menu
        freshStart = new MenuItem("Fresh Start");
        quit = new MenuItem("Quit");
        freshStart.setOnAction(e->{
            //clear everything related to prior games
            winDrawLossDisplay.setText(emptyTextField);
            priorGames.getItems().clear();
            numWon = 0;
            numLost = 0;
            numDrawn = 0;
        });
        quit.setOnAction(e->System.exit(0)); //quit buttons exits game
        options.getItems().addAll(freshStart, quit);

        boardSizeOptions = new Menu("Board Size"); //menu for board sizes
        size3x3 = new MenuItem("Standard (3x3)");
        size4x4 = new MenuItem("Large (4x4)");
        size5x5 = new MenuItem("Huge (5x5)");
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

        playAsOptions = new Menu("Choose Side"); //menu to choose being X or O
        playX = new MenuItem("Player X");
        playO = new MenuItem("Player O");
        playX.setOnAction(e->{
            player = G.X;
            ai.setPlayer(G.O);
            currentPlayerDisplay.setText("You are Player X");
        });
        playO.setOnAction(e->{
            player = G.O;
            ai.setPlayer(G.X);
            currentPlayerDisplay.setText("You are Player O");
        });
        playAsOptions.getItems().addAll(playX, playO);

        topMenu.getMenus().addAll(options, boardSizeOptions, difficultyOptions, playAsOptions);




        //create left VBox with current settings and play button

        //text fields for displaying size and difficulty, disabled and style to not be greyed out
        currentPlayerDisplay = new TextField("You are Player X");
        currentDifficultyDisplay = new TextField("Easy");
        currentBoardSizeDisplay = new TextField("3x3 Board");
        currentPlayerDisplay.setDisable(true);
        currentDifficultyDisplay.setDisable(true);
        currentBoardSizeDisplay.setDisable(true);
        currentDifficultyDisplay.setStyle("-fx-opacity: 1.0");
        currentBoardSizeDisplay.setStyle("-fx-opacity: 1.0");
        currentPlayerDisplay.setStyle("-fx-opacity: 1.0");

        playButton = new Button("Play A Game!"); //button for playing a game
        playButton.setOnAction(e->{
            remakeBoard(); //set up board
            ai.setSkillLevel(chosenDifficulty); //set up difficulty
            ai.setPlayer(player == G.X ? G.O : G.X); //set AI to opposite of human
            primaryStage.setTitle(playTitle); //and change over to playing scene
            primaryStage.setScene(playScene);

            //and if AI is X, have them do first move
            if(ai.getPlayer() == G.X)
                doAIMove();
        });

        selectionAndPlayHolder = new VBox(20, currentPlayerDisplay, currentDifficultyDisplay, currentBoardSizeDisplay, playButton);

        priorGames = new ListView<>(); //create variables to display results of previous games and current stats
        winDrawLossDisplay = new TextField(emptyTextField);
        infoHolder = new VBox(20, priorGames, winDrawLossDisplay);

        //combine the two VBoxes into an HBox
        bottomOfHomePane = new HBox(40, selectionAndPlayHolder, infoHolder);

        homeSceneBox = new VBox(50, topMenu, bottomOfHomePane);
        homeScene = new Scene(homeSceneBox, 600, 600);
    }

    private void createPlayScene()
    {
        //text field to show location of most recent move
        mostRecentMoveDisplay = new TextField(emptyTextField);
        mostRecentMoveDisplay.setDisable(true);
        mostRecentMoveDisplay.setStyle("-fx-opaque: 1.0");

        //the board is created upon pressing the play button, not here, hence the minimal code
        board = new GridPane();

        playBox = new BorderPane();

        backToHome = new Button("Return to Main Menu");
        whoWonDisplay = new TextField(emptyTextField);
        cancelExitGame = new Button("No, Keep Playing");
        confirmExitGame = new Button("Yes, Leave Game");
        backToHome.setOnAction(e->{
            if(hasGameInProgress)
            {
                //unhide confirmation buttons and get user input
                cancelExitGame.setVisible(true);
                confirmExitGame.setVisible(true);
            }
            else //no game in progress, just return to home screen
            {
                primaryStage.setTitle(homeTitle);
                primaryStage.setScene(homeScene);
            }
        });
        confirmExitGame.setOnAction(e->{
            primaryStage.setTitle(homeTitle);
            primaryStage.setScene(homeScene);
        });
        cancelExitGame.setOnAction(e->{
            //hide these buttons since we don't want to exit the game
            confirmExitGame.setVisible(false);
            cancelExitGame.setVisible(false);
        });

        //put the text field and button into an HBox
        bottomInfo = new HBox(20, backToHome, confirmExitGame, cancelExitGame, whoWonDisplay);

        //set the border pane
        playBox.setTop(mostRecentMoveDisplay);
        playBox.setCenter(board);
        playBox.setBottom(bottomInfo);

        playScene = new Scene(playBox, 1000, 1000);

    }

    private void createScenes()
    {
        createHomeScene();
        createPlayScene();
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
        G.N = 2; //initially an invalid size so that the first press of play does make a board and set G.N to 3 as proper
        boardSize = 3;
        chosenDifficulty = Difficulty.EASY;
        numDrawn = 0;
        numLost = 0;
        numWon = 0;
        hasGameInProgress = false;
        ai = new AILogic();
        player = G.X;
        this.primaryStage = primaryStage; //set equal so we can reference the primary stage outside of start4

        createScenes();
        primaryStage.setTitle(homeTitle);
        primaryStage.setScene(homeScene);
        primaryStage.show();
    }

}
