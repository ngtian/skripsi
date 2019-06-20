/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Text;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.UnitValue;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.property.HorizontalAlignment;
import com.itextpdf.layout.property.VerticalAlignment;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import javax.imageio.ImageIO;
/**
 *
 * @author Surianto
 */


public class laporan {

    private static final String biasa = "/Resource/times.ttf";
    private static final String bold = "/Resource/timesbold.ttf";
    
    private static Cell cellNoBorder(Image image) {
        return new Cell()
                .setBorder(Border.NO_BORDER)
                .add(image);
    }
    
    private static List<String> hari() {
        return Arrays.asList(
                "",
                "Senin",
                "Selasa",
                "Rabu",
                "Kamis",
                "Jumat",
                "Sabtu",
                "Minggu"
        );
    }

    private static List<String> bulan() {
        return Arrays.asList(
                "",
                "Januari",
                "Februari",
                "Maret",
                "April",
                "Mei",
                "Juni",
                "Juli",
                "Agustus",
                "September",
                "Oktober",
                "November",
                "Desember"
        );
    }
    
//    private static Table kop_surat(String judul) throws MalformedURLException, IOException {
//        BufferedImage img = ImageIO.read(
//                laporan.class.getResourceAsStream("/Resource/icons8-garage-26.PNG"));
//        byte[] imageInByte;
//        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
//            ImageIO.write(img, "png", baos);
//            baos.flush();
//            imageInByte = baos.toByteArray();
//        }
//        Image image = new Image(ImageDataFactory.create(imageInByte));
//
//        return new Table(new UnitValue[]{
//                new UnitValue(UnitValue.PERCENT, 10),
//                new UnitValue(UnitValue.PERCENT, 90)}, true)
//                .setFontSize(12)
//                .addCell(cellNoBorder(image.setAutoScale(true)))
//                .addCell(
//                        cellNoBorder("SEKOLAH MENGEMUDI TALENTA JAYA\n Jalan Raya CiNERE (Samping Informa) Cinere Depok \n Telepon : 081318002095\n \n \n" +judul+ "\n \n")
//                                .setTextAlignment(TextAlignment.CENTER)
//                                .setHorizontalAlignment(HorizontalAlignment.CENTER)
//                                .setVerticalAlignment(VerticalAlignment.MIDDLE));               
//    }

    private static Table signature(LocalDate tgl) {
        return new Table(
                1)
                .setFontSize(10)
                .setWidth(200)
                .setHeight(100)
                .setMarginTop(50)//antara table dan ttd
                .setHorizontalAlignment(HorizontalAlignment.RIGHT)
                .addCell(
                        cellNoBorder("Depok" + ", " +
                                //hari().get(tgl.getDayOfWeek()) + ", " +
                                tgl.getDayOfMonth() + " " +
                                bulan().get(tgl.getMonthOfYear()) + " " +
                                tgl.getYear())
                                .setTextAlignment(TextAlignment.CENTER))
                .addCell(
                        cellNoBorder("Admin\n Astari")
                                .setTextAlignment(TextAlignment.CENTER)
                                .setVerticalAlignment(VerticalAlignment.BOTTOM));
    }

    public static void daftar_pelanggan(List<registrasi> reg) throws IOException {
        LocalDate localDate = new LocalDate(new Date());
        String fileName = String.format("laporan-data-pelanggan-%s.pdf", localDate.toString());

        PdfWriter writer = new PdfWriter(fileName);
        PdfDocument pdf = new PdfDocument(writer);
        try (Document document = new Document(pdf)) {
//            document.add(kop_surat("Laporan Data Pelanggan"));
            
            Table detailTable = new Table(new UnitValue[]{
                new UnitValue(UnitValue.PERCENT, 50),
                new UnitValue(UnitValue.PERCENT, 50)}, true)
                ;
   
            Table pelangganTable = new Table(8);
            pelangganTable.setWidth(520);
            pelangganTable.addHeaderCell(cell("Id Pelanggan"));
            pelangganTable.addHeaderCell(cell("Tanggal Daftar"));
            pelangganTable.addHeaderCell(cell("Nama Pelanggan"));
            pelangganTable.addHeaderCell(cell("Tempat Lahir"));
            pelangganTable.addHeaderCell(cell("Tanggal Lahir"));
            pelangganTable.addHeaderCell(cell("Alamat"));
            pelangganTable.addHeaderCell(cell("Jenis Kelamin"));
            pelangganTable.addHeaderCell(cell("No HP"));
            
            reg.forEach(registrasi -> {
                pelangganTable.addCell(cell(String.valueOf(registrasi.getId_pelanggan())));
                pelangganTable.addCell(cell(registrasi.getTanggal_daftar().toString()));
                pelangganTable.addCell(cell(registrasi.getNama_pelanggan()));
                pelangganTable.addCell(cell(registrasi.getTempat_lahir()));
                pelangganTable.addCell(cell(registrasi.getTanggal_lahir().toString()));
                pelangganTable.addCell(cell(registrasi.getAlamat()));
                pelangganTable.addCell(cell(registrasi.getJenis_kelamin()));
                pelangganTable.addCell(cell(registrasi.getNo_hp()));
            });
            
            document.add(pelangganTable.setMarginTop(10));
            document.add(signature(localDate));
        }
        showReport(fileName);
    }

