/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.input.MouseEvent;
import model.paket;
import static model.paket.getPaket;
import model.rupiah;

/**
 * FXML Controller class
 *
 * @author Surianto
 */
public class PaketController implements Initializable {

    @FXML
    private JFXTextField nama_paket;

    @FXML
    private JFXComboBox<String> jumlah;

    @FXML
    private JFXTextField harga_paket;

    @FXML
    private JFXTreeTableView<paket> tabel_paket;

    @FXML
    private JFXButton simpanButton;

    @FXML
    private JFXButton ubahButton;

    @FXML
    private JFXButton hapusButton;

    @FXML
    private JFXButton batalButton;

    private ObservableList<String> jumlahList;
    
    void resetForm(){
        nama_paket.setText("");
        jumlah.getSelectionModel().clearSelection();
        harga_paket.setText("");
    }
    
    void resetButton(){
        simpanButton.setDisable(true);
        ubahButton.setDisable(false);
        hapusButton.setDisable(false);
        batalButton.setDisable(false);
    }
    
    private void resetButton2(){
        simpanButton.setDisable(false);
        ubahButton.setDisable(true);
        hapusButton.setDisable(true);
        batalButton.setDisable(true);
    }
    
    @FXML
    void batalHandle(ActionEvent event) {
        resetForm();
        resetButton2();
    }

    @FXML
    void hapusHandle(ActionEvent event) {
        paket pkt = tabel_paket.getSelectionModel().getSelectedItem().getValue();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Hapus Data Paket");
        alert.setHeaderText(null);
        alert.setContentText("Apakah anda yakin untuk menghapus data paket?");
        Optional result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            Alert keluar = new Alert(Alert.AlertType.INFORMATION);
            keluar.setTitle("Data Paket");
            keluar.setHeaderText(null);
            if(pkt.deletePaket()){
                keluar.setContentText("data berhasil di hapus");
                keluar.show();
                resetButton2();
                resetForm();
            } else {
                keluar.setContentText("data gagal di hapus");
                keluar.show();
            }
        }
        else {
            alert.close();
        }
    }

    @FXML
    void mousepressedHandle(MouseEvent event) {
        if (event.getClickCount() == 1) {
            ambilData();
        }
    }

    void ambilData(){
        paket pkt = tabel_paket.getSelectionModel().getSelectedItem().getValue();
        nama_paket.setText(pkt.getNama_paket());
        int index = jumlahList.indexOf(pkt.getJumlah());
        jumlah.getSelectionModel().clearAndSelect(index);
        harga_paket.setText(String.valueOf(pkt.getHarga_paket()));
        resetButton();
    }
    
    private boolean validasi(){
        return !nama_paket.getText().isEmpty() && !jumlah.getSelectionModel().isEmpty() && !harga_paket.getText().isEmpty();
    }
    
    @FXML
    void simpanHandle(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Paket");
        if(validasi()){
            paket pkt = new paket(
                nama_paket.getText(),
                Integer.parseInt(jumlah.getSelectionModel().getSelectedItem()),
                Integer.parseInt(harga_paket.getText())
                );
            if(pkt.createPaket()){
                alert.setContentText("Data Berhasil Disimpan");
                alert.show();
                resetForm();
            }
        }else {
            alert.setContentText("Nama Paket, Jumlah  atau Harga Paket Kosong");
            alert.show();
        }
    }

    @FXML
    void ubahHandle(ActionEvent event) {
        if(validasi()){
            paket pkt = tabel_paket.getSelectionModel().getSelectedItem().getValue();
            pkt.setNama_paket(nama_paket.getText());
            pkt.setJumlah(Integer.parseInt(jumlah.getSelectionModel().getSelectedItem()));
            pkt.setHarga_paket(Integer.parseInt(harga_paket.getText()));
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Edit Data Paket");
            alert.setHeaderText(null);
            alert.setContentText("Apakah anda yakin untuk mengedit data paket?");
            Optional result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    if(pkt.updatePaket()){
                        Alert keluar = new Alert(Alert.AlertType.INFORMATION);
                        keluar.setTitle("Data Paket");
                        keluar.setHeaderText(null);
                        keluar.setContentText("data berhasil di ubah");
                        resetButton2();
                        keluar.show();
                        tabel_paket.refresh();
                    }
                resetForm();
            }
            else {
                alert.close();
            }     
        }
    }
    
    @Override
    
    public void initialize(URL url, ResourceBundle rb) {
        jumlahList = FXCollections.observableArrayList("10","8","7","6");
        jumlah.setItems(jumlahList);
        
        TreeTableColumn<paket, Integer> idCol = new TreeTableColumn<>("Id Paket");
        TreeTableColumn<paket, String> namaCol = new TreeTableColumn<>("Nama Paket");
        TreeTableColumn<paket, Integer> jumlahCol = new TreeTableColumn<>("Jumlah Pertemuan");
        TreeTableColumn<paket, String> hargaCol = new TreeTableColumn<>("Harga Paket");
        
        idCol.setCellValueFactory(param -> param.getValue().getValue().id_paketProperty());
        namaCol.setCellValueFactory(param -> param.getValue().getValue().nama_paketProperty());
        jumlahCol.setCellValueFactory(param -> param.getValue().getValue().jumlahProperty());
        hargaCol.setCellValueFactory(param -> param.getValue().getValue().harga_paketProperty());
                
        idCol.prefWidthProperty().bind(tabel_paket.prefWidthProperty().multiply(0.2));
        namaCol.prefWidthProperty().bind(tabel_paket.prefWidthProperty().multiply(0.4));
        jumlahCol.prefWidthProperty().bind(tabel_paket.prefWidthProperty().multiply(0.2));
        hargaCol.prefWidthProperty().bind(tabel_paket.prefWidthProperty().multiply(0.2));
        
        tabel_paket.getColumns().add(idCol);
        tabel_paket.getColumns().add(namaCol);
        tabel_paket.getColumns().add(jumlahCol);
        tabel_paket.getColumns().add(hargaCol);
        
        TreeItem<paket> paketRoot = new RecursiveTreeItem<>(getPaket(), RecursiveTreeObject::getChildren);
        
        tabel_paket.setRoot(paketRoot);
        tabel_paket.setShowRoot(false);
    }    
    
}
