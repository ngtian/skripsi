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
import model.mobil;
import static model.mobil.getMobil;

/**
 * FXML Controller class
 *
 * @author Surianto
 */
public class MobilController implements Initializable {

    @FXML
    private JFXTextField merk_mobil;

    @FXML
    private JFXTextField type_mobil;

    @FXML
    private JFXComboBox<String> jenis_mobil;
    
    @FXML
    private JFXTextField plat_mobil;

    @FXML
    private JFXButton simpanbutton;

    @FXML
    private JFXButton ubahbutton;

    @FXML
    private JFXButton hapusbutton;

    @FXML
    private JFXButton batalbutton;

    @FXML
    private JFXTreeTableView<mobil> tabelmobil;
    
    private ObservableList<String> jenisList;
    
    void resetForm(){
        merk_mobil.setText("");
        type_mobil.setText("");
        jenis_mobil.getSelectionModel().clearSelection();
        plat_mobil.setText("");
    }
    
    void resetButton(){
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
    void batalbutton(ActionEvent event) {
        resetForm();
        resetButton2();
    }

     @FXML
    void mousepressedHandle(MouseEvent event) {
        if (event.getClickCount() == 1) {
            ambilData();
        }
    }
    
    void ambilData(){
        mobil mbl = tabelmobil.getSelectionModel().getSelectedItem().getValue();
        merk_mobil.setText(mbl.getMerk_mobil());
        type_mobil.setText(mbl.getType_mobil());
        int index = jenisList.indexOf(mbl.getJenis_mobil());
        jenis_mobil.getSelectionModel().clearAndSelect(index);
        plat_mobil.setText(mbl.getPlat_mobil());
        resetButton();
    }
    
    @FXML
    void hapushandle(ActionEvent event) {
        mobil mbl = tabelmobil.getSelectionModel().getSelectedItem().getValue();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Hapus Data Mobil");
        alert.setHeaderText(null);
        alert.setContentText("Apakah anda yakin untuk menghapus data mobil?");
        Optional result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            Alert keluar = new Alert(Alert.AlertType.INFORMATION);
            keluar.setTitle("Data Mobil");
            keluar.setHeaderText(null);
            if(mbl.deleteMobil()){
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
        return !merk_mobil.getText().isEmpty() && !type_mobil.getText().isEmpty() && !jenis_mobil.getSelectionModel().isEmpty() && !plat_mobil.getText().isEmpty();
    }
    
    @FXML
    void simpanhandle(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Mobil");
        if(validasi()){
           mobil mbl = new mobil(
                merk_mobil.getText(),
                type_mobil.getText(),
                jenis_mobil.getSelectionModel().getSelectedItem(),
                plat_mobil.getText()
                );
            if(mbl.createMobil()){
                alert.setContentText("Data Berhasil Disimpan");
                alert.show();
                resetForm();
            }
        }else {
            alert.setContentText("Merk, Type, Jenis atau Plat Mobil Kosong");
            alert.show();
        }
    }

    @FXML
    void ubahhandle(ActionEvent event) {
        if(validasi()){
            mobil mbl = tabelmobil.getSelectionModel().getSelectedItem().getValue();
            mbl.setMerk_mobil(merk_mobil.getText());
            mbl.setType_mobil(type_mobil.getText());
            mbl.setJenis_mobil(jenis_mobil.getSelectionModel().getSelectedItem());
            mbl.setPlat_mobil(plat_mobil.getText());
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Edit Data Mobil");
            alert.setHeaderText(null);
            alert.setContentText("Apakah anda yakin untuk mengedit data mobil?");
            Optional result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    if(mbl.updateMobil()){
                        Alert keluar = new Alert(Alert.AlertType.INFORMATION);
                        keluar.setTitle("Data Mobil");
                        keluar.setHeaderText(null);
                        keluar.setContentText("data berhasil di ubah");
                        resetButton2();
                        keluar.show();
                        tabelmobil.refresh();
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
        jenisList = FXCollections.observableArrayList("Manual","Matic");
        jenis_mobil.setItems(jenisList);
        
        TreeTableColumn<mobil, Integer> idCol = new TreeTableColumn<>("Id Mobil");
        TreeTableColumn<mobil, String> merkCol = new TreeTableColumn<>("Merk Mobil");
        TreeTableColumn<mobil, String> typeCol = new TreeTableColumn<>("Type Mobil");
        TreeTableColumn<mobil, String> jenisCol = new TreeTableColumn<>("Jenis Mobil");
        TreeTableColumn<mobil, String> platCol = new TreeTableColumn<>("Plat Mobil");
        
        idCol.setCellValueFactory(param -> param.getValue().getValue().id_mobilProperty());
        merkCol.setCellValueFactory(param -> param.getValue().getValue().merk_mobilProperty());
        typeCol.setCellValueFactory(param -> param.getValue().getValue().type_mobilProperty());
        jenisCol.setCellValueFactory(param -> param.getValue().getValue().jenis_mobilProperty());
        platCol.setCellValueFactory(param -> param.getValue().getValue().plat_mobilProperty());
        
        idCol.prefWidthProperty().bind(tabelmobil.prefWidthProperty().multiply(0.2));
        merkCol.prefWidthProperty().bind(tabelmobil.prefWidthProperty().multiply(0.2));
        typeCol.prefWidthProperty().bind(tabelmobil.prefWidthProperty().multiply(0.2));
        jenisCol.prefWidthProperty().bind(tabelmobil.prefWidthProperty().multiply(0.2));
        platCol.prefWidthProperty().bind(tabelmobil.prefWidthProperty().multiply(0.2));
        
        tabelmobil.getColumns().add(idCol);
        tabelmobil.getColumns().add(merkCol);
        tabelmobil.getColumns().add(typeCol);
        tabelmobil.getColumns().add(jenisCol);
        tabelmobil.getColumns().add(platCol);
        
        TreeItem<mobil> mobilRoot = new RecursiveTreeItem<>(getMobil(), RecursiveTreeObject::getChildren);
        
        tabelmobil.setRoot(mobilRoot);
        tabelmobil.setShowRoot(false);
        
    }    
    
}
