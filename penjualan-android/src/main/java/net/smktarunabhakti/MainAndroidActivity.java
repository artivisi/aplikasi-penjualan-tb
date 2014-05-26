package net.smktarunabhakti;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import net.smktarunabhakti.domain.Barang;
import net.smktarunabhakti.service.AndroidService;

import java.util.List;

public class MainAndroidActivity extends Activity {

    private static String TAG = "penjualan-android";
    private AndroidService appService = new AndroidService();
    ProgressDialog progressDialog;
    BarangAdapter barangAdapter;

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
    }

    public void tambahBarang(View view) {
        Intent barangActivity = new Intent(view.getContext(), BarangActivity.class);
        startActivity(barangActivity);
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
        protected void onPostExecute(final List<Barang> barangs) {
            dismissProgressDialog();
            Log.v(TAG, "size : " + barangs.size());
            ListView listView = (ListView) findViewById(R.id.listView);
            barangAdapter = new BarangAdapter(barangs, MainAndroidActivity.this);
            listView.setAdapter(barangAdapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                public void onItemClick(AdapterView<?> parentAdapter, View view, int position,
                                        long id) {



                }
            });
            registerForContextMenu(listView);
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

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        AdapterView.AdapterContextMenuInfo aInfo = (AdapterView.AdapterContextMenuInfo) menuInfo;

        // We know that each row in the adapter is a Map
        Barang map =  (Barang) barangAdapter.getItem(aInfo.position);

        menu.setHeaderTitle("Options for " + map.getNamaBarang());
        menu.add(1, 1, 1, "Details");
        menu.add(1, 2, 2, "Delete");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        final AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        final Barang mapBarang =  (Barang) barangAdapter.getItem(info.position);
        // Implements our logic
        switch (itemId) {
            case 1 :

                Intent updtBarangActivity = new Intent(this.getApplicationContext(), UpdateBarangActivity.class);
                updtBarangActivity.putExtra("ID_BARANG", mapBarang.getId());
                startActivity(updtBarangActivity);

                return true;
            case 2 :
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                Log.v("btn", "yes");
                                new DeleteDataBarang().execute(mapBarang.getId());
                                break;

                            case DialogInterface.BUTTON_NEGATIVE:
                                Log.v("btn", "no");
                                break;
                        }
                    }
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("Are you sure?").setPositiveButton("Yes", dialogClickListener)
                        .setNegativeButton("No", dialogClickListener).show();
                return true;
            default :
                Log.v("tag", "default");
                return true;
        }
    }

    class DeleteDataBarang extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... strings) {
            try{
                appService.deleteBarang(strings[0]);
                return null;
            } catch (Exception ex) {
                Log.i(TAG, "Error found [at] AsyncTask DeleteDataBarang, is : " + ex.getMessage());
                return null;
            }
        }

        @Override
        protected void onPreExecute() {
            showLoadingProgressDialog();
        }

        @Override
            protected void onPostExecute(Void aVoid) {
            dismissProgressDialog();
            new GetDataBarang().execute();
        }
    }
}