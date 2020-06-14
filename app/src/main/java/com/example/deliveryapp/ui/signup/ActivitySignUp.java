package com.example.deliveryapp.ui.signup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.deliveryapp.AccountType;
import com.example.deliveryapp.MainActivity;
import com.example.deliveryapp.R;
import com.example.deliveryapp.ui.login.LogInActivity;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ActivitySignUp extends AppCompatActivity {


    private TextInputEditText fNameEditText;
    private TextInputEditText lNameEditText;
    private TextInputEditText emailEditText;
    private RadioGroup accountTypeRadioGroup;
    private TextInputEditText passwordEditText;
    private Button signUpBtn;


    private String fNameText;
    private String lNameText;
    private String accountTypeText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        fNameEditText = findViewById(R.id.fNameField);
        lNameEditText = findViewById(R.id.lNameField);
        emailEditText = findViewById(R.id.emailField);
        accountTypeRadioGroup = findViewById(R.id.accountTypeRadioGroup);
        passwordEditText = findViewById(R.id.passwordField);
        signUpBtn = findViewById(R.id.signUpBtn);
        signUpBtn.setOnClickListener(signUpBtnOnClick());



    }

    private View.OnClickListener signUpBtnOnClick(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                fNameText = fNameEditText.getText().toString();
                lNameText = lNameEditText.getText().toString();
                String emailText = emailEditText.getText().toString();


                int selectedID = accountTypeRadioGroup.getCheckedRadioButtonId();
                RadioButton selectedRadioBtn = findViewById(selectedID);

                if(selectedRadioBtn != null){
                    accountTypeText = selectedRadioBtn.getText().toString().toLowerCase();
                    if(!accountTypeText.equals(AccountType.user.name()) &&
                            !accountTypeText.equals(AccountType.courier.name())){
                        Toast.makeText(getApplicationContext(), "Invalid account type, Try Again", Toast.LENGTH_SHORT).show();
                        accountTypeRadioGroup.clearCheck();
                        return;
                    }
                }
                else{
                    Toast.makeText(getApplicationContext(), "Select a account type", Toast.LENGTH_SHORT).show();
                    return;
                }


                String passwordText = passwordEditText.getText().toString();

                if(fNameText.isEmpty() || lNameText.isEmpty() || emailText.isEmpty()
                        || passwordText.isEmpty()){


                    if(fNameText.isEmpty()){
                        fNameEditText.setError("Please fill in field");
                    }else{
                        fNameEditText.setError(null);
                    }

                    if(lNameText.isEmpty()){
                        lNameEditText.setError("Please fill in field");
                    }else{
                        lNameEditText.setError(null);
                    }

                    if(emailText.isEmpty()){
                        emailEditText.setError("Please fill in field");
                    }else{
                        emailEditText.setError(null);
                    }

                    if(passwordText.isEmpty()){
                        passwordEditText.setError("Please fill in field");
                    }else{
                        passwordEditText.setError(null);
                    }

                }else{
                    fNameEditText.setError(null);
                    lNameEditText.setError(null);
                    emailEditText.setError(null);
                    passwordEditText.setError(null);

                    createAccountRequest(emailText, passwordText);

                }

            }
        };
    }


    public void createAccountRequest(String email, String password) {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JSONObject object = new JSONObject();
        try {
            //input your API body
            object.put("email", email );
            object.put("password",password);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String url = getString(R.string.url) +"api/user/new";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, object,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            String resp =  response.getString("status");
                            Log.d("HTTP", resp);
                            //checks if the response status is true
                            if(resp.equals("true")){
                                int accountID = response.getJSONObject("account").getInt("AccountID");
                                String token = response.getJSONObject("account").getString("token");
                                createProfileRequest(fNameText, lNameText,accountID,
                                        accountTypeText, token);
                            }else {
                                Toast.makeText(getApplicationContext(),
                                        response.getString("message"), Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("HTTP ERROR","Error on response" + error);
                Toast.makeText(getApplicationContext(), "Sign up Unsuccessful", Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(jsonObjectRequest);
    }


    public void createProfileRequest(String firstName, String lastName,
                                     int accountID, String accountType, final String token) {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JSONObject object = new JSONObject();
        try {
            //input your API body
            object.put("firstName", firstName );
            object.put("lastName",lastName);
            object.put("accountID",accountID);
            object.put("accountType",accountType);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String url = getString(R.string.url) + "api/user/new/profile";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, object,
                new Response.Listener<JSONObject>() {


                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            String resp =  response.getString("status");
                            Log.d("HTTP", resp);
                            //checks if the response status is true
                            if(resp.equals("true")){
                                Toast.makeText(getApplicationContext(), "Sign up successful", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(), LogInActivity.class);
                                startActivity(intent);
                            }else {
                                Toast.makeText(getApplicationContext(),
                                        response.getString("message"), Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("HTTP ERROR","Error on response" + error);
                Toast.makeText(getApplicationContext(), "Sign up Unsuccessful", Toast.LENGTH_SHORT).show();
            }

        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Authorization", token);
                return headers;
            }
        };
        requestQueue.add(jsonObjectRequest);
    }


}
