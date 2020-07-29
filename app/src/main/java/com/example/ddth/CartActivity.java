package com.example.ddth;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
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
import com.example.ddth.Common.Common;
import com.example.ddth.Database.Database;
import com.example.ddth.Model.Order;
import com.example.ddth.Model.Requests;
import com.example.ddth.Model.Users;
import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class CartActivity extends AppCompatActivity {

    public static final String TAG = "Request";
    FirebaseDatabase database;
    DatabaseReference ref;
    FirebaseUser user;
    FirebaseAuth auth;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    public TextView txtTotalPrice;
    private Button btnPlaceOrder;
    private Requests requestsOrder;
    private List<Order> carts = new ArrayList<>();
    private CartAdapter cartAdapter;

    private Users users;
    private EditText editAddress;
    private String address;
    PlacesClient placesClient;
    Place shippingAddress;
    List<Place.Field> placeFields = Arrays.asList(Place.Field.ID,
            Place.Field.NAME,
            Place.Field.LAT_LNG,
            Place.Field.ADDRESS);
    AutocompleteSupportFragment autocompleteSupportFragment;
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
        editAddress = findViewById(R.id.edit_address);
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

        initPlaces();
        LayoutInflater inflater = this.getLayoutInflater();
        View order_addres_comment = inflater.inflate(R.layout.order_address_comment, null);
        setupPlaceAutocomplete();
        editAddress = new EditText(this);
//        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
//                LinearLayout.LayoutParams.MATCH_PARENT,
//                LinearLayout.LayoutParams.MATCH_PARENT
//        );
//        editAddress.setLayoutParams(layoutParams);
        alertDialog.setView(order_addres_comment);
        alertDialog.setView(editAddress);
        alertDialog.setIcon(R.drawable.ic_add_shopping_cart_24px);

        alertDialog.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
//                if("".equals(address))
//              address = shippingAddress.getAddress();
//                address = editAddress.getText().toString();
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
                getSupportFragmentManager().beginTransaction()
                        .remove(Objects.requireNonNull(getSupportFragmentManager().findFragmentById(R.id.places_autocomplete_fragment)))
                        .commit();
            }
        });
        alertDialog.show();
    }

    private void setupPlaceAutocomplete() {
        autocompleteSupportFragment = (AutocompleteSupportFragment)getSupportFragmentManager().findFragmentById(R.id.places_autocomplete_fragment);
        autocompleteSupportFragment.getView().findViewById(R.id.places_autocomplete_search_button).setVisibility(View.GONE);
        ((EditText)autocompleteSupportFragment.getView().findViewById(R.id.places_autocomplete_search_input)).setHint("Chọn địa chỉ giao hàng");
        //set Text size
        ((EditText)autocompleteSupportFragment.getView().findViewById(R.id.places_autocomplete_search_input)).setTextSize(14);

        autocompleteSupportFragment.setPlaceFields(placeFields);
        autocompleteSupportFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(@NonNull Place place) {
                shippingAddress = place;
            }

            @Override
            public void onError(@NonNull Status status) {
                Toast.makeText(CartActivity.this,""+status.getStatusMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void initPlaces() {
        Places.initialize(this,getString(R.string.places_api_key));
        placesClient = Places.createClient(this);
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
                            "0",
                            carts,
                            users.uid,
                            ""
//                                    +String.format("%s,%s", shippingAddress.getLatLng().latitude,shippingAddress.getLatLng().longitude)
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



    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        if(item.getTitle().equals(Common.DELETE))
            deleteCart(item.getOrder());
        return true;
    }

    private void deleteCart(int position) {
        carts.remove(position);
        new Database(this).cleanCart();
        for(Order item:carts )
            new Database(this).addToCart(item);
        cartAdapter.notifyDataSetChanged();

    }
}