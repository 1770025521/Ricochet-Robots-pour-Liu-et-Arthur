package isep.ricochetrobot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Queue;

import static isep.ricochetrobot.Cell.Direction;
import static isep.ricochetrobot.Cell.Direction.*;

public class IA {
    //private List graphList;
    private Color mainRobotColor;
    private List<GameBoard> gameBoardsToTest;
    private List<GameBoard> gameBoardsToTestTemp;
    private List<String> gameBoardTested;

    public IA(){

    }

    public void run(){
        this.gameBoardsToTest = new ArrayList<>();
        this.gameBoardsToTestTemp = new ArrayList<>();
        this.gameBoardTested = new ArrayList<>();
        final GameBoard c =  GameBoard.context;
        this.gameBoardsToTest.add(c);
        //this.gameBoardTested.add(this.getIdContext(c));
        this.mainRobotColor=c.getSelectedSymbol().getColor();

        this.makeTheMove();

        for(GameBoard gb : this.gameBoardsToTest){
            System.out.println(gb.getRobots().get(Color.RED).getPosX());
        }


    }

    private void makeTheMove(){
        for(GameBoard gameBoard:gameBoardsToTest){
            System.out.println(getIdContext(gameBoard));
            for(Color color: Color.values()){
                for(Direction direction: Direction.values()){
                    final GameBoard newContext = gameBoard;
                    newContext.setSelectedRobot(color);
                    newContext.processDeplacement(direction);
                    String id = this.getIdContext(newContext);
                    if (!this.gameBoardTested.contains(id)){
                        this.gameBoardTested.add(id);
                        this.gameBoardsToTestTemp.add(newContext);
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
        System.out.println(id);
        for (Robot robot:gb.getRobots().values()){
            if (robot.getColor() != this.mainRobotColor){
                l.add(robot.getPosX()+16*robot.getPosY());
            }
        }
        Collections.sort(l);
        id = id + String.format("%03d",l.get(0))
                + String.format("%03d",l.get(1))
                + String.format("%03d",l.get(2));
        for (int e : l){
            System.out.println(e);
        }
        System.out.println(Long.parseLong("444444444444"));
        return id;
    }

}
