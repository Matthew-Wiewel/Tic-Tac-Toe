//used for storing global constants, G for Global
class G
{
    static int N = 3; //determines boards size
    static final int BLANK = 0; //denotes blank squares on board or neither player for win purposes
    static final int X = 1; //denotes squares with X or a win for player X
    static final int O = -1; //denotes squares with an O or a win for player O (note zero is 0 in this font)
    static final int DRAW = 2;

    static String toString(int num)
    {
        switch(num)
        {
            case BLANK:
                return "Blank";
            case X:
                return "X";
            case O:
                return "O";
            case DRAW:
                return "Draw";
            default:
                return null;
        }
    }
}
