package com.example.deliveryapp.ui.delivery;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.example.deliveryapp.dummy.DeliveryContent.DeliveryItem;
import com.example.deliveryapp.ui.delivery.search.DeliverySearchActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class DeliveryListFragment extends Fragment {

    private static final String ARG_COLUMN_COUNT = "column-count";
    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;
    private Profile currentProfile;
    private RecyclerView recyclerView;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public DeliveryListFragment() {
    }


    public static DeliveryListFragment newInstance(int columnCount) {
        DeliveryListFragment fragment = new DeliveryListFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }


    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (getActivity().getClass() == DeliveryJobsActivity.class){
            currentProfile = ((DeliveryJobsActivity)getActivity()).getCurrentProfile();
            userDeliveriesGET(currentProfile);
            recyclerView.setAdapter(new MyDeliveryRecyclerViewAdapter(new ArrayList<DeliveryItem>(), mListener, currentProfile));
        }else if(getActivity().getClass() == DeliveryJobsActivity.class){
            currentProfile = ((DeliverySearchActivity)getActivity()).getCurrentProfile();
            recyclerView.setAdapter(new MyDeliveryRecyclerViewAdapter(new ArrayList<DeliveryItem>(), mListener, currentProfile));
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_delivery_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }





        }
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(DeliveryItem item);
    }



    public void userDeliveriesGET(final Profile profile) {
        final RequestQueue requestQueue = Volley.newRequestQueue(getContext());

        String url;
        if(profile.getAccountType() == AccountType.user ){
            url = getString(R.string.url) +"api/user/deliveries/"+ profile.getProfileID();
        }else{
            return;
        }

        final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            String resp =  response.getString("message");
                            Log.d("HTTP", resp);


                            if(resp.equals("Success")){
                                JSONArray deliveriesData = response.getJSONArray("data");
                                List<DeliveryItem> deliveryItems = new ArrayList<>();

                                JSONObject jO;
                                DeliveryItem item;
                                //gets each delivery data from the response
                                for(int i = 0; i < deliveriesData.length(); i++){

                                    jO = deliveriesData.getJSONObject(i);
                                    item = new DeliveryItem(new Delivery(
                                            jO.getInt("deliveryID"),
                                            jO.getInt("profileID"),
                                            jO.getString("status"),
                                            jO.getString("collectionAddress"),
                                            jO.getString("deliveryAddress"),
                                            jO.getString("deliveryType"),
                                            jO.getString("expiryDate"),
                                            jO.getString("extraDetails")
                                    ));
                                    //sets the longitude and latitude
                                    item.getDelivery().setCollectionLatLng(jO.getDouble("collectionLat"),
                                            jO.getDouble("collectionLon"));
                                    item.getDelivery().setDeliveryLatLng(jO.getDouble("deliveryLat"),
                                            jO.getDouble("deliveryLon")); //TODO change to lng
                                    deliveryItems.add(item);
                                }
                                //sets the list adapter
                                recyclerView.setAdapter(new MyDeliveryRecyclerViewAdapter(deliveryItems, mListener, currentProfile));
                                Toast.makeText(getContext(), "Loaded", Toast.LENGTH_SHORT).show();

                            }else {
                                Toast.makeText(getContext(), resp, Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("HTTP ERROR","Error on response" + error);
                Toast.makeText(getContext(), "Profile Error", Toast.LENGTH_SHORT).show();
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



    public void searchDeliveriesGET(String startAddress, String endAddress, double milesRadius, final Profile profile) {
        final RequestQueue requestQueue = Volley.newRequestQueue(getContext());

        JSONObject body = new JSONObject();
        try {
            //input your API body
            body.put("journeyStart", startAddress); //only works at the moment
            body.put("journeyEnd",endAddress);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        String url = getString(R.string.url) +"api/user/deliveries/search/"+ milesRadius ;

        final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, body,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            String resp =  response.getString("message");

                            if(resp.equals("Success")){

                                JSONArray deliveriesData = response.getJSONArray("data");
                                List<DeliveryItem> deliveryItems = new ArrayList<>();

                                JSONObject jO;
                                DeliveryItem item;
                                //gets each delivery data from the response
                                for(int i = 0; i < deliveriesData.length(); i++){

                                    jO = deliveriesData.getJSONObject(i);
                                    item = new DeliveryItem(new Delivery(
                                            jO.getInt("deliveryID"),
                                            jO.getInt("profileID"),
                                            jO.getString("status"),
                                            jO.getString("collectionAddress"),
                                            jO.getString("deliveryAddress"),
                                            jO.getString("deliveryType"),
                                            jO.getString("expiryDate"),
                                            jO.getString("extraDetails")
                                    ));
                                    //sets the longitude and latitude
                                    item.getDelivery().setCollectionLatLng(jO.getDouble("collectionLat"),
                                            jO.getDouble("collectionLon"));
                                    item.getDelivery().setDeliveryLatLng(jO.getDouble("deliveryLat"),
                                            jO.getDouble("deliveryLon"));
                                    deliveryItems.add(item);


                                }

                                //sets the list adapter
                                recyclerView.setAdapter(new MyDeliveryRecyclerViewAdapter(deliveryItems, mListener, profile));
                                Toast.makeText(getContext(), deliveryItems.size() + " Found"  , Toast.LENGTH_SHORT).show();

                            }else {
                                Log.e("HTTP", resp);
                                Toast.makeText(getContext(), resp, Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("HTTP ERROR","Error on response" + error);
                Toast.makeText(getContext(), "Profile Error", Toast.LENGTH_SHORT).show();
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
