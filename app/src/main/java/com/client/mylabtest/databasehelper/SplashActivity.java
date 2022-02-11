package com.client.mylabtest.databasehelper;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.client.mylabtest.AddCategoryModel;
import com.client.mylabtest.MainActivity;
import com.client.mylabtest.MapsActivity;
import com.client.mylabtest.R;

import java.util.ArrayList;
import java.util.List;

public class SplashActivity extends BaseActivity {

    private static int SPLASH_TIME_OUT = 3000;

    List<AddCategoryModel> list;

    DbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        dbHelper=new DbHelper(SplashActivity.this);

        list=new ArrayList<>();

        insertProduct();



    }

    private void insertProduct() {
       saveProduct("My First Product","Good quality Product","20","30.7333","76.7794");

    }

    private void saveProduct(String name, String descripton,String price,String lat,String lon) {
        long primaryKey = dbHelper.insertCategory(name, descripton,price,lat,lon);
        Log.e("Key",primaryKey+"");
        if(primaryKey == -1) {
            Toast.makeText(SplashActivity.this, "Data is not inserted in database", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(SplashActivity.this, "Data Successfully inserted", Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    Intent i = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(i);
                    finish();
                }
            }, SPLASH_TIME_OUT);
        }

    }
}