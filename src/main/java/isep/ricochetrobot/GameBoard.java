package isep.ricochetrobot;

import javafx.scene.effect.Glow;

import java.util.*;

import static isep.ricochetrobot.Color.*;
import static isep.ricochetrobot.GameBoard.Status.*;
import static isep.ricochetrobot.Cell.Direction.*;

public class GameBoard {

    static GameBoard context;

    private Cell[][] cells;
    private Symbol[][] symbols;
    private Map<Color,Robot> robots;
    private Map<Robot,int[]> robotsOrigin;
    private List<Symbol> symbolList;
    public static final int SIZE = 16;
    private Robot selectedRobot;
    private Symbol selectedSymbol;
    private int count;

    private Status status;

    public enum Status{
        CHOOSE_PLAYER("Cliquez sur le bouton [Jouer]"),
        CHOOSE_ROBOT("Cliquez sur le robot à déplacer"),
        CHOOSE_TILE("Cliquez sur la case destination"),
        END_TIMER("");
        Status(String toolTip) { this.toolTip = toolTip; }
        private final String toolTip;
        public String getToolTip() { return this.toolTip; }
    }

    public static void start(Board[] boards){
        if (GameBoard.context != null) {
            throw new RuntimeException
                    ("Impossible de lancer plusieurs fois la partie...");
        }
        GameBoard.context = new GameBoard(boards);
        GameBoard.context.setStatus(CHOOSE_ROBOT);
    }

