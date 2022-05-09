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

public class MainController implements Initializable {
    @FXML
    private Label text1;

    @FXML
    private GridPane gameGrid;

    private int[][] board ={
            {0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,1,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0},
            {0,6,0,0,0,0,0,0,0,5,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,5,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0}
    };

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

                String wallUrl = switch (board[x][y]) {
                    case 1 -> "WU.png";
                    case 2 -> "WR.png";
                    case 3 -> "WD.png";
                    case 4 -> "WL.png";
                    case 5 -> "WUL.png";
                    case 6 -> "WUR.png";
                    case 7 -> "WDL.png";
                    case 8 -> "WDR.png";
                    default -> null;
                };

                if(wallUrl != null){
                    Image wallImage = new Image(wallUrl, 50, 50, false, false);
                    ImageView tileWall = new ImageView(wallImage);
                    tileGui.getChildren().add(tileWall);
                }

                gameGrid.add(tileGui, x, y);

            }
        }


    }
}
