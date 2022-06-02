package isep.ricochetrobot;

/**
 * Classe qui represente les deplacements d'un robot
 */
public class Deplacement{
    private final Color color;
    private final Direction direction;

    /**
     * Constructeur de Deplacement
     * @param color la couleur du robot deplace
     * @param direction la direction du deplacement
     */
    public Deplacement(Color color, Direction direction){
        this.color = color;
        this.direction = direction;
    }
    @Override
    public String toString(){
        return this.color + " " + this.direction ;
    }

    /**
     * Recupere la couleur du robot deplace
     * @return la couleur du robot deplace
     */
    public Color getColor() {
        return color;
    }
    /**
     * Recupere la direction du deplacement
     * @return la direction du deplacement
     */
    public Direction getDirection() {
        return direction;
    }
}