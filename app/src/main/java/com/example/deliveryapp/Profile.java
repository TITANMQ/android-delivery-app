package com.example.deliveryapp;

import android.os.Parcel;
import android.os.Parcelable;

public class Profile implements Parcelable {

    private int accountID;
    private int profileID;
    private String firstName;
    private String lastName;
    private String email;
    private AccountType accountType;
    private String token;


    public Profile(int accountID, int profileID, String firstName, String lastName,
                   String email, AccountType accountType) {
        this.accountID = accountID;
        this.profileID = profileID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.accountType = accountType;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    protected Profile(Parcel in) {
        accountID = in.readInt();
        profileID = in.readInt();
        firstName = in.readString();
        lastName = in.readString();
        email = in.readString();
        accountType = AccountType.valueOf(in.readString());
        token = in.readString();
    }

    public int getAccountID() {
        return accountID;
    }

    public int getProfileID() {
        return profileID;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public AccountType getAccountType() {
        return accountType;
    }


    @Override
    public String toString() {
        return "Profile{" +
                "profileID=" + profileID +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", accountType=" + accountType +
                ", token='" + token + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(accountID);
        parcel.writeInt(profileID);
        parcel.writeString(firstName);
        parcel.writeString(lastName);
        parcel.writeString(email);
        parcel.writeString(accountType.toString());
        parcel.writeString(token);
    }

    public static final Creator<Profile> CREATOR = new Creator<Profile>() {
        @Override
        public Profile createFromParcel(Parcel in) {
            return new Profile(in);
        }

        @Override
        public Profile[] newArray(int size) {
            return new Profile[size];
        }
    };
}
