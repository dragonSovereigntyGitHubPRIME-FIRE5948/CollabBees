package com.example.collabbees.Chat;


import com.example.collabbees.Accounts.AccountModel;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.ServerTimestamp;

import java.sql.Time;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class MessageModel {

    // PROPERTIES //
    private String message;

    private Timestamp messageDateTime;
    private String urlImage;
    private AccountModel messageSender;
    // account uid, boolean isRead
    private Map<String, Boolean> messageRead = new HashMap<>();

    // CONSTRUCTORS //

    // for firebase
    public MessageModel() {}

    // create text message
    public MessageModel(String message, AccountModel messageSender, Map<String, Boolean> messageRead) {
        this.message = message;
        this.messageSender = messageSender;
        this.messageRead = messageRead;
        this.messageDateTime = Timestamp.now();
    }

    // create image message
    public MessageModel(String message, String urlImage, AccountModel messageSender, HashMap<String, Boolean> messageRead) {
        this.message = message;
        this.urlImage = urlImage;
        this.messageSender = messageSender;
        this.messageRead = messageRead;
        this.messageDateTime = Timestamp.now();
    }

    // GETTERS, SETTERS //

    public String getMessage() {return message;}
    public void setMessage(String message) {this.message = message;}
    public Timestamp getMessageDateTime() {return messageDateTime;}
    public String getUrlImage() {return urlImage;}
    public AccountModel getMessageSender() {return messageSender;}
    public Map<String, Boolean> getMessageRead() {return messageRead;}
}
