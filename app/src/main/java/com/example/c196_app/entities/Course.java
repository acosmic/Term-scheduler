package com.example.c196_app.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "courses")
public class Course {
    @PrimaryKey(autoGenerate = true)
    private int ID;
    private String Title;
    private Date StartDate;
    private Date EndDate;
    private String Status; // (in progress, complete, dropped, planned to take)
    private int termID;
    private int instructorID;



    public Course (int ID, String Title, Date StartDate, Date EndDate, String Status, int termID, int instructorID) {
        this.ID = ID;
        this.Title = Title;
        this.StartDate = StartDate;
        this.EndDate = EndDate;
        this.Status = Status;
        this.termID = termID;
        this.instructorID = instructorID;
    }

    public Course (){}

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String Title) {
        this.Title = Title;
    }

    public Date getStartDate() {
        return StartDate;
    }

    public void setStartDate(Date StartDate) {
        this.StartDate = StartDate;
    }

    public Date getEndDate() {
        return EndDate;
    }

    public void setEndDate(Date EndDate) {
        this.EndDate = EndDate;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String Status) {
        this.Status = Status;
    }

    public int getTermID() {
        return termID;
    }

    public void setTermID(int termID) {
        this.termID = termID;
    }

    public int getInstructorID() {
        return instructorID;
    }

    public void setInstructorID(int instructorID) {
        this.instructorID = instructorID;
    }
}
