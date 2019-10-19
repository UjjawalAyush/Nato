package com.ujjawalayush.example.nato;

public class RecyclerData4 {
    String status,message,username,date,uid;
    Long id;

    public Long getId() {
        return id;
    }

    public void setUsername(String username) {
        this.uid = username;
    }

    public String getUsername() {
        return uid;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUid() {
        return username;
    }

    public void setUid(String username) {
        this.username = username;
    }

    public String getDate() {
        return date;
    }

    public String getMessage() {
        return message;
    }

    public String getStatus() {
        return status;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
