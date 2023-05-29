package com.example.c196_app.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.c196_app.Database.Repository;
import com.example.c196_app.R;
import com.example.c196_app.entities.Instructor;

public class InstructorDetails extends AppCompatActivity {
    EditText editInstructorFirstName;
    EditText editInstructorLastName;
    EditText editInstructorEmail;
    EditText editInstructorPhone;

    int id;
    String firstName;
    String lastName;
    String email;
    String phone;

    Instructor instructor;
    Instructor currentInstructor;
    Repository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructor_details);
        editInstructorFirstName = findViewById(R.id.editFirstName);
        editInstructorLastName = findViewById(R.id.editLastName);
        editInstructorEmail = findViewById(R.id.editEmail);
        editInstructorPhone = findViewById(R.id.editPhone);
        id = getIntent().getIntExtra("id", -1);
        firstName = getIntent().getStringExtra("firstName");
        lastName = getIntent().getStringExtra("lastName");
        email = getIntent().getStringExtra("email");
        phone = getIntent().getStringExtra("phone");

        editInstructorFirstName.setText(firstName);
        editInstructorLastName.setText(lastName);
        editInstructorEmail.setText(email);
        editInstructorPhone.setText(phone);

        repository = new Repository(getApplication());

        // DELETE Instructor
        Button deleteInstructorButton = findViewById(R.id.deleteInstructor);
        deleteInstructorButton.setOnClickListener(view -> {
            for (Instructor instructor : repository.getAllInstructors()){
                if (instructor.getID() == id) currentInstructor = instructor;
            }
            repository.delete(currentInstructor);
            Toast.makeText(InstructorDetails.this, currentInstructor.getFirstName() +" "+currentInstructor.getLastName()+" successfully deleted", Toast.LENGTH_LONG).show();
            this.finish();
        });

        // SAVE Instructor
        Button saveInstructorButton = findViewById(R.id.saveInstructor);
        saveInstructorButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if (id == -1){
                    instructor = new Instructor(0,editInstructorFirstName.getText().toString(),
                            editInstructorLastName.getText().toString(),editInstructorPhone.getText().toString(),
                            editInstructorEmail.getText().toString());
                    repository.insert(instructor);
                    Toast.makeText(InstructorDetails.this, instructor.getFirstName()+" "+instructor.getLastName()+" successfully added", Toast.LENGTH_LONG).show();
                    finishActivity();
                }
                else {
                    instructor = new Instructor(id,editInstructorFirstName.getText().toString(),
                            editInstructorLastName.getText().toString(),editInstructorPhone.getText().toString(),
                            editInstructorEmail.getText().toString());
                    repository.update(instructor);
                    Toast.makeText(InstructorDetails.this, instructor.getFirstName()+" "+instructor.getLastName()+" successfully updated", Toast.LENGTH_LONG).show();
                    finishActivity();
                }
            }
        });
    }
    private void finishActivity(){
        this.finish();
    }
}
