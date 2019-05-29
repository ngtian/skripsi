/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.time.ZoneId;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import model.registrasi;

/**
 * FXML Controller class
 *
 * @author Surianto
 */
public class RegistrasiController implements Initializable {
    
    @FXML
    private JFXTextField nama_pel;

    @FXML
    private JFXTextField tempat_lahir;

    @FXML
    private JFXDatePicker tanggal_lahir;

    @FXML
    private JFXTextArea alamat_pel;

    @FXML
    private JFXComboBox<String> jenis_kel;

    @FXML
    private JFXTextField no_tlf;

    private ObservableList<String> jkList;
    
    private boolean validasi(){
        return !nama_pel.getText().isEmpty() && 
                !tempat_lahir.getText().isEmpty() && 
                tanggal_lahir.getValue() != null && 
                !alamat_pel.getText().isEmpty() && 
                !jenis_kel.getSelectionModel().isEmpty() && 
                !no_tlf.getText().isEmpty();
    }
   
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        jkList = FXCollections.observableArrayList("Laki-Laki","Perempuan");
        jenis_kel.setItems(jkList);
    }    

    boolean simpanRegistrasi() {
        if(validasi()){
           Date dates = Date.from(tanggal_lahir.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()); 
           registrasi.reg = new registrasi(
                    new Date(),
                    nama_pel.getText(),
                    tempat_lahir.getText(),
                    dates,
                    alamat_pel.getText(),
                    jenis_kel.getSelectionModel().getSelectedItem(),
                    no_tlf.getText()
            );
            System.out.println(registrasi.reg);
           return true;
        }else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Registrasi");
            alert.setContentText("Data ada yang kosong");
            alert.show();
            return false;
        }
    }
    
}
