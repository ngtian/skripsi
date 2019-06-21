/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import model.paket;

/**
 * FXML Controller class
 *
 * @author Surianto
 */
public class BayarController implements Initializable {

    @FXML
    private JFXTextField harga_paket;

    @FXML
    private JFXTextField uang_dibayar;

    @FXML
    private JFXTextField kembalian;
    
    boolean setHargaPaket(){
        if (paket.pkt != null) {
            harga_paket.setText(String.valueOf(paket.pkt.getHarga_paket()));
            return true;
        }
        return false;
    }
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        uang_dibayar.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null && !newValue.equals("")) {
                int kembali = Integer.parseInt(newValue) - paket.pkt.getHarga_paket();  
                kembalian.setText(String.valueOf(kembali));
            }
        });
    }    

    void reset() {
        harga_paket.setText("");
        uang_dibayar.setText("");
        kembalian.setText("");
    }
    
}
