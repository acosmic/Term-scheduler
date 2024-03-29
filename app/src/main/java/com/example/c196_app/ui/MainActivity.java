package com.example.c196_app.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.c196_app.Database.Repository;
import com.example.c196_app.R;
import com.example.c196_app.entities.Course;
import com.example.c196_app.entities.Instructor;
import com.example.c196_app.entities.Term;
import com.example.c196_app.entities.User;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    public static int numAlert;
    EditText userNameField;
    EditText passWordField;
    Repository repository;
    String userName;
    String passWord;
    List<User> userList;
    boolean userExists;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button signIn=findViewById(R.id.buttonEnter);
        Button createAccount=findViewById(R.id.createAccount);
        userNameField = findViewById(R.id.editTextUsername);
        passWordField = findViewById(R.id.editTextPassword);
        repository = new Repository(getApplication());

        // CREATE ACCOUNT
        createAccount.setOnClickListener(view -> {
            Intent intent=new Intent(MainActivity.this, CreateAccount.class);
            startActivity(intent);
        });

        // SIGN IN
        signIn.setOnClickListener(view -> {
            userName = userNameField.getText().toString();
            passWord = passWordField.getText().toString();
            userList = repository.getAllUsers();
            userExists = false;

            if (userList.isEmpty()){
                Toast.makeText(MainActivity.this,  "No users exist, please add sample data and sign in with UN:admin PW:admin.", Toast.LENGTH_LONG).show();
            } else {
                for (User user : userList) {
                    if (user.getUserName().equals(userName) && user.getPassWord().equals(passWord)) {
                        userExists = true;
                        break;
                    }
                }
                if (userExists) {
                    Toast.makeText(MainActivity.this, userName +" authenticated successfully", Toast.LENGTH_LONG).show();
                    Intent intent=new Intent(MainActivity.this, TermList.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(MainActivity.this, "Invalid credentials!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.addSampleData:
                String myFormat = "MM/dd/yy";
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                Date termStartDate = new Date();
                Term term = new Term(0, "Spring", termStartDate, termStartDate);
                Term term2 = new Term(0, "Summer", termStartDate, termStartDate);
                Term term3 = new Term(0, "Fall", termStartDate, termStartDate);
                Term term4 = new Term(0, "Winter", termStartDate, termStartDate);
                Course course = new Course(0, "Biology", termStartDate, termStartDate, "Plan To Take", "", 1,1);
                Course course2 = new Course(0, "Calculus", termStartDate, termStartDate, "In Progress", "", 2,2);
                Course course3 = new Course(0, "Astronomy", termStartDate, termStartDate, "Plan To Take", "", 3,1);
                Course course4 = new Course(0, "Physics", termStartDate, termStartDate, "Plan To Take", "", 4,2);
                Instructor instructor=new Instructor(0,"Carl", "Sagan", "123-123-1234","CSagan@email.com");
                Instructor instructor2=new Instructor(0,"Stephen", "Hawking", "321-321-4321","SHawking@email.com");
                User user = new User(0,"admin", "admin");
                Repository repository=new Repository(getApplication());
                repository.insert(user);
                repository.insert(term);
                repository.insert(term2);
                repository.insert(term3);
                repository.insert(term4);
                repository.insert(instructor);
                repository.insert(instructor2);
                repository.insert(course);
                repository.insert(course2);
                repository.insert(course3);
                repository.insert(course4);
                Toast.makeText(MainActivity.this, "Sample data added!", Toast.LENGTH_LONG).show();
                return true;

        }
        return super.onOptionsItemSelected(item);
    }
}