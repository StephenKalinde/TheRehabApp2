package com.example.therehabapp;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.hotspot2.pps.HomeSp;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

public class CarePacks extends AppCompatActivity {

    private Button pack1, pack2, pack3;

    private Button pack1Btn;
    private Button pack2Btn;
    private Button pack3Btn;

    FrameLayout myLayout;

    private Context mContext;
    private Activity mActivity;

    private PopupWindow popupWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.care_pack_view);

        mContext= getApplicationContext();

        mActivity= CarePacks.this;

        pack1= (Button) findViewById(R.id.btn_1);
        pack2= (Button) findViewById(R.id.btn_2);
        pack3= (Button) findViewById(R.id.btn_3);

        pack1Btn= (Button) findViewById(R.id.select_btn);
        pack2Btn= (Button) findViewById(R.id.select_btn_2);
        pack3Btn= (Button) findViewById(R.id.select_btn_3);

        myLayout = (FrameLayout) findViewById(R.id.care_pack_activity_view);
        //myLayout.getForeground().setAlpha(0);

        pack1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //add code here

                //start activity
                startActivity(new Intent(CarePacks.this, Home.class) );
            }
        });

        pack2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // add code here

                //start activity
                startActivity(new Intent(CarePacks.this, Home.class) );
            }
        });

        pack3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // add code here

                //start activity
                startActivity(new Intent(CarePacks.this, Home.class) );
            }
        });

        /** show overlays on click of the pack info buttons**/

        pack1Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(LAYOUT_INFLATER_SERVICE);
                View popUpView = inflater.inflate(R.layout.pack_1_popup, null);

                popupWindow = new PopupWindow(popUpView,LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);


                if(Build.VERSION.SDK_INT>=21)
                {
                    popupWindow.setElevation(5.0f);
                }

                popupWindow.showAtLocation(myLayout, Gravity.CENTER, 0,0);
                //myLayout.getForeground().setAlpha( 220);


                popUpView.setOnTouchListener(new View.OnTouchListener(){
                    @Override
                    public boolean onTouch(View v, MotionEvent event)
                    {
                        popupWindow.dismiss();
                        //myLayout.getForeground().setAlpha(0);
                        return true;
                    }
                });
            }
        });

        pack2Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(LAYOUT_INFLATER_SERVICE);
                View popUpView = inflater.inflate(R.layout.pack_2_popup, null);

                popupWindow = new PopupWindow(popUpView,LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);


                if(Build.VERSION.SDK_INT>=21)
                {
                    popupWindow.setElevation(5.0f);
                }

                popupWindow.showAtLocation(myLayout, Gravity.CENTER, 0,0);
                //myLayout.getForeground().setAlpha( 220);


                popUpView.setOnTouchListener(new View.OnTouchListener(){
                    @Override
                    public boolean onTouch(View v, MotionEvent event)
                    {
                        popupWindow.dismiss();
                        //myLayout.getForeground().setAlpha(0);
                        return true;
                    }
                });
            }
        });

        pack3Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(LAYOUT_INFLATER_SERVICE);
                View popUpView = inflater.inflate(R.layout.pack_3_popup, null);

                popupWindow = new PopupWindow(popUpView,LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);


                if(Build.VERSION.SDK_INT>=21)
                {
                    popupWindow.setElevation(5.0f);
                }

                popupWindow.showAtLocation(myLayout, Gravity.CENTER, 0,0);
                //myLayout.getForeground().setAlpha( 220);


                popUpView.setOnTouchListener(new View.OnTouchListener(){
                    @Override
                    public boolean onTouch(View v, MotionEvent event)
                    {
                        popupWindow.dismiss();
                        //myLayout.getForeground().setAlpha(0);
                        return true;
                    }
                });
            }
        });




    }
}
