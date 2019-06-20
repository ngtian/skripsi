/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.sql2o.Connection;

/**
 *
 * @author Surianto
 */
public class registrasi {
    int id_pelanggan;
    Date tanggal_daftar;
    String nama_pelanggan;
    String tempat_lahir;
    Date tanggal_lahir;
    String alamat;
    String jenis_kelamin;
    String no_hp;

    private static ObservableList<registrasi> pelangganList = FXCollections.observableArrayList();
    public static registrasi reg;
    
    
    public registrasi(Date tanggal_daftar, String nama_pelanggan, String tempat_lahir, Date tanggal_lahir, String alamat, String jenis_kelamin, String no_hp) {
        this.tanggal_daftar = tanggal_daftar;
        this.nama_pelanggan = nama_pelanggan;
        this.tempat_lahir = tempat_lahir;
        this.tanggal_lahir = tanggal_lahir;
        this.alamat = alamat;
        this.jenis_kelamin = jenis_kelamin;
        this.no_hp = no_hp;
    }

    public static ObservableList<registrasi> getPelanggan(){
        refreshDB();
        return pelangganList;
    }
    
    private static void refreshDB() {
        try(Connection connection = DB.koneksi.sql2o.open()) {
            final String query = "SELECT * FROM pelanggan";
            pelangganList.setAll(connection.createQuery(query).executeAndFetch(registrasi.class));
        }
    }
    
    public boolean createPelanggan(){
        try (Connection connection = DB.koneksi.sql2o.open()) {
            final String query = "INSERT INTO pelanggan (tanggal_daftar,nama_pelanggan,tempat_lahir,tanggal_lahir,alamat,jenis_kelamin,no_hp) VALUE (:tanggal_daftar, :nama_pelanggan, :tempat_lahir, :tanggal_lahir, :alamat, :jenis_kelamin, :no_hp)";
            connection.createQuery(query).bind(this).executeUpdate();
            id_pelanggan = connection.getKey(Integer.class);
            if(connection.getResult()>0){
                refreshDB();
                return true;
            }
            return false;
        }
    }
    
    public boolean updatePelanggan(){
        try (Connection connection = DB.koneksi.sql2o.open()) {
            final String query = "UPDATE pelanggan SET `tanggal_daftar` = :tanggal_daftar, `nama_pelanggan` = :nama_pelanggan, `tempat_lahir` = :tempat_lahir, `tanggal_lahir` = :tanggal_lahir, `alamat` = :alamat, `jenis_kelamin` = :jenis_kelamin, `no_hp` = :no_hp WHERE `id_pelanggan` = :id_pelanggan";
            connection.createQuery(query).bind(this).executeUpdate();
            if(connection.getResult()>0){
                refreshDB();
                return true;
            }
            return false;
        }
    }

    public static registrasi getReg(jadwal jdw) {
        return getPelanggan()
                .stream()
                .filter(registrasi -> registrasi.id_pelanggan==jdw.getId_pelanggan())
                .findFirst()
                .orElse(null);
    }
    
    public static registrasi getReg(bayar byr) {
        return getPelanggan()
                .stream()
                .filter(registrasi -> registrasi.id_pelanggan==byr.getId_pelanggan())
                .findFirst()
                .orElse(null);
    }
    
     public static List <registrasi> registrasi (LocalDate dari, LocalDate sampai){
        return getPelanggan()
                .stream()
                .filter((t) -> {
                    LocalDate localDate = new LocalDate(t.getTanggal_daftar());
                    return localDate.isAfter(dari) && localDate.isBefore(sampai) || localDate.equals(sampai);
                }).collect(Collectors.toList());
        }
     
    public int getId_pelanggan() {
        return id_pelanggan;
    }

    public void setId_pelanggan(int id_pelanggan) {
        this.id_pelanggan = id_pelanggan;
    }

    public Date getTanggal_daftar() {
        return tanggal_daftar;
    }

    public void setTanggal_daftar(Date tanggal_daftar) {
        this.tanggal_daftar = tanggal_daftar;
    }

    public String getNama_pelanggan() {
        return nama_pelanggan;
    }

    public void setNama_pelanggan(String nama_pelanggan) {
        this.nama_pelanggan = nama_pelanggan;
    }

    public String getTempat_lahir() {
        return tempat_lahir;
    }

    public void setTempat_lahir(String tempat_lahir) {
        this.tempat_lahir = tempat_lahir;
    }

    public Date getTanggal_lahir() {
        return tanggal_lahir;
    }

    public void setTanggal_lahir(Date tanggal_lahir) {
        this.tanggal_lahir = tanggal_lahir;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getJenis_kelamin() {
        return jenis_kelamin;
    }

    public void setJenis_kelamin(String jenis_kelamin) {
        this.jenis_kelamin = jenis_kelamin;
    }

    public String getNo_hp() {
        return no_hp;
    }

    public void setNo_hp(String no_hp) {
        this.no_hp = no_hp;
    }

    @Override
    public String toString() {
        return "registrasi{" + "id_pelanggan=" + id_pelanggan + ", tanggal_daftar=" + tanggal_daftar + ", nama_pelanggan=" + nama_pelanggan + ", tempat_lahir=" + tempat_lahir + ", tanggal_lahir=" + tanggal_lahir + ", alamat=" + alamat + ", jenis_kelamin=" + jenis_kelamin + ", no_hp=" + no_hp + '}';
    }
}
