package com.example.philip01.models;

public class Users {
    private Long userID;
    private String userName;
    private String passWord;

    public Users() {}

    public Users(String userName, String passWord) {
        this.userName = userName;
        this.passWord = passWord;
    }

    public Users(Long userID, String userName, String passWord) {
        this.userID = userID;
        this.userName = userName;
        this.passWord = passWord;
    }

    public Long getUserID() {
        return userID;
    }

    public void setUserID(Long userID) {
        this.userID = userID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    @Override
    public String toString() {
        return "Users{" +
                "userID=" + userID +
                ", userName='" + userName + '\'' +
                ", passWord='" + passWord + '\'' +
                '}';
    }
}
