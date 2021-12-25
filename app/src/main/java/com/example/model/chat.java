package com.example.model;

public class chat {
    String message, reciver, sender, timestamp ,chatId;
    boolean isSeen;

    public chat() {
    }

    public chat(String message, String reciver, String sender, String timestamp, boolean isSeen,String chatId) {
        this.message = message;
        this.reciver = reciver;
        this.sender = sender;
        this.timestamp = timestamp;
        this.isSeen = isSeen;
        this.chatId=chatId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getReciver() {
        return reciver;
    }

    public void setReciver(String reciver) {
        this.reciver = reciver;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public boolean isSeen() {
        return isSeen;
    }

    public void setSeen(boolean seen) {
        isSeen = seen;
    }

    public String getChatId() {
        return chatId;
    }

    public void setChatId(String chatId) {
        this.chatId = chatId;
    }
}
