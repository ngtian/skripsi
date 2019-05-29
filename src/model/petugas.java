/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import java.util.List;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.sql2o.Connection;

/**
 *
 * @author Surianto
 */
public class petugas extends RecursiveTreeObject<petugas>{
    int id_petugas;
    String nama_petugas;
    String nohp_petugas;

    private static ObservableList<petugas> petugasList = FXCollections.observableArrayList();
    
    public petugas(String nama_petugas, String nohp_petugas) {
        this.nama_petugas = nama_petugas;
        this.nohp_petugas = nohp_petugas;
    }

    public static ObservableList<petugas> getPetugas(){
        refreshDB();
        return petugasList;
    }
    
    private static void refreshDB() {
        try(Connection connection = DB.koneksi.sql2o.open()) {
            final String query = "SELECT * FROM petugas";
            petugasList.setAll(connection.createQuery(query).executeAndFetch(petugas.class));
        }
    }
    
    public boolean createPetugas(){
        try (Connection connection = DB.koneksi.sql2o.open()) {
            final String query = "INSERT INTO petugas (nama_petugas,nohp_petugas) VALUE (:nama_petugas, :nohp_petugas)";
            connection.createQuery(query).bind(this).executeUpdate();
            if(connection.getResult()>0){
                refreshDB();
                return true;
            }
            return false;
        }
    }
    
    public boolean updatePetugas(){
        try (Connection connection = DB.koneksi.sql2o.open()) {
            final String query = "UPDATE petugas SET `nama_petugas` = :nama_petugas, `nohp_petugas` = :nohp_petugas WHERE `id_petugas` = :id_petugas";
            connection.createQuery(query).bind(this).executeUpdate();
            if(connection.getResult()>0){
                refreshDB();
                return true;
            }
            return false;
        }
    }
    
    public boolean deletePetugas(){
        try (Connection connection = DB.koneksi.sql2o.open()) {
            final String query = "DELETE FROM petugas where id_petugas=:id_petugas";
            connection.createQuery(query).bind(this).executeUpdate();
            if(connection.getResult()>0){
                refreshDB();
                return true;
            }
            return false;
        }
    }
    
    public ObjectProperty<Integer> id_petugasProperty() {
        return new SimpleObjectProperty(id_petugas);
    }
    
    public StringProperty nama_petugasProperty() {
        return new SimpleStringProperty(nama_petugas);
    }
    
    public StringProperty nohp_petugasProperty() {
        return new SimpleStringProperty(nohp_petugas);
    }
    
    public int getId_petugas() {
        return id_petugas;
    }

    public void setId_petugas(int id_petugas) {
        this.id_petugas = id_petugas;
    }

    public String getNama_petugas() {
        return nama_petugas;
    }

    public void setNama_petugas(String nama_petugas) {
        this.nama_petugas = nama_petugas;
    }

    public String getNohp_petugas() {
        return nohp_petugas;
    }

    public void setNohp_petugas(String nohp_petugas) {
        this.nohp_petugas = nohp_petugas;
    }
    
    
}
