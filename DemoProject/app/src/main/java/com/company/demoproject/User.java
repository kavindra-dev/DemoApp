package com.company.demoproject;

public class User {
    String name, email, created_at;

    public User(){

    }

    public User(String name, String email, String created_at) {
        this.name = name;
        this.email = email;
        this.created_at = created_at;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }
}
