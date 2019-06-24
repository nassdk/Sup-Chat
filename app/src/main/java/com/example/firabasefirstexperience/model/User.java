package com.example.firabasefirstexperience.model;

public class User {

    private String id;
    private String userName;
    private String password;
    private String eMail;
    private String imageUri;

    public User(String id, String userName, String password, String eMail, String imageUri) {

        this.id = id;
        this.userName = userName;
        this.password = password;
        this.eMail = eMail;
        this.imageUri = imageUri;
    }

    public User() {

    }

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
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

}
