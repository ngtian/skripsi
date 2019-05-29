/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.input.MouseEvent;
import model.petugas;
import static model.petugas.getPetugas;

/**
 * FXML Controller class
 *
 * @author Surianto
 */
public class PetugasController implements Initializable {

    @FXML
    private JFXTextField nama_petugas;

    @FXML
    private JFXTextField nohp_petugas;

    @FXML
    private JFXButton simpanbutton;

    @FXML
    private JFXButton ubahbutton;

    @FXML
    private JFXButton hapusbutton;

    @FXML
    private JFXButton batalbutton;

    @FXML
    private JFXTreeTableView<petugas> tabelpetugas;

    @FXML
    void mousePressedHandle(MouseEvent event) {
        if (event.getClickCount() == 1) {
            ambilData();
        }
    }
    
    private void ambilData() {
        petugas ptg = tabelpetugas.getSelectionModel().getSelectedItem().getValue();
        nama_petugas.setText(ptg.getNama_petugas());
        nohp_petugas.setText(ptg.getNohp_petugas());
        resetButton();
    }
    
    private void resetForm(){
        nama_petugas.setText("");
        nohp_petugas.setText("");
    }
    
    private void resetButton(){
        simpanbutton.setDisable(true);
        ubahbutton.setDisable(false);
        hapusbutton.setDisable(false);
        batalbutton.setDisable(false);
    }
    
    private void resetButton2(){
        simpanbutton.setDisable(false);
        ubahbutton.setDisable(true);
        hapusbutton.setDisable(true);
        batalbutton.setDisable(true);
    }
    
    @FXML
    void batalHandle(ActionEvent event) {
        resetForm();
        resetButton2();

    }

    @FXML
    void hapusHandle(ActionEvent event) {
        petugas ptg = tabelpetugas.getSelectionModel().getSelectedItem().getValue();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Hapus Data Petugas");
        alert.setHeaderText(null);
        alert.setContentText("Apakah anda yakin untuk menghapus data petugas?");
        Optional result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            Alert keluar = new Alert(Alert.AlertType.INFORMATION);
            keluar.setTitle("Data Petugas");
            keluar.setHeaderText(null);
            if(ptg.deletePetugas()){
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

    private boolean validasi(){
        return !nama_petugas.getText().isEmpty() && !nohp_petugas.getText().isEmpty();
    }
    
    @FXML
    void simpanHandle(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Petugas");
        if(validasi()){
            petugas ptg = new petugas(
                nama_petugas.getText(),
                nohp_petugas.getText()
                );
            if(ptg.createPetugas()){
                alert.setContentText("Data Berhasil Disimpan");
                alert.show();
                resetForm();
            }
        }else {
            alert.setContentText("Nama Petugas atau No HP Kosong");
            alert.show();
        }
    }

    @FXML
    void ubahHandle(ActionEvent event) {
        if(validasi()){
            petugas ptg = tabelpetugas.getSelectionModel().getSelectedItem().getValue();
            ptg.setNama_petugas(nama_petugas.getText());
            ptg.setNohp_petugas(nohp_petugas.getText());
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Edit Data Petugas");
            alert.setHeaderText(null);
            alert.setContentText("Apakah anda yakin untuk mengedit data petugas?");
            Optional result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    if(ptg.updatePetugas()){
                        Alert keluar = new Alert(Alert.AlertType.INFORMATION);
                        keluar.setTitle("Data Petugas");
                        keluar.setHeaderText(null);
                        keluar.setContentText("data berhasil di ubah");
                        resetButton2();
                        keluar.show();
                        tabelpetugas.refresh();
                    }
                resetForm();
            }
            else {
                alert.close();
            }     
        }
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        TreeTableColumn<petugas, Integer> idCol = new TreeTableColumn<>("Id Petugas");
        TreeTableColumn<petugas, String> namaCol = new TreeTableColumn<>("Nama Petugas");
        TreeTableColumn<petugas, String> nohpCol = new TreeTableColumn<>("No HP Petugas");
        
        idCol.setCellValueFactory(param -> param.getValue().getValue().id_petugasProperty());
        namaCol.setCellValueFactory(param -> param.getValue().getValue().nama_petugasProperty());
        nohpCol.setCellValueFactory(param -> param.getValue().getValue().nohp_petugasProperty());
        
        idCol.prefWidthProperty().bind(tabelpetugas.prefWidthProperty().multiply(0.2));
        namaCol.prefWidthProperty().bind(tabelpetugas.prefWidthProperty().multiply(0.5));
        nohpCol.prefWidthProperty().bind(tabelpetugas.prefWidthProperty().multiply(0.3));
        
        tabelpetugas.getColumns().add(idCol);
        tabelpetugas.getColumns().add(namaCol);
        tabelpetugas.getColumns().add(nohpCol);
        
        TreeItem<petugas> petugasRoot = new RecursiveTreeItem<>(getPetugas(), RecursiveTreeObject::getChildren);
        
        tabelpetugas.setRoot(petugasRoot);
        tabelpetugas.setShowRoot(false);
        
    }    
    
}
