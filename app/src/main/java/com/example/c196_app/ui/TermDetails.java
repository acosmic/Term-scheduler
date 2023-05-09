package com.example.c196_app.ui;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.c196_app.Database.Repository;
import com.example.c196_app.R;
import com.example.c196_app.entities.Course;
import com.example.c196_app.entities.Term;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class TermDetails extends AppCompatActivity {
    EditText editTermName;
    EditText editTermStartDate;
    EditText editTermEndDate;

    DatePickerDialog.OnDateSetListener startDate;
    DatePickerDialog.OnDateSetListener endDate;
    final Calendar myCalendarStart = Calendar.getInstance();
    final Calendar myCalendarEnd = Calendar.getInstance();

    int id;
    String name;
    String termStartDate;
    String termEndDate;


    Term term;
    Term currentTerm;
    Repository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_details);
        editTermName = findViewById(R.id.editTermName);
        editTermStartDate = findViewById(R.id.editTermStartDate);
        editTermEndDate = findViewById(R.id.editTermEndDate);
        id = getIntent().getIntExtra("id", -1);
        name = getIntent().getStringExtra("name");

        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
//        editTermStartDate.setText(sdf.format(new Date()));
//        editTermEndDate.setText(sdf.format(new Date()));
        termStartDate = getIntent().getStringExtra("startDate");
        termEndDate = getIntent().getStringExtra("endDate");
        editTermStartDate.setText(termStartDate);
        editTermEndDate.setText(termEndDate);
        editTermName.setText(name);
//        editTermStartDate.setText(startDate);
//        editTermEndDate.setText(endDate);

        // Show Courses
        repository = new Repository(getApplication());
        RecyclerView recyclerView = findViewById(R.id.courseRecyclerView);
        repository = new Repository(getApplication());
        final CourseAdapter courseAdapter = new CourseAdapter(this);
        recyclerView.setAdapter(courseAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<Course> filteredCourses = new ArrayList<>();
        for (Course c : repository.getAllCourses()) {
            if (c.getTermID() == id) filteredCourses.add(c);
        }
        courseAdapter.setCourses(filteredCourses);

        //Delete TERM
        Button deleteButton = findViewById(R.id.deleteTerm);
        deleteButton.setOnClickListener(view -> {
            for (Term term : repository.getAllTerms()){
                if (term.getID() == id) currentTerm = term;
            }
        repository.delete(currentTerm);
        Intent intent = new Intent(TermDetails.this, TermList.class);
        startActivity(intent);
        Toast.makeText(TermDetails.this, currentTerm.getName() +" successfully deleted", Toast.LENGTH_LONG).show();

        });


        //Save TERM
        Button saveButton = findViewById(R.id.saveterm);
        saveButton.setOnClickListener(view -> {
            if(id == -1){
                try {
                    term = new Term(0, editTermName.getText().toString(),
                            sdf.parse(editTermStartDate.getText().toString()),
                            sdf.parse(editTermEndDate.getText().toString()));
                    repository.insert(term);
                    Toast.makeText(TermDetails.this, term.getName() +" successfully added", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(TermDetails.this, TermList.class);
                    startActivity(intent);

                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            else {
                try {
                    term = new Term(id, editTermName.getText().toString(),
                            sdf.parse(editTermStartDate.getText().toString()),
                            sdf.parse(editTermEndDate.getText().toString()));
                    repository.update(term);
                    Toast.makeText(TermDetails.this, term.getName() +" successfully updated", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(TermDetails.this, TermList.class);
                    startActivity(intent);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
//                Intent intent=new Intent();
        });

        editTermStartDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Date date;
                //get value from other screen,but I'm going to hard code it right now
                String info = editTermStartDate.getText().toString();
                if (info.equals("")) info = "02/10/23";
                try {
                    myCalendarStart.setTime(sdf.parse(info));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                new DatePickerDialog(TermDetails.this, startDate, myCalendarStart.get(Calendar.YEAR),
                        myCalendarStart.get(Calendar.MONTH), myCalendarStart.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        startDate = (view, year, monthOfYear, dayOfMonth) -> {
            // TODO Auto-generated method stub

            myCalendarStart.set(Calendar.YEAR, year);
            myCalendarStart.set(Calendar.MONTH, monthOfYear);
            myCalendarStart.set(Calendar.DAY_OF_MONTH, dayOfMonth);


            updateLabelStart();
        };

        editTermEndDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Date date;
                //get value from other screen,but I'm going to hard code it right now
                String info = editTermEndDate.getText().toString();
                if (info.equals("")) info = "02/10/23";
                try {
                    myCalendarEnd.setTime(sdf.parse(info));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                new DatePickerDialog(TermDetails.this, endDate, myCalendarEnd.get(Calendar.YEAR),
                        myCalendarEnd.get(Calendar.MONTH), myCalendarEnd.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        endDate = (view, year, monthOfYear, dayOfMonth) -> {
            // TODO Auto-generated method stub

            myCalendarEnd.set(Calendar.YEAR, year);
            myCalendarEnd.set(Calendar.MONTH, monthOfYear);
            myCalendarEnd.set(Calendar.DAY_OF_MONTH, dayOfMonth);


            updateLabelEnd();
        };
        // GOTO CLASS DETAILS SCREEN TO ADD NEW COURSES
        FloatingActionButton fab = findViewById(R.id.addCourseDetails);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TermDetails.this, CourseDetails.class);
                startActivity(intent);
            }
        });
    }

        private void updateLabelStart () {
            String myFormat = "MM/dd/yy";
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

            editTermStartDate.setText(sdf.format(myCalendarStart.getTime()));
        }

        private void updateLabelEnd () {
            String myFormat = "MM/dd/yy";
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

            editTermEndDate.setText(sdf.format(myCalendarEnd.getTime()));
        }



}
