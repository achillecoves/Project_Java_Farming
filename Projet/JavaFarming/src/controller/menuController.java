package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.scene.Node;


public class menuController {

    @FXML
    private Button btnPlay;

    @FXML
    private Button btnCredits;

    @FXML
    private Button btnQuit;

    public void initialize(){

        pressBtnPlay();
        pressBtnCredits();
        pressBtnQuit();
    }

    public void pressBtnPlay(){

        btnPlay.setOnAction(e -> {
            try {

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/game.fxml"));
                Parent root = loader.load();

                Stage stage = (Stage) btnPlay.getScene().getWindow();
                stage.setScene(new Scene(root, 850, 750));



            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
    }

    public void pressBtnCredits(){

        btnCredits.setOnAction(e -> {
            try {

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/credits.fxml"));
                Parent root = loader.load();

                Stage stage = (Stage) btnCredits.getScene().getWindow();
                stage.setScene(new Scene(root, 800, 600));



            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
    }

    public void pressBtnQuit() {

        btnQuit.setOnAction(e -> {
            Stage stage = (Stage) btnQuit.getScene().getWindow();
            stage.close();
        });
    }
}

