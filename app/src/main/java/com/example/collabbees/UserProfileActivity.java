package com.example.collabbees;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.collabbees.Accounts.AccountsManager;
import com.example.collabbees.Accounts.AccountModel;
import com.example.collabbees.databinding.ActivityUserProfileBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class UserProfileActivity extends AppCompatActivity {

    ///////////////
    // VARIABLES //
    //////////////

    // VIEW BINDINGS //
    ActivityUserProfileBinding binding;
    // MANAGERS //
    AccountsManager accountsManager = AccountsManager.getInstance();
    // VARIABLES //
//    Intent signInIntent;
//    ActivityResultLauncher signInLauncher;

    private AccountModel user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        binding = ActivityUserProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // initialisations
//        signInIntent = FirebaseUtils.createSignInIntent();
//        signInLauncher = FirebaseUtils.createSignInLauncher(this);

        //
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

    // LISTENERS //
    private void setupListeners(){
        // Edit profile button
        binding.btnEditProfile.setOnClickListener(view -> {
            startActivity(new Intent(UserProfileActivity.this, EditProfileActivity.class));
        });

        // Sign out account button
        binding.btnSignOutAccount.setOnClickListener(view -> {
            new AlertDialog.Builder(this)
                    .setMessage(R.string.sign_out_account_confirmation_message)
                    .setPositiveButton(R.string.Sign_Out, (dialogInterface, i) ->
                            this.signOutAccount()
                    )
                    .setNegativeButton(R.string.Cancel, null)
                    .show();
        });

        // Delete account button
        binding.btnDeleteAccount.setOnClickListener(view -> {
            new AlertDialog.Builder(this)
                    .setMessage(R.string.delete_account_confirmation_message)
                    .setPositiveButton(R.string.Delete, (dialogInterface, i) ->
                            this.deleteAccount()
                    )
                    .setNegativeButton(R.string.Cancel, null)
                    .show();
        });
    }

    // ACCOUNT //
    /** Receive AccountModel object of current from intent */
    private AccountModel getUserAccountFromIntent() {
        if (getIntent().getParcelableExtra("user")!=null) {
            return user = getIntent().getParcelableExtra("user");
        } else {
            return null;
        }
    }

    // UI //

    /** Updates the ui with user account details and profile pic */
    private void updateUI() {
        AccountModel userAccount;
        if (getUserAccountFromIntent() != null) {
            userAccount = getUserAccountFromIntent();
            // set details and profile pic
            setUserAccountData(userAccount);
        }
    }

    /** Set user account details */
    private void setUserAccountData(AccountModel userAccount) {
        Utils.setTextView(binding.tvUsername, userAccount.getUsername());
        Utils.setTextView(binding.tvRole, userAccount.getRole());
        Utils.setTextView(binding.tvCountry, userAccount.getCountry());
        Utils.setTextView(binding.tvOrganisation, userAccount.getOrganisation());
        Utils.setTextView(binding.tvBio, userAccount.getBio());

        Utils.setImageView(binding.sivProfilePic, userAccount.getUrlProfilePic(), this);
    }

    // ACCOUNT MANAGEMENT //
    /** Sign out account */
    private void signOutAccount() {
        accountsManager.signOutAccount(this).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), R.string.Account_Signed_Out_Successfully, Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(UserProfileActivity.this, MainActivity.class));
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), R.string.Account_Signed_Out_Unsuccessfully, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /** Delete account */
    private void deleteAccount() {
        accountsManager.deleteAccount(this).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), R.string.Account_Deleted_Successfully, Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(UserProfileActivity.this, MainActivity.class));
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), R.string.Account_Deleted_Unsuccessfully, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}


//
//    public void updateEmail() {
//        // [START update_email]
//        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//
//        user.updateEmail("user@example.com")
//                .addOnCompleteListener(new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Void> task) {
//                        if (task.isSuccessful()) {
//                            Log.d(TAG, "User email address updated.");
//                        }
//                    }
//                });
//        // [END update_email]
//    }
//
//    public void updatePassword() {
//        // [START update_password]
//        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//        String newPassword = "SOME-SECURE-PASSWORD";
//
//        user.updatePassword(newPassword)
//                .addOnCompleteListener(new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Void> task) {
//                        if (task.isSuccessful()) {
//                            Log.d(TAG, "User password updated.");
//                        }
//                    }
//                });
//        // [END update_password]
//    }
//

//    public void sendPasswordReset() {
//        // [START send_password_reset]
//        FirebaseAuth auth = FirebaseAuth.getInstance();
//        String emailAddress = "user@example.com";
//
//        auth.sendPasswordResetEmail(emailAddress)
//                .addOnCompleteListener(new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Void> task) {
//                        if (task.isSuccessful()) {
//                            Log.d(TAG, "Email sent.");
//                        }
//                    }
//                });
//        // [END send_password_reset]
//    }
//

//    public void testPhoneVerify() {
//        // [START auth_test_phone_verify]
//        String phoneNum = "+16505554567";
//        String testVerificationCode = "123456";
//
//        // Whenever verification is triggered with the whitelisted number,
//        // provided it is not set for auto-retrieval, onCodeSent will be triggered.
//        FirebaseAuth auth = FirebaseAuth.getInstance();
//        PhoneAuthOptions options = PhoneAuthOptions.newBuilder(auth)
//                .setPhoneNumber(phoneNum)
//                .setTimeout(60L, TimeUnit.SECONDS)
//                .setActivity(this)
//                .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
//                    @Override
//                    public void onCodeSent(@NonNull String verificationId,
//                                           @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
//                        // Save the verification id somewhere
//                        // ...
//
//                        // The corresponding whitelisted code above should be used to complete sign-in.
//                        MainActivity.this.enableUserManuallyInputCode();
//                    }
//
//                    @Override
//                    public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
//                        // Sign in with the credential
//                        // ...
//                    }
//
//                    @Override
//                    public void onVerificationFailed(@NonNull FirebaseException e) {
//                        // ...
//                    }
//                })
//                .build();
//        PhoneAuthProvider.verifyPhoneNumber(options);
//        // [END auth_test_phone_verify]
//    }
//
//
//    public void testPhoneAutoRetrieve() {
//        // [START auth_test_phone_auto]
//        // The test phone number and code should be whitelisted in the console.
//        String phoneNumber = "+16505554567";
//        String smsCode = "123456";
//
//        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
//        FirebaseAuthSettings firebaseAuthSettings = firebaseAuth.getFirebaseAuthSettings();
//
//        // Configure faking the auto-retrieval with the whitelisted numbers.
//        firebaseAuthSettings.setAutoRetrievedSmsCodeForPhoneNumber(phoneNumber, smsCode);
//
//        PhoneAuthOptions options = PhoneAuthOptions.newBuilder(firebaseAuth)
//                .setPhoneNumber(phoneNumber)
//                .setTimeout(60L, TimeUnit.SECONDS)
//                .setActivity(this)
//                .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
//                    @Override
//                    public void onVerificationCompleted(@NonNull PhoneAuthCredential credential) {
//                        // Instant verification is applied and a credential is directly returned.
//                        // ...
//                    }
//
//                    // [START_EXCLUDE]
//                    @Override
//                    public void onVerificationFailed(@NonNull FirebaseException e) {
//
//                    }
//                    // [END_EXCLUDE]
//                })
//                .build();
//        PhoneAuthProvider.verifyPhoneNumber(options);
//        // [END auth_test_phone_auto]
//    }