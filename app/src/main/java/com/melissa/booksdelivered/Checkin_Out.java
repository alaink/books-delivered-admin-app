package com.melissa.booksdelivered;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;


/**
 * Created by mkagaju on 7/17/17.
 */

public class Checkin_Out extends Fragment {


    private ImageView checkin_Imageview;
    private ImageView checkout_Imageview;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.checkin_out, container, false);


        checkin_Imageview = (ImageView) layout.findViewById(R.id.checkin_ImageView);
        checkin_Imageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });


        checkout_Imageview = (ImageView) layout.findViewById(R.id.checkout_ImageView);
        checkout_Imageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v) {
                Intent intent = new Intent(container.getContext(), BookSearch.class);
                startActivity(intent);
            }
        });
        return layout;
    }




}
