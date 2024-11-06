public class Position {

    private int x;
    private int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    Position add(Direction direction) {
        return new Position(this.x + direction.getDx(), this.y + direction.getDy());
    }

    public boolean equals(Position position) {
        return this.x == position.getX() && this.y == position.getY();
    }

    public String toString() {
        return "(" + this.x + "," + this.y + ")";
    }

    Position sub(Direction direction) {
        return new Position(this.x - direction.getDx(), this.y - direction.getDy());
    }

}
