package com.example.collabbees;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.Toast;

import com.example.collabbees.Accounts.AccountsManager;
import com.example.collabbees.databinding.ActivityEditProfileBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class EditProfileActivity extends AppCompatActivity {

    /**
    ///////////////
    // VARIABLES //
    ///////////////
     */

    // VARIABLES //
    String newUsername;
    String newEmail;
    String newPassword;
    String newRole;
    String newCountry;
    String newOrganisation;
    String newBio;

    // VIEW BINDINGS //
    private ActivityEditProfileBinding binding;
    // MANAGERS //
    AccountsManager accountsManager = AccountsManager.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setupListeners();
    }

    /**
    ///////////////
    // FUNCTIONS //
    ///////////////
     */

    /** Set up listeners */
    private void setupListeners(){

//        binding.ibtnConfirm.setOnClickListener(view -> {
//
//            //check if et was changed, use TextWatcher
////            binding.etUsername.hasFocus();
//
//            // check TextWatcher and get text if there is change
//
////            newUsername = binding.etUsername.getText().toString();
//            String newEmail;
//            String newPassword;
//            String newRole;
//            String newCountry;
//            String newOrganisation;
//            String newBio;
//
//            // update details in Auth
//
//            // update details in Firestore
////            updateProfileInFirestore("username", newUsername);
////            updateProfileInFirestore("username", newUsername);
////            updateProfileInFirestore("username", newUsername);
////            updateProfileInFirestore("username", newUsername);
////            updateProfileInFirestore("username", newUsername);
////            updateProfileInFirestore("username", newUsername);
//        });

//        binding.ibtnCancel.setOnClickListener(view -> {
//            finish();
//        });
    }

    /** Update an account detail */
    private void updateProfileInFirestore(String field, String newValue, Object... moreFieldsAndValues) {
        accountsManager.updateProfileInFirestore(field, newValue, moreFieldsAndValues).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    startActivity(new Intent(EditProfileActivity.this, UserProfileActivity.class));
                    Toast.makeText(getApplicationContext(), field+R.string.Account_Detail_Updated_Successfully, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    //TODO
    /** Update user account profile detail(s) (username &/or profile pic) in Auth. */
//    public void updateProfileInAuth(String newEmail) {

//    UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
//            .setDisplayName("Jane Q. User")
//            .setPhotoUri(Uri.parse("https://example.com/jane-q-user/profile.jpg"))
//            .build();

//        accountsManager.updateProfileInAuth(profileUpdates).addOnCompleteListener(new OnCompleteListener<Void>() {
//            @Override
//            public void onComplete(@NonNull Task<Void> task) {
//                if (task.isSuccessful()) {
//                }
//            }
//        });
//    }

    /** Update user email in Auth */
    private void updateEmailInAuth(String newEmail) {
        accountsManager.updateEmailInAuth(newEmail).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                }
            }
        });
    }

    //TODO
    /** Update user password in Auth */
    private void updatePasswordInAuth(String newEmail) {
        accountsManager.updatePasswordInAuth(newEmail).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                }
            }
        });
    }


    // TODO

    /** Create TextWatcher */
    private String createTextWatcher(EditText et, String value) {

        final String[] newText = new String[1];

        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                newText[0] = editable.toString();
            }
        };
        et.addTextChangedListener(textWatcher);
        value = newText[0];
        return value;
    }

}