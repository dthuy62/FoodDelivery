package com.example.ddth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.ddth.Database.Database;
import com.example.ddth.Model.Food;
import com.example.ddth.Model.Order;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class DetailsActivity extends AppCompatActivity {
    private TextView food_name, food_price, food_description;
    private ImageView food_image;
    String foodId="";
    private ElegantNumberButton numberButton;
    private Button btnCart;
    private  Food currentFood;
    FirebaseDatabase database;
    DatabaseReference  foodRef;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        ImageView imageViewBack = findViewById(R.id.img_back);
        imageViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MainActivity.class);
                context.startActivity(intent);
            }
        });
        database = FirebaseDatabase.getInstance();
        foodRef = database.getReference("Food");

        numberButton = findViewById(R.id.btn_number);
        food_name = findViewById(R.id.name_food_detail);
        food_price = findViewById(R.id.price_detail);
        food_description = findViewById(R.id.description);
        food_image = findViewById(R.id.image_food_detail);
        btnCart = findViewById(R.id.btn_cart);
        btnCart.setOnClickListener(v -> {
            new Database(getBaseContext()).addToCart(new Order(
                    foodId,
                    currentFood.getNamefood(),
                    Integer.parseInt(numberButton.getNumber()),
                    currentFood.getPrice(),
                    currentFood.getDiscount()

            ));

            Toast.makeText(DetailsActivity.this, "Added To Cart", Toast.LENGTH_SHORT).show();
        });

        if(getIntent() != null)
        {
            foodId = getIntent().getStringExtra("FoodId");

        }
        if (!foodId.isEmpty())
        {
            getDetailFood(foodId);
        }



    }

    private void getDetailFood(String foodId) {
        foodRef.child(foodId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                currentFood = snapshot.getValue(Food.class);

                Picasso.get().load(currentFood.getImg()).into(food_image);

                food_name.setText(currentFood.getNamefood());
                food_price.setText(Integer.toString(currentFood.getPrice()));
                food_description.setText(currentFood.getDesc());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}