package isep.ricochetrobot;

public enum Symbol {

    NONE(null),
    YELLOWMOON("yellowMoon.png"),
    BLUESUN("blueSun.png"),
    GREENSTAR("greenStar.png"),
    REDPLANET("redPlanet.png");

    private final String url;

    Symbol(String url) {
        this.url = url;
    }

    public String getUrl() {
        return this.url;
    }
}
