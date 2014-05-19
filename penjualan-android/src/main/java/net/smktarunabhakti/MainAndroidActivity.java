package net.smktarunabhakti;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import net.smktarunabhakti.domain.Barang;
import net.smktarunabhakti.service.AndroidService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainAndroidActivity extends Activity {

    private static String TAG = "penjualan-android";
    List<Map<String, String>> barangLists = new ArrayList<Map<String,String>>();
    private AndroidService appService = new AndroidService();
    ProgressDialog progressDialog;

    /**
     * Called when the activity is first created.
     * @param savedInstanceState If the activity is being re-initialized after 
     * previously being shut down then this Bundle contains the data it most 
     * recently supplied in onSaveInstanceState(Bundle). <b>Note: Otherwise it is null.</b>
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		Log.i(TAG, "onCreate");
        setContentView(R.layout.main);

        new GetDataBarang().execute();

        ListView listView = (ListView) findViewById(R.id.listView);
        SimpleAdapter simpleAdapter = new SimpleAdapter(this, barangLists, android.R.layout.simple_list_item_1,
                new String[] {"barang"}, new int[] {android.R.id.text1});
        listView.setAdapter(simpleAdapter);
    }

    // All Url
    private class GetDataBarang extends AsyncTask<Void, Void, List<Barang>> {

        @Override
        protected List<Barang> doInBackground(Void... voids) {
            try{
                return appService.getDataBarang();
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
        protected void onPostExecute(List<Barang> barangs) {
            dismissProgressDialog();
            initData(barangs);
        }
    }

    public void showLoadingProgressDialog() {
        showProgressDialog("Loading. Please wait...", MainAndroidActivity.this);
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

    private void initData(List<Barang> barangs) {
        //Get data barang and populate to barangLists
        if(barangs.size() < 0 || barangs == null) {
            return;
        }

        for (Barang barang : barangs) {
            barangLists.add(createBarang("barang", barang.getNamaBarang()));
        }

    }

    private HashMap<String, String> createBarang(String key, String name) {
        HashMap<String, String> barang = new HashMap<String, String>();
        barang.put(key, name);

        return barang;
    }
}

