/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import java.net.URL;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import com.jfoenix.controls.JFXDatePicker;
import java.io.IOException;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import model.bayar;
import model.jadwal;
import model.laporan;
import model.registrasi;
import org.joda.time.LocalDate;
/**
 * FXML Controller class
 *
 * @author Surianto
 */
public class LaporanController implements Initializable {

    @FXML
    private JFXDatePicker dari;

    @FXML
    private JFXDatePicker sampai;

    @FXML
    private JFXComboBox<String> laporanCB;

    @FXML
    private JFXButton proses;
    
    private ObservableList<String> laporanList;

    @FXML
    void pilihAction(ActionEvent event) {
        if(laporanCB.getSelectionModel().getSelectedItem().equals("Laporan Data Mobil")){
                    dari.setDisable(true);
                    sampai.setDisable(true);
                } else {
                    dari.setDisable(false);
                    sampai.setDisable(false);
        }
    }
    
    @FXML
    void prosesLaporan(ActionEvent event) {
        Thread thread = new Thread(() ->{
            try{
                if(laporanCB.getSelectionModel().getSelectedItem().equals("Laporan Data Pelanggan")){
                    List<registrasi> reglist = registrasi.registrasi(getDariDate(), getSampaiDate());
                    laporan.daftar_pelanggan(reglist);
                }
                if(laporanCB.getSelectionModel().getSelectedItem().equals("Laporan Data Mobil")){
                    laporan.daftar_mobil();
                }
                if(laporanCB.getSelectionModel().getSelectedItem().equals("Laporan Jadwal")){
                    List<jadwal> jadwallist = jadwal.jadwal(getDariDate(), getSampaiDate());
                    laporan.daftar_jadwal(jadwallist);
                }
                if(laporanCB.getSelectionModel().getSelectedItem().equals("Laporan Pembayaran")){
                    List<bayar> bayarlist = bayar.bayar(getDariDate(), getSampaiDate());
                    laporan.daftar_pembayaran(bayarlist);
                }
            } catch (IOException e){
                e.printStackTrace();
            }
        });
        thread.start();
    }
    
    private LocalDate getDariDate() {
        if (dari.getValue() == null) return new LocalDate().minusMonths(1);
        else {
            Date dr = Date.from(dari.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
            return new LocalDate(dr);
        }
    }

    private LocalDate getSampaiDate() {
        if (sampai.getValue() == null) return new LocalDate();
        else {
            Date smp = Date.from(sampai.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
            return new LocalDate(smp);
        }
    }

    /**
    * Initializes the controller class.
    */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        laporanList = FXCollections.observableArrayList("Laporan Data Pelanggan","Laporan Data Mobil","Laporan Jadwal","Laporan Pembayaran");
        laporanCB.setItems(laporanList);
    }    
}
