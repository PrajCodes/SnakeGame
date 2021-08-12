public class Position {

    private int x;
    private int y;

    public Position(){

    }

    public Position(int xCoordinate, int yCoordinate){
        x = xCoordinate;
        y = yCoordinate;
    }

    public int getX() {
        return x;
    }

    public void setX(int a) {
       x = a;
    }

    public int getY() {
        return y;
    }

    public void setY(int b) {
        y = b;
    }

    public boolean equals(Position b){
        if(this.getX() == b.getX() && this.getY() == b.getY()){
            return true;
        }
        return false;
    }

}
