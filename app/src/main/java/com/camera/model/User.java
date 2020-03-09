package com.camera.model;
// 单例
public class User {
    private static User user = null;
    private String username;
    private String password;

    private User(String username,  String password) {
        this.username = username;
        this.password = password;
    }

    public static User getInstance(String username,  String password)  {
        if(user == null) {
            user = new User(username, password);
        }
        return user;
    }


    public String getUsername() {
        return this.username;
    }
    
    public String getPassword() {
        return this.password;
    }
}
