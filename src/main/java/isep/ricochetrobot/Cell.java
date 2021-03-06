package isep.ricochetrobot;

import java.util.HashMap;
import java.util.Map;

import static isep.ricochetrobot.Cell.Direction.*;

public enum Cell {

    //                            UP     RIGHT  DOWN   LEFT
    NoWall(new boolean[]{         false, false, false, false  },null),

    WallUp(new boolean[]{         true,  false, false, false  }, "WU.png"),
    WallRight(new boolean[]{      false, true,  false, false  },"WR.png"),
    WallDown(new boolean[]{       false, false, true,  false  }, "WD.png"),
    WallLeft(new boolean[]{       false, false, false, true   }, "WL.png"),

    WallUpLeft(new boolean[]{     true, false,  false, true   }, "WUL.png"),
    WallUpRight(new boolean[]{    true, true,   false, false   }, "WUR.png"),
    WallDownLeft(new boolean[]{   false, false, true,  true   }, "WDL.png"),
    WallDownRight(new boolean[]{  false,  true, true,  false   }, "WDR.png");

    private Map<Direction, Boolean> block;
    private String url;

    public enum Direction{
        UP,
        RIGHT,
        DOWN,
        LEFT;
    }


    private Cell(boolean[] block, String url){
        this.block = new HashMap<Direction, Boolean>();
        for(int i = 0; i< block.length; i++){
            Direction dir = switch (i){
                case 0 -> UP;
                case 1 -> RIGHT;
                case 2 -> DOWN;
                case 3 -> LEFT;
                default -> throw new IllegalStateException("Erreur dans la taille des Cells");
            };
            this.block.put(dir,block[i]);
        }
        this.url = url;
    }

    public String getUrl() {
        return this.url;
    }

    public Map<Direction, Boolean> getBlock() {
        return this.block;
    }
}
