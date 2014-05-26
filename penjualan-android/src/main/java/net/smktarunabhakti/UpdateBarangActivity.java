package net.smktarunabhakti;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import net.smktarunabhakti.domain.Barang;
import net.smktarunabhakti.service.AndroidService;

/**
 * Created on 26/05/14.
 * by jimmy [@jimmyrengga]
 */
public class UpdateBarangActivity extends Activity {

    private static String TAG = "barangActivity";
    private AndroidService appService = new AndroidService();
    ProgressDialog progressDialog;
    TextView tKodeBarang;
    TextView tNamaBarang;
    String idBarang;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate");
        setContentView(R.layout.tambah_barang);

        Intent intent = getIntent();
        idBarang = intent.getStringExtra("ID_BARANG");
        Log.i(TAG, "getFrom Intent : " + idBarang);

        tKodeBarang = (TextView) findViewById(R.id.editText);
        tNamaBarang = (TextView) findViewById(R.id.editText2);
        new GetBarangById().execute(idBarang);
    }

    public void simpanBarang(View view) {
        Barang b = new Barang();
        b.setId(idBarang);
        b.setKodeBarang(tKodeBarang.getText().toString());
        b.setNamaBarang(tNamaBarang.getText().toString());

        new UpdateDataBarang().execute(b);
    }

    class GetBarangById extends AsyncTask<String, Void, Barang> {

        @Override
        protected void onPreExecute() {
            showLoadingProgressDialog();
            Log.v(TAG, "Starting");
        }

        @Override
        protected Barang doInBackground(String... strings) {
            try{
                return appService.getBarangById(strings[0]);
            } catch (Exception ex) {
                Log.i(TAG, "Error found [at] AsyncTask GetDataBarang, is : " + ex.getMessage());
                return null;
            }
        }

        @Override
        protected void onPostExecute(Barang brg) {
            dismissProgressDialog();
            tKodeBarang.setText(brg.getKodeBarang());
            tNamaBarang.setText(brg.getNamaBarang());
        }
    }

    class UpdateDataBarang extends AsyncTask<Barang, Void, Void> {

        @Override
        protected Void doInBackground(Barang... barangs) {
            try{
                appService.updateBarang(barangs[0]);
                return null;
            } catch (Exception ex) {
                Log.i(TAG, "Error found [at] AsyncTask GetDataBarang, is : " + ex.getMessage());
                return null;
            }
        }

        @Override
        protected void onPreExecute() {
            showLoadingProgressDialog();
            Log.v(TAG, "Starting");
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            dismissProgressDialog();
            Toast.makeText(UpdateBarangActivity.this, "Data berhasil Terupdate.", Toast.LENGTH_LONG).show();
        }
    }

    public void showLoadingProgressDialog() {
        showProgressDialog("Loading. Please wait...", UpdateBarangActivity.this);
    }

    public void showProgressDialog(CharSequence message, Context context) {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(context);
            progressDialog.setIndeterminate(true);
        }

        progressDialog.setMessage(message);
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    public void dismissProgressDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }

}
