package com.example.c196_app.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;


@Entity(tableName="terms")
public class Term {
    @PrimaryKey(autoGenerate = true)
    private int ID;
    private String Name;
    private Date StartDate;
    private Date EndDate;

    public Term (int ID, String Name, Date StartDate, Date EndDate) {
        this.ID = ID;
        this.Name = Name;
        this.StartDate = StartDate;
        this.EndDate = EndDate;
    }

    public Term (){}

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
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
}
