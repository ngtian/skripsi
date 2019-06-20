/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import model.bayar;
import model.laporan;

/**
 * FXML Controller class
 *
 * @author Surianto
 */
public class KembaliLanjutController implements Initializable {
    @FXML
    private JFXButton kembaliButton;

    @FXML
    private JFXButton lanjutButton;
    
    @FXML
    private ScrollPane scrollPane;
    
    private AnchorPane registrasi;
    private AnchorPane jadwal;
    private AnchorPane bayar;
    private AnchorPane konfirmasi;
    
    RegistrasiController RC;
    JadwalController JC;
    BayarController BC;
    KonfirmasiController KC;
    
    @FXML
    void kembaliHandle(ActionEvent event) {
        if (scrollPane.getContent() == konfirmasi) {
            lanjutButton.setText("Lanjut");
            scrollPane.setContent(bayar);
        } else if (scrollPane.getContent() == bayar) {
            scrollPane.setContent(jadwal);
        } else if (scrollPane.getContent() == jadwal) {
            scrollPane.setContent(registrasi);
        }
    }

    @FXML
    void lanjutHandle(ActionEvent event) throws IOException {
        if (scrollPane.getContent() == registrasi) {
            if (RC.simpanRegistrasi()) {
                scrollPane.setContent(jadwal);
            }
        } else if (scrollPane.getContent() == jadwal) {
            if (BC.setHargaPaket() && model.jadwal.getJadwal().size() == model.paket.pkt.getJumlah()) {
                scrollPane.setContent(bayar);
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Paket Belum diPilih atau Tanggal dan Waktu Belum Selesai Memilih");
                alert.show();
            }
            lanjutButton.setText("Konfirmasi");
        } else if (scrollPane.getContent() == bayar) {
            KC.setRegistrasi();
            lanjutButton.setText("Simpan");
            scrollPane.setContent(konfirmasi);
        } else if ((scrollPane.getContent() == konfirmasi)){
            scrollPane.setContent(konfirmasi);
            model.registrasi.reg.createPelanggan();
            model.jadwal.getJadwal().forEach((t) -> {
                t.setId_pelanggan(model.registrasi.reg.getId_pelanggan());
                t.createJadwal();
            });
            model.bayar.byr = new bayar(model.registrasi.reg.getId_pelanggan(),model.paket.pkt.getId_paket());
            model.bayar.byr.setId_pelanggan(model.registrasi.reg.getId_pelanggan());
            model.bayar.byr.createBayar();
            laporan.struk();
        } 
    }
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        FXMLLoader fxml = new FXMLLoader();
        fxml.setLocation(getClass().getResource("/view/Registrasi.fxml"));
        try {
            registrasi = fxml.load();
        } catch (IOException ex) {
            Logger.getLogger(KembaliLanjutController.class.getName()).log(Level.SEVERE, null, ex);
        }
        RC = fxml.getController();
    
        fxml = new FXMLLoader();
        fxml.setLocation(getClass().getResource("/view/Jadwal.fxml"));
        try {
            jadwal = fxml.load();
        } catch (IOException ex) {
            Logger.getLogger(KembaliLanjutController.class.getName()).log(Level.SEVERE, null, ex);
        }
        JC = fxml.getController();
        
        fxml = new FXMLLoader();
        fxml.setLocation(getClass().getResource("/view/Bayar.fxml"));
        try {
            bayar = fxml.load();
        } catch (IOException ex) {
            Logger.getLogger(KembaliLanjutController.class.getName()).log(Level.SEVERE, null, ex);
        }
        BC = fxml.getController();
        
        fxml = new FXMLLoader();
        fxml.setLocation(getClass().getResource("/view/Konfirmasi.fxml"));
        try {
            konfirmasi = fxml.load();
        } catch (IOException ex) {
            Logger.getLogger(KembaliLanjutController.class.getName()).log(Level.SEVERE, null, ex);
        }
        KC = fxml.getController();

        scrollPane.setContent(registrasi);
        
    }    
}
