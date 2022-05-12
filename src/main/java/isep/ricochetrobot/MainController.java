package isep.ricochetrobot;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

import java.lang.annotation.Target;
import java.net.URL;
import java.util.ResourceBundle;

import static isep.ricochetrobot.Color.*;
import static isep.ricochetrobot.GameBoard.Status.*;

public class MainController implements Initializable {

    @FXML
    private Label textCount;

    @FXML
    private Label textTimer;

    @FXML
    private GridPane gameGrid;

    @FXML
    private StackPane targetDisplayed;

    @FXML
    private Button resetButton;

    @FXML
    private Button  newTargetButton;

    Cell [][] board = GameBoard.context.getCells();
    Symbol[][] symbols = GameBoard.context.getSymbols();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        this.newTargetButton.setOnAction(event -> {
            resetRobot();
            this.newTarget();
        } );

        this.resetButton.setOnAction(event -> resetRobot());

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

        //Affichage de la cible

        this.showTarget();

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

                this.setCount(GameBoard.context.getCount()+1);

                if (GameBoard.context.checkWin()){
                    this.showWarning("Vous avez gagné en " + GameBoard.context.getCount() + " coups");
                    this.newTarget();
                }

            }else{
                System.out.println("Mouvement impossible");
            }
            GameBoard.context.setStatus(CHOOSE_ROBOT);
        }

    }

    private void setCount(int count){
        GameBoard.context.setCount(count);
        StringBuilder sb = new StringBuilder();
        sb.append(count);
        this.textCount.setText(sb.toString());

    }

    private void showWarning(String message) {
        Alert startMessage
                = new Alert(Alert.AlertType.INFORMATION, message);
        startMessage.setHeaderText(null);
        startMessage.setGraphic(null);
        startMessage.showAndWait();
    }

    private void showTarget(){
        Image targetImage= new Image(GameBoard.context.getSelectedSymbol().getUrl(), 100, 100, false, false);
        ImageView targetGui = new ImageView(targetImage);
        Image centerImage= new Image("center.png", 100, 100, false, false);
        ImageView tileBackground = new ImageView(centerImage);
        targetDisplayed.getChildren().add(tileBackground);
        targetDisplayed.getChildren().add(targetGui);
    }

    private void newTarget(){
        GameBoard.context.processSelectSelectedSymbol();
        this.showTarget();
        GameBoard.context.setRobotsOrigin();
    }

    private void resetRobot(){
        GameBoard.context.restoreRobotOrigin();
        for(Robot robot : GameBoard.context.getRobots().values()){
            GridPane.setConstraints(robot.getGui(),robot.getPosX(),robot.getPosY());
        }
        this.setCount(0);
    }


}
