package com.example.deliveryapp.ui.delivery;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.deliveryapp.Delivery;
import com.example.deliveryapp.Profile;
import com.example.deliveryapp.R;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.Calendar;

import java.util.Date;
import java.util.HashMap;

import java.util.Locale;
import java.util.Map;

public class AddDelivery extends AppCompatActivity {

    private AutoCompleteTextView deliveryTypeField;
    private TextInputEditText expiryDateField;
    private TextInputEditText expiryTimeField;
    private AutoCompleteTextView collectionAddressField;
    private AutoCompleteTextView deliveryAddressField;
    private TextInputEditText extraDetailsField;
    private Button createBtn;
    private int mYear, mMonth, mDay;
    private Date currentDate;
    private  SimpleDateFormat dateFormat;
    private int mHour, mMinute, currentTime;
    private Profile currentProfile;


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_delivery);


        Intent intent = getIntent();
        currentProfile = (Profile) intent.getParcelableExtra("profile_data");
        //time/day
        dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);

        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);

        try {
            currentDate = dateFormat.parse(String.format(Locale.ENGLISH,"%d-%d-%d", mDay, mMonth, mYear ));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        currentTime = mHour + mMinute;


        expiryDateField = findViewById(R.id.expiry_date_field);
        expiryTimeField = findViewById(R.id.expiry_time_field);
        deliveryTypeField = findViewById(R.id.delivery_type_dropDown);
        extraDetailsField = findViewById(R.id.delivery_extra_details);
        createBtn = findViewById(R.id.createDeliveryBtn);

        createBtn.setOnClickListener(createDelivery());

        collectionAddressField = findViewById(R.id.add_delivery_collection_address);
        collectionAddressField.setThreshold(7);
        collectionAddressField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(final CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.length() >= 6 && charSequence.length()  <= 8 ){
                    RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                    String url = "https://api.getaddress.io/find/" +charSequence +
                            "?api-key=" + getString(R.string.address_api_key);

                    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                            new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {

                                    try {
                                        JSONArray jArray =  response.getJSONArray("addresses");
                                        ArrayAdapter<String> addresses = new ArrayAdapter<String>(getApplicationContext(),
                                                android.R.layout.simple_dropdown_item_1line);
                                        for(int i = 0; i < jArray.length(); i++){
                                            addresses.add((jArray.get(i).toString()+ ", " + charSequence).replaceAll(" ,", ""));
                                        }
                                        collectionAddressField.setAdapter(addresses);
                                        collectionAddressField.showDropDown();


                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.e("HTTP ERROR","Error on response" + error);
                        }
                    });
                    requestQueue.add(jsonObjectRequest);

                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        deliveryAddressField = findViewById(R.id.add_delivery_delivery_address);
        deliveryAddressField.setThreshold(7);
        deliveryAddressField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(final CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.length() >= 7 && charSequence.length()  <= 8 ){
                    RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                    String url = "https://api.getaddress.io/find/" +charSequence +
                            "?api-key=" + getString(R.string.address_api_key);

                    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                            new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {

                                    try {
                                        JSONArray jArray =  response.getJSONArray("addresses");
                                        ArrayAdapter<String> addresses = new ArrayAdapter<String>(getApplicationContext(),
                                                android.R.layout.simple_dropdown_item_1line);
                                        for(int i = 0; i < jArray.length(); i++){
                                            addresses.add((jArray.get(i).toString()+ ", " + charSequence).replaceAll(" ,", ""));
                                        }
                                        deliveryAddressField.setAdapter(addresses);
                                        deliveryAddressField.showDropDown();



                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.e("HTTP ERROR","Error on response" + error);
                        }
                    });
                    requestQueue.add(jsonObjectRequest);

                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });



        // drop down
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.delivery_types_array, R.layout.support_simple_spinner_dropdown_item);

        deliveryTypeField.setAdapter(adapter);


        expiryDateField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                DatePickerDialog datePickerDialog = new DatePickerDialog(AddDelivery.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                Date chosenDate;
                                String month, day;

                                try {
                                    chosenDate = dateFormat.parse(String.format(Locale.ENGLISH,"%d-%d-%d", dayOfMonth, monthOfYear, year));
                                    if(chosenDate.after(currentDate) ) {

                                        if((monthOfYear + 1)<10){
                                            month = "0"+(monthOfYear + 1);
                                        }else{
                                            month = Integer.toString(monthOfYear + 1);
                                        }

                                        if(dayOfMonth < 10){
                                            day = "0"+dayOfMonth;
                                        }else{
                                            day = Integer.toString(dayOfMonth);
                                        }
                                        expiryDateField.setText(String.format("%s-%s-%s",
                                                day,
                                                month, year));
                                    }else{
                                        Toast.makeText(AddDelivery.this,
                                                "Date is in the past", Toast.LENGTH_SHORT).show();
                                    }
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }


                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();

            }
        });

        expiryTimeField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Launch Time Picker Dialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(AddDelivery.this,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {

                                //TODO make sure not past time
                                String minuteText, hourText;

                                if(hourOfDay < 10){
                                    hourText = "0" + hourOfDay;
                                }else{
                                    hourText = Integer.toString(hourOfDay);
                                }

                                if(minute < 10){
                                    minuteText = "0" + minute;
                                }else{
                                    minuteText = Integer.toString(minute);
                                }

                                expiryTimeField.setText(String.format("%s:%s", hourText, minuteText));
                            }
                        }, mHour, mMinute, false);
                timePickerDialog.show();
            }
        });


    }


    private View.OnClickListener createDelivery(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO Verify that all fields are completed

                int userProfileID = currentProfile.getProfileID();
                String deliveryType = deliveryTypeField.getText().toString().toLowerCase();
                String collectionAddress = collectionAddressField.getText().toString();
                String deliveryAddress = deliveryAddressField.getText().toString();
                String expiryDate = expiryDateField.getText().toString();
                String expiryTime = expiryTimeField.getText().toString();
                String deliveryExpiry = createDeliveryExpiryDate(expiryDate, expiryTime);
                String extraDetails = extraDetailsField.getText().toString();
                String status = "Awaiting response";

                Delivery delivery = new Delivery(0, userProfileID, status, collectionAddress,
                        deliveryAddress, deliveryType,deliveryExpiry, extraDetails);

                createDeliveryPOST(delivery);

            }
        };
    }

    
    public void createDeliveryPOST(Delivery delivery) {
        Log.d("delivery", delivery.toString());
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JSONObject object = new JSONObject();
        try {
            //input your API body
            object.put("profileID", delivery.getProfileID() );
            object.put("deliveryType",delivery.getDeliveryType());
            object.put("collectionAddress",delivery.getCollectionAddress());
            object.put("deliveryAddress",delivery.getDeliveryAddress());
            object.put("expiryDate",delivery.getExpiryDate());
            object.put("extraDetails", delivery.getExtraDetails());
            object.put("accepted", false);
            object.put("status", delivery.getStatus());


        } catch (JSONException e) {
            e.printStackTrace();
        }
        String url = getString(R.string.url) +"api/delivery/new";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, object,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            String resp =  response.getString("status");
                            Log.d("HTTP", resp);

                            if(resp.equals("true")){
                                Toast.makeText(getApplicationContext(),
                                        response.getString("message"), Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(), DeliveryJobsActivity.class);
                                intent.putExtra("user_data", currentProfile);
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
                Toast.makeText(getApplicationContext(), "Delivery creation unsuccessful", Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Authorization", currentProfile.getToken());
                return headers;
            }
        };
        requestQueue.add(jsonObjectRequest);
    }

    //Creates date into sql timestamp format
    private String createDeliveryExpiryDate(String date, String time){
        String[] dateArray;
        dateArray = date.split("-");


        String sb = dateArray[2] + //year
                dateArray[1] + //month
                dateArray[0];//day

        return sb + " " + time;
    }
}

