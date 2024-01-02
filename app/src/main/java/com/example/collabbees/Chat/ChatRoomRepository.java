package com.example.collabbees.Chat;

import androidx.annotation.Nullable;

import com.example.collabbees.Accounts.AccountModel;
import com.example.collabbees.Accounts.AccountsManager;
import com.example.collabbees.Constants;
import com.example.collabbees.Utils;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.HashMap;

public class ChatRoomRepository {

    // VARIABLES //
    private static volatile ChatRoomRepository instance;
    private AccountsManager accountsManager;

    private static final String COLLECTION_CHATS_ROOMS = Constants.COLLECTION_CHAT_ROOMS;
    private static final String COLLECTION_MESSAGES = Constants.COLLECTION_MESSAGES;

    private static final Integer ACCESS_ADMIN = Constants.ACCESS_ADMIN;
    private static final Integer ACCESS_MANAGER = Constants.ACCESS_MANAGER;
    private static final Integer ACCESS_REGULAR = Constants.ACCESS_REGULAR;

    // FUNCTIONS //
    public static ChatRoomRepository getInstance() {
        ChatRoomRepository result = instance;
        if (result != null) {return result;}
        synchronized(ChatRoomRepository.class) {
            if (instance == null) {instance = new ChatRoomRepository();}
            return instance;}
    }


    private ChatRoomRepository() {this.accountsManager = accountsManager.getInstance();}

    // COLLECTION REFERENCES //
    /** Get the Collection reference to 'chat_rooms' */
    public CollectionReference getCollectionChatRooms(){
        return FirebaseFirestore.getInstance().collection(COLLECTION_CHATS_ROOMS);
    }
    /** Get the Sub-collection reference to 'messages' of 'chat_rooms' Doc */
    public CollectionReference getCollectionMessages(String chatRoomUid){
        return getCollectionChatRooms()
                .document(chatRoomUid)
                .collection(COLLECTION_MESSAGES);
    }

    /** ////////////
    // 1. CREATE //
    /////////// */

    /** Create 1to1 chat room doc, set a composite uid for the doc using both account's uid */
    public ChatRoomModel createChatRoom(AccountModel memberAccount) {
        FirebaseUser currentUser = accountsManager.getCurrentUser();

        if (currentUser!= null) {
            HashMap<String, Integer> members = new HashMap<>();
            String chatRoomUid;
            String username;
            @Nullable String urlProfilePic;
//                    ArrayList<String> membersUid = new ArrayList<>();

            // add user account who started chat room with default access rights 'admin'
            members.put(currentUser.getUid(), ACCESS_ADMIN);
            // add account default access rights 'regular'
            members.put(memberAccount.getUid(), ACCESS_REGULAR);

            //create uid with both account uid, by using lexicographically method
            String[] accountUidList = {currentUser.getUid(), memberAccount.getUid()};
            accountUidList = Utils.sortLexicographically(accountUidList);
            chatRoomUid = accountUidList[0] +"_"+accountUidList[1];

            // reference or create chat room doc
            DocumentReference docRef = this.getCollectionChatRooms().document(chatRoomUid);
            // get username of account
            username = memberAccount.getUsername();
            // get profile pic of account
            urlProfilePic = memberAccount.getUrlProfilePic();
            // create ChatRooms object and set it into the doc
            ChatRoomModel chatRoom = new ChatRoomModel(chatRoomUid, members, username, urlProfilePic);
            // set the doc
            docRef.set(chatRoom);
            return chatRoom;
            // add chat room uid to 'chatRooms' field of user account and accounts of members
//                    membersUid.add(memberAccount.getUid());
//                    accountsManager.addChatRoomUid(chatRoomUid, membersUid);
        }
        else {return null;}
    }

