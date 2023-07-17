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
    boolean userUpdated;
    Repository repository = new Repository(ApplicationProvider.getApplicationContext());
    
    User testDeleteUser;

    @Test
    public void insertAndReturnUser() {
        // working
        userExists = false;
        User testUser = new User(0,"Test", "Testing@123");
        repository.insert(testUser);
        List<User> userList = repository.getAllUsers();
        for (User user : userList) {
            if (user.getUserName().equals(testUser.getUserName()) && user.getPassWord().equals(testUser.getPassWord())) {
                userExists = true;
                repository.delete(user);
                break;
            }
        }
        assertTrue(userExists);
    }

    @Test
    public void insertAndUpdateUser() {
        userUpdated = false;
        int testUpdateUserID = 0;
        User testUser = new User(0, "Test", "Testing@123");
        repository.insert(testUser);
        List<User> userList = repository.getAllUsers();
        for (User user : userList) {
            if (user.getUserName().equals(testUser.getUserName()) && user.getPassWord().equals(testUser.getPassWord())) {
                testUpdateUserID = user.getID();
                break;
            }
        }
        User testUpdateUser = new User(testUpdateUserID, "testing", "123@Testing");
        repository.update(testUpdateUser);

        // NOT WORKING - WHAT THE FUCK IS WRONG WITH THIS
        List<User> updatedUserList = repository.getAllUsers();
        for (User user : updatedUserList) {
            if (user.getUserName().equals("testing") && user.getPassWord().equals("123@Testing")) {
                userUpdated = true;
                repository.delete(user);
                break;
            }
        }
        assertTrue(userUpdated);
    }

    @Test
    public void insertAndDeleteUser() {
        // THIS BULLSHIT WORKS THANK FUCK
        userExists = false;
        User testUser = new User(0,"Test", "Testing@123");
        repository.insert(testUser);
        List<User> userList = repository.getAllUsers();
        for (User user : userList) {
            if (user.getUserName().equals(testUser.getUserName()) && user.getPassWord().equals(testUser.getPassWord())) {
                userExists = true;
                repository.delete(user);
                break;
            }
        }
        List<User> userUpdatedList = repository.getAllUsers();
        if (userUpdatedList.isEmpty()) {
            userExists = false;
        }
        assertFalse(userExists);
    }
}