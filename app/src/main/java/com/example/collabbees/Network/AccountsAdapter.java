package com.example.collabbees.Network;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.collabbees.Accounts.AccountModel;
import com.example.collabbees.Accounts.AccountsManager;
import com.example.collabbees.ProfileActivity;
import com.example.collabbees.R;
import com.example.collabbees.Utils;
import com.example.collabbees.databinding.ViewholderAccountsBinding;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class AccountsAdapter extends FirestoreRecyclerAdapter<AccountModel, AccountsAdapter.AccountsViewHolder> {

    /////////////////////////
    // 1. Accounts Adapter //
    ////////////////////////

    ///////////////
    // VARIABLES //
    //////////////

    // VARIABLES //
    Activity activity;
    AccountsManager accountsManager;

    /** Constructor */
        public AccountsAdapter(@NonNull FirestoreRecyclerOptions<AccountModel> options, Activity activity, AccountsManager accountsManager) {
        super(options);
        this.activity = activity;
        this.accountsManager = accountsManager;
    }

    /** onBindViewHolder */
    @Override
    protected void onBindViewHolder(@NonNull AccountsViewHolder holder, int position, AccountModel model) {
        // bind the AccountModel object to the AccountViewHolder
        holder.updateUI(model);
        holder.setUpItemListener(holder, activity, model);
        holder.setUpListeners(accountsManager, model);
    }

    /** onCreateViewHolder */
    @Override
    public AccountsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Create a new instance of the ViewHolder, in this case I am using a custom
        // layout called R.layout.message for each item
        View inflatedView = LayoutInflater.from(parent.getContext()).inflate
                (R.layout.viewholder_accounts, parent, false);

        return new AccountsViewHolder(inflatedView);
    }

    ////////////////////////////////////////
    // 2. AccountsViewHolder (Inner Class) //
    ///////////////////////////////////////

    public class AccountsViewHolder extends RecyclerView.ViewHolder {

        // VARIABLES //
        private final ViewholderAccountsBinding binding;

        // CONSTRUCTOR //
        public AccountsViewHolder(@NonNull View accountView) {
            super(accountView);
            // binds the ViewHolder object field to root view of argument (messageView) using bind func created below
            binding = ViewholderAccountsBinding.bind(accountView);
        }

        // FUNCTIONS //

        /**   */
        private void updateUI(AccountModel model) {
            // set account details
            setUserAccountDetails(model);
            // set account profile pic
            Utils.setImageView(binding.sivProfilePic, model.getUrlProfilePic(), activity);
        }

        /** Set user account details */
        private void setUserAccountDetails(AccountModel userAccount) {
            Utils.setTextView(binding.tvUsername, userAccount.getUsername());
            Utils.setTextView(binding.tvRole, userAccount.getRole());
            Utils.setTextView(binding.tvCountry, userAccount.getCountry());
            Utils.setTextView(binding.tvOrganisation, userAccount.getOrganisation());
            Utils.setTextView(binding.tvBio, userAccount.getBio());
        }

        /** Set up individual item listeners */
        private void setUpItemListener(AccountsViewHolder holder, Activity activity, AccountModel model) {
            holder.itemView.setOnClickListener(v -> {
                Intent intent = new Intent(activity.getApplicationContext(), ProfileActivity.class);
                intent.putExtra("account", model);
                activity.startActivity(intent);
                activity.finish();
            });
        }

        /** Set up view listeners */
        private void setUpListeners(AccountsManager manager, AccountModel model) {
//            binding.btnConnect.setOnClickListener(view -> {
//                manager.addFollowing(model)
//                        .addOnSuccessListener(task -> {
//                            Toast.makeText(activity.getApplicationContext(), R.string.Successfully_Followed, Toast.LENGTH_SHORT).show();
//                            //TODO REMOVE FROM RECYCLER
//                        })
//                        .addOnFailureListener(task -> {
//                            Toast.makeText(activity.getApplicationContext(), R.string.Unsuccessfully_Followed, Toast.LENGTH_SHORT).show();
//                        });
//            });
        }

    }

}
