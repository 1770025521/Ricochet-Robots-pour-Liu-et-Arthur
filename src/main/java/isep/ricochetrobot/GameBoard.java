package isep.ricochetrobot;

import javafx.scene.effect.Glow;

import java.util.*;

import static isep.ricochetrobot.Color.*;
import static isep.ricochetrobot.Status.*;
import static isep.ricochetrobot.Direction.*;

/**
 * Classe qui represente le plateau de jeu
 */
public class GameBoard {
    static GameBoard context;
    private Cell[][] cells;
    private Symbol[][] symbols;
    private Map<Color,Robot> robots;
    private Map<Robot,int[]> robotsOrigin;
    private List<Symbol> symbolList;
    private Robot selectedRobot;
    private Symbol selectedSymbol;
    private int count;
    private int time;

    private final List<Deplacement> previousMove; //Uniquement Utiliser par l'IA

    private Status status;

    /**
     * Lance le jeu
     * Verifie que le je n'est pas deja lance
     * Stoque le nouveau plateau dans la variable static GameBoard.context
     * Cette variable sera utiliser pour interagir avec le plateau
     * @param boards Un tableau de 4 Board qui forment le plateau final
     * @param time   Le temps entre deux tours
     */
    public static void start(Board[] boards, int time){
        if (GameBoard.context != null) {
            throw new RuntimeException
                    ("Impossible de lancer plusieurs fois la partie...");
        }
        GameBoard.context = new GameBoard(boards);
        GameBoard.context.setStatus(CHOOSE_ROBOT);
        GameBoard.context.setTime(time);

    }

