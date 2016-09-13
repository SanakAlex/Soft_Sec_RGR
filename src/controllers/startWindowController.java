package controllers;

import javafx.scene.Node;
import javafx.scene.layout.GridPane;


public class startWindowController{
    public GridPane vistaHolder;
    void setVistaHolder(Node node) {vistaHolder.getChildren().setAll(node);}


    public void exit() {
        System.exit(1);
    }
    public void goToEduc() {
        Navigator.loadVista(Navigator.education);
    }

    public void goToAuth() {
        Navigator.loadVista(Navigator.authorization);
    }
}
