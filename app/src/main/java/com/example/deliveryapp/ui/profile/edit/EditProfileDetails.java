package com.example.deliveryapp.ui.profile.edit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.deliveryapp.Profile;
import com.example.deliveryapp.R;
import com.example.deliveryapp.ui.profile.ProfileActivity;

public class EditProfileDetails extends AppCompatActivity {


    private Button saveBtn;
    private Button deleteAccBtn;
    private ImageView backBtn;
    private EditText editFName;
    private EditText editLName;
    private EditText editEmail;

    //TODO Verify that all fields are completed

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile_details);

        editFName = findViewById(R.id.edit_profile_fName);
        editLName = findViewById(R.id.edit_profile_lName);
        editEmail = findViewById(R.id.edit_profile_email);

        Intent intent = getIntent();
        final Profile currentProfile = (Profile) intent.getParcelableExtra("user_data");
        setEditFields(currentProfile);

        saveBtn = findViewById(R.id.profile_save_btn);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(getBaseContext(), "Account updated", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getBaseContext(), ProfileActivity.class);
                intent.putExtra("user_data", currentProfile);
                startActivity(intent);
                //TODO implement save profile
            }
        });

        backBtn = findViewById(R.id.back_btn);
        backBtn.setOnClickListener(backBtnOnClick());

        deleteAccBtn = findViewById(R.id.deleteAccountBtn);
        deleteAccBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO implement delete account
            }
        });


    }


    private void setEditFields(Profile profile){
        if(profile != null){
            editFName.setText(profile.getFirstName());
            editLName.setText(profile.getLastName());
            editEmail.setText(profile.getEmail());
        }
    }


    private View.OnClickListener backBtnOnClick(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        };
    }
}
