package isep.ricochetrobot;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApplication extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        //GameBoard.start();
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("boardSelection-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Main");
        stage.setScene(scene);
        stage.show();

    }


    public  static void main(String[] args ) {
        launch();
    }
}
