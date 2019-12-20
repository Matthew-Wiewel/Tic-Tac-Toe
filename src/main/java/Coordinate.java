//nested Coordinate class
class Coordinate //tested
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
