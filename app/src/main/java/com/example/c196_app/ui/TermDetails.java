package com.example.c196_app.ui;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.c196_app.Adapter.CourseAdapter;
import com.example.c196_app.Database.Repository;
import com.example.c196_app.R;
import com.example.c196_app.entities.Course;
import com.example.c196_app.entities.Term;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class TermDetails extends AppCompatActivity {
    EditText editTermName;
    EditText editTermStartDate;
    EditText editTermEndDate;
    TextView coursesLabel;

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
    Repository repository = new Repository(getApplication());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_details);
        editTermName = findViewById(R.id.editTermName);
        editTermStartDate = findViewById(R.id.editTermStartDate);
        editTermEndDate = findViewById(R.id.editTermEndDate);
        coursesLabel = findViewById(R.id.coursesLabel);
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
        String courseLabel = name+" - Courses";
        coursesLabel.setText(courseLabel);


        // Show Courses
        repository = new Repository(getApplication());
        RecyclerView recyclerView = findViewById(R.id.courseRecyclerView);
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

            List<Course> checkCourses = new ArrayList<>();
            for (Course c : repository.getAllCourses()) {
                if (c.getTermID() == id) checkCourses.add(c);
            }

            if (checkCourses.size() == 0){
                repository.delete(currentTerm);
                Toast.makeText(TermDetails.this, currentTerm.getName() +" successfully deleted", Toast.LENGTH_LONG).show();
                this.finish();
            } else {
                Toast.makeText(TermDetails.this, currentTerm.getName() +" can't be deleted until all associated courses have been deleted.", Toast.LENGTH_LONG).show();
            }

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
//                    Intent intent = new Intent(TermDetails.this, TermList.class);
//                    startActivity(intent);
                    this.finish();

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
                    this.finish();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });

        // START DATE
        editTermStartDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Date date = new Date();
                String info = editTermStartDate.getText().toString();
                if (info.equals("")) info = date.toString();
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


            myCalendarStart.set(Calendar.YEAR, year);
            myCalendarStart.set(Calendar.MONTH, monthOfYear);
            myCalendarStart.set(Calendar.DAY_OF_MONTH, dayOfMonth);


            updateLabelStart();
        };

        // END DATE
        editTermEndDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Date date = new Date();
                String info = editTermEndDate.getText().toString();
                if (info.equals("")) info = date.toString();
                try {
                    myCalendarEnd.setTime(sdf.parse(info));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                DatePickerDialog endDPD = new DatePickerDialog(TermDetails.this, endDate, myCalendarEnd.get(Calendar.YEAR),
                        myCalendarEnd.get(Calendar.MONTH), myCalendarEnd.get(Calendar.DAY_OF_MONTH));
                endDPD.getDatePicker().setMinDate(myCalendarStart.getTimeInMillis());
                endDPD.show();
            }
        });

        endDate = (view, year, monthOfYear, dayOfMonth) -> {


            myCalendarEnd.set(Calendar.YEAR, year);
            myCalendarEnd.set(Calendar.MONTH, monthOfYear);
            myCalendarEnd.set(Calendar.DAY_OF_MONTH, dayOfMonth);


            updateLabelEnd();
        };
        // GOTO COURSE DETAILS SCREEN TO ADD NEW COURSES
        FloatingActionButton addCourse = findViewById(R.id.addCourseDetails);
        addCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TermDetails.this, CourseDetails.class);
                intent.putExtra("termID", id);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        RecyclerView recyclerView = findViewById(R.id.courseRecyclerView);
        final CourseAdapter courseAdapter = new CourseAdapter(this);
        recyclerView.setAdapter(courseAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<Course> filteredCourses = new ArrayList<>();
        for (Course c : repository.getAllCourses()) {
            if (c.getTermID() == id) filteredCourses.add(c);
        }
        courseAdapter.setCourses(filteredCourses);
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
