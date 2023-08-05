package com.ph25579.assignment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.ph25579.assignment.R;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.ph25579.assignment.mainFragment.AcountAdminFragment;
import com.ph25579.assignment.mainFragment.HomeAdminFragment;
import com.ph25579.assignment.mainFragment.ListAdminFragment;

public class MainActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;
    public int idUser=0;
    public String userName = "";
    public String userPassword = "";
    public String routeUser = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        initData();
    }

    private void initViews() {
        bottomNavigationView = findViewById(R.id.bottom_navigation);
    }

    private void initData() {
//        Intent intent = getIntent();
//        Bundle bundle = intent.getBundleExtra("bundleUser");
        SharedPreferences sharedPreferences = getSharedPreferences("UsersAccount", Context.MODE_PRIVATE);
        idUser = sharedPreferences.getInt("userId", 0);
        userName = sharedPreferences.getString("userName", "");
        userPassword = sharedPreferences.getString("userPassword", "");
        int route = sharedPreferences.getInt("userRoute", 1);
        if(route==0){
            routeUser = "Nhà cung cấp";
        }else if(route==1){
            routeUser = "Thành viên";
        }
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_home1:
                        Fragment fragment1 = new HomeAdminFragment();
                        replaceFragment(fragment1);
                        break;
                    case R.id.navigation_list:
                        Fragment fragment2 = new ListAdminFragment();
                        replaceFragment(fragment2);
                        break;
                    case R.id.navigation_notifications:
                        Fragment fragment3 = new AcountAdminFragment();
                        replaceFragment(fragment3);
                        break;
                    default:
                        Fragment fragment4 = new HomeAdminFragment();
                        replaceFragment(fragment4);
                        break;
                }
                return false;
            }
        });

    }

    public void replaceFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//        transaction.setCustomAnimations(R.anim.enter_right_to_left, R.anim.exit_right_to_left, R.anim.enter_left_to_right, R.anim.exit_left_to_right);
        transaction.replace(R.id.frame_main, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void replaceFragment1(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//        transaction.setCustomAnimations(R.anim.enter_right_to_left, R.anim.exit_right_to_left, R.anim.enter_left_to_right, R.anim.exit_left_to_right);
        transaction.replace(R.id.frame_main, fragment);
        transaction.commit();
    }
}