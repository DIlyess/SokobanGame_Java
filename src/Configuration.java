import java.util.ArrayList;

public class Configuration {

    private ArrayList<Caisse> caisses;
    private Position positionJoueur;
    private Niveau niveau;
    private Joueur joueur;

    public Configuration(Niveau niveau, Position positionJoueur) {
        this.positionJoueur = positionJoueur;
        this.niveau = niveau;
        this.caisses = new ArrayList<Caisse>();
        this.joueur = new Joueur(this, positionJoueur);
    }

    public Configuration(Configuration config) {
        this.niveau = config.getNiveau();
        this.positionJoueur = config.getJoueur().getPosition();
        this.caisses = new ArrayList<Caisse>();
        for (Caisse caisse : config.caisses) {
            this.caisses.add(new Caisse(this, caisse.getPosition()));
        }
        this.joueur = new Joueur(this, this.positionJoueur);
    }

    public int getX() {
        return this.niveau.getX();
    }

    public int getY() {
        return this.niveau.getY();
    }

    public Joueur getJoueur() {
        return this.joueur;
    }

    public Niveau getNiveau() {
        return this.niveau;
    }

    public boolean addCaisse(Position position) {
        for (Caisse caisse : this.caisses) {
            if (caisse.getPosition().equals(position)) {
                return false;
            }
        }
        this.caisses.add(new Caisse(this, position));
        return true;
    }

    public boolean estCible(Position position) {
        return this.niveau.estCible(position);
    }

    public boolean estVide(Position position) {
        for (Caisse caisse : this.caisses) {
            if (caisse.getPosition().equals(position)) {
                return false;
            }
        }
        return this.niveau.estVide(position);
    }

    public Element get(Position position) {
        if (this.positionJoueur.equals(position)) {
            return this.joueur;
        }
        for (Caisse caisse : this.caisses) {
            if (caisse.getPosition().equals(position)) {
                return caisse;
            }
        }
        return this.niveau.getElement(position);
    }

    public boolean bougerJoueurVers(Direction direction) {
        if (this.joueur.bougerVers(direction)) {
            this.positionJoueur = this.joueur.getPosition();
            this.joueur.getHisto().add(direction);
            return true;
        }
        return false;
    }

    public boolean victoire() {
        for (Caisse caisse : this.caisses) {
            if (!this.niveau.estCible(caisse.getPosition())) {
                return false;
            }
        }
        return true;
    }

    public String toString() {
        String s = "";
        for (int i = 0; i < this.niveau.getX(); i++) {
            for (int j = 0; j < this.niveau.getY(); j++) {
                Position position = new Position(i, j);
                if (this.niveau.estMur(position)) {
                    s += "#";
                } else if (this.niveau.estCible(position)) {
                    s += "$";
                } else if (estCible(position)) {
                    s += ".";
                } else if (this.positionJoueur.equals(position)) {
                    s += "@";
                } else {
                    s += " ";
                }
            }
        }
        return s;
    }
}
