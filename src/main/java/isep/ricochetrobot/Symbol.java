package isep.ricochetrobot;

public enum Symbol {

    NONE(null),
    YELLOWMOON("yellowMoon.png"),
    BLUESUN("blueSun.png"),
    GREENSTAR("greenStar.png"),
    REDPLANET("redPlanet.png"),
    BLUEMOON("blueMoon.png"),
    REDSTAR("redStar.png"),
    YELLOWSUN("yellowSun.png"),
    GREENPLANET("greenPlanet.png"),
    GREENSUN("greenSun.png"),
    YELLOWSTAR("YellowStar.png"),
    REDMOON("redMoon.png"),
    BLUEPLANET("bluePlanet.png"),
    YELLOWPLANET("yellowPlanet.png"),
    GREENMOON("greenMoon.png"),
    BLUESTAR("blueStar.png"),
    REDSUN("redSun.png");

    private final String url;

    Symbol(String url) {
        this.url = url;
    }

    public String getUrl() {
        return this.url;
    }
}
