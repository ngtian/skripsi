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
import org.joda.time.LocalDate;
import org.sql2o.Connection;

/**
 *
 * @author Surianto
 */
public class mobil extends RecursiveTreeObject<mobil>{
    int id_mobil;
    String merk_mobil;
    String type_mobil;
    String jenis_mobil;
    String plat_mobil;

    private static ObservableList<mobil> mobilList = FXCollections.observableArrayList();
    
    public mobil(String merk_mobil, String type_mobil, String jenis_mobil, String plat_mobil) {
        this.merk_mobil = merk_mobil;
        this.type_mobil = type_mobil;
        this.jenis_mobil = jenis_mobil;
        this.plat_mobil = plat_mobil;
    }
    
    public static ObservableList<mobil> getMobil(){
        refreshDB();
        return mobilList;
    }
    
    private static void refreshDB() {
        try(Connection connection = DB.koneksi.sql2o.open()) {
            final String query = "SELECT * FROM mobil";
            mobilList.setAll(connection.createQuery(query).executeAndFetch(mobil.class));
        }
    }
    
    public boolean createMobil(){
        try (Connection connection = DB.koneksi.sql2o.open()) {
            final String query = "INSERT INTO mobil (merk_mobil,type_mobil,jenis_mobil,plat_mobil) VALUE (:merk_mobil, :type_mobil, :jenis_mobil, :plat_mobil)";
            connection.createQuery(query).bind(this).executeUpdate();
            if(connection.getResult()>0){
                refreshDB();
                return true;
            }
            return false;
        }
    }
    
    public boolean updateMobil(){
        try (Connection connection = DB.koneksi.sql2o.open()) {
            final String query = "UPDATE mobil SET `merk_mobil` = :merk_mobil, `type_mobil` = :type_mobil, `jenis_mobil` = :jenis_mobil, `plat_mobil` = :plat_mobil WHERE `id_mobil` = :id_mobil";
            connection.createQuery(query).bind(this).executeUpdate();
            if(connection.getResult()>0){
                refreshDB();
                return true;
            }
            return false;
        }
    }
    
    public boolean deleteMobil(){
        try (Connection connection = DB.koneksi.sql2o.open()) {
            final String query = "DELETE FROM mobil where id_mobil=:id_mobil";
            connection.createQuery(query).bind(this).executeUpdate();
            if(connection.getResult()>0){
                refreshDB();
                return true;
            }
            return false;
        }
    }
    
    public ObjectProperty<Integer> id_mobilProperty() {
        return new SimpleObjectProperty(id_mobil);
    }
    
    public StringProperty merk_mobilProperty() {
        return new SimpleStringProperty(merk_mobil);
    }
    
    public StringProperty type_mobilProperty() {
        return new SimpleStringProperty(type_mobil);
    }
    
    public StringProperty jenis_mobilProperty() {
        return new SimpleStringProperty(jenis_mobil);
    }
    
    public StringProperty plat_mobilProperty() {
        return new SimpleStringProperty(plat_mobil);
    }

    public int getId_mobil() {
        return id_mobil;
    }

    public void setId_mobil(int id_mobil) {
        this.id_mobil = id_mobil;
    }

    public String getMerk_mobil() {
        return merk_mobil;
    }

    public void setMerk_mobil(String merk_mobil) {
        this.merk_mobil = merk_mobil;
    }

    public String getType_mobil() {
        return type_mobil;
    }

    public void setType_mobil(String type_mobil) {
        this.type_mobil = type_mobil;
    }

    public String getJenis_mobil() {
        return jenis_mobil;
    }

    public void setJenis_mobil(String jenis_mobil) {
        this.jenis_mobil = jenis_mobil;
    }

    public String getPlat_mobil() {
        return plat_mobil;
    }

    public void setPlat_mobil(String plat_mobil) {
        this.plat_mobil = plat_mobil;
    }

    public static ObservableList<mobil> getMobilList() {
        return mobilList;
    }

    public static void setMobilList(ObservableList<mobil> mobilList) {
        mobil.mobilList = mobilList;
    }
    
}