    public static void daftar_mobil() throws IOException {
        LocalDate localDate = new LocalDate(new Date());
        String fileName = String.format("laporan-data-mobil-%s.pdf", localDate.toString());

        PdfWriter writer = new PdfWriter(fileName);
        PdfDocument pdf = new PdfDocument(writer);
        try (Document document = new Document(pdf)) {
//            document.add(kop_surat("Laporan Data Mobil"));
            
            Table detailTable = new Table(new UnitValue[]{
                new UnitValue(UnitValue.PERCENT, 50),
                new UnitValue(UnitValue.PERCENT, 50)}, true)
                ;
            
            Table mobilTable = new Table(5);
            mobilTable.setWidth(520);
            mobilTable.addHeaderCell(cell("Id Mobil"));
            mobilTable.addHeaderCell(cell("Merk Mobil"));
            mobilTable.addHeaderCell(cell("Type Mobil"));
            mobilTable.addHeaderCell(cell("Jenis Mobil"));
            mobilTable.addHeaderCell(cell("Plat Mobil"));
            
            mobil.getMobil().forEach(mobil -> {
                mobilTable.addCell(cell(String.valueOf(mobil.getId_mobil())));
                mobilTable.addCell(cell(mobil.getMerk_mobil()));
                mobilTable.addCell(cell(mobil.getType_mobil()));
                mobilTable.addCell(cell(mobil.getJenis_mobil()));
                mobilTable.addCell(cell(mobil.getPlat_mobil()));
                });
            
            document.add(mobilTable.setMarginTop(10));
            document.add(signature(localDate));
        }
        showReport(fileName);
    }

    public static void daftar_jadwal(List<jadwal> jdw) throws IOException {
        LocalDate localDate = new LocalDate(new Date());
        String fileName = String.format("laporan-data-jadwal-%s.pdf", localDate.toString());

        PdfWriter writer = new PdfWriter(fileName);
        PdfDocument pdf = new PdfDocument(writer);
        try (Document document = new Document(pdf)) {
//            document.add(kop_surat("Laporan Data Jadwal"));
            
            Table detailTable = new Table(new UnitValue[]{
                new UnitValue(UnitValue.PERCENT, 50),
                new UnitValue(UnitValue.PERCENT, 50)}, true)
                ;
            
            Table jadwalTable = new Table(6);
            jadwalTable.setWidth(520);
            jadwalTable.addHeaderCell(cell("Id Jadwal"));
            jadwalTable.addHeaderCell(cell("Id Pelanggan"));
            jadwalTable.addHeaderCell(cell("Nama Pelanggan"));
            jadwalTable.addHeaderCell(cell("Id Paket"));
            jadwalTable.addHeaderCell(cell("Nama Paket"));
            jadwalTable.addHeaderCell(cell("Tanggal Pertemuan"));
            
            jdw.forEach(jadwal -> {
                jadwalTable.addCell(cell(String.valueOf(jadwal.getId_jadwal())));
                jadwalTable.addCell(cell(String.valueOf(jadwal.getId_pelanggan())));
                jadwalTable.addCell(cell(registrasi.getReg(jadwal).getNama_pelanggan()));
                jadwalTable.addCell(cell(String.valueOf(jadwal.getId_paket())));
                jadwalTable.addCell(cell(paket.getPaket(jadwal).getNama_paket()));
                jadwalTable.addCell(cell(jadwal.getTanggal_pertemuan().toString()));
            });
            
            document.add(jadwalTable.setMarginTop(10));
            document.add(signature(localDate));
        }
        showReport(fileName);
    }
    
