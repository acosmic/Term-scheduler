package com.example.c196_app.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.c196_app.Adapter.InstructorAdapter;
import com.example.c196_app.Database.Repository;
import com.example.c196_app.R;
import com.example.c196_app.entities.Instructor;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class InstructorList extends AppCompatActivity {
    private Repository repository;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructor_list);
        RecyclerView recyclerView=findViewById(R.id.instructorRecyclerView);
        final InstructorAdapter instructorAdapter = new InstructorAdapter(this);
        recyclerView.setAdapter(instructorAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        repository = new Repository(getApplication());
        List<Instructor> allInstructors = repository.getAllInstructors();
        instructorAdapter.setInstructors(allInstructors);
        FloatingActionButton addInstructor = findViewById(R.id.addInstructorButton);
        addInstructor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(InstructorList.this, InstructorDetails.class);
                startActivity(intent);
            }
        });
    }
}
