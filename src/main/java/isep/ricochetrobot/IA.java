package isep.ricochetrobot;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

import static isep.ricochetrobot.Cell.Direction;
import static isep.ricochetrobot.Cell.Direction.*;

public class IA {
    //private List graphList;
    static Queue<Deplacement> winQueue;
    private GameBoard context;



    public void run(){
        final GameBoard c = GameBoard.context;
        this.context = c;
        IA.winQueue = processWinQueue();
    }

    private static class Deplacement{
        private final Color color;
        private final Direction direction;
        public Deplacement(Color color, Direction direction){
            this.color = color;
            this.direction = direction;
        }
        @Override
        public String toString(){

            return this.color + " " + this.direction ;
        }
        public Color getColor() {
            return color;
        }
        public Direction getDirection() {
            return direction;
        }
    }

    private Queue<Deplacement> processWinQueue(){

        List<Queue<Deplacement>> graphList;
        graphList = new ArrayList<Queue<Deplacement>>();
        //GameBoard.context.processDeplacement(dir);
        boolean win = false;
        while (!win){
            for(Direction dir : new Direction[]{UP,RIGHT,DOWN,LEFT} ){
                for(Robot robot:this.context.getRobots().values()){
                    System.out.println(robot.getColor());
                }
            }
        }



    return null;
    }
}
