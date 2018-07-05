package com.comp3350.rev_u_hub.data_objects;

public class UserObject implements SearchableObject {
    private String userName;
    private String passWord;

    public UserObject(){
        this.userName="";
        this.passWord="";
    }

    public UserObject(String userName, String passWord){
        this.userName=userName;
        this.passWord=passWord;
    }

    public void changePassWord(String passWord){
        this.passWord=passWord;
    }

    public String getUserName(){
        return userName;
    }
    public String getPassWord(){
        return passWord;
    }
    public boolean isEmpty() {
        return userName.equals("");
    }

    public boolean equals(UserObject other) {
        return userName.equals(other.getUserName()) && passWord.equals(other.getPassWord());
    }

    public String toString() {
        return getUserName();
    }
}