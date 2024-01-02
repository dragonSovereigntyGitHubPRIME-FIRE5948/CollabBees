package com.example.collabbees.Chat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.collabbees.Accounts.AccountsManager;
import com.example.collabbees.ToolBarListeners;
import com.example.collabbees.databinding.ActivityAllChatsBinding;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class AllChatsActivity extends AppCompatActivity {

    // VARIABLES
    private RecyclerView rvChats;
    private ChatAdapter chatAdapter;
    private LinearLayoutManager linearLayoutManager;
    // MANAGERS//
    private ChatRoomManager chatRoomManager = ChatRoomManager.getInstance();
    private AccountsManager accountsManager = AccountsManager.getInstance();
    // BINDINGS //
    private ActivityAllChatsBinding binding;

    // LIFECYCLE //

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAllChatsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Toolbar Listeners
        setUpAdapter();
        setupToolbarListeners();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (chatAdapter != null) {chatAdapter.startListening();}
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (chatAdapter == null) {chatAdapter.stopListening();}
    }

    // FUNCTIONS //

    /** Set up ChatAdapter */
    private void setUpAdapter() {
        // get RecyclerView binding
        rvChats = binding.rvChats;
        // create, config and set layout manager
        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.canScrollVertically();
        // set layout manager to recyclerview
        rvChats.setLayoutManager(linearLayoutManager);
        // set up FirebaseRecyclerOptions
        FirestoreRecyclerOptions<ChatRoomModel> options =
                new FirestoreRecyclerOptions.Builder<ChatRoomModel>()
                        .setQuery(chatRoomManager.queryAllChatRooms(), ChatRoomModel.class)
                        .setLifecycleOwner(this) // start listening when activity starts
                        .build();
        // create, config and set adapter
        chatAdapter = new ChatAdapter(options, this, accountsManager);
        // set adapter to recyclerview
        rvChats.setAdapter(chatAdapter);

        // set up listener for adapter
        // Scroll to bottom on new messages
//        messageAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
//            @Override
//            public void onItemRangeInserted(int positionStart, int itemCount) {
//                binding.rvMessages.smoothScrollToPosition(messageAdapter.getItemCount());
//            }
//        });
    }

    /** Set up tool bar listeners */
    private void setupToolbarListeners(){
        // return
        ToolBarListeners.btnReturn(binding.toolBar.btnReturn, this);
    }

}