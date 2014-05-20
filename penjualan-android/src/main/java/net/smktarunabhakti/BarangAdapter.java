package net.smktarunabhakti;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import net.smktarunabhakti.domain.Barang;

import java.util.ArrayList;
import java.util.List;

/**
 * Created on 20/05/14.
 * by jimmy [@jimmyrengga]
 */
public class BarangAdapter extends BaseAdapter {

    private List<Barang> listBarang = new ArrayList<Barang>();
    private Context context;
    private TextView tId;
    private TextView tNama;

    public BarangAdapter(List<Barang> listBarang, Context context) {
        this.listBarang = listBarang;
        this.context = context;
    }

    @Override
    public int getCount() {
        return listBarang.size();
    }

    @Override
    public Object getItem(int i) {
        return listBarang.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.list_layout, viewGroup, false);
        }

        tId = (TextView) view.findViewById(R.id.tId);
        tNama = (TextView) view.findViewById(R.id.tNama);

        Barang barang = (Barang) getItem(i);
        if(barang != null) {
            Log.v("Barang", barang.getNamaBarang());
            tId.setText(barang.getId());
            tNama.setText(barang.getNamaBarang());
        }

        return view;
    }
}
