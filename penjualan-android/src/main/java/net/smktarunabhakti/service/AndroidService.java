package net.smktarunabhakti.service;

import net.smktarunabhakti.domain.Barang;

import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.xml.ws.Response;

/**
 * Created on 19/05/14.
 * by Jimmy Rengga [@jimmyrengga]
 */
public class AndroidService {

    // Ganti BASE_URL dengan alamat web server anda sendiri
    private static final String BASE_URI = "http://192.168.1.217:10000/";
    RestTemplate restTemplate = new RestTemplate();

    public AndroidService() {
        this.restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
    }

    public List<Barang> getDataBarang() {
        String url = BASE_URI + "barang";

        restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

        ResponseEntity<Barang[]> entityBarang = restTemplate.getForEntity(url, Barang[].class);

        return new ArrayList<Barang>(Arrays.asList(entityBarang.getBody()));
    }

    public void deleteBarang(String id) {
        String url = BASE_URI + "barang/" + id;

        restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

        restTemplate.delete(url);
    }

}
