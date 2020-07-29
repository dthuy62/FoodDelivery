package com.example.ddth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;


import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.ddth.Common.Common;
import com.example.ddth.Database.Database;
import com.example.ddth.Model.Food;
import com.example.ddth.Model.Order;
import com.example.ddth.Model.Ratting;
import com.example.ddth.Model.Users;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.stepstone.apprating.AppRatingDialog;
import com.stepstone.apprating.listener.RatingDialogListener;

import java.util.Arrays;

public class DetailsActivity extends AppCompatActivity implements RatingDialogListener {
    private TextView food_name, food_price, food_description;
    private ImageView food_image;
    String foodId="";
    private ElegantNumberButton numberButton;
    private Button btnCart;
    private  Food currentFood;
    FirebaseDatabase database;
    DatabaseReference  foodRef;
    DatabaseReference ratingTbl;
    Context context;
    Database localDB;
    private RatingBar ratingBar;
    private FloatingActionButton fab_rating;
    private Ratting ratting;

    private Button btnComment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        ImageView imageViewBack = findViewById(R.id.img_back);
        imageViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               startActivity(new Intent(DetailsActivity.this, MainActivity.class));
                finish();
            }
        });



        localDB = new Database(this);

        database = FirebaseDatabase.getInstance();
        foodRef = database.getReference("Food");
        database = FirebaseDatabase.getInstance();
        ratingTbl = database.getReference("Rating");

        numberButton = findViewById(R.id.btn_quantity);
        food_name = findViewById(R.id.name_food_detail);
        food_price = findViewById(R.id.price_detail);
        food_description = findViewById(R.id.description);
        food_image = findViewById(R.id.image_food_detail);



        ratingBar = findViewById(R.id.rating_bar);
        fab_rating = findViewById(R.id.fab_rating);
        fab_rating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showRatingDialog();
            }
        });


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
            getRatingFood(foodId);
        }
        btnComment = findViewById(R.id.show_comment);
        btnComment.setOnClickListener(v -> {
            Intent intent = new Intent(DetailsActivity.this, CommentActivity.class);
            intent.putExtra(Common.INTENT_FOOD_ID, foodId);
            Log.d("alo","gì"+foodId);
            startActivity(intent);

        });




    }

    private void getRatingFood(String foodId) {
        Query foodrating = ratingTbl.orderByChild("foodId").equalTo(foodId);
        foodrating.addListenerForSingleValueEvent(new ValueEventListener() {
            int count = 0, sum =0;
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot sn : snapshot.getChildren())
                {
                    Ratting item = sn.getValue(Ratting.class);
                    sum+=Integer.parseInt(item.getRatevalue());
                    count++;

                }
                if(count != 0)
                {
                    float average = sum/count;
                    ratingBar.setRating(average);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void showRatingDialog() {
       AppRatingDialog.Builder ratingBuider = new AppRatingDialog.Builder();
       ratingBuider.setPositiveButtonText("Xác nhận").setNegativeButtonText("Hủy bỏ")
               .setNoteDescriptions(Arrays.asList("Rất dở", "Không ngon", "Ngon", "Rất ngon","Tuyệt vời"))
               .setDefaultRating(1)
                 .setTitle("Đánh giá")
               .setDescription("Vui lòng đánh giá và gửi phản hồi về cho chúng tôi")
               .setTitleTextColor(R.color.colorBlue)
               .setDescriptionTextColor(R.color.black)
               .setHintTextColor(R.color.yellow)
               .setCommentTextColor(android.R.color.black)
               .setCommentBackgroundColor(R.color.bgBottomNavigation)
               .setWindowAnimation(R.style.RatingFadeAnimation)
               .create(DetailsActivity.this)
               .show();

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

    @Override
    public void onNegativeButtonClicked() {

    }

    @Override
    public void onNeutralButtonClicked() {

    }

    @Override
    public void onPositiveButtonClicked(int value, String comment) {



        Query query = FirebaseDatabase.getInstance().getReference("Users").orderByChild("uid").equalTo(FirebaseAuth.getInstance().getCurrentUser().getUid());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot sn : snapshot.getChildren())
                {
                    Users users = sn.getValue(Users.class);
                    users.getPhone();
                     ratting = new Ratting(
                            users.getPhone(),
                            foodId,
                            String.valueOf(value),
                            comment);

                   /* ratingTbl.child(users.getPhone()).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.child(users.getPhone()).exists())
                            {
                                // delete value old
                                ratingTbl.child(users.getPhone()).removeValue();
                                ratingTbl.child(users.getPhone()).setValue(ratting);
                            }
                            else
                            {
                                ratingTbl.child(users.getPhone()).setValue(ratting);

                            }
                            Toast.makeText(DetailsActivity.this, "Cảm ơn bạn đã đánh giá ", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });*/
                }
                ratingTbl.push()
                        .setValue(ratting)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Toast.makeText(DetailsActivity.this, "Cảm ơn bạn đã đánh giá ", Toast.LENGTH_SHORT).show();
                            }
                        });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}