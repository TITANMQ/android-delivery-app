package com.example.deliveryapp.ui.profile.details;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.deliveryapp.Profile;
import com.example.deliveryapp.ui.profile.ProfileActivity;
import com.example.deliveryapp.R;

public class ProfileDetails extends Fragment {

    private TextView fName;
    private TextView lName;
    private TextView email;


    public static ProfileDetails newInstance() {
        return new ProfileDetails();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        final ViewGroup root = (ViewGroup)
                inflater.inflate(R.layout.profile_details_fragment, container, false);

        fName = root.findViewById(R.id.profile_fName);
        lName = root.findViewById(R.id.profile_lName);
        email = root.findViewById(R.id.profile_email);



        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        final Profile currentProfile =  ((ProfileActivity) getActivity()).getCurrentProfile();
        setProfileDetails(currentProfile);


    }

    private void setProfileDetails(Profile profile){
        if(profile != null){
            fName.setText(profile.getFirstName());
            lName.setText(profile.getLastName());
            email.setText(profile.getEmail());
        }else{
            fName.setText("Error");
            lName.setText("Error");
            email.setText("Error");
        }
    }

}
