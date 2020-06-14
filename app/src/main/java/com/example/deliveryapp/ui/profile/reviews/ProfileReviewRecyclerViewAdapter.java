package com.example.deliveryapp.ui.profile.reviews;

import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.deliveryapp.ui.profile.reviews.ProfileReviewListFragment.OnListFragmentInteractionListener;
import com.example.deliveryapp.R;
import com.example.deliveryapp.dummy.ReviewItemContent;

import java.util.ArrayList;
import java.util.List;


//TODO polish out this code
/**
 * {@link RecyclerView.Adapter} that can display  review and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class ProfileReviewRecyclerViewAdapter extends RecyclerView.Adapter<ProfileReviewRecyclerViewAdapter.ViewHolder> {

    private final List<ReviewItemContent.Review> mValues;
    private final OnListFragmentInteractionListener mListener;
    private Context context;

    public ProfileReviewRecyclerViewAdapter(List<ReviewItemContent.Review> items, OnListFragmentInteractionListener listener, Context context) {
        mValues = items;
        mListener = listener;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.review_list_item, parent, false);
        return new ViewHolder(view);
    }


    private void setStarsImages(int stars, List<ImageView> imageViewList){
        for(int i = 0; i < stars-1; i++){
            imageViewList.get(i).setImageDrawable(context.getDrawable(R.drawable.star_24dp));
        }

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mReview = mValues.get(position);
        holder.mTitleView.setText((mValues.get(position).getTitle()));
        holder.mDateView.setText((mValues.get(position).getDate()));
        setStarsImages(mValues.get(position).stars, holder.imageViewList);
        holder.mMessageView.setText((mValues.get(position).getMessage()));

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
//                    mListener.onListFragmentInteraction(holder.mItem);
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
        public final TextView mTitleView;
        public final TextView mDateView;
        public final ImageView mStar1;
        public final ImageView mStar2;
        public final ImageView mStar3;
        public final ImageView mStar4;
        public final ImageView mStar5;
        public final TextView mMessageView;
        public List<ImageView> imageViewList;

        public ReviewItemContent.Review mReview;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            imageViewList = new ArrayList<>();
            mTitleView = (TextView) view.findViewById(R.id.review_title);
            mDateView= (TextView) view.findViewById(R.id.review_date);
            mStar1 = (ImageView) view.findViewById(R.id.starOne);
            imageViewList.add(mStar1);
            mStar2 = (ImageView) view.findViewById(R.id.starTwo);
            imageViewList.add(mStar2);
            mStar3 = (ImageView) view.findViewById(R.id.starThree);
            imageViewList.add(mStar3);
            mStar4 = (ImageView) view.findViewById(R.id.starFour);
            imageViewList.add(mStar4);
            mStar5 = (ImageView) view.findViewById(R.id.starFive);
            imageViewList.add(mStar5);
            mMessageView = (TextView) view.findViewById(R.id.review_message);


        }

//        @Override
//        public String toString() {
//            return super.toString() + " '" + mContentView.getText() + "'";
//        }
    }
}
