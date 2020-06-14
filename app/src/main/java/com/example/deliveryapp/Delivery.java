package com.example.deliveryapp;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.maps.model.LatLng;

public class Delivery implements Parcelable {

    private int deliveryID;
    private int profileID;
    private String status;
    private String collectionAddress;
    private String deliveryAddress;
    private String deliveryType;
    private String expiryDate;
    private String extraDetails;
    private double collectionLat;
    private double collectionLng;
    private double deliveryLat;
    private double deliveryLng;

    public Delivery(int deliveryID, int profileID, String status, String collectionAddress, String deliveryAddress,
                    String deliveryType, String expiryDate, String extraDetails) {
        this.deliveryID = deliveryID;
        this.profileID = profileID;
        this.status = status;
        this.collectionAddress = collectionAddress;
        this.deliveryAddress = deliveryAddress;
        this.deliveryType = deliveryType;
        this.expiryDate = expiryDate;
        this.extraDetails = extraDetails;
    }


    protected Delivery(Parcel in) {
        deliveryID = in.readInt();
        profileID = in.readInt();
        status = in.readString();
        collectionAddress = in.readString();
        deliveryAddress = in.readString();
        deliveryType = in.readString();
        expiryDate = in.readString();
        extraDetails = in.readString();
        collectionLat = in.readDouble();
        collectionLng = in.readDouble();
        deliveryLat = in.readDouble();
        deliveryLng = in.readDouble();
    }

    public static final Creator<Delivery> CREATOR = new Creator<Delivery>() {
        @Override
        public Delivery createFromParcel(Parcel in) {
            return new Delivery(in);
        }

        @Override
        public Delivery[] newArray(int size) {
            return new Delivery[size];
        }
    };

    public int getDeliveryID() {
        return deliveryID;
    }

    public int getProfileID() {
        return profileID;
    }

    public String getStatus() {
        return status;
    }

    public String getCollectionAddress() {
        return collectionAddress;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public String getDeliveryType() {
        return deliveryType;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public String getExtraDetails() {
        return extraDetails;
    }



    public void setCollectionLatLng(double lat, double lng) {
        this.collectionLat = lat;
        this.collectionLng = lng;
    }

    public void setDeliveryLatLng(double lat, double lng) {
        this.deliveryLat = lat;
        this.deliveryLng = lng;
    }

    public LatLng getDeliveryLatLng(){
        return new LatLng(this.deliveryLat, this.deliveryLng);
    }

    public LatLng getCollectionLatLng(){
        return new LatLng(this.collectionLat, this.collectionLng);
    }


    @Override
    public String toString() {
        return "Delivery{" +
                "deliveryID=" + deliveryID +
                ", profileID=" + profileID +
                ", status='" + status + '\'' +
                ", collectionAddress='" + collectionAddress + '\'' +
                ", deliveryAddress='" + deliveryAddress + '\'' +
                ", deliveryType='" + deliveryType + '\'' +
                ", expiryDate='" + expiryDate + '\'' +
                ", extraDetails='" + extraDetails + '\'' +
                ", collectionLat=" + collectionLat +
                ", collectionLng=" + collectionLng +
                ", deliveryLat=" + deliveryLat +
                ", deliveryLng=" + deliveryLng +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

        parcel.writeInt(this.deliveryID);
        parcel.writeInt(this.profileID);
        parcel.writeString(this.status);
        parcel.writeString(this.collectionAddress);
        parcel.writeString(this.deliveryAddress);
        parcel.writeString(this.deliveryType);
        parcel.writeString(this.expiryDate);
        parcel.writeString(this.extraDetails);
        parcel.writeDouble(this.collectionLat);
        parcel.writeDouble(this.collectionLng);
        parcel.writeDouble(this.deliveryLat);
        parcel.writeDouble(this.deliveryLng);
    }
}
