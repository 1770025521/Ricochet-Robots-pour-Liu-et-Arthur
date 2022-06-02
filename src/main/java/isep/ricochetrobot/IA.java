package isep.ricochetrobot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Queue;


import static isep.ricochetrobot.Direction.*;

/**
 * Classe qui gere l'Itelligence artificielle
 */
public class IA {
    //private List graphList;
    private Color mainRobotColor;
    private List<GameBoard> gameBoardsToTest;
    private List<GameBoard> gameBoardsToTestTemp;
    private List<String> gameBoardTested;
    private List<Deplacement> solution;
    private boolean win;

    public IA(){

    }

    /**
     * Lance le processus de détection de solution
     */
    public void run(){
        this.gameBoardsToTest = new ArrayList<>();
        this.gameBoardsToTestTemp = new ArrayList<>();
        this.gameBoardTested = new ArrayList<>();
        this.solution = new ArrayList<>();
        final GameBoard c =  GameBoard.context;
        this.gameBoardsToTest.add(c);
        //this.gameBoardTested.add(this.getIdContext(c));
        this.mainRobotColor=c.getSelectedSymbol().getColor();
        this.win = false;

        //this.makeTheMove();

        while (!win){
        //showGameBoardsToTest();

        this.makeTheMove();
        System.out.println(this.gameBoardsToTest.size());
        }

        System.out.println("Ya win");
        for (Deplacement d:this.solution){
            System.out.println(d);
        }
    }

    private void showGameBoardsToTest(){
        for(GameBoard gb : this.gameBoardsToTest){
            System.out.println(gb);
            System.out.println(gb.getRobots().get(Color.RED).getPosX() + " " +gb.getRobots().get(Color.RED).getPosY());
        }
    }

    private void makeTheMove(){
        for(GameBoard gameBoard:gameBoardsToTest){
            //System.out.println(getIdContext(gameBoard));
            for(Color color: Color.values()){
                for(Direction direction: Direction.values()){
                    final GameBoard newContext = new GameBoard(gameBoard) ;
                    newContext.setSelectedRobot(color);
                    //System.out.println(newContext.getSelectedRobot().getPosX());
                    newContext.processDeplacement(direction);
                    String id = this.getIdContext(newContext);
                    if (!this.gameBoardTested.contains(id)){
                        newContext.getPreviousMove().add(new Deplacement(color,direction));
                        this.gameBoardTested.add(id);
                        this.gameBoardsToTestTemp.add(newContext);
                        if (newContext.checkWin()){
                            this.win = true ;
                            this.solution = newContext.getPreviousMove();
                        }

                        //System.out.println(this.win);
                    }
                    //System.out.println(color+" "+direction);
                }
            }
        }
        this.gameBoardsToTest.clear();
        this.gameBoardsToTest.addAll(this.gameBoardsToTestTemp);
        this.gameBoardsToTestTemp.clear();
    }

    private String getIdContext(GameBoard gb){
        String id = String.format("%03d", (gb.getRobots().get(this.mainRobotColor).getPosX()
                + 16*gb.getRobots().get(this.mainRobotColor).getPosY()));

        List<Integer> l = new ArrayList<>();
        for (Robot robot:gb.getRobots().values()){
            if (robot.getColor() != this.mainRobotColor){
                l.add(robot.getPosX()+16*robot.getPosY());
            }
        }
        Collections.sort(l);
        id = id + String.format("%03d",l.get(0))
                + String.format("%03d",l.get(1))
                + String.format("%03d",l.get(2));
        return id;
    }

    /**
     * Recupere la liste des deplacement de la solution
     * @return la liste des deplacement de la solution
     */
    public List<Deplacement> getSolution(){
        return this.solution;
    }

}
