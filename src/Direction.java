import java.util.ArrayList;

public class Direction {

    public static Direction HAUT;
    public static Direction BAS;
    public static Direction GAUCHE;
    public static Direction DROITE;

    private int dx;
    private int dy;

    public Direction(int dx, int dy) {
        this.dx = dx;
        this.dy = dy;
    }

    public int getDx() {
        return this.dx;
    }

    public int getDy() {
        return this.dy;
    }

    static {
        HAUT = new Direction(0, -1);
        BAS = new Direction(0, 1);
        GAUCHE = new Direction(-1, 0);
        DROITE = new Direction(1, 0);
    }

    public ArrayList<Direction> getDirections() {
        ArrayList<Direction> directions = new ArrayList<Direction>();
        directions.add(Direction.HAUT);
        directions.add(Direction.BAS);
        directions.add(Direction.GAUCHE);
        directions.add(Direction.DROITE);
        return directions;
    }

}
