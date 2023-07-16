package com.example.c196_app.dao;

import static org.junit.Assert.*;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import com.example.c196_app.Database.Repository;
import com.example.c196_app.entities.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import java.util.List;

@RunWith(AndroidJUnit4.class)
public class UserDAOTest{
    boolean userExists;


    @Test
    public void insertAndReturnUser() {
        Repository repository = new Repository(ApplicationProvider.getApplicationContext());
        User testUser = new User(0,"Test", "Testing@123");
        repository.insert(testUser);
        List<User> userList = repository.getAllUsers();
        userExists = false;
        for (User user : userList) {
            if (user.getUserName().equals(testUser.getUserName()) && user.getPassWord().equals(testUser.getPassWord())) {
                userExists = true;
                break;
            }
        }
        assertTrue(userExists);
    }

    @Test
    public void update() {
    }

    @Test
    public void delete() {
    }

    @Test
    public void getAllUsers() {
    }
}