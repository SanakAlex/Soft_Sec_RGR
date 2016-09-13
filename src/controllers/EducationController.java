package controllers;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;

import static db.TimeCalc.getSeqOfTime;
import static main.Main.dataBase;

/**
 * Created by Sanak Alex on 26.05.2016.
 */
public class EducationController{
    public TextField phraseText;
    public TextField loginText;
    public Button finishButton;
    public Label triesLabel;
    public Button checkLoginButton;
    private String newLogin;
    private int tryNum = 1;
    public static int maxTries = 5;
    private long[][] timeTries;
    private int currentLetter;

    static final String keyWord = "longcode";
    private boolean withErrors = false;


    public void goToStart() {
        Navigator.loadVista(Navigator.startWindow);
    }
    public void exit() {
        System.exit(1);
    }

    public void finish() throws IOException {
        double s = getSeqOfTime(timeTries)[0];
        double m = getSeqOfTime(timeTries)[1];
        System.out.println(s);
        System.out.println(m);
        dataBase.addUser(newLogin, s,m);
        dataBase.writeInDb();
        Navigator.loadVista(Navigator.startWindow);
    }
    public void checkLogin() {
        if (loginText.getText().equals("")){
            Navigator.alertLogin();
        } else if (!dataBase.checkLogin(loginText.getText())){
            newLogin = loginText.getText();
            timeTries = new long[maxTries][keyWord.length()];
            phraseText.setDisable(false);
            loginText.setDisable(true);
            checkLoginButton.setDisable(true);
            triesLabel.setText("Control phrase:("+tryNum+"/"+maxTries+" tries)");
            System.out.println(newLogin);
            phraseText.setOnKeyReleased((e) -> {
                try{
                    if(e.getCode().isLetterKey() && e.getText().equals(String.valueOf(keyWord.charAt(currentLetter)))) {
                        timeTries[tryNum-1][currentLetter++] = System.currentTimeMillis();
                        System.out.println(phraseText.getText());
                    } else {
                        System.out.println("!!!");
                        withErrors = true;
                    }
                    if(phraseText.getText().length() == keyWord.length()){
                        System.out.println("try is OK");
                        if(!withErrors && phraseText.getText().equals(keyWord)){
                            tryNum++;
                            triesLabel.setText("Control phrase:("+tryNum+"/"+maxTries+" tries)");
                            currentLetter = 0;
                            phraseText.setText("");
                            withErrors = false;
                        } else {
                            withErrors = false;
                            Navigator.alertInput();
                            triesLabel.setText("Control phrase:("+tryNum+"/"+maxTries+" tries)");
                            currentLetter = 0;
                            phraseText.setText("");
                            withErrors = false;
                        }
                    }
                } catch(Exception ex){
                    triesLabel.setText("Control phrase:("+tryNum+"/"+maxTries+" tries)");
                    currentLetter = 0;
                    phraseText.setText("");
                }
                if(tryNum == maxTries){
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Congratulations!");
                    alert.setHeaderText(null);
                    alert.setContentText("You just have finished registration. Press FINISH to complete");
                    alert.showAndWait();
                    finishButton.setDisable(false);
                    phraseText.setDisable(true);
                }
            });
        } else if (dataBase.checkLogin(loginText.getText())){
            Navigator.alertLoginExist();
            loginText.setText("");
        }
    }
}
