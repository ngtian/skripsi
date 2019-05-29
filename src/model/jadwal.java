/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import java.util.Date;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.sql2o.Connection;

/**
 *
 * @author Surianto
 */
public class jadwal extends RecursiveTreeObject<jadwal>{
    int id_jadwal;
    int id_pelanggan;
    int id_paket;
    Date tanggal_pertemuan;

    private static ObservableList<jadwal> jadwalList = FXCollections.observableArrayList();

    public jadwal(int id_pelanggan, int id_paket, Date tanggal_pertemuan) {
        this.id_pelanggan = id_pelanggan;
        this.id_paket = id_paket;
        this.tanggal_pertemuan = tanggal_pertemuan;
    }
    
    public static ObservableList<jadwal> getJadwal(){
//        refreshDB();
        return jadwalList;
    }
    
//    private static void refreshDB() {
//        try(Connection connection = DB.koneksi.sql2o.open()) {
//            final String query = "SELECT * FROM jadwal";
//            jadwalList.setAll(connection.createQuery(query).executeAndFetch(jadwal.class));
//        }
//    }
    
    public boolean createJadwal(){
        try (Connection connection = DB.koneksi.sql2o.open()) {
            final String query = "INSERT INTO jadwal (id_pelanggan,id_paket,tanggal_pertemuan) VALUE (:id_pelanggan, :id_paket, :tanggal_pertemuan)";
            connection.createQuery(query).bind(this).executeUpdate();
            if(connection.getResult()>0){
      //          refreshDB();
                return true;
            }
            return false;
        }
    }
    
    public boolean updateJadwal(){
        try (Connection connection = DB.koneksi.sql2o.open()) {
            final String query = "UPDATE jadwal SET `id_pelanggan` = :id_pelanggan, `id_paket` = :id_paket, `tanggal_pertemuan` = :tanggal_pertemuan WHERE `id_jadwal` = :id_jadwal";
            connection.createQuery(query).bind(this).executeUpdate();
            if(connection.getResult()>0){
        //        refreshDB();
                return true;
            }
            return false;
        }
    }

    public int getId_jadwal() {
        return id_jadwal;
    }

    public void setId_jadwal(int id_jadwal) {
        this.id_jadwal = id_jadwal;
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

    public Date getTanggal_pertemuan() {
        return tanggal_pertemuan;
    }

    public void setTanggal_pertemuan(Date tanggal_pertemuan) {
        this.tanggal_pertemuan = tanggal_pertemuan;
    }

    
    public StringProperty tanggal_pertemuanProperty() {
        return new SimpleStringProperty(new LocalDate(tanggal_pertemuan).toString());
    }
    
    public StringProperty waktu_pertemuanProperty() {
        LocalTime localtime = new LocalTime(tanggal_pertemuan);
        return new SimpleStringProperty(localtime.getHourOfDay()+":"+String.format("%02d",localtime.getMinuteOfHour()));
   }
}
