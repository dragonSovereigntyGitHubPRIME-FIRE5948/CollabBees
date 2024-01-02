package com.example.collabbees;

//public class TestActivity extends AppCompatActivity {
//
//    private static final int REQ_ONE_TAP = 2;  // Can be any integer unique to the Activity.
//    private boolean showOneTapUI = true;
//    GoogleSignInClient googleSignInClient;
//    int RC_SIGN_IN = 2;
//
//    AccountsManager accountsManager = AccountsManager.getInstance();
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_test);
//
//        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//                .requestIdToken(getString(R.string.default_web_client_id))
//                .requestEmail()
//                .build();
//
//
//        googleSignInClient = GoogleSignIn.getClient(this,gso);
//
////        binding.btnGoogle.setOnClickListener(view -> {
//            Intent intent = googleSignInClient.getSignInIntent();
//            startActivityForResult(intent, RC_SIGN_IN);
////        });
//
//    }
//
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
//        String id ="";
//        if (requestCode == RC_SIGN_IN) {
//            // The Task returned from this call is always completed, no need to attach
//            // a listener.
//            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
//            try {
//                // Google Sign In was successful, authenticate with Firebase
//                GoogleSignInAccount account = task.getResult(ApiException.class);
//                AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
//                upgradeAnonymousAccount(credential);
//
//                //TODO
//                id = account.getIdToken();
//
//            } catch (ApiException e) {
//                // Google Sign In failed, update UI appropriately
//                Log.w(TAG, "Google sign in failed", e);
//            }
//
//            handleSignInResult(task);
//        }
//    }
//
//    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
//        try {
//            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
//            // Signed in successfully, show authenticated UI.
////            upgradeAnonymousAccount(credential);
//
//        } catch (ApiException e) {
//            // The ApiException status code indicates the detailed failure reason.
//            // Please refer to the GoogleSignInStatusCodes class reference for more information.
//            Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
//        }
//    }
//
//    private void upgradeAnonymousAccount(AuthCredential credential) {
//        accountsManager.getCurrentUser().linkWithCredential(credential)
//                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if (task.isSuccessful()) {
//                            FirebaseUser user = task.getResult().getUser();
////                            binding.tvUsername.setText(user.getEmail());
////                            Toast.makeText(this, "Upgrade Finished.",
////                                    Toast.LENGTH_SHORT).show();
//                        } else {
//                            Log.w(TAG, "linkWithCredential:failure", task.getException());
////                            Toast.makeText(this, "Upgrade failed.",
////                                    Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                });
//    }
//}