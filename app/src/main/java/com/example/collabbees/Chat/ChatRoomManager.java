package com.example.collabbees.Chat;

import androidx.annotation.Nullable;

import com.example.collabbees.Accounts.AccountModel;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;

public class ChatRoomManager {

    private static volatile ChatRoomManager instance;
    private ChatRoomRepository chatRoomRepo;
    private ChatRoomManager() {
        chatRoomRepo = ChatRoomRepository.getInstance();
    }

    public static ChatRoomManager getInstance() {
        ChatRoomManager result = instance;
        if (result != null) {return result;}
        synchronized(ChatRoomManager.class) {
            if (instance == null) {instance = new ChatRoomManager();}
            return instance;}
    }

    public ChatRoomModel createChatRoom(AccountModel memberAccount) {
        return chatRoomRepo.createChatRoom(memberAccount);
    }

    public void createMessage(String message, @Nullable String urlImage, ArrayList<String> members, String chatRoomId){
        chatRoomRepo.createMessage(message, urlImage, members, chatRoomId);
    }

    /** Retrieves chat rooms of current user. */
    // func runs only if user is logged in (getCurrentUser)
    // only retrieves chat room docs that have user account as a member
    public Query queryAllChatRooms() {
        return chatRoomRepo.queryAllChatRooms();
    }

    /** Retrieve a chat room with the chat room uid.
     Used for 1to1 chat rooms.
     Chat room uid is a composite uid of both accounts.
     * */
    public Task<ChatRoomModel> getChatRoom(String chatRoomUid) {
        return chatRoomRepo.getChatRoom(chatRoomUid).continueWith(task ->
                task.getResult().toObject(ChatRoomModel.class));
    }

    /** Retrieves all messages (limit 50) of a chat room. */
    public Query queryAllMessages(String chatRoomUid) {return chatRoomRepo.queryAllMessages(chatRoomUid);}

}
