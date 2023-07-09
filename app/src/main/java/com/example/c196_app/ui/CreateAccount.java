package com.example.c196_app.ui;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

import com.example.c196_app.Database.Repository;
import com.example.c196_app.R;
import com.example.c196_app.entities.User;

public class CreateAccount extends AppCompatActivity {
    private Repository repository;
    EditText userName;
    EditText passWord;
    EditText verifyPassWord;

    String newUsername;
    String newPassword;
    String newVerifyPassword;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        userName = findViewById(R.id.editTextUsername2);
        passWord = findViewById(R.id.editTextPassword2);
        verifyPassWord = findViewById(R.id.editTextPassword3);
        Button saveAccountButton = findViewById(R.id.saveAccount);
        repository = new Repository(getApplication());

        saveAccountButton.setOnClickListener(view -> {
            newUsername = userName.getText().toString();
            newPassword = passWord.getText().toString();
            newVerifyPassword = verifyPassWord.getText().toString();

            String finalUsername="";
            String finalPassword ="";
            String finalVerifyPassword ="";

            for (char ch: newUsername.toCharArray()){
                if (!Character.isWhitespace(ch)){
                    finalUsername = finalUsername + ch;
                }
            }

            for (char ch: newPassword.toCharArray()){
                if (!Character.isWhitespace(ch)){
                    finalPassword = finalPassword + ch;
                }
            }

            for (char ch: newVerifyPassword.toCharArray()){
                if (!Character.isWhitespace(ch)){
                    finalVerifyPassword = finalVerifyPassword + ch;
                }
            }

            if (!finalUsername.isEmpty() && !finalPassword.isEmpty() && !finalVerifyPassword.isEmpty()){
                if (containsUpperAndLowerCase(finalPassword)){
                    if (finalPassword.length() > 7) {
                        if (containsNumber(finalPassword)){
                            if (containsSpecialCharacters(finalPassword)){
                                if (finalPassword.equals(finalVerifyPassword)){
                                    User user = new User(0,finalUsername, finalPassword);
                                    repository.insert(user);
                                    Toast.makeText(this, "New account " +user.getUserName()+" has been created!", Toast.LENGTH_SHORT).show();
                                    finishActivity();
                                } else{
                                    Toast.makeText(this, "Passwords are not matching, please try again.", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(this, "Password must contain a special character.", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(this, "Password must contain a number.", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(this, "Password must be 8 or more characters long.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "Password must contain both upper and lowercase letters.", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "All fields are required.", Toast.LENGTH_SHORT).show();
            }

        });


    }
    public boolean containsUpperAndLowerCase(String text){
        int upperCount = 0;
        int lowerCount = 0;
        for (char ch:text.toCharArray()){
            if (Character.isUpperCase(ch)) {
                upperCount++;
            }
            else if (Character.isLowerCase(ch)) {
                lowerCount++;
            }
        }
        if (upperCount > 0 && lowerCount >0){
            return true;
        } else {
            return false;
        }
    }
    public boolean containsNumber(String text){
        int numberCount = 0;
        for (char ch:text.toCharArray()){
            if (Character.isDigit(ch)){
                numberCount++;
            }
        }
        if (numberCount > 0) {
            return true;
        }else{
            return false;
        }
    }
    public boolean containsSpecialCharacters(String text){
        int count = 0;
        for (char ch:text.toCharArray()){
            if (!Character.isLetter(ch)
                    && !Character.isDigit(ch)
                    && !Character.isWhitespace(ch)) {
                count++;
            }
        }
        if (count > 0){
            return true;
        } else {
            return false;
        }
    }
    private void finishActivity(){
        this.finish();
    }
}
