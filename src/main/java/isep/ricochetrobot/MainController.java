package isep.ricochetrobot;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

import java.net.URL;
import java.util.ResourceBundle;

import static isep.ricochetrobot.Color.*;
import static isep.ricochetrobot.GameBoard.Status.*;

public class MainController implements Initializable {
    @FXML
    private Label text1;

    @FXML
    private GridPane gameGrid;


    Cell [][] board = GameBoard.context.getCells();
    Symbol[][] symbols = GameBoard.context.getSymbols();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.text1.setText("Test grille");

        //initialisation de la grille

        Image tileImage = new Image("cell.png", 50, 50, false, false);
        for(int y = 0; y < board.length; y++ ){
            for (int x = 0; x < board.length; x++ ){

                StackPane tileGui = new StackPane();
                ImageView tileBackground = new ImageView(tileImage);
                tileGui.getChildren().add(tileBackground);

                String wallUrl = board[x][y].getUrl();
                String symbolUrl = symbols[x][y].getUrl();

                if(wallUrl != null){
                    Image wallImage = new Image(wallUrl, 50, 50, false, false);
                    ImageView tileWall = new ImageView(wallImage);
                    tileGui.getChildren().add(tileWall);
                }

                if(symbolUrl != null){
                    Image symbolImage = new Image(symbolUrl, 50, 50, false, false);
                    ImageView tileSymbol = new ImageView(symbolImage);
                    tileGui.getChildren().add(tileSymbol);
                }

                int finalX = x;
                int finalY = y;
                tileGui.setOnMouseClicked(event -> updateSelectedRobot(finalX, finalY));

                gameGrid.add(tileGui, x, y);

            }
        }

        //Creation des Robots

        addRobot(RED);
        addRobot(GREEN);
        addRobot(BLUE);
        addRobot(YELLOW);

    }

    private void addRobot(Color color) {
        Robot robot = GameBoard.context.getRobots().get(color);
        ImageView robotGui = new ImageView( new Image(robot.getUrl(),50,50, false, true) );
        //Event Selection Robot
        robotGui.setOnMouseClicked (event -> GameBoard.context.processSelectRobot(color));
        gameGrid.add(robotGui, robot.getPosX(), robot.getPosY());
        // Association de l' "ImageView" avec le robot stocké dans le jeu
        robot.setGui(robotGui);
    }

    private void updateSelectedRobot(int x,int y){

        if (GameBoard.context.getStatus() == CHOOSE_TILE){
            //System.out.println(x + " "+y);
            if (GameBoard.context.checkDiagonal(x,y)){
                Robot robot = GameBoard.context.getSelectedRobot();

                Cell.Direction dir = GameBoard.context.getDirectionMouv(x,y);
                //System.out.println(dir);

                GameBoard.context.processDeplacement(dir);


                GridPane.setConstraints(robot.getGui(), GameBoard.context.getSelectedRobot().getPosX(),
                                                        GameBoard.context.getSelectedRobot().getPosY());
                //robot.setPos(x,y);
                //GameBoard.context.processDeplacement();

            }else{
                System.out.println("Mouvement impossible");
            }
            GameBoard.context.setStatus(CHOOSE_ROBOT);
        }

    }


}
