package com.example.firabasefirstexperience.Model;

import java.util.Objects;

public class User {

    private String id;
    private String userName;
    private String password;
    private String eMail;

    public User(String id, String userName, String password, String eMail) {

        this.id = id;
        this.userName = userName;
        this.password = password;
        this.eMail = eMail;
    }

    public User() {

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
