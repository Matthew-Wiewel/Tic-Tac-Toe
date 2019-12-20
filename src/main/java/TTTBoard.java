import java.util.ArrayList;
import java.util.Arrays;

//used to hold the board of the tic tac toe game, TTTBoard for Tic-Tac-Toe Board
class TTTBoard
{
    private int[][] board; //holds the tic-tac-toe board
    private ArrayList<Coordinate> openSpaces;

    //method to get which diagonal a coordinate is on, must take away private to test in TestBoard and uncomment the test
    private static int getDiagonal(int row, int col) //tested
    {
        if((row == col) && (row + col == G.N - 1)) //center tile
            return 3; //denotes both diagonals
        else if(row == col) //must be on top-left to bottom-left
            return 1; //denotes top-left to bottom-right diagonal
        else if(row + col == G.N - 1) //must be on bottom-left to top-right
            return 2; //denotes bottom-left to top-right diagonal
        else //not on a diagonal
            return 0; //denotes no diagonal
    }

    //method to take a player and a start row/col and see if there's a win for the player
    private int win(int player, int row, int col) //tested via testing setAndCheckWin's ability to see a win
    {
        //quick test to see if enough marks have even been placed on the board for there to be a win
        //based on G.N moves by player 1 and G.N-1 moves by player 2 with all of player 1's being in a line
        if(openSpaces.size() > (G.N*G.N - (2*G.N - 1)))
            return G.BLANK; //indicates no win

        int numSameFound = 0; //used to see how marks of the player's we've found in a search direction

        //check squares in the same row as the mark to see if we have a win
        for(int i = 0; i < G.N; i++)
        {
            if(board[row][i] == player)
                numSameFound++; //for each square we find of the player's, increase this count
            else
                break; //if we find a square not belonging to the player, they cannot have a win in this direction
        }

        //check for a win
        if(numSameFound == G.N) //if we've found N marks, we have a win
            return player;
        else //no win found, reset numSameFound
            numSameFound = 0;

        //check squares in the same colum as the mark to see if we have a win
        for(int i = 0; i < G.N; i++)
        {
            if(board[i][col] == player)
                numSameFound++;
            else
                break;
        }

        //check for win
        if(numSameFound == G.N)
            return player;
        else
            numSameFound = 0;

        /*get a value for if we're on a diagonal and if so, which diagonal
        Return values are:
            0: not on a diagonal
            1: on top-left to bottom-right diagonal
            2: on bottom-left to top-right diagonal
            3: Center of the board. On both diagonals.
         */
        int diagonal = getDiagonal(row, col);

        if(diagonal == 1 || diagonal == 3) //square is on the top-left to bottom-right diagonal
        {
            //traverse board from top=left to bottom-right.
            for(int i = 0; i < G.N; i++)
            {
                if(board[i][i] == player)
                    numSameFound++;
                else
                    break;
            }

            //check for win
            if(numSameFound == G.N)
                return player;
            else
                numSameFound = 0;
        }
        if(diagonal == 2 || diagonal == 3) //squares is on bottom-left to top-right diagonal
        {
            int modifiedN = G.N - 1; //modified N to be compatible with array indices
            //traverse from top-right to bottom left
            for(int i = 0; i < G.N; i++)
            {
                if(board[i][modifiedN -i] == player)
                    numSameFound++;
                else
                    break;
            }

            //no comparison in this if as it's taken care of at the end
        }

        if(numSameFound == G.N) //found the tiles for a win
            return player;
        else if(openSpaces.isEmpty()) //no win, but also no more moves
            return G.DRAW;
        else //no win or draw found at all
            return G.BLANK;
    }

    //default constructor
    TTTBoard() //tested
    {
        board = new int[G.N][G.N]; //allocate board
        openSpaces = new ArrayList<Coordinate>();
        reset(); //initialize board to be fully blank and openSpaces to know all those spaces are blank
    }

    //copy constructor
    TTTBoard(TTTBoard other) //tested
    {
        board = other.getBoardDeepCopy(); //get the board
        openSpaces = other.getOpenSpacesDeepCopy(); //get a deep copy of the open spaces
    }

    //nested Coordinate class
    static class Coordinate //tested
    {
        private int x;
        private int y;

        //constructor that takes x and y
        Coordinate(int x, int y)
        {
            this.x = x;
            this.y = y;
        }
        //copy constructor
        Coordinate(Coordinate other)
        {
            this.x = other.getX();
            this.y = other.getY();
        }
        //getters and setters
        int getX()
        { return x; }
        int getY()
        { return y; }
        void setX(int x)
        { this.x = x; }
        void setY(int y)
        { this.y = y; }

        //overridden equals method
        @Override
        public boolean equals(Object obj)
        {
            if(this == obj) //same reference is true
                return true;

            if(obj.getClass().getName().equals("TTTBoard$Coordinate")) //check class
            {
                Coordinate other = (Coordinate)obj;
                return this.x == other.getX() && this.y == other.getY(); //compare coordinates
            }

            return false; //non-same classes cannot be equal
        }

    }

