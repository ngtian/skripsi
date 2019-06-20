/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import model.jadwal;
import static model.jadwal.getJadwal;
import model.paket;
import model.registrasi;

/**
 * FXML Controller class
 *
 * @author Surianto
 */
public class KonfirmasiController implements Initializable {

    @FXML
    private JFXTreeTableView<jadwal> tabel_Jadwal;

    @FXML
    private Label nama_pelanggan;

    @FXML
    private Label Tempat_Lahir;

    @FXML
    private Label Tanggal_Lahir;

    @FXML
    private Label Alamat;

    @FXML
    private Label Jenis_Kelamin;

    @FXML
    private Label No_Hp;

    @FXML
    private Label Nama_Paket;

    @FXML
    private Label jumlah_pertemuan;

    @FXML
    private Label Harga_Paket;

    void setRegistrasi(){
        nama_pelanggan.setText(registrasi.reg.getNama_pelanggan());
        Tempat_Lahir.setText(registrasi.reg.getTempat_lahir());
        Tanggal_Lahir.setText(registrasi.reg.getTanggal_lahir().toString());
        Alamat.setText(registrasi.reg.getAlamat());
        Jenis_Kelamin.setText(registrasi.reg.getJenis_kelamin());
        No_Hp.setText(registrasi.reg.getNo_hp());
        Nama_Paket.setText(paket.pkt.getNama_paket());
        jumlah_pertemuan.setText(String.valueOf(paket.pkt.getJumlah()));
        Harga_Paket.setText(String.valueOf(paket.pkt.getHarga_paket()));
    }
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       TreeTableColumn<jadwal, String> tglCol = new TreeTableColumn<>("Tanggal Pertemuan");
       TreeTableColumn<jadwal, String> wktCol = new TreeTableColumn<>("Waktu Pertemuan");
       
       tglCol.setCellValueFactory(param -> param.getValue().getValue().tanggal_pertemuanProperty());
       wktCol.setCellValueFactory(param -> param.getValue().getValue().waktu_pertemuanProperty());

       tglCol.prefWidthProperty().bind(tabel_Jadwal.prefWidthProperty().multiply(0.5));
       wktCol.prefWidthProperty().bind(tabel_Jadwal.prefWidthProperty().multiply(0.5));
       
       tabel_Jadwal.getColumns().add(tglCol);
       tabel_Jadwal.getColumns().add(wktCol);
       
       TreeItem<jadwal> jadwalRoot = new RecursiveTreeItem<>(getJadwal(), RecursiveTreeObject::getChildren);
        
       tabel_Jadwal.setRoot(jadwalRoot);
       tabel_Jadwal.setShowRoot(false);
       
    }        
}
