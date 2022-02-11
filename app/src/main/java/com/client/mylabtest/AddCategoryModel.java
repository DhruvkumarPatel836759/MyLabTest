package com.client.mylabtest;

import android.os.Parcel;
import android.os.Parcelable;

public class AddCategoryModel implements Parcelable {

    private int id;
    private String productName;
    private String  productDescription;
    private String  productPrice;
    private String  productLat;
    private String  productLong;
    private String timestamp;

    public static final String TABLE_NAME = "table_products";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_PRODUCT_NAME = "name";
    public static final String COLUMN_PRODUCT_DESCRIPTION = "description";
    public static final String COLUMN_PRODUCT_PRICE = "price";
    public static final String COLUMN_PRODUCT_LAT = "lat";
    public static final String COLUMN_PRODUCT_LONG = "lon";
    public static final String COLUMN_TIMESTAMP = "timestamp";

    // Create table SQL query
    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_PRODUCT_NAME + " TEXT,"
                    + COLUMN_PRODUCT_DESCRIPTION + " TEXT,"
                    + COLUMN_PRODUCT_PRICE + " TEXT,"
                    + COLUMN_PRODUCT_LAT + " TEXT,"
                    + COLUMN_PRODUCT_LONG + " TEXT,"
                    + COLUMN_TIMESTAMP + " DATETIME DEFAULT CURRENT_TIMESTAMP"
                    + ")";



    public AddCategoryModel() {
    }


    public AddCategoryModel(int id, String productName, String productDescription, String productPrice, String productLat, String productLong, String timestamp) {
        this.id = id;
        this.productName = productName;
        this.productDescription = productDescription;
        this.productPrice = productPrice;
        this.productLat = productLat;
        this.productLong = productLong;
        this.timestamp = timestamp;
    }

    protected AddCategoryModel(Parcel in) {
        id = in.readInt();
        productName = in.readString();
        productDescription = in.readString();
        productPrice = in.readString();
        productLat = in.readString();
        productLong = in.readString();
        timestamp = in.readString();
    }

    public static final Creator<AddCategoryModel> CREATOR = new Creator<AddCategoryModel>() {
        @Override
        public AddCategoryModel createFromParcel(Parcel in) {
            return new AddCategoryModel(in);
        }

        @Override
        public AddCategoryModel[] newArray(int size) {
            return new AddCategoryModel[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public String getProductLat() {
        return productLat;
    }

    public void setProductLat(String productLat) {
        this.productLat = productLat;
    }

    public String getProductLong() {
        return productLong;
    }

    public void setProductLong(String productLong) {
        this.productLong = productLong;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(productName);
        dest.writeString(productDescription);
        dest.writeString(productPrice);
        dest.writeString(productLat);
        dest.writeString(productLong);
        dest.writeString(timestamp);
    }
}
