/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTimePicker;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.input.MouseEvent;
import model.jadwal;
import static model.jadwal.getJadwal;
import model.paket;
import model.registrasi;

/**
 * FXML Controller class
 *
 * @author Surianto
 */
public class JadwalController implements Initializable {

    private jadwal jdw;
    
    @FXML
    private JFXComboBox<paket> id_paket;

    @FXML
    private JFXTextField nama_paket;

    @FXML
    private JFXTextField jumlah;

    @FXML
    private JFXDatePicker tgl_per;

    @FXML
    private JFXTimePicker waktu;

    @FXML
    private JFXButton tambahButton;

    @FXML
    private JFXButton hapusButton;
     
    @FXML
    private JFXTreeTableView<jadwal> tabel_jadwal;
    
    @FXML
    void pilhpaketHandle(ActionEvent event) {
        paket.pkt = id_paket.getValue();
        if (paket.pkt != null) {
            nama_paket.setText(paket.pkt.getNama_paket());
            jumlah.setText(String.valueOf(paket.pkt.getJumlah()));
        }
    }

    private boolean validasi(){
        return id_paket.getSelectionModel().getSelectedItem()!=null && tgl_per.getValue()!=null && waktu.getValue()!=null;
    }
    
    @FXML
    void tambahJadwal(ActionEvent event) {
        if(validasi()){
            if(jadwal.getJadwal().size()< paket.pkt.getJumlah()){
                LocalDate localdate = tgl_per.getValue();
                LocalDateTime datelocation = localdate.atTime(waktu.getValue());
                ZonedDateTime sdt = datelocation.atZone(ZoneId.systemDefault());
                Date output = Date.from(sdt.toInstant());
                jadwal.getJadwal().add(new jadwal(
                    registrasi.reg.getId_pelanggan(),
                    paket.pkt.getId_paket(),
                    output
                ));
                tgl_per.setValue(null);
                waktu.setValue(null);
                tgl_per.requestFocus();
            }
        }
        else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setContentText("Paket atau Tanggal dan Waktu Belum di Pilih");
            alert.show();
            } 
        }    

    @FXML
    void hapusJadwal(ActionEvent event) {
        jadwal.getJadwal().remove(jdw);
        hapusButton.setDisable(true);
    }
    
    @FXML
    void onMousePressed(MouseEvent event) {
        if (tabel_jadwal.getSelectionModel().getSelectedItem() != null) {
            jdw = tabel_jadwal.getSelectionModel().getSelectedItem().getValue();
            hapusButton.setDisable(false);
        }
    }

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       TreeTableColumn<jadwal, String> tglCol = new TreeTableColumn<>("Tanggal Pertemuan");
       TreeTableColumn<jadwal, String> wktCol = new TreeTableColumn<>("Waktu Pertemuan");
       
       tglCol.setCellValueFactory(param -> param.getValue().getValue().tanggal_pertemuanProperty());
       wktCol.setCellValueFactory(param -> param.getValue().getValue().waktu_pertemuanProperty());

       tglCol.prefWidthProperty().bind(tabel_jadwal.prefWidthProperty().multiply(0.5));
       wktCol.prefWidthProperty().bind(tabel_jadwal.prefWidthProperty().multiply(0.5));
       
       tabel_jadwal.getColumns().add(tglCol);
       tabel_jadwal.getColumns().add(wktCol);
       
       TreeItem<jadwal> jadwalRoot = new RecursiveTreeItem<>(getJadwal(), RecursiveTreeObject::getChildren);
        
       tabel_jadwal.setRoot(jadwalRoot);
       tabel_jadwal.setShowRoot(false);
       
       id_paket.setItems(paket.getPaket());
    }    

    void reset() {
        id_paket.getSelectionModel().clearSelection();
        jumlah.setText("");
        nama_paket.setText("");
        tgl_per.setValue(null);
        waktu.setValue(null);
        getJadwal().clear();
    }
    
}
