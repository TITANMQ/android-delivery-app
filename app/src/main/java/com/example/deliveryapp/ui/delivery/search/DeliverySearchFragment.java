package com.example.deliveryapp.ui.delivery.search;

import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.deliveryapp.Profile;
import com.example.deliveryapp.R;
import com.example.deliveryapp.ui.delivery.DeliveryJobsActivity;
import com.example.deliveryapp.ui.delivery.DeliveryListFragment;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DeliverySearchFragment extends DialogFragment {

    private Button cancelBtn;
    private Button searchBtn;
    private AutoCompleteTextView journeyStart;
    private AutoCompleteTextView journeyEnd;
    private TextInputEditText searchRadius;
    private View view;
    private Profile currentProfile;

    public static DeliverySearchFragment newInstance() {
        return new DeliverySearchFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.delivery_search_fragment, container, false);
        return view;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);



        if (getActivity().getClass() == DeliverySearchActivity.class) {
            currentProfile = ((DeliverySearchActivity) getActivity()).getCurrentProfile();


        }

        searchRadius = view.findViewById(R.id.search_radius);

        journeyStart = view.findViewById(R.id.search_journey_start);
        journeyStart.setThreshold(7);
        journeyStart.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(final CharSequence charSequence, int i, int i1, int i2) {
                //if charSequence length equals the length of a postcode
                if(charSequence.length() >= 6 && charSequence.length()  <= 8 ){
                    RequestQueue requestQueue = Volley.newRequestQueue(view.getContext());
                    String url = "https://api.getaddress.io/find/" +charSequence +
                            "?api-key=" + getString(R.string.address_api_key);

                    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                            new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {

                                    try {
                                        JSONArray jArray =  response.getJSONArray("addresses");
                                        ArrayAdapter<String> addresses = new ArrayAdapter<String>(view.getContext(),
                                                android.R.layout.simple_dropdown_item_1line);
                                        for(int i = 0; i < jArray.length(); i++){
                                            //adds the post code to the end of addresses
                                            addresses.add((jArray.get(i).toString() + ", " + charSequence).replaceAll(" ,", ""));

                                        }

                                        journeyStart.setAdapter(addresses);
                                        journeyStart.showDropDown();


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


        journeyEnd = view.findViewById(R.id.search_journey_end);
        journeyEnd.setThreshold(7);
        journeyEnd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.length() >= 6 && charSequence.length()  <= 8 ){
                    RequestQueue requestQueue = Volley.newRequestQueue(view.getContext());
                    String url = "https://api.getaddress.io/find/" +charSequence +
                            "?api-key=" + getString(R.string.address_api_key);

                    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                            new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {

                                    try {
                                        JSONArray jArray =  response.getJSONArray("addresses");
                                        ArrayAdapter<String> addresses = new ArrayAdapter<String>(view.getContext(),
                                                android.R.layout.simple_dropdown_item_1line);
                                        for(int i = 0; i < jArray.length(); i++){
                                            addresses.add(jArray.get(i).toString().replaceAll(" ,", ""));
                                        }
                                        journeyEnd.setAdapter(addresses);
                                        journeyEnd.showDropDown();


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

        cancelBtn = view.findViewById(R.id.dialog_cancel_btn);
        cancelBtn.setOnClickListener(cancelBtnOnClick());

        searchBtn = view.findViewById(R.id.dialog_search_btn);
        searchBtn.setOnClickListener(searchBtnOnClick());


    }

    private View.OnClickListener cancelBtnOnClick(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        };
    }

    private View.OnClickListener searchBtnOnClick(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (getActivity().getClass() == DeliverySearchActivity.class) {

                    String start = journeyStart.getText().toString();
                    String end = journeyEnd.getText().toString();
                    String radius = searchRadius.getText().toString();

                    if(!start.equals("") && !end.equals("") && !radius.equals("")){

                        DeliveryListFragment deliveryListFragment =
                                ((DeliverySearchActivity) getActivity()).getDeliveryListFragment();
                        deliveryListFragment.searchDeliveriesGET(start, end,
                                Double.parseDouble(radius), currentProfile);

                        dismiss();

                    }else{
                        Toast.makeText(view.getContext(),
                                "Enter in all fields", Toast.LENGTH_SHORT).show();
                        Log.d("Some fields are empty", "Enter all fields");
                    }
                }

            }
        };
    }

}
