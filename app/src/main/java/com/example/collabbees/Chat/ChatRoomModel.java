package com.example.collabbees.Chat;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.Timestamp;

import java.sql.Time;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class ChatRoomModel implements Parcelable {

    // PROPERTIES //
    private String chatRoomUid;
    private String chatRoomName;
    private String chatRoomUrlImage;
    private String chatRoomDesc;
    private Timestamp dateTimeCreated;
    // account uid, message
    private Map<String, String> lastMessage = new HashMap<>();
    //account uid, administrative access
    private Map<String, Integer> members = new HashMap<>();

    // CONSTRUCTORS //

    // for firebase
    public ChatRoomModel() {};

    // create 1to1 chat room
    public ChatRoomModel(String chatRoomUid, Map<String, Integer> members, String chatRoomName,
                         @Nullable String chatRoomUrlImage) {
        this.chatRoomUid = chatRoomUid;
        this.members = members;
        this.chatRoomUrlImage = chatRoomUrlImage;
        this.chatRoomName = chatRoomName;
        this.dateTimeCreated = Timestamp.now();
        this.lastMessage.put("lastMessage", null);
        this.lastMessage.put("sentBy", null);
        this.chatRoomDesc = null;
    }

    // create group chat room
    public ChatRoomModel(String chatRoomUid, HashMap<String, Integer> members,
                         String chatRoomName, String chatRoomUrlImage, String chatRoomDesc) {
        this.chatRoomUid = chatRoomUid;
        this.members = members;
        this.chatRoomName = chatRoomName;
        this.chatRoomUrlImage = chatRoomUrlImage;
        this.chatRoomDesc = chatRoomDesc;
        this.lastMessage.put("lastMessage", null);
        this.lastMessage.put("sentBy", null);
        this.lastMessage.put("senderPic", null);
        this.lastMessage.put("messageDateTime", null);
        this.dateTimeCreated = Timestamp.now();
    }

    // GETTERS, SETTERS //
    public String getChatRoomName() {return chatRoomName;}
    public String getChatRoomUid() {return chatRoomUid;}
    public String getChatRoomUrlImage() {return chatRoomUrlImage;}
    public String getChatRoomDesc() {return chatRoomDesc;}
    public Map<String, Integer> getMembers() {return members;}
    public Timestamp getDateTimeCreated() {return dateTimeCreated;}
    public Map<String, String> getLastMessage() {return lastMessage;}

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(chatRoomUid);
        dest.writeString(chatRoomName);
        dest.writeString(chatRoomUrlImage);
        dest.writeString(chatRoomDesc);

        dest.writeInt(members.size());
        for(Map.Entry<String,Integer> entry : members.entrySet()){
            dest.writeString(entry.getKey());
            dest.writeInt(entry.getValue());
        }

        dest.writeInt(lastMessage.size());
        for(Map.Entry<String,String> entry : lastMessage.entrySet()){
            dest.writeString(entry.getKey());
            dest.writeString(entry.getValue());
        }

    }

    protected ChatRoomModel(Parcel in) {
        chatRoomUid = in.readString();
        chatRoomName = in.readString();
        chatRoomUrlImage = in.readString();
        chatRoomDesc = in.readString();

        int membersSize = in.readInt();
        for(int i = 0; i < membersSize; i++){
            String key = in.readString();
            Integer value = in.readInt();
            members.put(key,value);
        }

        int lastMessageSize = in.readInt();
        for(int i = 0; i < lastMessageSize; i++){
            String key = in.readString();
            String value = in.readString();
            lastMessage.put(key,value);
        }

    }

    public static final Creator<ChatRoomModel> CREATOR = new Creator<ChatRoomModel>() {
        @Override
        public ChatRoomModel createFromParcel(Parcel in) {
            return new ChatRoomModel(in);
        }

        @Override
        public ChatRoomModel[] newArray(int size) {
            return new ChatRoomModel[size];
        }
    };

}
