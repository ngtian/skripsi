/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.login;

/**
 *
 * @author Surianto
 */
public class LoginController implements Initializable {
    
    @FXML
    private AnchorPane anchor;
    
    @FXML
    private JFXTextField username;

    @FXML
    private JFXPasswordField password;

    @FXML
    private JFXButton buttonLogin;

    @FXML
    private JFXButton buttonBatal;

    @FXML
    void ActionBatal(ActionEvent event) {
        System.exit(0);
    }

    @FXML
    void ActionLogin(ActionEvent event) throws IOException {
      login log = login.getLogin(username.getText());      
      if(log !=null){
            if(log.getPassword().equals(password.getText())){
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Utama.fxml"));
                Parent parent = loader.load();
                Stage stage = (Stage)buttonLogin.getScene().getWindow();
                stage.setScene(new Scene(parent));
                stage.centerOnScreen();
                
              } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Login Eror");
                alert.setContentText("Password salah");
                alert.show();
                password.requestFocus();
              }   
      }else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Login Eror");
            alert.setContentText("Username tidak ditemukan");
            alert.show();
            username.requestFocus();
      }

    }

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    
    
}
