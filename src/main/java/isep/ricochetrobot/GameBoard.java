package isep.ricochetrobot;

import javafx.css.Size;

import java.util.HashMap;
import java.util.Map;

import static isep.ricochetrobot.Color.*;

public class GameBoard {

    static GameBoard context;

    private Cell[][] cells;
    private Symbol[][] symbols;
    private Map<Color,Robot> robots;
    public static final int SIZE = 16;

    public static void start(){
        if (GameBoard.context != null) {
            throw new RuntimeException
                    ("Impossible de lancer plusieurs fois la partie...");
        }
        GameBoard.context = new GameBoard();
        //GameBoard.context.setStatus(CHOOSE_PLAYER);
    }

    private GameBoard(){

        /*
            Creation du plateau de jeu à partir de 4 planches
        */

        Board plate1 = Board.PLANCHE4;
        Board plate2 = Board.PLANCHE1;
        Board plate3 = Board.PLANCHE3;
        Board plate4 = Board.PLANCHE2;

        int[][] cells1 = plate1.getCells();
        int[][] cells2 = util.rotateTable(plate2.getCells()) ;
        int[][] cells3 = util.rotateTable(util.rotateTable(util.rotateTable(plate3.getCells())));
        int[][] cells4 = util.rotateTable(util.rotateTable(plate4.getCells()));

        int[][] symbols1 = plate1.getSymbols();
        int[][] symbols2 = util.rotateTable(plate2.getSymbols()) ;
        int[][] symbols3 = util.rotateTable(util.rotateTable(util.rotateTable(plate3.getSymbols())));
        int[][] symbols4 = util.rotateTable(util.rotateTable(plate4.getSymbols()));

        this.cells = new Cell[16][16];
        this.symbols = new Symbol[16][16];

        //Planche 1 : en haut à gauche
        for(int y = 0; y < cells.length/2; y++ ){
            for(int x = 0; x < cells.length/2; x++ ){
                //Mur
                this.cells[y][x] = switch (cells1[x][y]){
                    case 1 -> Cell.WallUp;
                    case 2 -> Cell.WallRight;
                    case 3 -> Cell.WallDown;
                    case 4 -> Cell.WallLeft;
                    case 5 -> Cell.WallUpRight;
                    case 6 -> Cell.WallUpLeft;
                    case 7 -> Cell.WallDownLeft;
                    case 8 -> Cell.WallDownRight;
                    default -> Cell.NoWall;
                };
                //Symbole
                this.symbols[y][x] = pickSymbol (symbols1[x][y]);
            }
        }

        //Planche 2 : en haut à droite rotation de 90°
        for(int y = cells.length/2; y < cells.length; y++ ){
            for(int x = 0; x < cells.length/2; x++ ){
                this.cells[y][x] = switch (cells2[x][y-8]){
                    case 0 -> Cell.NoWall;
                    case 1 -> Cell.WallRight;
                    case 2 -> Cell.WallDown;
                    case 3 -> Cell.WallLeft;
                    case 4 -> Cell.WallUp;
                    case 5 -> Cell.WallDownRight;
                    case 6 -> Cell.WallUpRight;
                    case 7 -> Cell.WallUpLeft;
                    case 8 -> Cell.WallDownLeft;
                    default -> null;
                };
                //Symbole
                this.symbols[y][x] = pickSymbol (symbols2[x][y-8]);
            }
        }

        //Planche 3 : en bas à gauche rotation de -90°
        for(int y = 0; y < cells.length/2; y++ ){
            for(int x = cells.length/2; x < cells.length; x++ ){
                this.cells[y][x] = switch (cells3[x-8][y]){
                    case 0 -> Cell.NoWall;
                    case 1 -> Cell.WallLeft;
                    case 2 -> Cell.WallUp;
                    case 3 -> Cell.WallRight;
                    case 4 -> Cell.WallDown;
                    case 5 -> Cell.WallUpLeft;
                    case 6 -> Cell.WallDownLeft;
                    case 7 -> Cell.WallDownRight;
                    case 8 -> Cell.WallUpRight;
                    default -> null;
                };
                //Symbole
                this.symbols[y][x] = pickSymbol (symbols3[x-8][y]);
            }
        }

        //planche 4 : en bas à droite
        for(int y = cells.length/2; y < cells.length; y++ ){
            for(int x = cells.length/2; x < cells.length; x++ ){
                this.cells[y][x] = switch (cells4[x-8][y-8]){
                    case 0 -> Cell.NoWall;
                    case 1 -> Cell.WallDown;
                    case 2 -> Cell.WallLeft;
                    case 3 -> Cell.WallUp;
                    case 4 -> Cell.WallRight;
                    case 5 -> Cell.WallDownLeft;
                    case 6 -> Cell.WallDownRight;
                    case 7 -> Cell.WallUpRight;
                    case 8 -> Cell.WallUpLeft;
                    default -> null;
                };
                //Symbole
                this.symbols[y][x] = pickSymbol (symbols4[x-8][y-8]);
            }
        }

        /*
            Creation des Robots
        */

        robots = new HashMap<Color, Robot>();
        robots.put(RED, new Robot(RED));
        robots.put(GREEN, new Robot(GREEN));
        robots.put(BLUE, new Robot(BLUE));
        robots.put(YELLOW, new Robot(YELLOW));
    }

    //Pour récupérer les symboles dans le constructeur
    public Symbol pickSymbol(int id){
        return switch (id){
            case 1 -> Symbol.YELLOWMOON;
            case 2 -> Symbol.BLUESUN;
            case 3 -> Symbol.GREENSTAR;
            case 4 -> Symbol.REDPLANET;
            case 5 -> Symbol.BLUEMOON;
            case 6 -> Symbol.REDSTAR;
            case 7 -> Symbol.YELLOWSUN;
            case 8 -> Symbol.GREENPLANET;
            case 9 -> Symbol.GREENSUN;
            case 10 -> Symbol.YELLOWSTAR;
            case 11 -> Symbol.REDMOON;
            case 12 -> Symbol.BLUEPLANET;
            case 13 -> Symbol.YELLOWPLANET;
            case 14 -> Symbol.GREENMOON;
            case 15 -> Symbol.BLUESTAR;
            case 16 -> Symbol.REDSUN;
            default -> Symbol.NONE;
        };
    }

    //Les getteurs
    public Cell[][] getCells(){
        return this.cells;
    }
    public Symbol[][] getSymbols(){
        return this.symbols;
    }
    public Map<Color, Robot> getRobots(){
        return this.robots;
    }
}
