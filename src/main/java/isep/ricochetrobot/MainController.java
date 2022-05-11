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
        //robotGui.setOnMouseClicked
        //        (event -> GameBoard.context.processSelectRobot(color));
        gameGrid.add(robotGui, robot.getPosX(), robot.getPosY());
        // Association de l' "ImageView" avec le robot stocké dans le jeu
        robot.setGui(robotGui);
    }
}
