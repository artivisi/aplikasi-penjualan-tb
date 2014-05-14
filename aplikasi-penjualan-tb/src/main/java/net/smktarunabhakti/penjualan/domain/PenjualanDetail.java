/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.smktarunabhakti.penjualan.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

/**
 *
 * @author jimmy
 */
@Entity
@Table(name="trx_jual_detail")
public class PenjualanDetail implements Serializable{

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    private String id;

    @ManyToOne
    private Penjualan header;

    @ManyToOne
    private Barang barang;

    @Column(name = "jumlah_barang")
    private int jumlah;

    @Column(name = "total_harga")
    private BigDecimal totalHarga;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Penjualan getHeader() {
        return header;
    }

    public void setHeader(Penjualan header) {
        this.header = header;
    }

    public Barang getBarang() {
        return barang;
    }

    public void setBarang(Barang barang) {
        this.barang = barang;
    }

    public int getJumlah() {
        return jumlah;
    }

    public void setJumlah(int jumlah) {
        this.jumlah = jumlah;
    }

    public BigDecimal getTotalHarga() {
        return totalHarga;
    }

    public void setTotalHarga(BigDecimal totalHarga) {
        this.totalHarga = totalHarga;
    }

    
    
}
