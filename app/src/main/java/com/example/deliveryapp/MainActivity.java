package com.example.deliveryapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.deliveryapp.ui.delivery.DeliveryJobsActivity;
import com.example.deliveryapp.ui.profile.ProfileActivity;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomnavigation.LabelVisibilityMode;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Profile currentProfile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Intent intent = getIntent();
        currentProfile = (Profile) intent.getParcelableExtra("profile_data");

        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setLabelVisibilityMode(LabelVisibilityMode.LABEL_VISIBILITY_LABELED);
        BadgeDrawable badge = navView.getOrCreateBadge(R.id.navigation_notifications);
        badge.setVisible(true);
        badge.setNumber(5);

        navView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Intent intent;
                switch (item.getItemId()){

                    case R.id.navigation_home:
                        intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                        return true;
                    case R.id.navigation_deliveries:
                        intent = new Intent(MainActivity.this,
                                DeliveryJobsActivity.class);
                        intent.putExtra("profile_data", currentProfile);
                        startActivity(intent);
                        return true;
                    case R.id.navigation_notifications:
//                        intent = new Intent(MainActivity.this, AddDelivery.class);
//                        startActivity(intent);
                        return true;
                    case R.id.navigation_profile:
                        intent = new Intent(MainActivity.this, ProfileActivity.class);
                        intent.putExtra("profile_data", currentProfile);
                        startActivity(intent);
                        return true;
                    default:
                        return false;
                }
            }
        });


    }

    public Profile getCurrentProfile() {
        return currentProfile;
    }
}
