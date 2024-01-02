// RESOURCES //
//Firebase UI Auth - https://github.com/firebase/FirebaseUI-Android/blob/master/auth/README.md
//User Management - https://github.com/firebase/snippets-android/blob/f5674d4fa41e1023c0abdaedf1b0151c3767a35e/auth/app/src/main/java/com/google/firebase/quickstart/auth/MainActivity.java#L77-L91
package com.example.collabbees;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.splashscreen.SplashScreen;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.collabbees.Accounts.AccountsManager;
import com.example.collabbees.Chat.AllChatsActivity;
import com.example.collabbees.Network.NetworkSocialActivity;
import com.example.collabbees.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    ///////////////
    // VARIABLES //
    //////////////

    // VARIABLES //

    // MANAGERS //
    AccountsManager accountsManager = AccountsManager.getInstance();

    // VIEW BINDINGS //
    private ActivityMainBinding binding;

    // VIEWS //

    // INTENTS //
    Intent userProfileIntent;
    Intent networkPeopleIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Handle the splash screen transition.
//        try {Thread.sleep(1000);}
//        catch (InterruptedException e) {throw new RuntimeException(e);}
        SplashScreen.installSplashScreen(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Initialisations
        userProfileIntent = new Intent(this, UserProfileActivity.class);
        networkPeopleIntent = new Intent(this, NetworkSocialActivity.class);

        //TODO

        checkLoginStatus();
        setupListeners();
    }

    // Check if user is signed in
    @Override
    public void onStart() {
        super.onStart();
        // TODO Check if user is signed in (non-null) and update UI accordingly.
//        FirebaseUtils.startSignInActivity(this);
    }

    //////////////
    // FUNCTIONS //
    //////////////

    private void setupListeners(){
        binding.btnStart.setOnClickListener(view -> {
            startActivity(networkPeopleIntent);
        });

        binding.btnGo.setOnClickListener(view -> {
            startActivity(userProfileIntent);
        });

        binding.btnAll.setOnClickListener(view -> {
            startActivity(new Intent(this, AllChatsActivity.class));
        });
    }

    /**
     * Check login status.
        If logged in, will get account and add it into Intent object.
        If not logged in, will start sign in activity.
     */
    private void checkLoginStatus() {
        // 1. User logged in
        if (accountsManager.getCurrentUser() != null) {
            // get current user account and put into Intent object
            getUserAccount();
        }
        // 2. User not logged in
        else {
            FirebaseUtils.startSignInActivity(this);
        }
    }

    //TODO DONT PUT IN INTENT, RETRIEVE THE OBJECT AND PUT IT IN INTENT LATER
    /** Get current user account and add it into Intent object */
    private void getUserAccount() {
        if (accountsManager.isCurrentUserLoggedIn()) {
            accountsManager.getCurrentAccount()
                .addOnSuccessListener(user -> {
                    userProfileIntent.putExtra("user", user);
                })
                .addOnFailureListener(task -> {
                    Log.e(TAG, "Null Response: "+ task.getClass());
                });
        }
    }

    //TODO DONT PUT IN INTENT, RETRIEVE THE OBJECT AND PUT IT IN INTENT LATER
    /** Get arraylist of all accounts other user account and add it into Intent object */
//    private void getAllAccounts() {
//        if (accountsManager.isCurrentUserLoggedIn()) {
//            accountsManager.getCurrentAccount()
//                    .addOnSuccessListener(user -> {
//                        userProfileIntent.putExtra("user", user);
//                    })
//                    .addOnFailureListener(task -> {
//                        Log.e(TAG, "Null Response: "+ task.getClass());
//                    });
//        }
//    }
}