    /** Create chat room doc when chat is initiated by user account. */
    public void createGroupChatRoom(ArrayList<AccountModel> membersAccount,
                                    String chatRoomName, String chatRoomUrlImage, String chatRoomDesc) {

        // add user account
        accountsManager.getCurrentAccount()
        // onSuccess get user account, add user account with 'admin' access rights
                .addOnSuccessListener(user -> {
                    HashMap<String, Integer> members = new HashMap<>();
                    String chatRoomUid;
//                    ArrayList<String> membersUid = new ArrayList<>();

                    // add user account who started chat room with default access rights 'admin'
                    members.put(user.getUid(), ACCESS_ADMIN);

                    // add accounts of members with default access rights 'regular'
                    for (int i = 0; i < membersAccount.size(); i++) {
                        members.put(membersAccount.get(i).getUid(), ACCESS_REGULAR);
//                        membersUid.add(membersAccount.get(i).getUid());
                    }

                    DocumentReference docRef = this.getCollectionChatRooms().document();
                    // get the uid
                    chatRoomUid = docRef.getId();
                    // create ChatRooms object and set it into the doc
                    ChatRoomModel chatRoom = new ChatRoomModel(chatRoomUid, members, chatRoomName, chatRoomUrlImage, chatRoomDesc);
                    // set the doc
                    docRef.set(chatRoom);
                    // add chat room uid to 'chatRooms' field of user account
//                    accountsManager.addChatRoomUid(chatRoomUid, membersUid);
        })
                //TODO
                .addOnFailureListener(user -> {

        });
    }

    /** Create (text or image) message doc when message is sent by user account. */
    public void createMessage(String message, @Nullable String urlImage, ArrayList<String> members, String chatRoomId){
        accountsManager.getCurrentAccount()
                .addOnSuccessListener(sender -> {
                    // Create the Message object

                    MessageModel messageModel;
                    HashMap<String, Boolean> messageRead = new HashMap<>();

                    for (int i=0;i<members.size();i++){
                        messageRead.put(members.get(i), false);
                    }

                    // create text message if no image
                    if (urlImage != null) {
                        // create message
                        messageModel = new MessageModel(message, urlImage, sender, messageRead);
                    }

                    // create image message if there is image
                    else {
                        // create message
                        messageModel = new MessageModel(message, sender, messageRead);
                        //TODO update last message
                    }

                    // add message model doc to 'messages' sub-collection in chat room doc
                    this.getCollectionMessages(chatRoomId).add(messageModel);
                    updateLastMessage(sender, chatRoomId, message, messageModel.getMessageDateTime()+"");
                })
                .addOnFailureListener(sender -> {
                    //TODO
                });
    }

    /** ///////////////
     // 2. RETRIEVE //
     ///////////// */

    /** Retrieves chat rooms of current user. */
    // func runs only if user is logged in (getCurrentUser)
    // only retrieves chat room docs that have user account as a member
    public Query queryAllChatRooms() {
        FirebaseUser user = accountsManager.getCurrentUser();
        if (user != null) {
            String id = user.getUid();
            return getCollectionChatRooms()
                    // use order by to check if 'key' exists
                    // dot notation for map/nested fields
                    .orderBy("members."+id);
        } else {return null;}
    }

    /** Retrieve a chat room with the chat room uid.
        Used for 1to1 chat rooms.
        Chat room uid is a composite uid of both accounts.
     * */
    public Task<DocumentSnapshot> getChatRoom(String chatRoomUid) {
        FirebaseUser user = accountsManager.getCurrentUser();
        if (user != null) {
            return getCollectionChatRooms().document(chatRoomUid).get();
        } else {return null;}
    }

    /** Retrieves all messages (limit 50) of a chat room. */
    public Query queryAllMessages(String chatRoomUid) {
        FirebaseUser user = accountsManager.getCurrentUser();
        if (user != null) {
            return getCollectionMessages(chatRoomUid)
                    .limit(100)
                    .orderBy("messageDateTime");
        } else {return null;}
    }

    /** Get members of a chat room. */
    public Task<DocumentSnapshot> getMembers(String chatRoomUid) {
        FirebaseUser user = accountsManager.getCurrentUser();
        if (user != null) {
            return getCollectionChatRooms()
                    .document(chatRoomUid)
                    .get();
        } else {return null;}
    }

    // 3. UPDATE //

    //TODO
    public void updateLastMessage(AccountModel sender, String chatRoomUid, String lastMessage, String messageDateTime){
        getCollectionChatRooms()
                .document(chatRoomUid)
                .update(
                    "lastMessage.lastMessage", lastMessage,
                    "lastMessage.sentBy", sender.getUsername()
                );
    }

    // 4. DESTROY //


    /** //////////////
    // 1. Chat Room //
   /////////////// */

    /** /////////////////
     // 2. Messages ////
     /////////////// */

    /** /////////////////////////
     // 3. Account Management //
     /////////////////////// */
    //TODO account management in chats
    // add, delete, update (when they update their details) member
    // set, remove admin role
    // check if member exist in group
    // add, delete, update chat room name
    // add, delete, update message
}