    //getters and setters
    //sets the board to a deep copy of the passed in array; boards should have same dimensions
    void setBoard(int[][] newBoard) //tested
    {
        openSpaces.clear(); //clear knowledge of current board
        for(int i = 0; i < G.N; i++)
        {
            for(int j = 0; j < G.N; j++)
            {
                board[i][j] = newBoard[i][j];
                if(board[i][j] == G.BLANK)
                    openSpaces.add(new Coordinate(i,j));
            }
        }
    }
    //returns a deep copy of the board
    int[][] getBoardDeepCopy() //tested
    {
        //create deep copy of the board
        int[][] retBoard = new int[G.N][G.N];
        for(int i = 0; i < G.N; i++)
            System.arraycopy(board[i], 0, retBoard[i], 0, G.N);

        return retBoard; //return the deep copy
    }
    //returns the reference to the TTTBoard's board without copying, int v is only there for overloading purposes
    int[][] getBoard() //tested
    { return board; }
    // method to get the value at a location in the board
    int get(int row, int col) //tested
    { return board[row][col]; }
    //getter for ArrayList of Coordinates
    ArrayList<Coordinate> getOpenSpaces() //tested
    { return openSpaces; }
    //getter for deep copy of openSpaces
    ArrayList<Coordinate> getOpenSpacesDeepCopy() //tested
    { return (ArrayList<Coordinate>) openSpaces.clone(); }

    //used to reset board
    void reset() //tested
    {
        openSpaces.clear(); //clear info
        //traverse board and set all squares to blank
        for(int i = 0; i < G.N; i++)
        {
            for(int j = 0; j < G.N; j++)
            {
                board[i][j] = G.BLANK;
                openSpaces.add(new Coordinate(i,j)); //add the open space
            }
        }
    }

    /*
    used to set the value of a square in the board and return if there's a win
    X and O returned to denote win of respective player, BLANK when game is ongoing
    NOTE: this should only be used for adding X's and O's during a game. It does not check to
    see if a square is blank again to re-add it to the openSpaces list as that would be extraneous
    for it's intended purpose of adding the X's and O's.
     */
    int setAndCheckWin(int val, int row, int col) throws NullPointerException //tested
    {
        openSpaces.remove(new Coordinate(row,col)); //no longer open, remove from the list of open spaces
        board[row][col] = val; //set the new value of the square
        return win(val, row, col); //return if that has caused a win
    }

    /*
    method to find the tiles used in a win, row and col should be the row and col you entered that setAndCheckWin
    returned a win for
    this exists as a separate method to avoid doing it over and over again every time we set a tile
    so even though it causes more work when a win happens than just creating the ArrayList at the same time
    we check for a win, it saves time overall, note that this method does not itself check for the win, that's your job
     */
    ArrayList<Coordinate> getWinningTiles(int row, int col)
    {
        //create array list to hold the coordinates
        ArrayList<Coordinate> winningTiles = new ArrayList<Coordinate>(); //create array list to hold coordinates
        int player = board[row][col]; //get which player whose win we're looking for

        //see if win was in row first
        for(int i = 0; i < G.N; i++)
        {
            if(board[row][i] == player)
                winningTiles.add(new Coordinate(row, i));
            else
                break;
        }

        //check if we got enough tiles for it be the win and return if so, otherwise clear the list and keep looking
        if(winningTiles.size() == G.N)
            return winningTiles;
        else
            winningTiles.clear();

        //see if win was the the column
        for(int i = 0; i < G.N; i++)
        {
            if(board[i][col] == player)
                winningTiles.add(new Coordinate(i, col));
            else
                break;
        }

        if(winningTiles.size() == G.N)
            return winningTiles;
        else
            winningTiles.clear();

        //get the diagonal
        int diagonal = getDiagonal(row, col);

        //if it's in the top-right to bototm-left diagonal, check there
        if(diagonal == 1 || diagonal == 3) //getDiagonal can return 1 or 3 when tile is on that diagonal
        {
            for (int i = 0; i < G.N; i++)
            {
                if (board[i][i] == player)
                    winningTiles.add(new Coordinate(i, i));
                else
                    break;
            }
        }

        //check if we found the win
        if(winningTiles.size() == G.N)
            return winningTiles;
        else
            winningTiles.clear();

        //if it's in the bottom-right to top-left diagonal, check there
        //no if check here because we will 100% find the win if we've gotten to this point without finding it
        int column = G.N - 1; //take off the extra 1 to fit G.N with array indices
        for(int i = 0; i < G.N; i++)
            winningTiles.add(new Coordinate(i,column--));

        return winningTiles;

    }

    @Override
    public boolean equals(Object obj) //tested
    {
        if(this == obj) //self-compare is true
            return true;

        if(obj.getClass().getName().equals("TTTBoard")) //if it's the same class, typecast it
        {
            TTTBoard other = (TTTBoard)obj; //cast and return equivalence via the boards, openSpaces should be the same
            return Arrays.deepEquals(board, other.getBoard());
        }
        else //different classes, can't compare
            return false;
    }




}