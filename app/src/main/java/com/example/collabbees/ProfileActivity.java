package com.example.collabbees;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.collabbees.Accounts.AccountModel;
import com.example.collabbees.Accounts.AccountsManager;
import com.example.collabbees.Chat.ChatActivity;
import com.example.collabbees.Chat.ChatRoomManager;
import com.example.collabbees.databinding.ActivityProfileBinding;

public class ProfileActivity extends AppCompatActivity {

    ///////////////
    // VARIABLES //
    //////////////

    // VARIABLES //
    private AccountModel account;

    // VIEW BINDINGS //
    private ActivityProfileBinding binding;

    // MANAGERS //
    AccountsManager accountsManager = AccountsManager.getInstance();
    ChatRoomManager chatRoomManager = ChatRoomManager.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        binding = ActivityProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // AccountModel
        account = getAccountFromIntent();

        updateUI();
        setupListeners();
    }

    @Override
    protected void onStart() {
        super.onStart();
//        FirebaseUtils.startSignInActivity(this);

//        updateUIWithUserData();

//        if (authStateListener != null){
//            FirebaseAuth.getInstance().signOut();
//        }
//        firebaseAuth.addAuthStateListener(authStateListener);
    }
//
//    @Override
//    protected void onStop() {
//        super.onStop();
//        if (authStateListener != null){
//            firebaseAuth.removeAuthStateListener(authStateListener);
//        }
//    }

    ///////////////
    // FUNCTIONS //
    //////////////

    // Listeners
    private void setupListeners(){
        binding.btnConnect.setOnClickListener(view -> {
//            accountsManager.addFollowing(account)
//                    .addOnSuccessListener(task -> {
//                        Toast.makeText(getApplicationContext(), R.string.Successfully_Followed, Toast.LENGTH_SHORT).show();
//                        binding.btnConnect.setVisibility(View.GONE);
//                        binding.btnFollowing.setVisibility(View.VISIBLE);
//                        binding.btnMessage.setVisibility(View.VISIBLE);
//                        binding.btnProjectInvite.setVisibility(View.VISIBLE);
//                    })
//                    .addOnFailureListener(task -> {
//                        Toast.makeText(getApplicationContext(), R.string.Unsuccessfully_Followed, Toast.LENGTH_SHORT).show();
//                    });
        });

        binding.btnFollowing.setOnClickListener(view -> {
//            accountsManager.addFollowing(account)
//                    .addOnSuccessListener(task -> {
//                        Toast.makeText(getApplicationContext(), R.string.Successfully_Unfollowed, Toast.LENGTH_SHORT).show();
//                        binding.btnFollowing.setVisibility(View.GONE);
//                        binding.btnConnect.setVisibility(View.VISIBLE);
//                        binding.btnMessage.setVisibility(View.GONE);
//                        binding.btnProjectInvite.setVisibility(View.GONE);
//                    })
//                    .addOnFailureListener(task -> {
//                        Toast.makeText(getApplicationContext(), R.string.Unsuccessfully_Unfollowed, Toast.LENGTH_SHORT).show();
//                    });
        });

        // Start chat
        binding.btnMessage.setOnClickListener(view -> {

//            ChatRoomModel chatRoom = chatRoomManager.createChatRoom(account);
            Intent intent = new Intent(this, ChatActivity.class);
            intent.putExtra("account", account);
            startActivity(intent);
            finish();
        });
    }


//    /** Checks if user detail is empty or null and sets default text if it is */
//    private void setTextView(TextView tv, String text) {
//        // check for empty String and whitespace only String
//        if (text == null) {
//            tv.setText(tv.getText());
//        } else {
//            if (!text.isEmpty() && text.trim().length() > 0) {
//                tv.setText(text);
//            } else {
//                tv.setText(tv.getText());
//            }
//        }
//    }

    // UI //

    /** Updates the ui with user account details and profile pic */
    private void updateUI() {
        AccountModel userAccount;
        if (getAccountFromIntent() != null) {
            userAccount = getAccountFromIntent();
            // set details and profile pic
            setUserAccountData(userAccount);
        }
    }

    /** Set user account details */
    private void setUserAccountData(AccountModel account) {
        Utils.setTextView(binding.toolBar.tvTitle, account.getUsername());
        Utils.setTextView(binding.tvRole, account.getRole());
        Utils.setTextView(binding.tvCountry, account.getCountry());
        Utils.setTextView(binding.tvOrganisation, account.getOrganisation());
        Utils.setTextView(binding.tvBio, account.getBio());
        Utils.setImageView(binding.sivProfilePic, account.getUrlProfilePic(), this);
    }

    /** Receive AccountModel object of selected account from intent */
    private AccountModel getAccountFromIntent() {
        if (getIntent().getParcelableExtra("account")!=null) {
            return account = getIntent().getParcelableExtra("account");
        } else {
            return null;
        }
    }

}