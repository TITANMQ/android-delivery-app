package com.example.deliveryapp.ui.delivery.search;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.deliveryapp.Delivery;
import com.example.deliveryapp.Profile;
import com.example.deliveryapp.ui.delivery.DeliveryActivity;
import com.example.deliveryapp.ui.delivery.DeliveryListFragment;
import com.example.deliveryapp.R;
import com.example.deliveryapp.dummy.DeliveryContent;

public class DeliverySearchActivity extends AppCompatActivity
        implements DeliveryListFragment.OnListFragmentInteractionListener {

    private ImageView searchBtn;
    private ImageView backBtn;
    private Profile currentProfile;
    private DeliveryListFragment deliveryListFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_search);

        Intent intent = getIntent();
        currentProfile = (Profile) intent.getParcelableExtra("profile_data");

        backBtn = findViewById(R.id.back_btn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        searchBtn = findViewById(R.id.search_btn);
        searchBtn.setOnClickListener(searchBtnOnclick());






    }


    public DeliveryListFragment getDeliveryListFragment() {
        return deliveryListFragment;
    }

    @Override
    public void onAttachFragment(@NonNull Fragment fragment) {
        if (fragment instanceof DeliveryListFragment) {
            deliveryListFragment = (DeliveryListFragment) fragment;
        }

    }


    @Override
    public void onListFragmentInteraction(DeliveryContent.DeliveryItem item) {


    }



    public Profile getCurrentProfile() {
        return currentProfile;
    }

    private View.OnClickListener searchBtnOnclick(){

        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getSupportFragmentManager();
                DialogFragment searchDialogFragment = DeliverySearchFragment.newInstance();
                searchDialogFragment.show(fm, "fragment_search");
            }
        };
    }
}
