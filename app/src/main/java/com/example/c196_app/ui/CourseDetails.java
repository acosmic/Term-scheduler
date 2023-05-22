package com.example.c196_app.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.c196_app.Database.Repository;
import com.example.c196_app.R;
import com.example.c196_app.entities.Course;
import com.example.c196_app.entities.Instructor;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;




public class CourseDetails extends AppCompatActivity {
    EditText editCourseName;
    EditText editCourseStartDate;
    EditText editCourseEndDate;
    EditText editCourseStatus;
    EditText editNote;
    Spinner spinnerInstructor;

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
    List<Instructor> instructorList;
    Instructor currentInstructor;
    String instructorFullName;

    Course course;
    Course currentCourse;
    Repository repository;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_details);
        editCourseName = findViewById(R.id.editCourseName);
        editCourseStartDate = findViewById(R.id.editCourseStartDate);
        editCourseEndDate = findViewById(R.id.editCourseEndDate);
        editCourseStatus = findViewById(R.id.editCourseStatus);
        spinnerInstructor = findViewById(R.id.spinnerInstructor);
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

        for (Instructor instructor : repository.getAllInstructors()){
            if (instructor.getID() == instructorID) currentInstructor = instructor;

        }



        // Instructor DropDown menu
        instructorList = repository.getAllInstructors();
        List<String> instructorNames = new ArrayList<>();
        for (Instructor instructor : instructorList) {
            instructorNames.add(instructor.getFirstName()+" "+instructor.getLastName());
        }

        ArrayAdapter<String> instructorArrayAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                instructorNames
        );
        instructorArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerInstructor.setAdapter(instructorArrayAdapter);

        if (instructorID > 0) {
            instructorFullName = currentInstructor.getFirstName() + " " + currentInstructor.getLastName();
            spinnerInstructor.setSelection(instructorArrayAdapter.getPosition(instructorFullName));
        }


        //Delete TERM
        Button deleteCourseButton = findViewById(R.id.deleteCourse);
        deleteCourseButton.setOnClickListener(view -> {
            for (Course course : repository.getAllCourses()){
                if (course.getID() == id) currentCourse = course;
            }
            repository.delete(currentCourse);
            Intent intent = new Intent(CourseDetails.this, TermDetails.class);
            startActivity(intent);
            Toast.makeText(CourseDetails.this, currentCourse.getTitle() +" successfully deleted", Toast.LENGTH_LONG).show();

        });

        //Save COURSE
        Button button = findViewById(R.id.saveCourse);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                instructorFullName = spinnerInstructor.getSelectedItem().toString();
                for (Instructor instructor : repository.getAllInstructors()){
                    if ((instructor.getFirstName() + " " + instructor.getLastName()).equals(instructorFullName)) instructorID = instructor.getID();
                }
                if(id == -1){
                    try {
                        course = new Course(0, editCourseName.getText().toString(),
                                sdf.parse(editCourseStartDate.getText().toString()),
                                sdf.parse(editCourseEndDate.getText().toString()),
                                editCourseStatus.getText().toString(),termID, instructorID);
                        repository.insert(course);
                        Toast.makeText(CourseDetails.this, currentCourse.getTitle() +" successfully added", Toast.LENGTH_LONG).show();

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
                        Toast.makeText(CourseDetails.this, currentCourse.getTitle() +" successfully updated", Toast.LENGTH_LONG).show();

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        editCourseStartDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
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
            myCalendarStart.set(Calendar.YEAR, year);
            myCalendarStart.set(Calendar.MONTH, monthOfYear);
            myCalendarStart.set(Calendar.DAY_OF_MONTH, dayOfMonth);


            updateLabelStart();
        };

        editCourseEndDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
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
            myCalendarEnd.set(Calendar.YEAR, year);
            myCalendarEnd.set(Calendar.MONTH, monthOfYear);
            myCalendarEnd.set(Calendar.DAY_OF_MONTH, dayOfMonth);


            updateLabelEnd();
        };
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