package com.melissa.booksdelivered;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.lang.reflect.Array;


public class UserInfo extends Activity {
    private ImageView imageView;
    private Spinner booksOnHold_spinner;
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);

        imageView = (ImageView) findViewById(R.id.userProfilePicture);
        if (Build.VERSION.SDK_INT<22) {
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.bookshelves));
        } else {
            imageView.setImageDrawable(getDrawable(R.drawable.bookshelves));
        }
        booksOnHold_spinner = (Spinner) findViewById(R.id.booksOnHold_spinner);

        String [] books = {"To Kill a Mockingbird", "The Kite Runner", "Outlier"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, books);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        booksOnHold_spinner.setAdapter(adapter);

    }

    protected void readFromServer (String serverLink) {

    }

}
