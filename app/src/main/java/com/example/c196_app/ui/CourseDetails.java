package com.example.c196_app.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.c196_app.Database.Repository;
import com.example.c196_app.R;
import com.example.c196_app.entities.Course;
import com.example.c196_app.entities.Term;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class CourseDetails extends AppCompatActivity {
    EditText editCourseName;
    EditText editCourseStartDate;
    EditText editCourseEndDate;
    EditText editCourseStatus;

    DatePickerDialog.OnDateSetListener startDate;
    DatePickerDialog.OnDateSetListener endDate;
    final Calendar myCalendarStart = Calendar.getInstance();
    final Calendar myCalendarEnd = Calendar.getInstance();

    int id;
    String title;
    String courseStartDate;
    String courseEndDate;
    String status;
    int termID;
    int instructorID;

    Course course;
    Repository repository;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_details);
        editCourseName = findViewById(R.id.editCourseName);
        editCourseStartDate = findViewById(R.id.editCourseStartDate);
        editCourseEndDate = findViewById(R.id.editCourseEndDate);
        editCourseStatus = findViewById(R.id.editCourseStatus);
        id = getIntent().getIntExtra("id", -1);
        title = getIntent().getStringExtra("title");
        status = getIntent().getStringExtra("status");
        termID = getIntent().getIntExtra("termID", -1);
        instructorID = getIntent().getIntExtra("instructorID", -1);

        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        courseStartDate = getIntent().getStringExtra("startDate");
        courseEndDate = getIntent().getStringExtra("endDate");
        editCourseStartDate.setText(courseStartDate);
        editCourseEndDate.setText(courseEndDate);
        editCourseName.setText(title);
        editCourseStatus.setText(status);

        repository = new Repository(getApplication());

        //Save COURSE
        Button button = findViewById(R.id.saveCourse);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(id == -1){
                    try {
                        course = new Course(0, editCourseName.getText().toString(),
                                sdf.parse(editCourseStartDate.getText().toString()),
                                sdf.parse(editCourseEndDate.getText().toString()),
                                editCourseStatus.getText().toString(),0, 0); //
                        repository.insert(course);

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
                else {
                    try {
                        course = new Course(id, editCourseName.getText().toString(),
                                sdf.parse(editCourseStartDate.getText().toString()),
                                sdf.parse(editCourseEndDate.getText().toString()),
                                editCourseStatus.getText().toString(),termID, instructorID);
                        repository.update(course);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
//                Intent intent=new Intent();
            }
        });
        editCourseStartDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Date date;
                //get value from other screen,but I'm going to hard code it right now
                String info = editCourseStartDate.getText().toString();
                if (info.equals("")) info = "02/10/23";
                try {
                    myCalendarStart.setTime(sdf.parse(info));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                new DatePickerDialog(CourseDetails.this, startDate, myCalendarStart.get(Calendar.YEAR),
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

        editCourseEndDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Date date;
                //get value from other screen,but I'm going to hard code it right now
                String info = editCourseEndDate.getText().toString();
                if (info.equals("")) info = "02/10/23";
                try {
                    myCalendarEnd.setTime(sdf.parse(info));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                new DatePickerDialog(CourseDetails.this, endDate, myCalendarEnd.get(Calendar.YEAR),
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
        // TODO  Change this for next screen? -- GOTO CLASS DETAILS SCREEN TO ADD NEW COURSES
//        FloatingActionButton fab = findViewById(R.id.addCourseDetails);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(TermDetails.this, CourseDetails.class);
//                startActivity(intent);
//            }
//        });
    }

    private void updateLabelStart () {
        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        editCourseStartDate.setText(sdf.format(myCalendarStart.getTime()));
    }

    private void updateLabelEnd () {
        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        editCourseEndDate.setText(sdf.format(myCalendarEnd.getTime()));
    }

}