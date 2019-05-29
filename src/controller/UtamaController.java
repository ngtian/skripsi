/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Surianto
 */
public class UtamaController implements Initializable {

    @FXML
    private AnchorPane anchor;
    
    @FXML
    private ScrollPane sc;

    @FXML
    private Label tanggal;

    @FXML
    private Label waktu;
    
    @FXML
    private JFXButton petugasButton;

    @FXML
    private JFXButton mobilButton;

    @FXML
    private JFXButton paketButton;

    @FXML
    private JFXButton registrasiButton;

    @FXML
    private JFXButton laporanButton;
    
    @FXML
    private JFXButton logoutButton;

   
    
    AnchorPane tampilan1;
    AnchorPane tampilan2;
    AnchorPane tampilan3;
    AnchorPane tampilan4;
    AnchorPane tampilan5;
    
    @FXML
    void actionHandle(ActionEvent event) throws IOException {
        if (event.getSource() == petugasButton){
            sc.setContent(tampilan1);
        }else if (event.getSource() == mobilButton){
            sc.setContent(tampilan2);    
        }else if (event.getSource() == paketButton) {
            sc.setContent(tampilan3);
        }else if (event.getSource() == registrasiButton){
            sc.setContent(tampilan4);
        }else if (event.getSource() == laporanButton){
            sc.setContent(tampilan5);
        }else if(event.getSource() == logoutButton){
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Login.fxml"));
            Parent parent = loader.load();
            Stage stage = (Stage)logoutButton.getScene().getWindow();
            stage.setScene(new Scene(parent));
        }
    }

    


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Thread thread = new Thread(()->{
           while(!Thread.interrupted()){
               try {
                   Date date = new Date();
                   SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
                   tanggal.setText(simpleDateFormat.format(date));
                   Date dt = new Date();
                   SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
                   Platform.runLater(()-> waktu.setText(sdf.format(dt)));
                   Thread.sleep(1000);
               } catch (InterruptedException ex) {
                   Logger.getLogger(UtamaController.class.getName()).log(Level.SEVERE, null, ex);
               }
           }
        });
        thread.start();
        
        try {
            tampilan1 = FXMLLoader.load(getClass().getResource("/view/Petugas.fxml"));
        } catch (IOException ex) {
            Logger.getLogger(UtamaController.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            tampilan2 = FXMLLoader.load(getClass().getResource("/view/Mobil.fxml"));
        } catch (IOException ex) {
            Logger.getLogger(UtamaController.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            tampilan3 = FXMLLoader.load(getClass().getResource("/view/Paket.fxml"));
        } catch (IOException ex) {
            Logger.getLogger(UtamaController.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            tampilan4 = FXMLLoader.load(getClass().getResource("/view/KembaliLanjut.fxml"));
        } catch (IOException ex) {
            Logger.getLogger(UtamaController.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            tampilan5 = FXMLLoader.load(getClass().getResource("/view/Laporan.fxml"));
        } catch (IOException ex) {
            Logger.getLogger(UtamaController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    sc.setContent(tampilan1);
    }
}
