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
    Repository repository = new Repository(ApplicationProvider.getApplicationContext());
    User testDeleteUser;

    @Test
    public void insertAndReturnUser() {
        // Clear the database before starting the test
        if (!repository.getAllUsers().isEmpty()){
            for (User user : repository.getAllUsers()) {
                repository.delete(user);
            }
        }

        // Insert a new user
        userExists = false;
        User testUser = new User(0,"Test", "Testing@123");
        repository.insert(testUser);

        // Check if the user was added
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
    public void UpdateUser() {
        User testUser = new User(14,"Clint", "Clint@123");

        boolean userUpdated = false;

        User testUpdateUser = new User(testUser.getID(), "DAVID", "Clint789!");
        // Update User
        repository.update(testUpdateUser);
        List<User> userListAfterUpdate = repository.getAllUsers();

        // Check if User is updated
        for (User user : userListAfterUpdate){
            System.out.println("AFTER    "+user.getID() + "  "+user.getUserName()+"  "+user.getPassWord());

            if (user.getUserName().equals(testUpdateUser.getUserName())){
                userUpdated = true;
            }
            assertTrue(userUpdated);
        }

//
//
//        // Check if the user was successfully updated
//        List<User> userListAfterUpdate = repository.getAllUsers();
//        for (User user : userListAfterUpdate) {
//
//            System.out.println(user.getUserName());
//
//            if (user.getUserName().equals(testUpdateUser.getUserName()) && user.getPassWord().equals(testUpdateUser.getPassWord())){
//                userUpdated = true;
//                System.out.println("CONDITIONAL WORKING:  "+user.getUserName());
//                break;
//            }
//        }
//        assertTrue(userUpdated);
    }

    @Test
    public void insertAndDeleteUser() {
        // Clear the database before starting the test
        if (!repository.getAllUsers().isEmpty()){
            for (User user : repository.getAllUsers()) {
                repository.delete(user);
            }
        }
        User testUser = new User(0, "Test", "Testing@123");
        repository.insert(testUser);

        // Check if the user exists before deletion
        List<User> userListBeforeDeletion = repository.getAllUsers();
        boolean userFound = false;
        User userToDelete = null;
        for (User user : userListBeforeDeletion) {
            if (user.getUserName().equals(testUser.getUserName()) && user.getPassWord().equals(testUser.getPassWord())) {
                userFound = true;
                userToDelete = user;
                break;
            }
        }
        assertTrue(userFound);

        // Delete the user
        repository.delete(userToDelete);
        // Check if the user is no longer in the database
        List<User> userListAfterDeletion = repository.getAllUsers();
        assertFalse(userListAfterDeletion.contains(userToDelete));
    }
}