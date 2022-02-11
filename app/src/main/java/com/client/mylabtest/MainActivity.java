package com.client.mylabtest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.client.mylabtest.databasehelper.BaseActivity;
import com.client.mylabtest.databasehelper.DbHelper;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.textfield.TextInputEditText;

import java.util.List;

public class MainActivity extends FragmentActivity implements OnMapReadyCallback {

    DbHelper dbHelper;
    TextView mName,mDescription,mPrice;
    List<AddCategoryModel> allProductsList;


    Button mShowListBtn;
    private GoogleMap mMap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        mName=findViewById(R.id.Name_tv);
        mDescription=findViewById(R.id.description_tv);
        mPrice=findViewById(R.id.price_tv);
        mShowListBtn=findViewById(R.id.mListBtn);


        dbHelper=new DbHelper(MainActivity.this);

        getAllProducts();

        mShowListBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,ProductListActivity.class));
            }
        });




    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;



        // Add a marker in Sydney and move the camera
        //LatLng sydney = new LatLng(30.7333, 76.7794);
        LatLng sydney = new LatLng(Double.parseDouble(allProductsList.get(0).getProductLat()), Double.parseDouble(allProductsList.get(0).getProductLong()));
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }

    private void getAllProducts() {
        allProductsList= dbHelper.getAllCategories();
        if(allProductsList.size()>0){
            mName.setText(allProductsList.get(0).getProductName());
            mDescription.setText(allProductsList.get(0).getProductDescription());
            mPrice.setText("$ "+allProductsList.get(0).getProductPrice());
        }else{
            Toast.makeText(MainActivity.this, "No Data found", Toast.LENGTH_SHORT).show();
        }



    }
}