    /**
     * Constructeur prive de la classe Gameboard
     * Utilisé dans la fonction start
     * S'occupe d'innitialise toute les variables du jeu
     * Fait la rotation des Board pour former le plateau
     * Cree les Robots
     * @param boards Le tableau des 4 Board qui forment le plateau final
     */
    private GameBoard(Board[]boards){

        count = 0;
        previousMove = new ArrayList<>();

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

    /**
     * Constructeur public pour creer une copie du plateau
     * Pour seulement creer un nouveau plateau, utiliser la fonction start()
     * @param gb le plateau que l'on veut copier
     */
    public GameBoard(GameBoard gb){
        //this.cells = gb.getCells();
        this.robots = new HashMap<>();

        for(Color color:gb.getRobots().keySet()){
            this.robots.put(color,new Robot(gb.getRobots().get(color)));
        }

        //this.robots.putAll(gb.getRobots());
        this.selectedSymbol = gb.selectedSymbol;
        this.previousMove = new ArrayList<>();
        this.previousMove.addAll(gb.previousMove);
    }

    //Pour récupérer les symboles dans le constructeur

    /**
     * Permet de retrouver un Sybole a partir de son id
     * @param id l'id du Symbol
     * @return le Symbol corespondant a l'id
     */
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
    /**
     * Recupere le tableau des murs
     * @return le tableau des murs
     */
    public Cell[][] getCells(){
        return this.cells;
    }
    /**
     * Recupere le tableau des Symbol
     * @return le tableau des Symbol
     */
    public Symbol[][] getSymbols(){
        return this.symbols;
    }
    /**
     * Recupere la hashMap des robots
     * @return la hashMap des robots
     */
    public Map<Color, Robot> getRobots(){
        return this.robots;
    }
    /**
     * Recupere le Robot selectione
     * @return le Robot selectione
     */
    public Robot getSelectedRobot(){
        return this.selectedRobot;
    }
    /**
     * Recupere le Status du jeu
     * @return le Status du jeu
     */
    public Status getStatus(){
        return this.status;
    }
    /**
     * Recupere le nombre de coup du mouvement actuel
     * @return le nombre de coup
     */
    public int getCount() {
        return this.count;
    }
    /**
     * Recupere le Symbol selectione
     * @return le Symbol selectione
     */
    public Symbol getSelectedSymbol(){
        return this.selectedSymbol;
    }

    /**
     * Recupere la liste des coups deja effectue
     * @return la liste des coups deja effectue
     */
    public List<Deplacement> getPreviousMove(){
        return this.previousMove;
    }

    //Les setteurs

    /**
     * Change le Status du jeu
     * @param status le nouveau status du jeu
     */
    public void setStatus(Status status){
        this.status = status;
    }
    /**
     * Change le nombre de coup effectue
     * @param count le nombre de coup
     */
    public void setCount(int count) {
        this.count = count;
    }

    //Les process

    /**
     * Selectionne un robot du plateau
     * Lui ajoute un effet glowing
     * @param color la couleur du Robot que l'on veut selectioner
     */
    public void processSelectRobot(Color color) {
        if(this.status == CHOOSE_ROBOT){
                this.selectedRobot = this.robots.get(color);
                Glow glow = new Glow();
                glow.setLevel(1);
                this.selectedRobot.getGui().setEffect(glow);
                setStatus(CHOOSE_TILE);
        }
    }

    /**
     * Deselectionne les Robots
     */
    public void processDeselectRobot(){
        this.selectedRobot = null;
    }

    /**
     * Determine la direction du mouvement d'un robot
     * Compare la position du robot selectione avec une position qui donne la direction
     * Les deplacement en diagonale sont interdits
     * @param x la position x ou le joueur veut deplacer le robot
     * @param y la position y ou le joueur veut deplacer le robot
     * @return la Direction du Robot
     */
    public Direction getDirectionMouv(int x,int y){
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

    /**
     * Deplace le Robot selectioner dans la direction donne
     * Le robot s'arrete quand il atteint un mur ou un autre Robot
     * @param dir la direction du deplacement voulu
     */
    public void processDeplacement(Direction dir){
        int x = this.getSelectedRobot().getPosX();
        int y = this.getSelectedRobot().getPosY();

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

    /**
     * Recherche si il a un robot just'a cote du robot selectione dans sa direction de deplacement
     * @param dir la direction du robot selectione
     * @return la presence d'un autre Robot
     */
    private boolean checkRobotNear(Direction dir){
        int x = this.getSelectedRobot().getPosX();
        int y = this.getSelectedRobot().getPosY();

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

    /**
     * Selectionne un nouvau Symbol
     */
    public void processSelectSelectedSymbol(){
        Random rand = new Random();
        Symbol symbol = GameBoard.context.getSelectedSymbol() ;
        while (symbol == GameBoard.context.getSelectedSymbol() ){
            symbol = symbolList.get(rand.nextInt(this.symbolList.size()));
        }
        this.selectedSymbol = symbol;
        GameBoard.context.setStatus(CHOOSE_ROBOT);
    }

    /**
     * Regarde si un deplacement n'est pas diagonal
     * @param x la position x ou le joueur veut deplacer le robot
     * @param y la position y ou le joueur veut deplacer le robot
     * @return si le deplacement n'est pas diagonale
     */
    public boolean checkDiagonal(int x, int y) {
        return((this.selectedRobot.getPosX() == x) ||  (this.selectedRobot.getPosY() == y));
    }

    /**
     * Regarde si la manche est gagne
     * une manche est gagne si le Robot de la bonne couleur est sur le symbole selectione
     * @return si la manche est gagne
     */
    public boolean checkWin(){
        int rx = this.selectedRobot.getPosX();
        int ry = this.selectedRobot.getPosY();
        return this.selectedRobot.getColor() == context.selectedSymbol.getColor()
                && context.symbols[rx][ry] == context.selectedSymbol;
    }

    /**
     * Cree et positionne un Robot a emplacement Valide
     * @param color la couleur du robot cree
     * @return le robot cree
     */
    public Robot createValidRobot(Color color){
        int posX = 8; int posY = 8;
        Robot robot = new Robot(color,posX,posY );
        Random rand = new Random();

        while ( (!((posX != 8 && posY != 7) || (posX != 7 && posY != 8))) && checkSuperpositionRobot(posX,posY)) {
            posX = rand.nextInt(this.cells.length);
            posY = rand.nextInt(this.cells.length);
            //System.out.println(posX + " " + posY);
        }
        robot.setPos(posX,posY);

        return robot;
    }

    /**
     * Regarde si un Robot n'est pas sur une case du plateau
     * @param x la position x du plateau
     * @param y la position y du plateau
     * @return si il y un robot sur cette case
     */
    private boolean checkSuperpositionRobot(int x, int y){
        for (Robot robot : this.robots.values()){
            if(robot.getPosX() == x && robot.getPosY() == y){
                return false;
            }
        }
        return true;
    }

    /**
     * Fixe l'origine des robots
     */
    public void setRobotsOrigin(){
        this.robotsOrigin = new HashMap<>();
        for(Robot robot : this.robots.values()){
            int x = robot.getPosX();
            int y = robot.getPosY();
            this.robotsOrigin.put(robot,new int[]{x,y});
        }
    }

    /**
     * Selectionne un Robot
     * @param color la couleur du Robot
     */
    public void setSelectedRobot(Color color){
        this.selectedRobot = this.robots.get(color);
    }

    /**
     * Repositionne les robots a leur position innitiales
     */
    public void restoreRobotOrigin(){
        for(Robot robot : this.robots.values()){
            int[]pos = this.robotsOrigin.get(robot);
            robot.setPos(pos[0],pos[1]);
        }
    }

    /**
     * Fixe le temps de round
     * @param time le temps d'un round
     */
    public void setTime(int time) {
        this.time = time;
    }

    /**
     * Recupere le temps d'un round
     * @return le temps d'un round
     */
    public int getTime() {
        return time;
    }

    /**
     * Selectionne un symbole
     * @param symbol le symbole a selectioner
     */
    public void setSelectedSymbol(Symbol symbol){
        this.selectedSymbol = symbol;
    }
}
