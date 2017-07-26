package com.melissa.booksdelivered;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Build;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class QrCodeScanner extends Activity implements ZXingScannerView.ResultHandler{
    private ZXingScannerView scannerView;
    private static final String TAG = "QrCodeScanner";
    public static final String EXTRA_VALID_CARD = "Is_Card_Valid";
    public static final String EXTRA_SCAN_RESULTS = "SCAN_RESULTS";
    public String scan_results;

    @Override
    public void onCreate (Bundle savedInstanceState) {
        Log.d(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        scannerView = new ZXingScannerView(this);
        setContentView(scannerView);

        scannerView.setResultHandler(this);
        scannerView.startCamera();
    }

    @Override
    public void onResume() {
        Log.d(TAG, "onResume");

        super.onResume();
        scannerView.setResultHandler(this);
        scannerView.startCamera();
    }

    @Override
    public void onPause() {
        Log.d(TAG, "onPause");
        super.onPause();
        scannerView.stopCamera();
    }



    @Override
    public void handleResult (Result result) {

        scan_results= result.getText();

        //If network connection is available
        if (isConnected()) {

            try {

                Intent data = new Intent(this, LibraryPage.class);
                data.putExtra(EXTRA_VALID_CARD, true);
                data.putExtra(EXTRA_SCAN_RESULTS, scan_results);
                startActivity(data);
                finish();


            } catch (Exception e) {
                Log.e(TAG, "Error "+ e.toString());
            }

        } else {
            Toast.makeText(this, "No Internet Connection", Toast.LENGTH_SHORT).show();
        }
    }

    //Method to check for internet Connection
    @TargetApi(23)
    public boolean isConnected() {
        boolean retval = false;

        ConnectivityManager check = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);

        if (Build.VERSION.SDK_INT >= 23) {
            Network[] networks = check.getAllNetworks();
            NetworkInfo netInfo;
            for (int i = 0; i < networks.length; i++) {
                netInfo = check.getNetworkInfo(networks[i]);
                if (netInfo.getState().equals(NetworkInfo.State.CONNECTED)) {
                    retval = true;
                }
            }
        }else {
            if (check != null) {
                NetworkInfo[] info = check.getAllNetworkInfo();
                if (info != null) {
                    for (int i = 0; i < info.length; i++) {
                        if (info[i].getState() == NetworkInfo.State.CONNECTED)
                            retval = true;
                    }
                }
            }

        }
        return retval;
    }




}

