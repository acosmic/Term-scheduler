package com.example.c196_app.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;


@Entity(tableName="terms")
public class Term {
    @PrimaryKey(autoGenerate = true)
    private int ID;
    private String name;
    private Date startDate;
    private Date endDate;

    public Term (int ID, String Name, Date StartDate, Date EndDate) {
        this.ID = ID;
        this.name = Name;
        this.startDate = StartDate;
        this.endDate = EndDate;
    }

    public Term (){}

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String Name) {
        this.name = Name;
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

    @Override
    public String toString() {
        return "Term{" +
                "ID=" + ID +
                ", name='" + name + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                '}';
    }
}
