package com.example.c196_app.ui;



import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.c196_app.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Date;

public class TermDetails extends AppCompatActivity {
    EditText editTermName;
    EditText editTermStartDate;
    String name;
    String startDate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_details);
        editTermName = findViewById(R.id.editTermName);
        editTermStartDate = findViewById(R.id.editStartDate);
        name = getIntent().getStringExtra("name");
        startDate = getIntent().getStringExtra("startDate");
        editTermName.setText(name);
        editTermStartDate.setText(startDate);

        FloatingActionButton fab=findViewById(R.id.addClassDetails);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                Intent intent=new Intent(TermDetails.this, CourseDetails.class);
                startActivity(intent);
            }
        });
    }
}