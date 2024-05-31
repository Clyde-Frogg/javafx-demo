package com.example.javafxdemo.user;

public class UserProfile {
    private String name;
    private String imagePath;
    private String role;

    public UserProfile(String name, String imagePath, String role) {
        this.name = name;
        this.imagePath = imagePath;
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
