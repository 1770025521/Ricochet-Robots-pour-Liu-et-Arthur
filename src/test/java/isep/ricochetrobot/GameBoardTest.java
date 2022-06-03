package isep.ricochetrobot;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;



import static org.junit.jupiter.api.Assertions.*;

class GameBoardTest {

    @Test
    void CollisionWallTest(){
        if (GameBoard.context == null){
            GameBoard.start(new Board[]{Board.PLANCHE1,Board.PLANCHE2,Board.PLANCHE3,Board.PLANCHE4},60);
        }


        GameBoard.context.getRobots().get(Color.RED).setPos(1,1);
        GameBoard.context.getRobots().get(Color.BLUE).setPos(14,1);
        GameBoard.context.getRobots().get(Color.GREEN).setPos(14,14);
        GameBoard.context.getRobots().get(Color.YELLOW).setPos(1,14);

        GameBoard.context.setSelectedRobot(Color.RED);
        GameBoard.context.processDeplacement(Direction.RIGHT);

        GameBoard.context.setSelectedRobot(Color.BLUE);
        GameBoard.context.processDeplacement(Direction.DOWN);

        GameBoard.context.setSelectedRobot(Color.GREEN);
        GameBoard.context.processDeplacement(Direction.LEFT);

        GameBoard.context.setSelectedRobot(Color.YELLOW);
        GameBoard.context.processDeplacement(Direction.UP);

        assertEquals(9,GameBoard.context.getRobots().get(Color.RED).getPosX());
        assertEquals(1,GameBoard.context.getRobots().get(Color.RED).getPosY());

        assertEquals(14,GameBoard.context.getRobots().get(Color.BLUE).getPosX());
        assertEquals(4,GameBoard.context.getRobots().get(Color.BLUE).getPosY());

        assertEquals(11,GameBoard.context.getRobots().get(Color.GREEN).getPosX());
        assertEquals(14,GameBoard.context.getRobots().get(Color.GREEN).getPosY());

        assertEquals(1,GameBoard.context.getRobots().get(Color.YELLOW).getPosX());
        assertEquals(13,GameBoard.context.getRobots().get(Color.YELLOW).getPosY());
    }

    @Test
    void CollisionRobotTest(){
        if (GameBoard.context == null){
            GameBoard.start(new Board[]{Board.PLANCHE1,Board.PLANCHE2,Board.PLANCHE3,Board.PLANCHE4},60);
        }

        GameBoard.context.getRobots().get(Color.RED).setPos(6,2);
        GameBoard.context.getRobots().get(Color.BLUE).setPos(6,10);
        GameBoard.context.getRobots().get(Color.GREEN).setPos(14,2);
        GameBoard.context.getRobots().get(Color.YELLOW).setPos(10,3);

        GameBoard.context.setSelectedRobot(Color.BLUE);
        GameBoard.context.processDeplacement(Direction.UP);

        GameBoard.context.setSelectedRobot(Color.GREEN);
        GameBoard.context.processDeplacement(Direction.LEFT);

        GameBoard.context.setSelectedRobot(Color.YELLOW);
        GameBoard.context.processDeplacement(Direction.LEFT);

        GameBoard.context.setSelectedRobot(Color.RED);
        GameBoard.context.processDeplacement(Direction.RIGHT);

        assertEquals(6,GameBoard.context.getRobots().get(Color.RED).getPosX());
        assertEquals(2,GameBoard.context.getRobots().get(Color.RED).getPosY());

        assertEquals(6,GameBoard.context.getRobots().get(Color.BLUE).getPosX());
        assertEquals(3,GameBoard.context.getRobots().get(Color.BLUE).getPosY());

        assertEquals(7,GameBoard.context.getRobots().get(Color.GREEN).getPosX());
        assertEquals(2,GameBoard.context.getRobots().get(Color.GREEN).getPosY());

        assertEquals(7,GameBoard.context.getRobots().get(Color.YELLOW).getPosX());
        assertEquals(3,GameBoard.context.getRobots().get(Color.YELLOW).getPosY());
    }

    @Test
    void DetectionDiagonalTest(){
        if (GameBoard.context == null){
            GameBoard.start(new Board[]{Board.PLANCHE1,Board.PLANCHE2,Board.PLANCHE3,Board.PLANCHE4},60);
        }

        GameBoard.context.getRobots().get(Color.RED).setPos(8,5);
        GameBoard.context.setSelectedRobot(Color.RED);

        boolean diag;

        diag = GameBoard.context.checkDiagonal(9,6);
        assertFalse(diag);

        diag = GameBoard.context.checkDiagonal(9,0);
        assertFalse(diag);

        diag = GameBoard.context.checkDiagonal(7,7);
        assertFalse(diag);

        diag = GameBoard.context.checkDiagonal(5,4);
        assertFalse(diag);

        diag = GameBoard.context.checkDiagonal(8,7);
        assertTrue(diag);

        diag = GameBoard.context.checkDiagonal(15,5);
        assertTrue(diag);


    }

    @Test
    void DirectionCalculationTest(){
        if (GameBoard.context == null){
            GameBoard.start(new Board[]{Board.PLANCHE1,Board.PLANCHE2,Board.PLANCHE3,Board.PLANCHE4},60);
        }

        GameBoard.context.getRobots().get(Color.RED).setPos(4,4);
        GameBoard.context.setSelectedRobot(Color.RED);

        Direction direction;

        direction = GameBoard.context.getDirectionMouv(4,15);
        assertEquals(Direction.DOWN,direction);

        direction = GameBoard.context.getDirectionMouv(4,0);
        assertEquals(Direction.UP,direction);

        direction = GameBoard.context.getDirectionMouv(8,4);
        assertEquals(Direction.RIGHT,direction);

        direction = GameBoard.context.getDirectionMouv(1,4);
        assertEquals(Direction.LEFT,direction);

    }

    @Test
    void winTest(){
        if (GameBoard.context == null){
            GameBoard.start(new Board[]{Board.PLANCHE1,Board.PLANCHE2,Board.PLANCHE3,Board.PLANCHE4},60);
        }

        GameBoard.context.getRobots().get(Color.RED).setPos(15,9);
        GameBoard.context.getRobots().get(Color.BLUE).setPos(0,11);
        GameBoard.context.getRobots().get(Color.GREEN).setPos(0,0);
        GameBoard.context.getRobots().get(Color.YELLOW).setPos(0,15);

        GameBoard.context.setSelectedSymbol(Symbol.REDSTAR);
        GameBoard.context.setSelectedRobot(Color.RED);

        boolean win;

        win = GameBoard.context.checkWin();
        assertFalse(win);

        GameBoard.context.processDeplacement(Direction.UP);

        win = GameBoard.context.checkWin();
        assertFalse(win);

        GameBoard.context.processDeplacement(Direction.LEFT);

        win = GameBoard.context.checkWin();
        assertTrue(win);

        GameBoard.context.processDeplacement(Direction.RIGHT);

        win = GameBoard.context.checkWin();
        assertFalse(win);

        GameBoard.context.setSelectedRobot(Color.BLUE);
        GameBoard.context.processDeplacement(Direction.DOWN);

        win = GameBoard.context.checkWin();
        assertFalse(win);

    }

}