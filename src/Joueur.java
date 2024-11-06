import java.util.ArrayList;

public class Joueur extends Mobile {

    public Joueur(Configuration config, Position position) {
        super(Types.JOUEUR, config, position);
    }

    ArrayList<Direction> histo = new ArrayList<Direction>();

    public ArrayList<Direction> getHisto() {
        return this.histo;
    }

    public void removeLast() {
        if (this.histo.size() > 0) {
            this.histo.remove(this.histo.size() - 1);
        }

    }

    public void setHisto(ArrayList<Direction> histo) {
        this.histo = histo;
    }

    public int getNbCoups() {
        return this.histo.size();
    }

}
