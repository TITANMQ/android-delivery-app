package com.example.deliveryapp.ui.profile.reviews;

import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.deliveryapp.AccountType;
import com.example.deliveryapp.R;
import com.example.deliveryapp.Profile;
import com.example.deliveryapp.ui.profile.ProfileActivity;

public class ReviewsFragment extends Fragment {

    private ReviewsViewModel mViewModel;


    public static ReviewsFragment newInstance() {
        return new ReviewsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.reviews_fragment, container, false);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(ReviewsViewModel.class);

        Profile currentProfile = ((ProfileActivity) getActivity()).getCurrentProfile();
        FragmentManager fm = getFragmentManager();
        if(currentProfile != null){
            if(currentProfile.getAccountType() == AccountType.user){

                fm.beginTransaction()
                        .hide(this)
                        .commit();
            }
        }


        // TODO: Use the ViewModel
    }

}
