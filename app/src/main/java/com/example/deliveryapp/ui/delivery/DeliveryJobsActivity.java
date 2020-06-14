package com.example.deliveryapp.ui.delivery;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.deliveryapp.Profile;
import com.example.deliveryapp.R;
import com.example.deliveryapp.dummy.DeliveryContent;
import com.example.deliveryapp.ui.delivery.search.DeliverySearchActivity;

public class DeliveryJobsActivity extends AppCompatActivity
        implements DeliveryListFragment.OnListFragmentInteractionListener  {


    private ImageView deliveryActionBtn;
    private Profile currentProfile;
    private ImageView backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_jobs);

        backBtn = findViewById(R.id.back_btn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        Intent intent = getIntent();
        currentProfile = (Profile) intent.getParcelableExtra("profile_data");

        deliveryActionBtn = findViewById(R.id.add_delivery_btn);

        if(currentProfile != null){


            switch (currentProfile.getAccountType()){
                case user: deliveryActionBtn.setOnClickListener(addDeliveryBtnOnClick());
                    break;
                case courier:
                    deliveryActionBtn.setImageDrawable(getDrawable(R.drawable.search_24dp));
                    deliveryActionBtn.setOnClickListener(deliverySearchBtnOnClick());
                    break;
            }

        }

    }



    private View.OnClickListener addDeliveryBtnOnClick(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), AddDelivery.class);
                intent.putExtra("profile_data", currentProfile);
                startActivity(intent);
            }
        };
    }

    private View.OnClickListener deliverySearchBtnOnClick(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), DeliverySearchActivity.class);
                intent.putExtra("profile_data", currentProfile);
                startActivity(intent);
            }
        };
    }

    @Override
    public void onListFragmentInteraction(DeliveryContent.DeliveryItem item) {

    }

    public Profile getCurrentProfile() {
        return currentProfile;
    }
}
