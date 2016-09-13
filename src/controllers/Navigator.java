package controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;

import java.io.IOException;

/**
 * Created by Sanak Alex on 27.05.2016.
 */
public class Navigator {
    public static final String startWindow    = "../fxml/startWindow.fxml";
    static final String education = "../fxml/education.fxml";
    static final String authorization = "../fxml/authorization.fxml";


    private static startWindowController mainController;
    public static void setMainController(startWindowController mainController) {
        Navigator.mainController = mainController;
    }

    static void loadVista(String fxml) {
        try {
            mainController.setVistaHolder(FXMLLoader.load(Navigator.class.getResource(fxml)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    static void alertLogin(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Incorrect input!");
        alert.setHeaderText(null);
        alert.setContentText("Please enter LOGIN");
        alert.showAndWait();
    }
    static void alertLoginNotExist(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Incorrect input!");
        alert.setHeaderText(null);
        alert.setContentText("Such login doesn't exist");
        alert.showAndWait();
    }
    static void alertLoginExist(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Incorrect input!");
        alert.setHeaderText(null);
        alert.setContentText("Such login already exists");
        alert.showAndWait();
    }
    static void alertInput(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Incorrect input!");
        alert.setHeaderText(null);
        alert.setContentText("Try to repeat");
        alert.showAndWait();
    }
}
