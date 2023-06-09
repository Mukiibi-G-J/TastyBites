package com.example.tastybites;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


public class FoodDetail extends AppCompatActivity {
    TextView txtFoodName;
    ImageView imgFoodDetails;
    TextView txtFoodPrice;
    TextView txtDescription;

    TextView txtQuantity;

    ImageView btnPlus;
    ImageView btnMinus;
    SharedPreferences.Editor editor;

    Button btnAddToCart;
    private SharedPreferences sharedPreferences;
    private List<Repo> productList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_detail);

        String foodId = getIntent().getStringExtra("foodid");
        String foodName = getIntent().getStringExtra("foodname");
        String foodPrice = getIntent().getStringExtra("foodprice");

        String foodImage = getIntent().getStringExtra("foodimage");
        int foodQuantity = getIntent().getIntExtra("foodquantity", 0);
        String foodTotalprice = getIntent().getStringExtra("foodtotalprice");
        getCurrentQuantity(foodId);


        txtFoodName = findViewById(R.id.txtFoodName);
        btnPlus = findViewById(R.id.btnPlus);
        btnMinus = findViewById(R.id.btnMin);
        txtQuantity = findViewById(R.id.txtNum);
        txtDescription = findViewById(R.id.txtDescription);
        txtFoodPrice = findViewById(R.id.txtFoodPrice);
        imgFoodDetails = findViewById(R.id.imgFoodDetails);
        txtFoodName.setText(getIntent().getStringExtra("foodname"));
        txtFoodPrice.setText(getIntent().getStringExtra("foodprice"));
        txtDescription.setText(getIntent().getStringExtra("description"));
        txtQuantity.setText(String.valueOf(foodQuantity));
        Glide.with(this).load(getIntent().getStringExtra("foodimage")).into(imgFoodDetails);

        btnAddToCart = findViewById(R.id.btnAddToCart);

        txtFoodName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // go to the home fragment from  the detail Activity
                  Intent intent = new Intent(FoodDetail.this, Home.class);
                    startActivity(intent);

            }
        });


        sharedPreferences = getSharedPreferences("shopping_cart", MODE_PRIVATE);
        String productsJson = sharedPreferences.getString("cartProducts", "[]");
//        sharedPreferences =getActivity().getSharedPreferences("shopping_cart", Context.MODE_PRIVATE);
//        String productsJson = sharedPreferences.getString("cartProducts", null);

        btnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                JSONArray itemList = null;
                try {
                    sharedPreferences = getSharedPreferences("shopping_cart", MODE_PRIVATE);
                    String productsJson = sharedPreferences.getString("cartProducts", "[]");
                    itemList = new JSONArray(productsJson);

                    if (itemList.length() == 0) {
                        Toast.makeText(FoodDetail.this, "Pleas first add product to cart", Toast.LENGTH_SHORT).show();
                        return;
                    } else {

                        for (int i = 0; i < itemList.length(); i++) {
                            JSONObject item = itemList.getJSONObject(i);
                            String food_Id = item.getString("id");
                            if (food_Id.equals(foodId)) {
                                int foodQuantity = item.getInt("quantity") + 1;
                                int price = Integer.parseInt(item.getString("price"));
                                int totalPrice = foodQuantity * price;
                                item.put("quantity", foodQuantity);
                                item.put("totalprice", totalPrice);
                                txtQuantity.setText(String.valueOf(foodQuantity));

                                // Save the updated list to SharedPreferences
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("cartProducts", itemList.toString());
                                editor.apply();

                                return;
                            }


                        }
                    }
                    Toast.makeText(FoodDetail.this, "please first add product to cart", Toast.LENGTH_SHORT).show();


                } catch (JSONException e) {
                    Toast.makeText(FoodDetail.this, "" + e, Toast.LENGTH_SHORT).show();
                    throw new RuntimeException(e);
                }
            }
        });
        btnMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                JSONArray itemList = null;

                try {
                    sharedPreferences = getSharedPreferences("shopping_cart", MODE_PRIVATE);
                    String productsJson = sharedPreferences.getString("cartProducts", "[]");
                    itemList = new JSONArray(productsJson);

                    if (itemList.length() == 0) {
                        Toast.makeText(FoodDetail.this, "Please first add product to cart", Toast.LENGTH_SHORT).show();

                    } else {

                        for (int i = 0; i < itemList.length(); i++) {
                            JSONObject item = itemList.getJSONObject(i);
                            String food_Id = item.getString("id");
                            if (food_Id.equals(foodId)) {
                                int foodQuantity = item.getInt("quantity") - 1;
//                                Toast.makeText(FoodDetail.this, "Quantity cannot be less than 1", Toast.LENGTH_SHORT).show();
                                if (foodQuantity >= 1) {
                                    int price = Integer.parseInt(item.getString("price"));
                                    int totalPrice = foodQuantity * price;
                                    item.put("quantity", foodQuantity);
                                    item.put("totalprice", totalPrice);
                                    txtQuantity.setText(String.valueOf(foodQuantity));

                                    // Save the updated list to SharedPreferences
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.putString("cartProducts", itemList.toString());
                                    editor.apply();
                                    return;
                                }
                                return;
                            }
                        }
                    }

                } catch (JSONException e) {
                    Toast.makeText(FoodDetail.this, "" + e, Toast.LENGTH_SHORT).show();
                    throw new RuntimeException(e);

                }

            }
        });
        btnAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // toast food id

                // add to cart to local storage
