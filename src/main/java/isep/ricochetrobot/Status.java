package isep.ricochetrobot;

public enum Status{
    CHOOSE_PLAYER("Cliquez sur le bouton [Jouer]"),
    CHOOSE_ROBOT("Cliquez sur le robot à déplacer"),
    CHOOSE_TILE("Cliquez sur la case destination"),
    END_TIMER("");
    Status(String toolTip) { this.toolTip = toolTip; }
    private final String toolTip;
    public String getToolTip() { return this.toolTip; }
}