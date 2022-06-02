package isep.ricochetrobot;
import static isep.ricochetrobot.Color.*;

/**
 * Enumeration des symboles qui composent les plateaux
 */
public enum Symbol {

    NONE(null, null),
    YELLOWMOON("yellowMoon.png", YELLOW),
    BLUESUN("blueSun.png", BLUE),
    GREENSTAR("greenStar.png", GREEN),
    REDPLANET("redPlanet.png", RED),
    BLUEMOON("blueMoon.png", BLUE),
    REDSTAR("redStar.png", RED),
    YELLOWSUN("yellowSun.png", YELLOW),
    GREENPLANET("greenPlanet.png", GREEN),
    GREENSUN("greenSun.png", GREEN),
    YELLOWSTAR("YellowStar.png", YELLOW),
    REDMOON("redMoon.png", RED),
    BLUEPLANET("bluePlanet.png",BLUE),
    YELLOWPLANET("yellowPlanet.png", YELLOW),
    GREENMOON("greenMoon.png", GREEN),
    BLUESTAR("blueStar.png", BLUE),
    REDSUN("redSun.png", RED);

    private final String url;
    private final Color color;
    private int posX;
    private int posY;

    Symbol(String url , Color color) {
        this.url = url;
        this.color = color;
    }

    /**
     * Recupere l'url de l'image du symbole
     * @return l'url du symbole
     */
    public String getUrl() {
        return this.url;
    }

    /**
     * Recupere la position x du symbole
     * @return la position x du symbole
     */
    public int getPosX() {
        return posX;
    }
    /**
     * Recupere la position y du symbole
     * @return la position y du symbole
     */
    public int getPosY() {
        return posY;
    }
    /**
     * Modifie la position x du symbole
     * @param posX la position x du symbole
     */
    public void setPosX(int posX) {
        this.posX = posX;
    }
    /**
     * Modifie la position x du symbole
     * @param posY la position y du symbole
     */
    public void setPosY(int posY) {
        this.posY = posY;
    }
    /**
     * Recupere la couleur du symbole
     * @return la couleur du symbole
     */
    public Color getColor() {
        return color;
    }
}
