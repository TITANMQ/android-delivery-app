package com.example.deliveryapp.ui.profile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.deliveryapp.R;
import com.example.deliveryapp.Profile;
import com.example.deliveryapp.dummy.ReviewItemContent;
import com.example.deliveryapp.ui.profile.edit.EditProfileDetails;
import com.example.deliveryapp.ui.profile.reviews.ProfileReviewListFragment;

public class ProfileActivity extends AppCompatActivity
        implements ProfileReviewListFragment.OnListFragmentInteractionListener {


    private Profile currentProfile;
    private ImageView editBtn;
    private ImageView backBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Intent intent = getIntent();
        currentProfile = (Profile) intent.getParcelableExtra("profile_data");

        editBtn = findViewById(R.id.edit_btn);
        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO implement profile edit btn on click

                Intent intent = new Intent(getBaseContext(), EditProfileDetails.class);
                intent.putExtra("user_data", currentProfile);
                startActivity(intent);
            }
        });

        backBtn = findViewById(R.id.back_btn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


    }

    public Profile getCurrentProfile() {
        return currentProfile;
    }

    @Override
    public void onListFragmentInteraction(ReviewItemContent.Review item) {

    }
}
