package controllers;

import db.Authorization;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.io.IOException;

import static controllers.EducationController.keyWord;
import static db.TimeCalc.getSeqForAuth;
import static main.Main.dataBase;

/**
 * Created by Sanak Alex on 27.05.2016.
 */
public class AuthController {
    public TextField loginText;
    public TextField phraseText;
    public Button checkLoginButton;
    public Button authButton;
    public Button exitButton;
    private long[] timeTries;
    private int currentLetter;
    private int tries = 3;
    private boolean withErrors = false;
    Authorization auth = new Authorization();

    public void goToStart() {
        Navigator.loadVista(Navigator.startWindow);
    }
    public void exit() {
        System.exit(1);
    }

    public void checkLogin() {
        if (loginText.getText().equals("")){
            Navigator.alertLogin();
        } else if (dataBase.checkLogin(loginText.getText())){
            dataBase.setCurrentUser(dataBase.searchUser(loginText.getText()));
            timeTries = new long[keyWord.length()];
            phraseText.setDisable(false);
            loginText.setDisable(true);
            checkLoginButton.setDisable(true);
            phraseText.setOnKeyReleased((e) -> {
                try{
                    if(e.getCode().isLetterKey() && e.getText().equals(String.valueOf(keyWord.charAt(currentLetter)))) {
                        timeTries[currentLetter++] = System.currentTimeMillis();
                        System.out.println(phraseText.getText());
                    } else {
                        System.out.println("!!!");
                        withErrors = true;
                    }
                } catch(Exception ex){
                    currentLetter = 0;
                    phraseText.setText("");
                }
                if(phraseText.getText().length() == keyWord.length()) {
                    if (!withErrors && phraseText.getText().equals(keyWord)) {
                        System.out.println("try is OK");
                        phraseText.setText("");
                        withErrors = false;
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("INFO");
                        alert.setHeaderText(null);
                        alert.setContentText("Press AUTHORIZE to check your authority");
                        alert.showAndWait();
                        phraseText.setDisable(true);
                        authButton.setDisable(false);
                    } else {
                        withErrors = false;
                        currentLetter = 0;
                        Navigator.alertInput();
                        phraseText.setText("");

                    }
                }
            });
        } else if(!dataBase.checkLogin(loginText.getText())){
            Navigator.alertLoginNotExist();
            loginText.setText("");
        }
    }

    public void authorize() throws IOException {
        if (auth.checkResult(getSeqForAuth(timeTries), dataBase.getCurrentUser().getM(), dataBase.getCurrentUser().getS())){
            dataBase.updateInfo(auth.getS(), auth.getM());
            dataBase.writeInDb();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Congratulations!");
            alert.setHeaderText(null);
            alert.setContentText("You are authorized! Press EXIT to complete");
            alert.showAndWait();
            exitButton.setDisable(false);
            checkLoginButton.setDisable(true);
            phraseText.setDisable(true);
            authButton.setDisable(true);
        }else {
            tries--;
            if (tries == 0){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("STOP");
                alert.setHeaderText(null);
                alert.setContentText("You missed all your tries! GO AWAY, HACKER!!!");
                alert.showAndWait();
                System.exit(1);
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Can't recognise you! Repeat, please\nYou have only " + tries + " from 3 tries");
                alert.showAndWait();
                withErrors = false;
                currentLetter = 0;
                phraseText.setText("");
                authButton.setDisable(true);
                phraseText.setDisable(false);
                checkLogin();
            }
        }
    }
}
