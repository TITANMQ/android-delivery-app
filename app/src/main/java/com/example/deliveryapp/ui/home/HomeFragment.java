package com.example.deliveryapp.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.deliveryapp.MainActivity;
import com.example.deliveryapp.R;
import com.example.deliveryapp.SettingsActivity;
import com.example.deliveryapp.ui.delivery.search.DeliverySearchActivity;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private ImageView settingsBtn;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.fragment_home, container, false);


        settingsBtn = root.findViewById(R.id.settings_btn);
        settingsBtn.setOnClickListener(settingsBtnOnClick());

        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);

        String welcomeMessage = "Hello, "+ ((MainActivity) getActivity()).getCurrentProfile().getFirstName();

        final TextView textView = root.findViewById(R.id.text_home);
        textView.setText(welcomeMessage);
        return root;
    }


    private View.OnClickListener settingsBtnOnClick(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), SettingsActivity.class));
            }
        };
    }
}