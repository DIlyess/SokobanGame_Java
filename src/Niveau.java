import java.util.ArrayList;

public class Niveau {

    private int x;
    private int y;
    public Immobile[][] grille;
    public ArrayList<Position> cibles;

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Niveau(int x, int y) {
        this.x = x;
        this.y = y;
        this.grille = new Immobile[x][y];
        this.cibles = new ArrayList<Position>();
    }

    public boolean addMur(Position position) {
        if ((getElement(position) instanceof Mur)) {
            return false;
        } else {
            this.grille[position.getX()][position.getY()] = new Mur();
            return true;
        }
    }

    public boolean estMur(Position position) {
        if (getElement(position) instanceof Mur) {
            return true;
        }
        return false;
    }

    public boolean estVide(Position position) {
        if (!(getElement(position) instanceof Mur)) {
            return true;
        }
        return false;
    }

    public boolean addCible(Position position) {
        if (this.cibles.contains(position) || estMur(position)) {
            return false;
        } else {
            this.cibles.add(position);
            return true;
        }
    }

    public boolean estCible(Position position) {
        for (Position cible : this.cibles) {
            if (cible.equals(position)) {
                return true;
            }
        }
        return false;
    }

    public Immobile getElement(Position position) {
        if (this.grille[position.getX()][position.getY()] != null) {
            return this.grille[position.getX()][position.getY()];
        } else {
            return new Case();
        }
    }

    public void afficher() {
        for (int i = 0; i < this.x; i++) {
            for (int j = 0; j < this.y; j++) {
                System.out.print(this.grille[i][j].toString());
            }
            System.out.println();
        }
    }

}
