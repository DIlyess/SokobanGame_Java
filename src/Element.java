
public class Element {

    public Types type;

    public Element(Types type) {
        this.type = type;
    }

    public Types getType() {
        return type;
    }

    boolean bougerVers(Direction direction) {
        if (this instanceof Immobile) {
            return false;
        } else {
            Mobile mobile = (Mobile) this;
            Position position = mobile.getPosition();
            Position positionSuivante = position.add(direction);
            // Si la position suivante est vide, on bouge
            if (mobile.setPosition(positionSuivante)) {
                return true;
            }
            // Sinon, on regarde si la position suivante contient un mobile
            else if (mobile.configuration.get(positionSuivante) instanceof Mobile) {
                // Si c'est le cas, on bouge le mobile
                Mobile mobileSuivant = (Mobile) mobile.configuration.get(positionSuivante);
                if (mobileSuivant.bougerVers(direction)) {
                    // Si le mobile suivant a pu bouger, on bouge le mobile actuel
                    if (mobile.setPosition(positionSuivante)) {
                        return true;
                    }
                }
            }
            return false;
        }

    }

}
