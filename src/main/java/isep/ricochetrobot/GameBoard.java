package isep.ricochetrobot;

public class GameBoard {

    private Cell[][] cells;

    public GameBoard(){

        /*
            Creation du plateau de jeu à partir de 4 planches
        */

        Board plate1 = Board.PLANCHE1;
        Board plate2 = Board.PLANCHE1;
        Board plate3 = Board.PLANCHE1;
        Board plate4 = Board.PLANCHE1;

        this.cells = new Cell[16][16];

        //Planche 1 : en haut à gauche
        for(int y = 0; y < cells.length/2; y++ ){
            for(int x = 0; x < cells.length/2; x++ ){
                this.cells[y][x] = switch (plate1.getCells()[x][y]){
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

        //Planche 2 : en haut à droite rotation de 90°

        for(int y = 0; y < cells.length/2; y++ ){
            for(int x = cells.length/2; x < cells.length; x++ ){
                this.cells[y][x] = switch (plate2.getCells()[x-8][y]){
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

        //planche 3 : en bas à gauche
        for(int y = cells.length/2; y < cells.length; y++ ){
            for(int x = 0; x < cells.length/2; x++ ){
                this.cells[y][x] = switch (plate3.getCells()[x][y-8]){
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

        //planche 4 : en bas à droite
        for(int y = cells.length/2; y < cells.length; y++ ){
            for(int x = cells.length/2; x < cells.length; x++ ){
                this.cells[y][x] = switch (plate4.getCells()[x-8][y-8]){
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

    }

    public Cell[][] getCells(){
        return this.cells;
    }
}
