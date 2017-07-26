package com.melissa.booksdelivered;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class MainPage extends AppCompatActivity {
    private final static String TAG = "MainPage";
    private Button qrCodeScan;
    private Button searchCardbyNames, searchCardbyNumber;
    //private static int RequestCode = 0;
    //private ZXingScannerView scannerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        qrCodeScan = (Button) findViewById(R.id.qrScan_button);
        qrCodeScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Start a scanner activity
                Intent intent = new Intent(MainPage.this, QrCodeScanner.class);
                startActivity(intent);
            }
        });

        searchCardbyNames =(Button) findViewById(R.id.search_byNames);
        searchCardbyNames.setOnClickListener( new View.OnClickListener(){
            @Override
            public void onClick (View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(MainPage.this);
                alert.setTitle("Search Card By Name");
                alert.setMessage("Input Name");

                // Set an EditText view to get user input
                final EditText input = new EditText(MainPage.this);
                alert.setView(input);
                alert.setPositiveButton("Search", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        String result = input.getText().toString();
                        searchName(result);
                    }
                });

                alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        dialog.dismiss();
                    }
                });
                alert.show();
            }
        });

        searchCardbyNumber= (Button) findViewById(R.id.search_byNumber);
        searchCardbyNumber.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(MainPage.this);
                alert.setTitle("Search Card By Number");
                alert.setMessage("Input Number");

                // Set an EditText view to get user input
                final EditText input = new EditText(MainPage.this);
                alert.setView(input);
                alert.setPositiveButton("Search", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        String result = input.getText().toString();
                        searchNumber(result);
                    }
                });

                alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        dialog.dismiss();
                    }
                });
                alert.show();
            }
        });
    }

    //Method to search for user name in database
    public void searchName (String input) {
        //TODO: Search in Database for Card Name

        Intent intent = new Intent (this, LibraryPage.class);
        startActivity(intent);
    }

    //Method to search for user Number in database
    public void searchNumber (String input) {
        //TODO: Search in Database for Card Number

        Intent intent = new Intent(this, LibraryPage.class);
        startActivity(intent);
    }


}
