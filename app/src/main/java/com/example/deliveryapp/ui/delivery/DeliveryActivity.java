package com.example.deliveryapp.ui.delivery;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
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
import com.example.deliveryapp.Delivery;
import com.example.deliveryapp.Profile;
import com.example.deliveryapp.R;
import com.example.deliveryapp.dummy.DeliveryContent;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DeliveryActivity extends AppCompatActivity implements OnMapReadyCallback {

    private Button acceptBtn;
    private ImageView backBtn;
    private TextView status;
    private TextView collectionAddress;
    private TextView deliveryAddress;
    private TextView deliveryType;
    private TextView expiryDate;
    private TextView extraDetails;


    private Delivery deliveryData;
    private Profile currentProfile;

    private GoogleMap map;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery);

        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.directionsMap);
        mapFragment.getMapAsync(this);

        status = findViewById(R.id.delivery_status);
        collectionAddress = findViewById(R.id.delivery_collection_address);
        deliveryAddress = findViewById(R.id.delivery_address);
        deliveryType = findViewById(R.id.delivery_type);
        expiryDate = findViewById(R.id.delivery_expiry_date);
        extraDetails = findViewById(R.id.delivery_extra_details);
        acceptBtn = findViewById(R.id.delivery_accept_btn);

        backBtn = findViewById(R.id.back_btn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        Intent i = getIntent();
        deliveryData = (Delivery) i.getParcelableExtra("delivery_data");
        currentProfile = (Profile) i.getParcelableExtra("profile_data");


        setDeliveryDetails(deliveryData);
        AccountType accountType = currentProfile.getAccountType();

        if(accountType != AccountType.courier){
            acceptBtn.setText(getText(R.string.cancel));
        }else{
            acceptBtn.setOnClickListener(acceptBtnOnclick());
        }
    }


    private void setDeliveryDetails(Delivery delivery){

        if(delivery != null){
            status.setText(delivery.getStatus());
            collectionAddress.setText(delivery.getCollectionAddress());
            deliveryAddress.setText(delivery.getDeliveryAddress());
            deliveryType.setText(delivery.getDeliveryType());
            expiryDate.setText(delivery.getExpiryDate());
            extraDetails.setText(delivery.getExtraDetails());

        }

    }
    @Override
    public void onMapReady(GoogleMap googleMap) {

        LatLng collectionLocation = deliveryData.getCollectionLatLng();
        if(collectionLocation == null){
            collectionLocation = new LatLng(51.9073, 10.202);
        }

        LatLng deliveryLocation = deliveryData.getDeliveryLatLng();
        if(deliveryLocation == null){
            deliveryLocation = new LatLng(55.9073, 10.202);
        }

        double latDif =Math.abs(collectionLocation.latitude - deliveryLocation.latitude);

        int zoom;

        if(latDif < 1){
            zoom = 10;

        }else if(latDif > 1 && latDif < 1.5){
            zoom = 5;
        }else {
            zoom = 3;
        }
        //TODO Implement maps direction
        map = googleMap;
        map.addMarker(new MarkerOptions()
                .position(collectionLocation)
                .title("Collection"));
        map.addMarker(new MarkerOptions()
                .position(deliveryLocation)
                .title("Delivery"));

        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(deliveryLocation)
                .zoom(zoom)
                .build();

        map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }

    private View.OnClickListener acceptBtnOnclick(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                acceptDeliveryPOST(deliveryData, currentProfile);

            }
        };
    }


    public void acceptDeliveryPOST(Delivery delivery, final Profile profile) {
        final RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JSONObject body = new JSONObject();
        try {
            //input your API body
            body.put("deliveryID", delivery.getDeliveryID());
            body.put("userAccountID", delivery.getProfileID());
            body.put("courierAccountID", profile.getAccountID());
            body.put("deliveryDate", delivery.getExpiryDate());

        } catch (JSONException e) {
            e.printStackTrace();
        }

        String url = getString(R.string.url) +"api/delivery/accept";

        final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, body,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            String resp =  response.getString("message");

                            if(resp.equals("Success")){
                                Toast.makeText(getApplicationContext(), "Delivery Accepted", Toast.LENGTH_SHORT).show();
                                acceptBtn.setText(getText(R.string.cancel));
                            }else {
                                Log.e("HTTP", resp);
                                Toast.makeText(getApplicationContext(), resp, Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("HTTP ERROR","Error on response " + error);
                Toast.makeText(getApplicationContext(), "Something went wrong, Try again.", Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Authorization", profile.getToken());
                return headers;
            }
        };
        requestQueue.add(jsonObjectRequest);
    }
}
