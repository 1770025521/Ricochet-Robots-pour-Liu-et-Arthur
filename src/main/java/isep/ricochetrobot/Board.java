package isep.ricochetrobot;

import java.lang.annotation.Target;

public enum Board {
    PLANCHE0(

            new int[][]
                    {
                            {6,1,1,1,1,1,1,1},
                            {4,0,0,0,0,0,0,0},
                            {4,0,0,6,0,0,0,0},
                            {4,0,0,0,0,0,0,0},
                            {6,0,5,0,0,0,0,0},
                            {4,0,0,0,0,0,0,0},
                            {4,0,0,0,0,0,0,3},
                            {4,0,0,0,0,0,2,0}


                    },

            null

    ),
    PLANCHE1(

            new int[][]
                    {
                            {6,1,1,1,1,1,1,1},
                            {4,0,0,0,0,0,0,0},
                            {4,0,0,0,0,0,0,0},
                            {4,0,0,0,0,0,0,0},
                            {4,0,0,0,0,0,0,0},
                            {4,0,0,0,0,0,0,0},
                            {4,0,0,0,0,0,0,3},
                            {4,0,0,0,0,0,2,0}


                    },

            null

    ),
    PLANCHE2(

            new int[][]
                    {
                            {6,1,1,1,1,1,1,1},
                            {4,0,0,0,0,0,0,0},
                            {4,0,0,0,0,0,0,0},
                            {4,0,0,0,0,0,0,0},
                            {4,0,0,0,0,0,0,0},
                            {4,0,0,0,0,0,0,0},
                            {4,0,0,0,0,0,0,3},
                            {4,0,0,0,0,0,2,0}


                    },

            null

    ),
    PLANCHE3(

            new int[][]
                    {
                            {6,1,1,1,1,1,1,1},
                            {4,0,0,0,0,0,0,0},
                            {4,0,0,0,0,0,0,0},
                            {4,0,0,0,0,0,0,0},
                            {4,0,0,0,0,0,0,0},
                            {4,0,0,0,0,0,0,0},
                            {4,0,0,0,0,0,0,3},
                            {4,0,0,0,0,0,2,0}


                    },

            null

    ),
    PLANCHE4(

            new int[][]
                    {
                            {6,1,1,1,1,1,1,1},
                            {4,0,0,0,0,0,0,0},
                            {4,0,0,0,0,0,0,0},
                            {4,0,0,0,0,0,0,0},
                            {4,0,0,0,0,0,0,0},
                            {4,0,0,0,0,0,0,0},
                            {4,0,0,0,0,0,0,3},
                            {4,0,0,0,0,0,2,0}


                    },

            null

    );

    Cell[][] cells;
    Symbol[][] symbols;

    private Board(int[][] cells,int[][] symbols ){
        this.cells = new Cell[12][12];
        for(int y = 0; y < cells.length; y++ ){
            for(int x = 0; x < cells.length; x++ ){
                this.cells[x][y] = switch (cells[x][y]){
                    case 0 -> Cell.NoWall;
                    case 1 -> Cell.WallUp;
                    case 2 -> Cell.WallRight;
                    case 3 -> Cell.WallDown;
                    case 4 -> Cell.WallLeft;
                    case 5 -> Cell.WallUpRight;
                    case 6 -> Cell.WallUpLeft;
                    case 7 -> Cell.WallDownLeft;
                    case 8 -> Cell.WallDownRight;
                    default -> null;
                };
            }
        }
        this.symbols = new Symbol[12][12];
    }

}
