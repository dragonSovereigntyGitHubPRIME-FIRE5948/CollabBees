package com.example.collabbees.Network;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import com.example.collabbees.Accounts.AccountModel;
import com.example.collabbees.Accounts.AccountsManager;
import com.example.collabbees.databinding.ActivityNetworkSocialBinding;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class NetworkSocialActivity extends AppCompatActivity {

    ///////////////
    // VARIABLES //
    //////////////

    // VARIABLES //
    private RecyclerView rvAccounts;
    private AccountsAdapter accountsAdapter;
    private GridLayoutManager gridLayoutManager;
    // MANAGERS //
    private AccountsManager accountsManager = AccountsManager.getInstance();
    // VIEW BINDINGS //
    private ActivityNetworkSocialBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNetworkSocialBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // get RecyclerView binding
        rvAccounts = binding.rvAccounts;
        //rvAccounts.setHasFixedSize(true);
        // create, config and set layout manager
        gridLayoutManager = new GridLayoutManager(this, 2);
        rvAccounts.setLayoutManager(gridLayoutManager);
        // set up FirebaseRecyclerOptions
        FirestoreRecyclerOptions<AccountModel> options =
         new FirestoreRecyclerOptions.Builder<AccountModel>()
                 .setQuery(accountsManager.queryGetAllAccounts() , AccountModel.class)
                 .setLifecycleOwner(this) // start listening when activity starts
                 .build();
        // create, config and set adapter
        accountsAdapter = new AccountsAdapter(options, this, accountsManager);
        rvAccounts.setAdapter(accountsAdapter);

        setupListeners();
    }
    private void setupListeners(){
        // Send button
//        binding.btn.setOnClickListener(view -> { sendMessage(); });
    }

    @Override
    protected void onStart() {
        super.onStart();
        accountsAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        accountsAdapter.stopListening();
    }




    // Configure recycler adapter options:
//  * Chat.class instructs the adapter to convert each DocumentSnapshot to a Chat object

//        accountsManager.getAllAccountsDocSnapshot().addOnCompleteListener(new OnCompleteListener<List<DocumentSnapshot>>() {
//            @Override
//            public void onComplete(@NonNull Task<List<DocumentSnapshot>> accountsList) {
//                if (accountsList.isSuccessful()) {
//                    for (int i = 1; i < accountsList.getResult().size(); i++) {
//
//                    }
//                }
//            }
//        });

//        accountsManager.getAllAccountsQuerySnapshot().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                if (task.isSuccessful()) {
//                    FirebaseRecyclerOptions<AccountModel> options =
//                            new FirebaseRecyclerOptions.Builder<AccountModel>()
//                                    .setQuery(task.getResult(), AccountModel.class)
//                                    .build();
//                }
//            }
//        });
}