package com.example.ddth.Fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ddth.EditUserActivity;
import com.example.ddth.Login.LoginActivity;
import com.example.ddth.MainActivity;
import com.example.ddth.Manage.ManageOrderActivity;
import com.example.ddth.Manage.ManageShipperActivity;
import com.example.ddth.Model.User;
import com.example.ddth.Model.Users;
import com.example.ddth.R;
import com.example.ddth.SplashActivity;
import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.lang.ref.ReferenceQueue;
import java.util.prefs.Preferences;

public class ProfileFragment extends Fragment {
    private Button btnLoginAndRegister, btnLogOut;
    private TextView ManageOrder, ManageShipper;

    private ImageView avatarUser;

    private FirebaseUser user;

    private FirebaseAuth auth;

    private FirebaseDatabase database;

    private TextView txtInfo;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_profile, container, false);


        ManageOrder = v.findViewById(R.id.manageOrder);
        ManageOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(requireContext(), ManageOrderActivity.class));
            }
        });
        ManageShipper = v.findViewById(R.id.manageShipper);
        ManageShipper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(requireContext(), ManageShipperActivity.class));
            }
        });
        btnLoginAndRegister = v.findViewById(R.id.btn_login_register);
        btnLogOut = v.findViewById(R.id.btn_logout);
        txtInfo = v.findViewById(R.id.userInfo);
        avatarUser = v.findViewById(R.id.avatar);
        user = auth.getInstance().getCurrentUser();

        if(user != null){

            avatarUser.setOnClickListener(v1 -> startActivity(new Intent(requireContext(), EditUserActivity.class)));

        }
        else {
            avatarUser.setOnClickListener(null);
        }
        return v;

    }
    private void checkUserStatus(){
         user = auth.getInstance().getCurrentUser();




        if (user != null ) {


            database = FirebaseDatabase.getInstance();
            DatabaseReference databaseReference = database.getReference("Users");
            Query query = databaseReference.orderByChild("email").equalTo(user.getEmail());
            ManageOrder.setVisibility(View.VISIBLE);
            btnLoginAndRegister.setVisibility(View.GONE);
            btnLogOut.setVisibility(View.VISIBLE);
            query.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot ds: snapshot.getChildren()){
                        // get data
                        String name = ""+ds.child("name").getValue();
                        String  image = ""+ds.child("image").getValue();
                        txtInfo.setText(name);
                        try
                        {
                            Picasso.get().load(image).into(avatarUser);
                        }
                        catch (Exception e)
                            {
                            Picasso.get().load(R.drawable.profile).into(avatarUser);
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }

        else {
            btnLoginAndRegister.setVisibility(View.VISIBLE);
            btnLogOut.setVisibility(View.GONE);
            ManageOrder.setVisibility(View.GONE);
            btnLoginAndRegister.setOnClickListener(v -> openBtnLoginAndRegister());
        }
    }
    @Override
    public void onStart() {
        super.onStart();
        checkUserStatus();
        sigOut();

    }
    public void sigOut(){
        btnLogOut.setOnClickListener(v -> {
            if (v.getId() == R.id.btn_logout) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(requireContext(), LoginActivity.class));

            }


        });

    }

    public void openBtnLoginAndRegister() {
       Intent intent = new Intent(requireContext(), LoginActivity.class);
       startActivity(intent);
    }
}