package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.scene.Node;


public class creditsController {

    @FXML
    private Button btnReturnCredits;


    public void initialize(){

        pressBtnReturnCredits();
    }

    public void pressBtnReturnCredits(){

        btnReturnCredits.setOnAction(e -> {
            try {

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/menu.fxml"));
                Parent root = loader.load();

                Stage stage = (Stage) btnReturnCredits.getScene().getWindow();
                stage.setScene(new Scene(root, 800, 600));



            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
    }

}

