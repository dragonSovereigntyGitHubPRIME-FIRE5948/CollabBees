package com.example.collabbees.Chat;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.collabbees.Accounts.AccountsManager;
import com.example.collabbees.R;
import com.example.collabbees.Utils;
import com.example.collabbees.databinding.ViewholerChatBinding;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import java.util.Map;

public class ChatAdapter extends FirestoreRecyclerAdapter<ChatRoomModel, ChatAdapter.ChatViewHolder> {

    ////////////////////////
    // 1. Message Adapter //
    ///////////////////////

    ///////////////
    // VARIABLES //
    //////////////

    private Context context;
    private AccountsManager accountsManager;

    /**
     * Constructor
     */
    public ChatAdapter(@NonNull FirestoreRecyclerOptions<ChatRoomModel> options, Context context, AccountsManager accountsManager) {
        super(options);
        this.context = context;
        this.accountsManager=accountsManager;
    }

    /**
     * onBindViewHolder
     */
    @Override
    protected void onBindViewHolder(@NonNull ChatViewHolder holder, int position, ChatRoomModel model) {
        // bind the MessageModel object to the MessageViewHolder
        holder.updateUI(model);
        holder.setUpItemListener(holder, context, model);
    }

    /**
     * onCreateViewHolder
     */
    @Override
    public ChatViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Create a new instance of the ViewHolder
        // layout called R.layout.message for each item
        View inflatedView = LayoutInflater.from(parent.getContext()).inflate
                (R.layout.viewholer_chat, parent, false);

        return new ChatViewHolder(inflatedView, context);
    }

    ////////////////////////////////////////
    // 2. ChatViewHolder (Inner Class) //
    ///////////////////////////////////////

    public static class ChatViewHolder extends RecyclerView.ViewHolder {

        // VARIABLES //
        private final ViewholerChatBinding binding;
        private Context context;

        // CONSTRUCTOR //
        public ChatViewHolder(@NonNull View chatView, Context context) {
            super(chatView);
            this.context = context;
            // binds the ViewHolder object field to root view of argument (messageView) using bind func created below
            binding = ViewholerChatBinding.bind(chatView);
        }

        // FUNCTIONS

        /** */
        private void updateUI(ChatRoomModel model) {
            // views
            setChatDetails(model);
        }

        private void setChatDetails(ChatRoomModel model) {
            //get room id, get last message
            Utils.setTextView(binding.tvChatRoomName, model.getChatRoomName());

            Utils.setTextView(binding.tvDateTime, Utils.formatDate(model.getDateTimeCreated().toDate()));
            Utils.setImageView(binding.sivChatRoomPic, model.getChatRoomUrlImage(), context);

            Map<String, String> lastMessage = model.getLastMessage();
            binding.tvMessage.setText(lastMessage.get("lastMessage"));
        }

        /** Set up individual item listeners */
        private void setUpItemListener(ChatAdapter.ChatViewHolder holder, Context context, ChatRoomModel model) {
            holder.itemView.setOnClickListener(v -> {
                Intent intent = new Intent(context, ChatActivity.class);
                intent.putExtra("chat_room", model);
                context.startActivity(intent);
                //TODO CHECK
                ((Activity) context).finish();
            });
        }
    }
}