package com.example.firabasefirstexperience.model;

public class User {

    private String id;
    private String userName;
    private String password;
    private String eMail;
    private String imageURL;
    private String status;

    public User(String id, String userName, String password, String eMail, String imageURL, String status) {

        this.id = id;
        this.userName = userName;
        this.password = password;
        this.eMail = eMail;
        this.imageURL = imageURL;
        this.status = status;
    }

    public User() {

    }


    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String geteMail() {
        return eMail;
    }

    public void seteMail(String eMail) {
        this.eMail = eMail;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
