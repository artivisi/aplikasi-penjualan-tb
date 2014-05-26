package net.smktarunabhakti;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import net.smktarunabhakti.domain.Barang;
import net.smktarunabhakti.service.AndroidService;

/**
 * Created on 20/05/14.
 * by jimmy [@jimmyrengga]
 */
public class BarangActivity extends Activity {

    private static String TAG = "barangActivity";
    private AndroidService appService = new AndroidService();
    ProgressDialog progressDialog;
    TextView tKodeBarang;
    TextView tNamaBarang;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate");
        setContentView(R.layout.tambah_barang);

        tKodeBarang = (TextView) findViewById(R.id.editText);
        tNamaBarang = (TextView) findViewById(R.id.editText2);
    }

    public void simpanBarang(View view) {
        Barang b = new Barang();
        b.setKodeBarang(tKodeBarang.getText().toString());
        b.setNamaBarang(tNamaBarang.getText().toString());

        new SaveDataBarang().execute(b);
    }

    class SaveDataBarang extends AsyncTask<Barang, Void, String> {

        @Override
        protected String doInBackground(Barang... barangs) {
            try{
                return appService.saveBarang(barangs[0]);
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
        protected void onPostExecute(String str) {
            dismissProgressDialog();
            Log.v(TAG, "size : " + str);
            tKodeBarang.setText("");
            tNamaBarang.setText("");
            Toast.makeText(BarangActivity.this, "Data berhasil tersimpan.", Toast.LENGTH_LONG).show();
        }
    }

    public void showLoadingProgressDialog() {
        showProgressDialog("Loading. Please wait...", BarangActivity.this);
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
