package isep.ricochetrobot;

public enum Cell {
    NoWall(new boolean[]{false, false, false, false},null),

    WallUp(new boolean[]{true, false, false, false},"WU.png"),
    WallRight(new boolean[]{false, true, false, false},"WR.png"),
    WallDown(new boolean[]{false, false, true, false},"WD.png"),
    WallLeft(new boolean[]{false, false, false, true},"WL.png"),

    WallUpLeft(new boolean[]{false, false, false, true},"WUL.png"),
    WallUpRight(new boolean[]{false, false, false, true},"WUR.png"),
    WallDownLeft(new boolean[]{false, false, false, true},"WDL.png"),
    WallDownRight(new boolean[]{false, false, false, true},"WDR.png");

    private boolean[] block;
    private String url;

    private Cell(boolean[] block, String url){
        this.block = block;
        this.url = url;
    }


    public String getUrl() {
        return url;
    }
}
