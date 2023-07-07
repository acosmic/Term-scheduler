package com.example.c196_app.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;


import com.example.c196_app.entities.User;

import java.util.List;

@Dao
public interface UserDAO {
        @Insert(onConflict = OnConflictStrategy.IGNORE)
        void insert(User user);
        @Update
        void update(User user);
        @Delete
        void delete(User user);
        @Query("SELECT * FROM USERS ORDER BY ID ASC")
        List<User> getAllUsers();
    }

