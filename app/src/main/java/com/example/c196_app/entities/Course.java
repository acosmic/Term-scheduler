package com.example.c196_app.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "courses")
public class Course {
    @PrimaryKey(autoGenerate = true)
    private int ID;
    private String title;
    private Date startDate;
    private Date endDate;
    private String status; // (in progress, complete, dropped, planned to take)
    private int termID;
    private int instructorID;



    public Course (int ID, String Title, Date StartDate, Date EndDate, String Status, int termID, int instructorID) {
        this.ID = ID;
        this.title = Title;
        this.startDate = StartDate;
        this.endDate = EndDate;
        this.status = Status;
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
        return title;
    }

    public void setTitle(String Title) {
        this.title = Title;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date StartDate) {
        this.startDate = StartDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date EndDate) {
        this.endDate = EndDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String Status) {
        this.status = Status;
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
