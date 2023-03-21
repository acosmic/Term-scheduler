package com.example.c196_app.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.c196_app.entities.Course;

import java.util.List;

@Dao
public interface CourseDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Course course);
    @Update
    void update(Course course);
    @Delete
    void delete(Course course);
    @Query("SELECT * FROM COURSES ORDER BY ID ASC")
    List<Course> getAllCourses();
}