//                for (Repo product : productList) {
//
//                    if (product.getId() == foodId) {
//                        Toast.makeText(FoodDetail.this, "Product already exists", Toast.LENGTH_SHORT).show();
//                        return;
//                    }
//
//                }
//                // Create a new product with random values
//                Repo product = new Repo(foodId, foodName, foodPrice, foodImage, foodQuantity);
//
//                // Add the product to the list and save it to SharedPreferences
//                productList.add(product);
//                saveProductListToSharedPreferences(productList);
//                Toast.makeText(FoodDetail.this, "Product added successfully", Toast.LENGTH_SHORT).show();

//                 Get the existing products from SharedPreferences
                JSONArray itemList = null;

                try {
                    sharedPreferences = getSharedPreferences("shopping_cart", MODE_PRIVATE);
                    String productsJson = sharedPreferences.getString("cartProducts", "[]");
                    itemList = new JSONArray(productsJson);
                    for (int i = 0; i < itemList.length(); i++) {
                        JSONObject item = itemList.getJSONObject(i);
                        String food_Id = item.getString("id");
                        if (food_Id.equals(foodId)) {
                            Toast.makeText(FoodDetail.this, "Product already exists", Toast.LENGTH_SHORT).show();

                            return;
                        }

                    }
                } catch (JSONException e) {
                    Toast.makeText(FoodDetail.this, "" + e, Toast.LENGTH_SHORT).show();
                    throw new RuntimeException(e);
                }


                Gson gson = new Gson();
//

                List<Repo> products = new ArrayList<>();
                if (!productsJson.isEmpty()) {
                    Type type = new TypeToken<List<Repo>>() {
                    }.getType();
                    products = gson.fromJson(productsJson, type);

                }

                Repo product1 = new Repo(foodId, foodName, foodPrice, foodImage, foodQuantity, foodTotalprice);
                products.add(product1);
                String updatedProductsJson = gson.toJson(products);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("cartProducts", updatedProductsJson);
                editor.apply();


//                Toast.makeText(FoodDetail.this, ""+ productsJson, Toast.LENGTH_SHORT).show();
                // Add the new product to the cart
//                boolean productExists = false;
                /// loop through the shared preferences list of productsJson


//                for (Repo product : products) {
//                    if (product.getId() == foodId) {
////                        product.setQuantity(product.getQuantity() + foodQuantity);
////                        productExists = true;
//                        Toast.makeText(FoodDetail.this, "Product already exists", Toast.LENGTH_SHORT).show();
//
//                        return;
//                    }
//
//
//                }
//                String productId =foodId;
////                Repo product = new Repo(productId, productName, productPrice, productImage , productQuantity);
//                products.add(product);
//                boolean food = (foodId == product.getId());
//                Toast.makeText(FoodDetail.this, ""+food, Toast.LENGTH_SHORT).show();


//                 Save the updated products to SharedPreferences


            }
        });
    }

    private void getCurrentQuantity(String foodId) {
        sharedPreferences = getSharedPreferences("shopping_cart", MODE_PRIVATE);
        String productsJson = sharedPreferences.getString("cartProducts", "[]");
        JSONArray itemList = null;
        try {
            itemList = new JSONArray(productsJson);
            for (int i = 0; i < itemList.length(); i++) {
                JSONObject item = itemList.getJSONObject(i);
                String food_Id = item.getString("id");
                if (food_Id.equals(foodId)) {
                    int foodQuantity = item.getInt("quantity");
                    TextView txtQuantity = findViewById(R.id.txtNum);
                    txtQuantity.setText(String.valueOf(foodQuantity));
                    return;
                }
            }
        } catch (JSONException e) {
            Toast.makeText(FoodDetail.this, "" + e, Toast.LENGTH_SHORT).show();
            throw new RuntimeException(e);
        }
    }


    private void saveProductListToSharedPreferences(List<Repo> productList) {
        // Convert the list of products to a JSON string
//        Gson gson = new Gson();
//        String productListJson = gson.toJson(productList);

        // Save the JSON string to SharedPreferences
        editor = sharedPreferences.edit();
//        editor.putString("productList", productListJson);
//        editor.apply();
        for (Repo product : productList) {
            String json = new Gson().toJson(product);
            editor.putString(String.valueOf(product.getId()), json);
        }
        editor.apply();
    }

    private List<Repo> getProductListFromSharedPreferences() {
        // Get the JSON string from SharedPreferences
        String productListJson = sharedPreferences.getString("productList", "");

        // Convert the JSON string to a list of products
        Gson gson = new Gson();
        Type productListType = new TypeToken<ArrayList<Repo>>() {
        }.getType();
        List<Repo> productList = gson.fromJson(productListJson, productListType);

        // If the list is null, create a new empty list
        if (productList == null) {
            productList = new ArrayList<>();
        }

        return productList;
    }

    private void updateProductPrice(Repo product) {
        // conver price to int
        int price = Integer.parseInt(product.getPrice());
        int newPrice = price * product.getQuantity();
        // Check if the price has changed
        if (newPrice != price) {
            // Update the price
//            product.setPrice(String.valueOf(newPrice));

            // update the price TextView
//            txtFoodPrice.setText(String.valueOf(newPrice));
//            creat textview for price lable "price" and set it to the price


            txtDescription.setText(String.valueOf(newPrice));
        }

    }
}


//    implement onclik on a two buttons one to increment count of product and the ohter to decrement count and store quantity,and the product with a uniqure number  in sharepreference in list of objects  if the quanity of the product changes price increasesimplement onclik on a two buttons one to increment count of product and the ohter to decrement count and store quantity,and the product with a uniqure number  in sharepreference in list of objects  if the quanity of the product changes price increases