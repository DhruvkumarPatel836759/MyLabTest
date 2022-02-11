package com.client.mylabtest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.client.mylabtest.databasehelper.BaseActivity;
import com.client.mylabtest.databasehelper.DbHelper;
import com.client.mylabtest.databasehelper.SplashActivity;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.List;

public class ProductListActivity extends BaseActivity {

    RecyclerView recyclerView;
    DbHelper dbHelper;
    ProductsAdapter productsAdapter;
    TextInputEditText searchEt;
    BottomSheetDialog editDialog,addDialog;
    FloatingActionButton mFab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);

        dbHelper=new DbHelper(ProductListActivity.this);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mFab = (FloatingActionButton) findViewById(R.id.fab);
        searchEt=findViewById(R.id.searchEt);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(ProductListActivity.this));

        getAllProducts();


        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addProduct();

            }
        });
    }

    private void saveProduct(String name, String descripton,String price,String lat,String lon) {
        long primaryKey = dbHelper.insertCategory(name, descripton,price,lat,lon);
        Log.e("Key",primaryKey+"");
        if(primaryKey == -1) {
            Toast.makeText(ProductListActivity.this, "Data is not inserted in database", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(ProductListActivity.this, "Data Successfully inserted", Toast.LENGTH_SHORT).show();

        }

    }



    private void addProduct() {

        View view = getLayoutInflater().inflate(R.layout.add_product_layout, null);

        EditText name = (EditText) view.findViewById(R.id.mNameEt);
        EditText des = (EditText) view.findViewById(R.id.mDesEt);
        EditText price = (EditText) view.findViewById(R.id.mPriceEt);
        EditText lat = (EditText) view.findViewById(R.id.mLatEt);
        EditText lng = (EditText) view.findViewById(R.id.mLngEt);


        Button mSaveBtn=(Button)view.findViewById(R.id.saveBtn);
        mSaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveProduct(name.getText().toString().trim(),des.getText().toString().trim(),
                        price.getText().toString().trim(),lat.getText().toString().trim(),lng.getText().toString().trim());
                addDialog.dismiss();
                getAllProducts();
            }
        });


        addDialog = new BottomSheetDialog(ProductListActivity.this);
        addDialog.setContentView(view);
        addDialog.show();


    }

    private void getAllProducts() {
        List<AddCategoryModel> allProductsList= dbHelper.getAllCategories();
        productsAdapter=new ProductsAdapter(ProductListActivity.this,allProductsList);
        recyclerView.setAdapter(productsAdapter);
        productsAdapter.notifyDataSetChanged();
    }


    //---------------------------lEDGER lIST Adapter------------------------------------//
    public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.MyViewHolder> {

        Context context;
        List<AddCategoryModel> childFeedList;


        public ProductsAdapter(Context context, List<AddCategoryModel> childFeedList) {
            this.context = context;
            this.childFeedList = childFeedList;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_list_design, parent, false);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, final int position) {
            AddCategoryModel model = childFeedList.get(position);
            holder.mName.setText(model.getProductName());
            holder.mDescription.setText(model.getProductDescription());
            holder.mPrice.setText("$"+model.getProductPrice());



            holder.mDeleteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dbHelper.deleteCategory(model);
                    getAllProducts();
                }
            });

            holder.mEditBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    editProduct(model);
                }
            });


        }


        @Override
        public int getItemCount() {
            return childFeedList.size();
        }




        public class MyViewHolder extends RecyclerView.ViewHolder {

            TextView mName,mDescription,mPrice;
            Button mDeleteBtn,mEditBtn;


            public MyViewHolder(View itemView) {
                super(itemView);

                mName=itemView.findViewById(R.id.Name_tv);
                mDescription=itemView.findViewById(R.id.description_tv);
                mPrice=itemView.findViewById(R.id.price_tv);
                mDeleteBtn=itemView.findViewById(R.id.mDeleteBtn);
                mEditBtn=itemView.findViewById(R.id.mEditBtn);



            }
        }
    }

    private void editProduct(AddCategoryModel addCategoryModel) {

        View view = getLayoutInflater().inflate(R.layout.edit_product_layout, null);

        EditText name = (EditText) view.findViewById(R.id.mNameEt);
        EditText des = (EditText) view.findViewById(R.id.mDesEt);
        EditText price = (EditText) view.findViewById(R.id.mPriceEt);
        EditText lat = (EditText) view.findViewById(R.id.mLatEt);
        EditText lng = (EditText) view.findViewById(R.id.mLngEt);

        name.setText(addCategoryModel.getProductName());
        des.setText(addCategoryModel.getProductDescription());
        price.setText(addCategoryModel.getProductPrice());
        lat.setText(addCategoryModel.getProductLat());
        lng.setText(addCategoryModel.getProductLong());


        Button mSaveBtn=(Button)view.findViewById(R.id.saveBtn);
        mSaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    dbHelper.updateCategory(addCategoryModel,name.getText().toString().trim(),des.getText().toString().trim(),
                            price.getText().toString().trim(),lat.getText().toString().trim(),lng.getText().toString().trim());
                    editDialog.dismiss();
                    getAllProducts();
            }
        });


        editDialog = new BottomSheetDialog(ProductListActivity.this);
        editDialog.setContentView(view);
        editDialog.show();


    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(ProductListActivity.this,MainActivity.class));
        finish();
    }
}