/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.smktarunabhakti.penjualan.dao;

import net.smktarunabhakti.penjualan.domain.Barang;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 *
 * @author jimmy
 */
public interface BarangDao extends PagingAndSortingRepository<Barang, String>{
    
}
