package com.example.ddth;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ddth.Adapter.CartAdapter;
import com.example.ddth.Database.Database;
import com.example.ddth.Model.Order;
import com.example.ddth.Model.Requests;
import com.example.ddth.Model.Users;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CartActivity extends AppCompatActivity {

    public static final String TAG = "Request";
    FirebaseDatabase database;
    DatabaseReference ref;
    FirebaseUser user;
    FirebaseAuth auth;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private TextView txtTotalPrice;
    private Button btnPlaceOrder;
    private Requests requestsOrder;
    private List<Order> carts = new ArrayList<>();
    private CartAdapter cartAdapter;

    private Users users;
    private EditText editAddress;

    public CartActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        user = auth.getInstance().getCurrentUser();


        recyclerView = findViewById(R.id.listCart);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        txtTotalPrice = findViewById(R.id.total);
        btnPlaceOrder = findViewById(R.id.btn_order);
        btnPlaceOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showAlertDialog();


            }


        });
        loadListFood();


    }

    private void showAlertDialog() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("Alo Alo");
        alertDialog.setMessage("Nhập địa chỉ : ");

        editAddress = new EditText(this);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
        );
        editAddress.setLayoutParams(layoutParams);
        alertDialog.setView(editAddress);
        alertDialog.setIcon(R.drawable.ic_add_shopping_cart_24px);

        alertDialog.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                getInfoUser();



                // Delete Cart
                new Database(getBaseContext()).cleanCart();
                Toast.makeText(CartActivity.this, "Cảm ơn bạn đã đặt hàng", Toast.LENGTH_SHORT).show();
                finish();
            }

        });
        alertDialog.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alertDialog.show();
    }

    private void loadListFood() {
        carts = new Database(this).getCarts();
        cartAdapter = new CartAdapter(carts, this);
        recyclerView.setAdapter(cartAdapter);
        // Calculate total price

        int total = 0;

        for (Order order : carts)
            total += ((order.getPrice())) * ((order.getQuantity()));
//                Locale locale = new Locale("vi","VN");
//                NumberFormat format = NumberFormat.getCurrencyInstance(locale);
//                txtTotalPrice.setText(format.format(total));
        txtTotalPrice.setText(total + "");
        Log.d("", "");


    }

    private void getInfoUser() {
        ref = FirebaseDatabase.getInstance().getReference("Users");
        Query query = ref.orderByChild("email").equalTo(user.getEmail());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot sn : snapshot.getChildren()) {
                    users = sn.getValue(Users.class);
                    requestsOrder = new Requests(
                            users.getPhone(),
                            editAddress.getText().toString(),
                            users.getName(),
                            Integer.parseInt(txtTotalPrice.getText().toString()),
                            carts
                    );

                    Log.e("Phone", "" + users.getPhone());
                    Log.i("Name", "" + users.getName());
                    Log.d("User", "" + users.toString());
                    Log.d("requestOrder", "" + requestsOrder);
                    database = FirebaseDatabase.getInstance();
                    ref = database.getReference("Requests");
                    ref.child(String.valueOf(System.currentTimeMillis()))
                            .setValue(requestsOrder);
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });
    }


}