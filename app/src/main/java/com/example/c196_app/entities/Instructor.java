package com.example.c196_app.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "instructors")
public class Instructor {
    @PrimaryKey(autoGenerate = true)
    private int ID;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;

    public Instructor(int ID, String firstName, String lastName, String phoneNumber, String email) {
        this.ID = ID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    public Instructor(){}

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
