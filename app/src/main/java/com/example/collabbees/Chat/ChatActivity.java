package com.example.collabbees.Chat;
//https://github.com/firebase/codelab-friendlychat-android/blob/master/build-android/app/src/main/java/com/google/firebase/codelab/friendlychat/MainActivity.kt

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.collabbees.Accounts.AccountModel;
import com.example.collabbees.Accounts.AccountsManager;
import com.example.collabbees.ToolBarListeners;
import com.example.collabbees.Utils;
import com.example.collabbees.databinding.ActivityChatBinding;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import java.util.ArrayList;
import java.util.Map;

public class ChatActivity extends AppCompatActivity implements MessageAdapter.Listener {

    // VARIABLES //
    private RecyclerView rvMessages;
    private MessageAdapter messageAdapter;
    private LinearLayoutManager linearLayoutManager;
    private ChatRoomModel chatRoom;
    private AccountModel account;
    // MANAGERS//
    private ChatRoomManager chatRoomManager = ChatRoomManager.getInstance();
    private AccountsManager accountsManager = AccountsManager.getInstance();
    // BINDINGS //
    private ActivityChatBinding binding;

    // LIFECYCLE //
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChatBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        updateUI();
        setUpAdapter();
        setupListeners();
        setupToolbarListeners();

        // TODO Disable Button
        // Disable the send button when there's no text in the input field
//        binding.editTextText3.addTextChangedListener(new MyButtonObserver(binding.sendButton));
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(messageAdapter != null){messageAdapter.startListening();}
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(messageAdapter == null) {messageAdapter.stopListening();}
    }

    // FUNCTIONS //

    /** Set up MessageAdapter */
    private void setUpAdapter() {
        // get RecyclerView binding
        rvMessages = binding.rvMessages;
        // create, config and set layout manager
        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(true);
        linearLayoutManager.canScrollVertically();
        // set layout manager to recyclerview
        rvMessages.setLayoutManager(linearLayoutManager);
        // set up FirebaseRecyclerOptions
        FirestoreRecyclerOptions<MessageModel> options =
                new FirestoreRecyclerOptions.Builder<MessageModel>()
                        .setQuery(chatRoomManager.queryAllMessages(chatRoom.getChatRoomUid()), MessageModel.class)
                        .setLifecycleOwner(this) // start listening when activity starts
                        .build();
        // create and set adapter
        messageAdapter = new MessageAdapter(options, accountsManager,this, this);

        // set up listener for adapter
        // Scroll to bottom on new messages
        messageAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                binding.rvMessages.smoothScrollToPosition(messageAdapter.getItemCount()-1 );
            }
        });

        // set adapter to recyclerview
        rvMessages.setAdapter(messageAdapter);
    }

    /** Create message */
    private void sendMessage(){
        // Check if TextInput empty
        boolean canSendMessage = !TextUtils.isEmpty(binding.etMessage.getText());
        if (canSendMessage){
            // Create a new message doc
            String message = binding.etMessage.getText().toString();
            Map<String, Integer> membersToAdd = chatRoom.getMembers();
            ArrayList<String> members = new ArrayList<>(membersToAdd.keySet());
            chatRoomManager.createMessage(message, null, members, chatRoom.getChatRoomUid());
            //todo update last message

            binding.etMessage.setText("");
        } else {
            //TODO SHOW MICROPHONE
        }
    }

    /** Updates the ui with chat room details and pic. */
    private void updateUI() {
        //TODO CHECK NULL
        account = getAccountFromIntent();
        if (getChatRoomFromIntent() != null) {
            chatRoom = getChatRoomFromIntent();
            setChatRoomDetails();
        } else {
            checkChatRoom(account);
            if (chatRoom != null) {
                setChatRoomDetails();
            } else {
                chatRoom = chatRoomManager.createChatRoom(account);
                setChatRoomDetails();
            }
        }
    }


    private void checkChatRoom(AccountModel account) {
        String userUid = accountsManager.getCurrentUser().getUid();
        String accountUid = account.getUid();

//        chatRoomManager.getChatRoom(userUid+"_"+accountUid)
//                .addOnSuccessListener(chatRoomModel -> {
//                    if (chatRoomModel == null) {
//                        chatRoomManager.getChatRoom(accountUid+"_"+userUid)
//                                .addOnSuccessListener(chatRoomModel_ -> {
//                                    chatRoom = chatRoomModel_;
//                                })
//                                .addOnFailureListener(task -> {
//                                    Log.e(TAG, "Null Response: "+ task.getClass());
//                                });
//                    } else {
//                        chatRoom = chatRoomModel;
//                    }
//                })
//                .addOnFailureListener(task -> {
//                    Log.e(TAG, "Null Response: "+ task.getClass());
//                });
    }

    /** Set chat room details. */
    private void setChatRoomDetails() {
        Utils.setTextView(binding.toolBar.tvTitle, chatRoom.getChatRoomName());
        Utils.setImageView(binding.toolBar.sivChatRoomPic, chatRoom.getChatRoomUrlImage(), this);
        //TODO SET LAST SEEN
    }

    /** Receive ChatRoomModel object of selected chat room from intent. */
    private ChatRoomModel getChatRoomFromIntent() {
        if (getIntent().getParcelableExtra("chat_room")!=null) {
            return chatRoom = getIntent().getParcelableExtra("chat_room");
        } else {return null;}
    }

    /** Receive AccountModel object of selected chat room from intent. */
    private AccountModel getAccountFromIntent() {
        if (getIntent().getParcelableExtra("account")!=null) {
            return account = getIntent().getParcelableExtra("account");
        } else {return null;}
    }

    // LISTENERS //
    /** MessageAdapter Listener (interface) */
    @Override
    public void onDataChanged() {
        // Show empty messages layout if adapter has 0 items (no messages)
        binding.layoutNoMessage.setVisibility(this.messageAdapter.getItemCount() == 0 ? View.VISIBLE : View.GONE);
    }

    /** Set up listeners */
    private void setupListeners(){
        // Send button
        binding.btnSend.setOnClickListener(view -> { sendMessage(); });
    }

    /** Set up Toolbar listeners*/
    private void setupToolbarListeners(){
        ToolBarListeners.btnReturn(binding.toolBar.btnReturn, this);
    }
}