    private GameBoard(Board[]boards){

        count = 0;

        /*
            Creation du plateau de jeu à partir de 4 planches
        */

        Board plate1 = boards[0];
        Board plate2 = boards[1];
        Board plate3 = boards[2];
        Board plate4 = boards[3];

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
        this.symbolList = new ArrayList<Symbol>();

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
                Symbol symbol = pickSymbol (symbols1[x][y]);
                this.symbols[y][x] = symbol;
                if (symbol != Symbol.NONE){
                    symbol.setPosX(y);
                    symbol.setPosY(x);
                    this.symbolList.add(symbol);
                }
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
                Symbol symbol = pickSymbol (symbols2[x][y-8]);
                this.symbols[y][x] = symbol;
                if (symbol != Symbol.NONE){
                    symbol.setPosX(y);
                    symbol.setPosY(x);
                    this.symbolList.add(symbol);
                }
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
                Symbol symbol = pickSymbol (symbols3[x-8][y]);
                this.symbols[y][x] = symbol;
                if (symbol != Symbol.NONE){
                    symbol.setPosX(y);
                    symbol.setPosY(x);
                    this.symbolList.add(symbol);
                }
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
                Symbol symbol = pickSymbol (symbols4[x-8][y-8]);
                this.symbols[y][x] = symbol;
                if (symbol != Symbol.NONE){
                    symbol.setPosX(y);
                    symbol.setPosY(x);
                    this.symbolList.add(symbol);
                }
            }
        }

        /*
            Creation des Robots
        */

        robots = new HashMap<Color, Robot>();
        robots.put(RED, createValidRobot(RED));
        robots.put(GREEN, createValidRobot(GREEN));
        robots.put(BLUE, createValidRobot(BLUE));
        robots.put(YELLOW, createValidRobot(YELLOW));

        setRobotsOrigin();

        // Creation de l'objectif

        Random rand = new Random();
        this.selectedSymbol = symbolList.get(rand.nextInt(this.symbolList.size()));

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
    public Robot getSelectedRobot(){
        return this.selectedRobot;
    }
    public Status getStatus(){
        return this.status;
    }
    public int getCount() {
        return this.count;
    }
    public Symbol getSelectedSymbol(){
        return this.selectedSymbol;
    }

    //Les setteurs
    public void setStatus(Status status){
        this.status = status;
    }
    public void setCount(int count) {
        this.count = count;
    }

    //Les process
    public void processSelectRobot(Color color) {
        if(this.status == CHOOSE_ROBOT){
                this.selectedRobot = this.robots.get(color);
                Glow glow = new Glow();
                glow.setLevel(1);
                this.selectedRobot.getGui().setEffect(glow);
                setStatus(CHOOSE_TILE);
        }
    }

    public void processDeselectRobot(){
        this.selectedRobot = null;
    }

    public Cell.Direction getDirectionMouv(int x,int y){
        int dx = this.selectedRobot.getPosX() - x;
        int dy = this.selectedRobot.getPosY() - y;

        if(dx!=0){
            //deplacement sur x
            if(dx>0){
                return LEFT;
            }else{
                return RIGHT;
            }

        }else{
            //deplacement sur y
            if(dy>0){
                return UP;
            }else{
                return DOWN;
            }

        }
    }

    public void processDeplacement(Cell.Direction dir){
        int x = context.getSelectedRobot().getPosX();
        int y = context.getSelectedRobot().getPosY();

        //System.out.println(x +" " + y);

        Cell[][] cells = GameBoard.context.getCells();

        switch (dir) {
            case UP:
                if (!cells[x][y].getBlock().get(UP)
                        && !cells[x][y - 1].getBlock().get(DOWN)
                        && !this.checkRobotNear(dir)) {

                    this.getSelectedRobot().setPos(x, y - 1);
                    this.processDeplacement(dir);
                }
                break;

            case RIGHT:
                if (!cells[x][y].getBlock().get(RIGHT)
                        && !cells[x + 1][y].getBlock().get(LEFT)
                        && !this.checkRobotNear(dir)) {
                    this.getSelectedRobot().setPos(x + 1, y);
                    this.processDeplacement(dir);
                }
                break;

            case DOWN:
                if (!cells[x][y].getBlock().get(DOWN) 
                        && !cells[x][y + 1].getBlock().get(UP) 
                        && !this.checkRobotNear(dir)) {
                    this.getSelectedRobot().setPos(x, y + 1);
                    this.processDeplacement(dir);
                }
                break;

            case LEFT:
                if (!cells[x][y].getBlock().get(LEFT)
                        && !cells[x - 1][y].getBlock().get(RIGHT)
                        && !this.checkRobotNear(dir)) {
                    this.getSelectedRobot().setPos(x - 1, y);
                    this.processDeplacement(dir);
                }
                break;

            default:
                throw new IllegalStateException("Erreur de direction dans les colisions " );
        }
    }

    private boolean checkRobotNear(Cell.Direction dir){
        int x = context.getSelectedRobot().getPosX();
        int y = context.getSelectedRobot().getPosY();

        for (Color color : this.robots.keySet()){
            Robot robot = this.robots.get(color);
            if (robot != context.getSelectedRobot()){
                switch (dir) {
                    case UP:
                        if (robot.getPosX() == x && robot.getPosY() == y - 1) {
                            return true;
                        }
                        break;
                    case RIGHT:
                        if (robot.getPosX() == x + 1 && robot.getPosY() == y) {
                            return true;
                        }
                        break;
                    case DOWN:
                        if (robot.getPosX() == x && robot.getPosY() == y + 1) {
                            return true;
                        }
                        break;
                    case LEFT:
                        if (robot.getPosX() == x - 1 && robot.getPosY() == y) {
                            return true;
                        }
                        break;
                    default:
                        throw new IllegalStateException("Erreur de direction dans les colisions " );
                }
            }
        }
        return false;
    }

    public void processSelectSelectedSymbol(){
        Random rand = new Random();
        Symbol symbol = GameBoard.context.getSelectedSymbol() ;
        while (symbol == GameBoard.context.getSelectedSymbol() ){
            symbol = symbolList.get(rand.nextInt(this.symbolList.size()));
        }
        this.selectedSymbol = symbol;
        GameBoard.context.setStatus(CHOOSE_ROBOT);
    }

    public boolean checkDiagonal(int x, int y) {
        return((this.selectedRobot.getPosX() == x) ||  (this.selectedRobot.getPosY() == y));
    }

    public boolean checkWin(){
        int rx = this.selectedRobot.getPosX();
        int ry = this.selectedRobot.getPosY();
        return this.selectedRobot.getColor() == this.selectedSymbol.getColor()
                && this.symbols[rx][ry] == this.selectedSymbol;
    }

    public Robot createValidRobot(Color color){
        int posX = 8; int posY = 8;
        Robot robot = new Robot(color,posX,posY );
        Random rand = new Random();

        while ( (!((posX != 8 && posY != 7) || (posX != 7 && posY != 8))) && checkSuperpositionRobot(posX,posY)) {
            posX = rand.nextInt(SIZE);
            posY = rand.nextInt(SIZE);
            //System.out.println(posX + " " + posY);
        }
        robot.setPos(posX,posY);

        return robot;
    }

    private boolean checkSuperpositionRobot(int x, int y){
        for (Robot robot : this.robots.values()){
            if(robot.getPosX() == x && robot.getPosY() == y){
                return false;
            }
        }
        return true;
    }

    public void setRobotsOrigin(){
        this.robotsOrigin = new HashMap<>();
        for(Robot robot : this.robots.values()){
            int x = robot.getPosX();
            int y = robot.getPosY();
            this.robotsOrigin.put(robot,new int[]{x,y});
        }
    }

    public void restoreRobotOrigin(){
        for(Robot robot : this.robots.values()){
            int[]pos = this.robotsOrigin.get(robot);
            robot.setPos(pos[0],pos[1]);
        }
    }


}
