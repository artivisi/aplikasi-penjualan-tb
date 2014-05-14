/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.smktarunabhakti.penjualan.service;

import net.smktarunabhakti.penjualan.domain.Barang;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 *
 * @author jimmy
 */
public interface AppService {
    
    void simpanBarang(Barang b);
    
    void hapusBarang(Barang b);
    
    Page<Barang> cariSemuaBarang(Pageable p);
    
    Barang cariBarangById(String id);
    
    Long countSemuaBarang();
    
}
