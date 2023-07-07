package com.example.c196_app.Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.c196_app.dao.AssessmentDAO;
import com.example.c196_app.dao.CourseDAO;
import com.example.c196_app.dao.InstructorDAO;
import com.example.c196_app.dao.TermDAO;
import com.example.c196_app.dao.UserDAO;
import com.example.c196_app.entities.Assessment;
import com.example.c196_app.entities.Course;
import com.example.c196_app.entities.Instructor;
import com.example.c196_app.entities.Term;
import com.example.c196_app.entities.User;

@Database(entities = {Term.class, Course.class, Assessment.class, Instructor.class, User.class}, version=7, exportSchema = false)
@TypeConverters(Converters.class)
public abstract class DatabaseBuilder extends RoomDatabase {
    public abstract TermDAO termDAO();
    public abstract CourseDAO courseDAO();
    public abstract AssessmentDAO assessmentDAO();
    public abstract InstructorDAO instructorDAO();
    public abstract UserDAO userDAO();

    private static volatile DatabaseBuilder INSTANCE;

    static DatabaseBuilder getDatabase(final Context context){
        if(INSTANCE==null){
            synchronized (DatabaseBuilder.class){
                if (INSTANCE==null){
                    INSTANCE= Room.databaseBuilder(context.getApplicationContext(), DatabaseBuilder.class, "MyTermDatabase.db")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
