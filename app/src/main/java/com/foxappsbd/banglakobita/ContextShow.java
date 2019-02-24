package com.foxappsbd.banglakobita;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.NativeExpressAdView;
import com.google.android.gms.ads.doubleclick.PublisherInterstitialAd;

import java.util.ArrayList;

/**
 * Created by Arafat on 10/07/2017.
 */

public class ContextShow extends AppCompatActivity {
    String str, action;
    int i=1,num,datanum,cunt,cknum,dltnum,agck;
    private AdView mAdView;
    Toolbar toolbar;
    TextView text1;
    PersonDatabase mydb;
    private Menu menu;
    ArrayList<String> theList,idList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_text);

        mydb = new PersonDatabase(this);

        text1 = (TextView) findViewById(R.id.textView);
        text1.setTextIsSelectable(true);
        //toolbar=(Toolbar)findViewById(R.id.toolbar);
        Intent intent = getIntent();
        Bundle bundle = getIntent().getExtras();
        str = bundle.getString("sms");
        num=bundle.getInt("position");
        text1.setText(str);
        //toolbar.setTitle("Sun");
        //for add..................................................
        mAdView = (AdView) findViewById(R.id.ad_view);
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        mAdView.loadAd(adRequest);
        //add end.......................................................


    }

    public void AddData() {
        if (cunt==1){
            dltnum=Integer.valueOf(idList.get(cknum));
            //for deleat
            Integer deletedRows = mydb.deleteData(idList.get(cknum));
            if (deletedRows > 0){
                Toast.makeText(this, "Delete Successful", Toast.LENGTH_SHORT).show();
                menu.getItem(0).setIcon(getResources().getDrawable(R.drawable.nfavorite));
                cunt=0;
            }
            else
                Toast.makeText(this, "Sorry", Toast.LENGTH_SHORT).show();

        }else{
                boolean isInserted = mydb.insertdata(str, String.valueOf(num));
                menu.getItem(0).setIcon(getResources().getDrawable(R.drawable.favorite));
                if (isInserted == true) {
                    checking();
                    Toast.makeText(ContextShow.this, "সেভ করা হয়েছে", Toast.LENGTH_SHORT).show();
                }
                else
                    Toast.makeText(ContextShow.this, "সেভ করা হইনি", Toast.LENGTH_SHORT).show();

            }

    }
    public void checking(){
        theList = new ArrayList<>();
        idList = new ArrayList<>();
        Cursor data = mydb.getAllData();
            while(data.moveToNext()){
                theList.add(data.getString(2));
                idList.add(data.getString(0));
            }
        for(i=0;i<data.getCount();i++){
            action=theList.get(i);
            datanum= Integer.valueOf(action);
            if (datanum==num){
                menu.getItem(0).setIcon(getResources().getDrawable(R.drawable.favorite));
               // Toast.makeText(this,"It's Your Fevriout" , Toast.LENGTH_SHORT).show();
                cknum=i;
                cunt=1;
            }

        }

    }
    public void showAlertDialog(Context context, String title, String message, Boolean status) {
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle(title);
        alertDialog.setMessage(message);
        alertDialog.setIcon(R.drawable.bcopy);

        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        alertDialog.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        this.menu = menu;
        checking();
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Take appropriate action for each action item click
        switch (item.getItemId()) {
            case R.id.action_search:
                AddData();
                return true;
            case R.id.action_copy:
                showAlertDialog(ContextShow.this, " লেখা কপি করার নিয়ম ",
                        "আপনি লেখার উপর চেপে ধরুন দেখবেন কপি অপশন আসবে", false);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
