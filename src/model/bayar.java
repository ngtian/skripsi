/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.List;
import java.util.stream.Collectors;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.joda.time.LocalDate;
import org.sql2o.Connection;

/**
 *
 * @author Surianto
 */
public class bayar {
    int id_pembayaran;
    int id_pelanggan;
    int id_paket;

    private static final ObservableList<bayar> bayarList = FXCollections.observableArrayList();
    public static bayar byr;
    
    public bayar(int id_pelanggan, int id_paket) {
        this.id_pelanggan = id_pelanggan;
        this.id_paket = id_paket;
    }

    public static ObservableList<bayar> getBayar(){
        refreshDB();
        return bayarList;
    }
    
    private static void refreshDB() {
        try(Connection connection = DB.koneksi.sql2o.open()) {
            final String query = "SELECT * FROM pembayaran";
            bayarList.setAll(connection.createQuery(query).executeAndFetch(bayar.class));
        }
    }
    
    public boolean createBayar(){
        try (Connection connection = DB.koneksi.sql2o.open()) {
            final String query = "INSERT INTO pembayaran (id_pelanggan,id_paket) VALUE (:id_pelanggan, :id_paket)";
            connection.createQuery(query).bind(this).executeUpdate();
            if(connection.getResult()>0){
                this.id_pembayaran = connection.getKey(Integer.class);
                refreshDB();
                return true;
            }
            return false;
        }
    }
    
    public boolean updateBayar(){
        try (Connection connection = DB.koneksi.sql2o.open()) {
            final String query = "UPDATE pembayaran SET `id_pelanggan` = :id_pelanggan, `id_paket` = :id_paketWHERE `id_pembayaran` = :id_pembayaran";
            connection.createQuery(query).bind(this).executeUpdate();
            if(connection.getResult()>0){
                refreshDB();
                return true;
            }
            return false;
        }
    }

public static List <bayar> bayar (LocalDate dari, LocalDate sampai){
        return getBayar()
                .stream()
                .filter((t) -> {
                    LocalDate localDate = new LocalDate(registrasi.getReg(t).getTanggal_daftar());
                    return localDate.isAfter(dari) && localDate.isBefore(sampai) || localDate.equals(sampai);
                }).collect(Collectors.toList());
        }
    
    public int getId_pembayaran() {
        return id_pembayaran;
    }

    public void setId_pembayaran(int id_pembayaran) {
        this.id_pembayaran = id_pembayaran;
    }

    public int getId_pelanggan() {
        return id_pelanggan;
    }

    public void setId_pelanggan(int id_pelanggan) {
        this.id_pelanggan = id_pelanggan;
    }

    public int getId_paket() {
        return id_paket;
    }

    public void setId_paket(int id_paket) {
        this.id_paket = id_paket;
    }
   
}
