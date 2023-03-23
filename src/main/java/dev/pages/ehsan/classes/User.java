package dev.pages.ehsan.classes;

import java.io.Serializable;

public class User implements Serializable {
    private static int userCount;
    private int id;
    private String name;
    private String email;
    private String phone;
    private String passwords;
    private String type;

    public User(String name, String email, String phone, String passwords) {
        // For Registration  & Receive from Server
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.passwords = passwords;
        this.type = "User";
        this.id = userCount;
        userCount++;
    }

    public User(String email, String passwords) {
        // For login (Send to server)
        this.email = email;
        this.passwords = passwords;

        this.name = null;
        this.phone = null;
        this.type = null;
    }

    // getter-setter
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPasswords() {
        return passwords;
    }

    public void setPasswords(String passwords) {
        this.passwords = passwords;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
