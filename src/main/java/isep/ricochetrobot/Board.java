package isep.ricochetrobot;

import java.lang.annotation.Target;

public enum Board {
    PLANCHE0(

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
    PLANCHE1(

            new int[][]
                    {
                            {6,1,1,1,6,1,1,1},
                            {4,0,0,0,0,0,0,0},
                            {4,0,0,0,7,0,0,0},
                            {4,0,6,0,0,0,0,0},
                            {0,0,0,0,0,8,0,0},
                            {7,0,0,5,0,0,0,0},
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


    private final int[][] cells;
    private final int[][] symbols;

    private Board(int[][] cells,int[][] symbols ){
        this.cells = cells;
        this.symbols = symbols;
    }

    public int[][] getCells() {
        return this.cells;
    }
}
