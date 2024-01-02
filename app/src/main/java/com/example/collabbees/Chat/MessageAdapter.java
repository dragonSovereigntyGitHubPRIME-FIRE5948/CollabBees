package com.example.collabbees.Chat;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.collabbees.Accounts.AccountsManager;
import com.example.collabbees.Constants;
import com.example.collabbees.R;
import com.example.collabbees.Utils;
import com.example.collabbees.databinding.ViewholderMessageBinding;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MessageAdapter extends FirestoreRecyclerAdapter<MessageModel, MessageAdapter.MessageViewHolder> {

    /** Listener for adapter changes */
    public interface Listener {
        void onDataChanged();
    }

    ////////////////////////
    // 1. Message Adapter //
    ///////////////////////

    ///////////////
    // VARIABLES //
    //////////////

    private Context context;
    private AccountsManager accountsManager;
    private Listener callback;

    private static final int MESSAGE_TYPE_SENDER = Constants.MESSAGE_TYPE_SENDER;
    private static final int MESSAGE_TYPE_RECIPIENT = Constants.MESSAGE_TYPE_RECIPIENT;

    /** Constructor */
    public MessageAdapter(@NonNull FirestoreRecyclerOptions<MessageModel> options, AccountsManager accountsManager,
                          Context context, Listener callback) {
        super(options);
        this.context = context;
        this.accountsManager=accountsManager;
        this.callback = callback;
    }

    /** onBindViewHolder */
    @Override
    protected void onBindViewHolder(@NonNull MessageViewHolder holder, int position, MessageModel model) {
        // bind the MessageModel object to the MessageViewHolder
        holder.itemView.invalidate();
        holder.bind(model);
        //TODO SET UP LISTENERS
    }

    /** onCreateViewHolder */
    @Override
    public MessageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Create a new instance of the ViewHolder
        // layout called R.layout.message for each item
        View inflatedView = LayoutInflater.from(parent.getContext()).inflate
                (R.layout.viewholder_message, parent, false);

        return new MessageAdapter.MessageViewHolder(inflatedView, viewType == 1);
    }

    @Override
    public int getItemViewType(int position) {
        // Determine the type of the message by if the user is the sender or not
        String currentUserUid = accountsManager.getCurrentUser().getUid();
        boolean isSender = getItem(position).getMessageSender().getUid().equals(currentUserUid);
        return (isSender) ? MESSAGE_TYPE_SENDER : MESSAGE_TYPE_RECIPIENT;
    }

    @Override
    public void onDataChanged() {
        super.onDataChanged();
        this.callback.onDataChanged();
    }

    ////////////////////////////////////////
    // 2. MessageViewHolder (Inner Class) //
    ///////////////////////////////////////

    public static class MessageViewHolder extends RecyclerView.ViewHolder {

        // VARIABLES //
        private final ViewholderMessageBinding binding;
        private boolean isSender;

        // CONSTRUCTOR //
        public MessageViewHolder(@NonNull View messageView, boolean isSender) {
            super(messageView);
            this.isSender = isSender;
            binding = ViewholderMessageBinding.bind(messageView);
        }

        // FUNCTIONS
        /** Set message and message date and time */
        private void setMessage(MessageModel model) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");
            if(isSender){
                binding.tvMessage.setVisibility(View.VISIBLE);
                binding.tvMessageDateTime.setVisibility(View.VISIBLE);
                binding.tvMessage.setText(model.getMessage());
                binding.tvMessageDateTime.setText(Utils.formatDate(model.getMessageDateTime().toDate()));
            } else {
                binding.tvReply.setVisibility(View.VISIBLE);
                binding.tvReplyDateTime.setVisibility(View.VISIBLE);
                binding.tvReply.setText(model.getMessage());
                binding.tvReplyDateTime.setText(Utils.formatDate(model.getMessageDateTime().toDate()));
            }
        }

        /**  */
        private void bind(MessageModel model) {
            binding.tvMessage.setText(model.getMessage());
            setMessage(model);
        }


        //TODO show profile pic if it group chat and sender is not current user
        // private void updateProfileContainer(){
        // }

//        /** Set user account profile image */
//        private void setProfilePicture(String urlProfilePic) {
//            if (urlProfilePic != null) {
//                Glide.with(activity.getApplicationContext())
//                        .load(Uri.parse(urlProfilePic))
//                        .apply(RequestOptions.fitCenterTransform())
//                        .into(binding.sivProfilePic);
//            }
//        }
//
//        /** Set up individual item listeners */
//        private void setUpItemListener(AccountsAdapter.AccountsViewHolder holder, Activity activity, AccountModel model) {
//            holder.itemView.setOnClickListener(v -> {
//                Intent intent = new Intent(activity.getApplicationContext(), ProfileActivity.class);
//                intent.putExtra("account", model);
//                activity.startActivity(intent);
//                activity.finish();
//            });
//        }
    }

}
