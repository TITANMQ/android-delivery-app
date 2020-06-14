package com.example.deliveryapp.ui.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
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
import com.example.deliveryapp.Profile;
import com.example.deliveryapp.R;
import com.example.deliveryapp.ui.signup.ActivitySignUp;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;


import java.util.HashMap;
import java.util.Map;


public class LogInActivity extends AppCompatActivity {

    //TODO Verify that all fields are completed

    private Button logInBtn;
    private TextInputEditText emailField;
    private TextInputEditText passwordField;
    private TextView signUpHereBtn;
    private RequestQueue requestQueue;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        logInBtn = findViewById(R.id.logInBtn);
        emailField = findViewById(R.id.logIn_email_field);
        passwordField = findViewById(R.id.logIn_password_field);
        signUpHereBtn = findViewById(R.id.sign_up_here_btn);


        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

        logInBtn.setOnClickListener(logInBtnOnClick());
        signUpHereBtn.setOnClickListener(signUpHereBtnOnClick());

    }

    /**
     * On click listener function for log in btn
     * @return onClickListener
     */
    private View.OnClickListener logInBtnOnClick(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emailText = emailField.getText().toString();
                String passwordText = passwordField.getText().toString();

                if(emailText.isEmpty() || passwordText.isEmpty()){


                    if(emailText.isEmpty()){
                        emailField.setError("Please fill in field");
                    }else{
                        emailField.setError(null);
                    }

                    if(passwordText.isEmpty()){
                        passwordField.setError("Please fill in field");
                    }else{
                        passwordField.setError(null);

                    }

                }else{
                    emailField.setError(null);
                    passwordField.setError(null);

                    logInPOST(emailText, passwordText);

                }
            }
        };
    }

    /**
     * On click listener function for sign up here btn
     * @return onClickListener
     */
    private View.OnClickListener signUpHereBtnOnClick(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getBaseContext(), ActivitySignUp.class);
                startActivity(intent);
            }
        };
    }

    /**
     * POST request to log in
     * @param email email
     * @param password password
     */
    public void logInPOST(String email, String password) {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JSONObject object = new JSONObject();
        try {
            //input your API body
            object.put("email", email );
            object.put("password",password);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String url = getString(R.string.url) +"api/user/login";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, object,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            String resp =  response.getString("message");
                            Log.d("HTTP", resp);

                            if(resp.equals("Logged In")){
                                int accountID = response.getJSONObject("account").getInt("AccountID");
                                String token  = response.getJSONObject("account").getString("token");
                                profileGET(accountID, token);

                            }else {
                                Toast.makeText(getApplicationContext(), resp, Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("HTTP ERROR","Error on response" + error);
                    Toast.makeText(getApplicationContext(), "Sign in Unsuccessful", Toast.LENGTH_SHORT).show();
                }
        });
        requestQueue.add(jsonObjectRequest);
    }


    /**
     * GET request to get profile data
     * @param accountID accountID
     */
    public void profileGET(int accountID, final String token) {
        final RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

        String url = getString(R.string.url) +"api/user/"+ accountID;

        final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            String resp =  response.getString("message");
                            int profileID = response.getJSONObject("data").getInt("profileID");
                            String fName = response.getJSONObject("data").getString("firstName");
                            String lName = response.getJSONObject("data").getString("lastName");
                            String email = response.getJSONObject("data").getString("email");
                            int accountID = response.getJSONObject("data").getInt("accountID");
                            String accountType = response.getJSONObject("data").getString("accountType");

                            Profile profile = new Profile(accountID, profileID, fName,
                                    lName, email,AccountType.valueOf(accountType));

                            profile.setToken(token);
//                            Log.d("HTTP", resp);  DEBUG ONLY

                            if(resp.equals("Success")){
                                Toast.makeText(getApplicationContext(), "Sign in Successful", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getBaseContext(), MainActivity.class);
                                intent.putExtra("profile_data",profile );
                                startActivity(intent);
                            }else {
                                Toast.makeText(getApplicationContext(), resp, Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("HTTP ERROR","Error on response" + error);
                Toast.makeText(getApplicationContext(), "Profile Error", Toast.LENGTH_SHORT).show();
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
