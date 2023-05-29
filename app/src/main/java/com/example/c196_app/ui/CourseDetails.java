package com.example.c196_app.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.c196_app.Adapter.AssessmentAdapter;
import com.example.c196_app.Database.Repository;
import com.example.c196_app.R;
import com.example.c196_app.entities.Assessment;
import com.example.c196_app.entities.Course;
import com.example.c196_app.entities.Instructor;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;




public class CourseDetails extends AppCompatActivity {
    EditText editCourseName;
    EditText editCourseStartDate;
    EditText editCourseEndDate;
    Spinner spinnerStatus;
    EditText editNote;
    Spinner spinnerInstructor;
    TextView assessmentsLabel;


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
        spinnerStatus = findViewById(R.id.editCourseStatus);
        spinnerInstructor = findViewById(R.id.spinnerInstructor);
        assessmentsLabel = findViewById(R.id.assessmentsLabel);
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
        String aLabel = title+ " - Assessments";
        assessmentsLabel.setText(aLabel);


        repository = new Repository(getApplication());
        //Show Assessments
        RecyclerView recyclerView = findViewById(R.id.assessmentRecyclerView);
        final AssessmentAdapter assessmentAdapter = new AssessmentAdapter(this);
        recyclerView.setAdapter(assessmentAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<Assessment> filteredAssessments = new ArrayList<>();
        for (Assessment a : repository.getAllAssessments()){
            if (a.getCourseID() == id) filteredAssessments.add(a);
        }
        assessmentAdapter.setAssessments(filteredAssessments);

        // Set List for Status Spinner
        List<String> statuses = new ArrayList<>();
        statuses.add("Plan To Take");
        statuses.add("In Progress");
        statuses.add("Dropped");
        statuses.add("Completed");

        ArrayAdapter<String> courseStatusArrayAdapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, statuses
        );
        courseStatusArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerStatus.setAdapter(courseStatusArrayAdapter);

        if (id > 0){
            spinnerStatus.setSelection(courseStatusArrayAdapter.getPosition(status));
        } else {
            spinnerStatus.setSelection(courseStatusArrayAdapter.getPosition("Plan To Take"));
        }


        // Get current Instructor
        for (Instructor instructor : repository.getAllInstructors()){
            if (instructor.getID() == instructorID) currentInstructor = instructor;

        }

        // Instructor Spinner menu
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




        //Delete COURSE
        Button deleteCourseButton = findViewById(R.id.deleteCourse);
        deleteCourseButton.setOnClickListener(view -> {
            for (Course course : repository.getAllCourses()){
                if (course.getID() == id) currentCourse = course;
            }
            repository.delete(currentCourse);
            Toast.makeText(CourseDetails.this, currentCourse.getTitle() +" successfully deleted", Toast.LENGTH_LONG).show();
            this.finish();
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
                status = spinnerStatus.getSelectedItem().toString();
                if(id == -1){
                    try {
                        course = new Course(0, editCourseName.getText().toString(),
                                sdf.parse(editCourseStartDate.getText().toString()),
                                sdf.parse(editCourseEndDate.getText().toString()),
                                status,termID, instructorID);
                        repository.insert(course);
                        Toast.makeText(CourseDetails.this, course.getTitle() +" successfully added", Toast.LENGTH_LONG).show();
                        finishActivity();
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
                else {
                    try {
                        course = new Course(id, editCourseName.getText().toString(),
                                sdf.parse(editCourseStartDate.getText().toString()),
                                sdf.parse(editCourseEndDate.getText().toString()),
                                status,termID, instructorID);
                        repository.update(course);
                        Toast.makeText(CourseDetails.this, course.getTitle() +" successfully updated", Toast.LENGTH_LONG).show();
                        finishActivity();
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        // ADD Assessment button
        FloatingActionButton addAssessment = findViewById(R.id.addAssessment);
        addAssessment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(CourseDetails.this, AssessmentDetails.class);
                intent.putExtra("courseID", id);
                startActivity(intent);
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

    @Override
    protected void onResume(){
        super.onResume();
        RecyclerView recyclerView = findViewById(R.id.assessmentRecyclerView);
        final AssessmentAdapter assessmentAdapter = new AssessmentAdapter(this);
        recyclerView.setAdapter(assessmentAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<Assessment> filteredAssessments = new ArrayList<>();
        for (Assessment a : repository.getAllAssessments()){
            if (a.getCourseID() == id) filteredAssessments.add(a);
        }
        assessmentAdapter.setAssessments(filteredAssessments);
    }

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_coursedetails, menu);
        return true;
    }

    // MENU ITEMS AND BACK NAVIGATION
    @SuppressLint("NonConstantResourceId")
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            case R.id.share:
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, editNote.getText().toString());
                sendIntent.putExtra(Intent.EXTRA_TITLE, "Message Title");
                sendIntent.setType("text/plain");
                Intent shareIntent = Intent.createChooser(sendIntent, null);
                startActivity(shareIntent);
                return true;
            case R.id.notifyCourseStart:
                String dateFromScreen=editCourseStartDate.getText().toString();
                String myFormat = "MM/dd/yy";
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                Date myStartDate=null;
                try {
                    myStartDate=sdf.parse(dateFromScreen);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Long trigger=myStartDate.getTime();
//                Intent intent= new Intent(CourseDetails.this, MyReceiver.class);
//                intent.putExtra("key", dateFromScreen + " should trigger");
//                PendingIntent sender = PendingIntent.getBroadcast(CourseDetails.this, ++MainActivity.numAlert, intent, PendingIntent.FLAG_IMMUTABLE);
//                AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
//                alarmManager.set(AlarmManager.RTC_WAKEUP, trigger, sender);
//                return true;
            case R.id.notifyCourseEnd:
                return true;
        }
        return super.onOptionsItemSelected(item);

    }
    private void finishActivity(){
        this.finish();
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