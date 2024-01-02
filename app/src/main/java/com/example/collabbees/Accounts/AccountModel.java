package com.example.collabbees.Accounts;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Date;

public class AccountModel implements Parcelable {

    // PROPERTIES //
    private String uid;
    private String username;
    private String email;
    @Nullable private String urlProfilePic;

    private String bio;
    private String role;
    private String country;
    private String organisation;
    private Date lastSeen;

    private String desc;
    private String whatIdo;
    private ArrayList<String> skills;
    private ArrayList<String> personality;
    private ArrayList<String> work_traits;
    private ArrayList<String> favorites;
    private ArrayList<String> hobbies;
    private ArrayList<String> socials;

//    private int rating; // comments
    //creation date

    // CONSTRUCTOR //

    // java.lang.RuntimeException: Could not deserialize object. Class com.example.collabbees.Accounts.AccountModel
    // does not define a no-argument constructor. If you are using ProGuard, make sure these constructors are not stripped
    //Because to deserialize using firestore requires no-arg constructor
    public AccountModel(){}

    public AccountModel(String uid, String username, String email, String urlProfilePic, Date lastSeen) {
        this.uid = uid;
        this.username = username;
        this.email = email;
        this.urlProfilePic = urlProfilePic;
        this.bio = "";
        this.role = "";

        this.desc = "";
        this.country = "";
        this.organisation = "";
        this.lastSeen = lastSeen;
    }

    // GETTERS, SETTERS //
    public String getUsername() {return username;}
    @Nullable public String getUrlProfilePic() {return urlProfilePic;}
    public String getBio() {return bio;}
    public String getDesc() {return desc;}
    public String getEmail() {return email;}
    public String getCountry() {return country;}
    public String getOrganisation() {return organisation;}
    public String getRole() {return role;}
    public String getUid() {return uid;}
    public Date getLastSeen() {return lastSeen;}

    // PARCELABLE //
    @Override
    public int describeContents() {return 0;}

    protected AccountModel(Parcel in) {
        uid = in.readString();
        username = in.readString();
        email = in.readString();
        bio = in.readString();
        role = in.readString();
        urlProfilePic = in.readString();
        desc = in.readString();
        country = in.readString();
        organisation = in.readString();
        lastSeen = new Date(in.readLong());
        whatIdo = in.readString();
        skills = in.createStringArrayList();
        personality = in.createStringArrayList();
        work_traits = in.createStringArrayList();
        favorites = in.createStringArrayList();
        hobbies = in.createStringArrayList();
        socials = in.createStringArrayList();
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(uid);
        dest.writeString(username);
        dest.writeString(email);
        dest.writeString(bio);
        dest.writeString(role);
        dest.writeString(urlProfilePic);
        dest.writeString(desc);
        dest.writeString(country);
        dest.writeString(organisation);
        dest.writeLong(lastSeen.getTime());
        dest.writeString(whatIdo);
        dest.writeStringList(skills);
        dest.writeStringList(personality);
        dest.writeStringList(work_traits);
        dest.writeStringList(favorites);
        dest.writeStringList(hobbies);
        dest.writeStringList(socials);
    }

    public static final Creator<AccountModel> CREATOR = new Creator<AccountModel>() {
        @Override
        public AccountModel createFromParcel(Parcel in) {
            return new AccountModel(in);
        }

        @Override
        public AccountModel[] newArray(int size) {
            return new AccountModel[size];
        }
    };

}