    public static void daftar_pembayaran(List<bayar> byr) throws IOException {
        LocalDate localDate = new LocalDate(new Date());
        String fileName = String.format("laporan-data-bayar-%s.pdf", localDate.toString());

        PdfWriter writer = new PdfWriter(fileName);
        PdfDocument pdf = new PdfDocument(writer);
        try (Document document = new Document(pdf)) {
//            document.add(kop_surat("Laporan Data Bayar"));
            
            Table detailTable = new Table(new UnitValue[]{
                new UnitValue(UnitValue.PERCENT, 50),
                new UnitValue(UnitValue.PERCENT, 50)}, true)
                ;
            
            Table bayarTable = new Table(6);
            bayarTable.setWidth(520);
            bayarTable.addHeaderCell(cell("Id Pembayaran"));
            bayarTable.addHeaderCell(cell("Id Pelanggan"));
            bayarTable.addHeaderCell(cell("Nama Pelanggan"));
            bayarTable.addHeaderCell(cell("Id Paket"));
            bayarTable.addHeaderCell(cell("Nama Paket"));
            bayarTable.addHeaderCell(cell("Harga Paket"));
            
            byr.forEach(bayar -> {
                bayarTable.addCell(cell(String.valueOf(bayar.getId_pembayaran())));
                bayarTable.addCell(cell(String.valueOf(bayar.getId_pelanggan())));
                bayarTable.addCell(cell(registrasi.getReg(bayar).getNama_pelanggan()));
                bayarTable.addCell(cell(String.valueOf(bayar.getId_paket())));
                bayarTable.addCell(cell(paket.getPaket(bayar).getNama_paket()));
                bayarTable.addCell(cell(String.valueOf(paket.getPaket(bayar).getHarga_paket())));
            });
            
            document.add(bayarTable.setMarginTop(10));
            document.add(signature(localDate));
        }
        showReport(fileName);
    }
    
    //STRUK
    public static void struk() throws IOException {
        LocalDate localDate = new LocalDate(new Date());  
        String fileName = "Kwitansi.pdf";
          PdfFont boldFont = PdfFontFactory.createFont(bold, true);
          PdfWriter writer = new PdfWriter(fileName);
          PdfDocument pdf = new PdfDocument(writer);
        try (Document document = new Document(pdf, new PageSize(new Rectangle(226.8f, 600f)))) {
            document.add(
                    new Paragraph()
                            .setTextAlignment(TextAlignment.CENTER)
                            .setFontSize(5)
                            .add(new Text("SEKOLAH MENGEMUDI TALENTA JAYA").setFont(boldFont))
                            .add("\n Jalan Raya Cinere (Samping Informa) Cinere Depok")
                            .add("\n-----------------------------------------------------------------------------------------\n")
                            .add("")
                            .add("\tTanggal:")
                            .add(localDate+" "+new LocalTime().toString().substring(0, 8))
                            .add("\n-----------------------------------------------------------------------------------------\n")
                    
            );
            
            Table itemsTable = new Table(new UnitValue[]{
                new UnitValue(UnitValue.PERCENT, 25),
                new UnitValue(UnitValue.PERCENT, 10),
                new UnitValue(UnitValue.PERCENT, 30),}, true);
            
            itemsTable.setFontSize(6);
            itemsTable.setTextAlignment(TextAlignment.LEFT);
            itemsTable.addCell(cell("Id Pembayaran").setFontSize(6));
            itemsTable.addCell(cell(":").setFontSize(6));
            itemsTable.addCell(cell(String.valueOf(bayar.byr.getId_pembayaran())).setFontSize(6));
            itemsTable.addCell(cell("Id Pelanggan").setFontSize(6));
            itemsTable.addCell(cell(":").setFontSize(6));
            itemsTable.addCell(cell(String.valueOf(bayar.byr.getId_pelanggan())).setFontSize(6));
            itemsTable.addCell(cell("Nama Pelanggan").setFontSize(6));
            itemsTable.addCell(cell(":").setFontSize(6));
            itemsTable.addCell(cell(registrasi.reg.getNama_pelanggan()).setFontSize(6));
            itemsTable.addCell(cell("Id Paket").setFontSize(6));
            itemsTable.addCell(cell(":").setFontSize(6));
            itemsTable.addCell(cell(String.valueOf(bayar.byr.getId_paket())).setFontSize(6));
            itemsTable.addCell(cell("Nama Paket").setFontSize(6));
            itemsTable.addCell(cell(":").setFontSize(6));
            itemsTable.addCell(cell(paket.pkt.getNama_paket()).setFontSize(6));
            itemsTable.addCell(cell("Harga Paket").setFontSize(6));
            itemsTable.addCell(cell(":").setFontSize(6));
            itemsTable.addCell(cell(String.valueOf(paket.pkt.getHarga_paket())).setFontSize(6));
            document.add(itemsTable);
            
            document.add(
                    new Paragraph()
                            .setTextAlignment(TextAlignment.CENTER)
                            .setFontSize(5)
                            .add("Terima Kasih")
            );
        }
        showReport(fileName);
    }
        
    private static Cell cellNoBorder(String text) {
        return new Cell()
                .setBorder(Border.NO_BORDER)
                .add(new Paragraph(text));
    }

    private static Cell cell(String text) {
        return new Cell().add(new Paragraph(text));
    }

    private static void showReport(String fileName) {
        File file = new File(fileName);
        Desktop desktop = Desktop.getDesktop();
        try {
            desktop.open(file);
        } catch (IOException e) {
        }
}
}