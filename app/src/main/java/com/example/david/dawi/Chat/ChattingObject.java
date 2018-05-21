package com.example.david.dawi.Chat;

public class ChattingObject {
    private String message;
    private Boolean currentUser;
    public ChattingObject(String message, Boolean currentUser){
        this.message = message;
        this.currentUser = currentUser;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(Boolean currentUser) {
        this.currentUser = currentUser;
    }
}
