package isep.ricochetrobot;
import static isep.ricochetrobot.Color.*;

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

    public String getUrl() {
        return this.url;
    }

    public int getPosX() {
        return posX;
    }
    public int getPosY() {
        return posY;
    }
    public void setPosX(int posX) {
        this.posX = posX;
    }
    public void setPosY(int posY) {
        this.posY = posY;
    }
    public Color getColor() {
        return color;
    }
}
