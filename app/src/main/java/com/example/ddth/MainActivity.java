package com.example.ddth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;

import com.example.ddth.Adapter.DanangFoodAdapter;
import com.example.ddth.Adapter.PopularFoodApdater;
import com.example.ddth.Fragment.FavoriteFragment;
import com.example.ddth.Fragment.HomeFragment;
import com.example.ddth.Fragment.ProfileFragment;
import com.example.ddth.Login.LoginActivity;
import com.example.ddth.Model.DanangFood;
import com.example.ddth.Model.PopularFood;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    RecyclerView popularRecyler;
    RecyclerView danangRecycler;
    PopularFoodApdater popularFoodApdater;
    DanangFoodAdapter danangFoodAdapter;
    private NavController navController;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_main);

        navController = Navigation.findNavController(this, R.id.fragment);

        BottomNavigationView navigationView = findViewById(R.id.navigation);
        navigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


        NavigationUI.setupWithNavController(navigationView, navController);


//        printKeyHash();

    }

//    private void printKeyHash() {
//        try {
//            PackageInfo info =  getPackageManager().getPackageInfo("com.example.ddth", PackageManager.GET_SIGNATURES);
//            for(Signature signature : info.signatures)
//            {
//                MessageDigest messageDigest = MessageDigest.getInstance("SHA");
//                messageDigest.update(signature.toByteArray());
//                Log.d("KeyHash", Base64.encodeToString(messageDigest.digest(), Base64.DEFAULT));
//            }
//        } catch (PackageManager.NameNotFoundException e) {
//            e.printStackTrace();
//
//        } catch (NoSuchAlgorithmException e)
//        {
//            e.printStackTrace();
//        }
//    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {


            switch (item.getItemId()){
                case R.id.home:
                    navController.navigate(R.id.home);
                    break;
                case R.id.order:

                        navController.navigate(R.id.order);


                    break;
                case R.id.favorite:
                    navController.navigate(R.id.favorite);
                    break;
                case R.id.profile:
                    navController.navigate(R.id.profile);
                    break;
            }
            return true;
        }
    };

}
