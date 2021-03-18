package com.example.chat.Models;

public class Users {

    String profilepic,username,mailid,password,userId,lastmessage,status;

    public String getUserId() {
        return userId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Users(String profilepic, String username, String mailid, String password, String userId, String lastmessage, String status) {
        this.profilepic = profilepic;
        this.username = username;
        this.mailid = mailid;
        this.password = password;
        this.userId = userId;
        this.lastmessage = lastmessage;
        this.status = status;
    }

    //For firebase we have to pass emplty constructor
    public Users(){}

    //for signup a special constructor

    public Users(String username, String mailid, String password) {

        this.username = username;
        this.mailid = mailid;
        this.password = password;

    }


    public String getProfilepic() {
        return profilepic;
    }

    public void setProfilepic(String profilepic) {
        this.profilepic = profilepic;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMailid() {
        return mailid;
    }

    public void setMailid(String mailid) {
        this.mailid = mailid;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }



    public String getLastmessage() {
        return lastmessage;
    }

    public void setLastmessage(String lastmessage) {
        this.lastmessage = lastmessage;
    }




}
