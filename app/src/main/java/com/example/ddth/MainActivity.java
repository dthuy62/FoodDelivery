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

import android.os.Bundle;
import android.view.MenuItem;

import com.example.ddth.Adapter.DanangFoodAdapter;
import com.example.ddth.Adapter.PopularFoodApdater;
import com.example.ddth.Fragment.FavoriteFragment;
import com.example.ddth.Fragment.HomeFragment;
import com.example.ddth.Fragment.ProfileFragment;
import com.example.ddth.Model.DanangFood;
import com.example.ddth.Model.PopularFood;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.bottomnavigation.BottomNavigationView;

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
        setContentView(R.layout.activity_main);

        navController = Navigation.findNavController(this, R.id.fragment);

        BottomNavigationView navigationView = findViewById(R.id.navigation);
        navigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


        NavigationUI.setupWithNavController(navigationView, navController);

    }
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
