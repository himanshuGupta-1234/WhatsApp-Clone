package com.example.chat.Models;

public class MessageModel {

    String messageId;
    String message;
    Long timestamp;
    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }




    public MessageModel(String messageId, String message) {
        this.messageId = messageId;
        this.message = message;
    }
    public MessageModel(String messageId, String message, Long timestamp) {
        this.messageId = messageId;
        this.message = message;
        this.timestamp = timestamp;
    }

    public MessageModel(){
    };


}
