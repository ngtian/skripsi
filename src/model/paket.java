/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import java.util.List;
import java.util.stream.Collectors;
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
public class paket extends RecursiveTreeObject<paket>{
    int id_paket;
    String nama_paket;
    int jumlah;
    int harga_paket;

    private static final ObservableList<paket> paketList = FXCollections.observableArrayList();
    public static paket pkt;
    
    public paket(String nama_paket, int jumlah, int harga_paket) {
        this.nama_paket = nama_paket;
        this.jumlah = jumlah;
        this.harga_paket = harga_paket;
    }
    
    
    public static ObservableList<paket> getPaket(){
        refreshDB();
        return paketList;
    }
    
    private static void refreshDB() {
        try(Connection connection = DB.koneksi.sql2o.open()) {
            final String query = "SELECT * FROM paket";
            paketList.setAll(connection.createQuery(query).executeAndFetch(paket.class));
        }
    }
    
    public boolean createPaket(){
        try (Connection connection = DB.koneksi.sql2o.open()) {
            final String query = "INSERT INTO paket (nama_paket,jumlah,harga_paket) VALUE (:nama_paket, :jumlah, :harga_paket)";
            connection.createQuery(query).bind(this).executeUpdate();
            if(connection.getResult()>0){
                refreshDB();
                return true;
            }
            return false;
        }
    }
    
    public boolean updatePaket(){
        try (Connection connection = DB.koneksi.sql2o.open()) {
            final String query = "UPDATE paket SET `nama_paket` = :nama_paket, `jumlah` = :jumlah, `harga_paket` = :harga_paket WHERE `id_paket` = :id_paket";
            connection.createQuery(query).bind(this).executeUpdate();
            if(connection.getResult()>0){
                refreshDB();
                return true;
            }
            return false;
        }
    }
    
    public boolean deletePaket(){
        try (Connection connection = DB.koneksi.sql2o.open()) {
            final String query = "DELETE FROM paket where id_paket=:id_paket";
            connection.createQuery(query).bind(this).executeUpdate();
            if(connection.getResult()>0){
                refreshDB();
                return true;
            }
            return false;
        }
    }
    
    public static paket getPaket(jadwal jdw) {
        return getPaket()
                .stream()
                .filter(paket -> paket.id_paket==jdw.getId_paket())
                .findFirst()
                .orElse(null);
    }
    
    public static paket getPaket(bayar byr) {
        return getPaket()
                .stream()
                .filter(paket -> paket.id_paket==byr.getId_paket())
                .findFirst()
                .orElse(null);
    }
    
    public ObjectProperty<Integer> id_paketProperty() {
        return new SimpleObjectProperty(id_paket);
    }
    
    public StringProperty nama_paketProperty() {
        return new SimpleStringProperty(nama_paket);
    }
    
    public ObjectProperty<Integer> jumlahProperty() {
        return new SimpleObjectProperty(jumlah);
    }
    
    public StringProperty harga_paketProperty() {
        return new SimpleStringProperty(rupiah.rupiah(harga_paket));
    }

    public int getId_paket() {
        return id_paket;
    }

    public void setId_paket(int id_paket) {
        this.id_paket = id_paket;
    }

    public String getNama_paket() {
        return nama_paket;
    }

    public void setNama_paket(String nama_paket) {
        this.nama_paket = nama_paket;
    }

    public int getJumlah() {
        return jumlah;
    }

    public void setJumlah(int jumlah) {
        this.jumlah = jumlah;
    }

    public int getHarga_paket() {
        return harga_paket;
    }

    public void setHarga_paket(int harga_paket) {
        this.harga_paket = harga_paket;
    }

    @Override
    public String toString() {
        return "Paket " + id_paket;
    }
    
}
