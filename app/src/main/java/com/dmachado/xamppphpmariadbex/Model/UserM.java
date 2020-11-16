package com.dmachado.xamppphpmariadbex.Model;

public class UserM {

    private String id, fullname, email, username;

    public UserM() {
    }

    public UserM(String id, String fullname, String email, String username) {
        this.id = id;
        this.fullname = fullname;
        this.email = email;
        this.username = username;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
