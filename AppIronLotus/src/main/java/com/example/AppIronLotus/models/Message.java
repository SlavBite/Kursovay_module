package com.example.AppIronLotus.models;

public class Message extends BaseModel {
    private int senderId;
    private int receiverId;
    private String message;
    private String timestamp;

    // Конструктор
    public Message(int id, int senderId, int receiverId, String message, String timestamp) {
        super();
        this.id = id;
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.message = message;
        this.timestamp = timestamp;
    }

    // Геттеры и сеттеры
    public int getSenderId() {
        return senderId;
    }

    public void setSenderId(int senderId) {
        this.senderId = senderId;
    }

    public int getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(int receiverId) {
        this.receiverId = receiverId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "Message{id=" + id + ", senderId=" + senderId + ", receiverId=" + receiverId +
                ", message='" + message + "', timestamp='" + timestamp + "'}";
    }
}
