package com.example.c196_app.ui;

import static com.example.c196_app.R.*;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class TermDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_term_details);
        FloatingActionButton fab=findViewById(id.addClassDetails);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                Intent intent=new Intent(TermDetails.this, CourseDetails.class);
                startActivity(intent);
            }
        });
    }
}