public class Mobile extends Element {

    private Position position;

    public Mobile(Types type, Configuration config, Position position) {
        super(type);
        this.position = position;
        this.configuration = config;
    }

    // Référence vers la configuration du jeu afin de pouvoir interagir avec les
    // autres éléments
    public Configuration configuration;

    public void setConfiguration(Configuration configuration) {
        this.configuration = configuration;
    }

    boolean setPosition(Position position) {
        if (configuration.estVide(position)) {
            this.position = position;
            return true;
        } else {
            return false;
        }

    }

    Position getPosition() {
        return position;
    }

}