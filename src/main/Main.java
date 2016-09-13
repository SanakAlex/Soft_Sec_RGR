package main;

import controllers.Navigator;
import controllers.startWindowController;
import db.DataBase;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    public static DataBase dataBase;

    @Override
    public void start(Stage stage) throws Exception{
        stage.setTitle("Program");
        stage.setScene(createScene(loadMainPane()));
        stage.show();
    }
    private Parent loadMainPane() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        Parent mainPane = loader.load(getClass().getResourceAsStream(Navigator.startWindow));
        startWindowController mainController = loader.getController();
        Navigator.setMainController(mainController);
        return mainPane;
    }
    private Scene createScene(Parent mainPane) {
        return new Scene(mainPane);
    }

    public static void main(String[] args) throws IOException {
        dataBase = new DataBase();
        dataBase.readFromFile();
        launch(args);

    }
}
