package isep.ricochetrobot;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * Controleur qui permet de choisir les parametres de la partie
 */
public class BoardSelectionController implements Initializable {

    @FXML
    private Button launchButton;

    @FXML
    private ChoiceBox<String> selector1;
    @FXML
    private ChoiceBox<String> selector2;
    @FXML
    private ChoiceBox<String> selector3;
    @FXML
    private ChoiceBox<String> selector4;
    @FXML
    private TextField selectorTime;

    private final Board[] boards = Board.values();
    private Board[] selectedBoards;
    private Map<String,Board> recupBoard;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){

        selector1.setValue(String.valueOf(boards[1]));
        selector2.setValue(String.valueOf(boards[2]));
        selector3.setValue(String.valueOf(boards[3]));
        selector4.setValue(String.valueOf(boards[4]));

        recupBoard = new HashMap<>();

        for(Board board:boards){
            selector1.getItems().add(String.valueOf(board));
            selector2.getItems().add(String.valueOf(board));
            selector3.getItems().add(String.valueOf(board));
            selector4.getItems().add(String.valueOf(board));
            recupBoard.put(String.valueOf(board),board);
        }
        launchButton.setOnAction(actionEvent -> {
            if (checkBoard()) launchGameView();
            else showWarning("Vous devez selectioner 4 planches différentes");

        });
    }

    private void launchGameView(){

        selectedBoards = new Board[] {
                recupBoard.get(selector1.getValue()),
                recupBoard.get(selector2.getValue()),
                recupBoard.get(selector3.getValue()),
                recupBoard.get(selector4.getValue())
        };

        int time = Integer.parseInt(this.selectorTime.getText());

        GameBoard.start(selectedBoards,time);

        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("main-view.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage stage = new Stage();
        stage.setTitle("Main");
        stage.setScene(scene);
        stage.show();

        Stage stage1 = (Stage) launchButton.getScene().getWindow();
        stage1.close();
    }

    private boolean checkBoard(){
        return
                   selector1.getValue() != null
                && selector2.getValue() != null
                && selector3.getValue() != null
                && selector4.getValue() != null

                && !selector1.getValue().equals(selector2.getValue())
                && !selector1.getValue().equals(selector3.getValue())
                && !selector1.getValue().equals(selector4.getValue())
                && !selector2.getValue().equals(selector3.getValue())
                && !selector2.getValue().equals(selector4.getValue())
                && !selector3.getValue().equals(selector4.getValue());
    }

    private void showWarning(String message) {
        Alert startMessage = new Alert(Alert.AlertType.INFORMATION, message);
        startMessage.setHeaderText(null);
        startMessage.setGraphic(null);
        startMessage.showAndWait();
    }

}
