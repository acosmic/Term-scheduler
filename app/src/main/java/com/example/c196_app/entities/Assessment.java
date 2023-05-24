package com.example.c196_app.entities;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "assessments")
public class Assessment {
    @PrimaryKey(autoGenerate = true)
    private int ID;
    private String title;
    private String type;
    private Date date;
    private int courseID;

    public Assessment(int ID, String title, String type, Date date, int  courseID) {
        this.ID = ID;
        this.title = title;
        this.type = type;
        this.date = date;
        this.courseID = courseID;
    }

    public Assessment() {}

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setCourseID(int courseID) {
        this.courseID = courseID;
    }

    public int getCourseID(){
        return courseID;
    }
}
