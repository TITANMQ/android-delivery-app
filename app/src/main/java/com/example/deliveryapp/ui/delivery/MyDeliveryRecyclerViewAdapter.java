package com.example.deliveryapp.ui.delivery;

import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.deliveryapp.Delivery;
import com.example.deliveryapp.Profile;
import com.example.deliveryapp.R;
import com.example.deliveryapp.ui.delivery.DeliveryListFragment.OnListFragmentInteractionListener;
import com.example.deliveryapp.dummy.DeliveryContent.DeliveryItem;


import java.util.List;


public class MyDeliveryRecyclerViewAdapter extends RecyclerView.Adapter<MyDeliveryRecyclerViewAdapter.ViewHolder> {

    private final List<DeliveryItem> mValues;
    private final OnListFragmentInteractionListener mListener;
    private Profile currentProfile;

    public MyDeliveryRecyclerViewAdapter(List<DeliveryItem> items, OnListFragmentInteractionListener listener, Profile currentProfile) {
        mValues = items;
        mListener = listener;
        this.currentProfile = currentProfile;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_delivery_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.mItem = mValues.get(position);
        holder.mCollectionAddress.setText(mValues.get(position).collectionAddress);
        holder.mDeliveryAddress.setText(mValues.get(position).deliveryAddress);
        holder.mDeliveryType.setText(mValues.get(position).deliveryType);
        holder.mExpiryDate.setText(mValues.get(position).expiryDate);

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    Intent intent = new Intent(v.getContext(), DeliveryActivity.class);
                    intent.putExtra("delivery_data", mValues.get(position).getDelivery());
                    Log.d("profile data", currentProfile.toString());
                    intent.putExtra("profile_data",currentProfile );

                    v.getContext().startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mCollectionAddress;
        public final TextView mDeliveryAddress;
        public final TextView mDeliveryType;
        public final TextView mExpiryDate;
        public DeliveryItem mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mCollectionAddress = (TextView) view.findViewById(R.id.list_collection_address);
            mDeliveryAddress = (TextView) view.findViewById(R.id.list_delivery_address);
            mDeliveryType = (TextView) view.findViewById(R.id.list_delivery_type);
            mExpiryDate = (TextView) view.findViewById(R.id.list_expiry_date);
        }

        @Override
        public String toString() {
            return "ViewHolder{" +
                    "mView=" + mView +
                    ", mCollectionAddress=" + mCollectionAddress +
                    ", mDeliveryAddress=" + mDeliveryAddress +
                    ", mDeliveryType=" + mDeliveryType +
                    ", mExpiryDate=" + mExpiryDate +
                    ", mItem=" + mItem +
                    '}';
        }
    }
}
