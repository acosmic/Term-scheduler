package com.example.c196_app.ui;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.c196_app.Database.Repository;
import com.example.c196_app.R;
import com.example.c196_app.entities.Assessment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class AssessmentDetails extends AppCompatActivity {
    EditText editAssessmentName;
    EditText editAssessmentDate;
    Spinner spinnerType;

    DatePickerDialog.OnDateSetListener date;
    final Calendar myCalendar = Calendar.getInstance();

    int id;
    String title;
    String assessmentDate;
    String type;
    int courseID;

    Assessment assessment;
    Assessment currentAssessment;
    Repository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_details);
        editAssessmentName = findViewById(R.id.editAssessmentName);
        editAssessmentDate = findViewById(R.id.editAssessmentDate);
        spinnerType = findViewById(R.id.editAssessmentType);
        id = getIntent().getIntExtra("id", -1);
        title = getIntent().getStringExtra("title");
        assessmentDate = getIntent().getStringExtra("date");
        type = getIntent().getStringExtra("type");
        courseID = getIntent().getIntExtra("courseID", -1);

        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        editAssessmentName.setText(title);
        editAssessmentDate.setText(assessmentDate);

        repository = new Repository(getApplication());

        // Set List for Type Spinner
        List<String> types = new ArrayList<>();
        types.add("Performance");
        types.add("Objective");

        ArrayAdapter<String> assessmentTypeArrayAdapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, types
        );
        assessmentTypeArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerType.setAdapter(assessmentTypeArrayAdapter);

        if (id > 0){
            spinnerType.setSelection(assessmentTypeArrayAdapter.getPosition(type));
        } else {
            spinnerType.setSelection(assessmentTypeArrayAdapter.getPosition("Objective"));
        }


        // date
        editAssessmentDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String info = editAssessmentDate.getText().toString();
                if (info.equals("")) info = "02/10/23";
                try {
                    myCalendar.setTime(sdf.parse(info));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                new DatePickerDialog(AssessmentDetails.this, date, myCalendar.get(Calendar.YEAR),
                        myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        date = (view, year, monthOfYear, dayOfMonth) -> {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            updateLabel();
        };

        // DELETE ASSESSMENT
        Button deleteAssessment = findViewById(R.id.deleteAssessment);
        deleteAssessment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (Assessment assessment : repository.getAllAssessments()){
                    if (assessment.getID() == id) currentAssessment = assessment;
                }
                repository.delete(currentAssessment);
                Intent intent = new Intent(AssessmentDetails.this, CourseDetails.class);
                intent.putExtra("id", courseID);
                startActivity(intent);
                Toast.makeText(AssessmentDetails.this, currentAssessment.getTitle() + " successfully deleted", Toast.LENGTH_LONG).show();
            }
        });

        // SAVE ASSESSMENT
        Button saveAssessment = findViewById(R.id.saveAssessment);
        saveAssessment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (id == -1){
                    try {
                        assessment = new Assessment(0,editAssessmentName.getText().toString(),
                                spinnerType.getSelectedItem().toString(),
                                sdf.parse(editAssessmentDate.getText().toString()),
                                courseID);
                        repository.insert(assessment);
                        Intent intent = new Intent(AssessmentDetails.this, CourseDetails.class);
                        intent.putExtra("id", courseID);
                        startActivity(intent);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        assessment = new Assessment(id,editAssessmentName.getText().toString(),
                                spinnerType.getSelectedItem().toString(),
                                sdf.parse(editAssessmentDate.getText().toString()),
                                courseID);
                        repository.update(assessment);
                        Intent intent = new Intent(AssessmentDetails.this, CourseDetails.class);
                        intent.putExtra("id", courseID);
                        startActivity(intent);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

    }

    private void updateLabel() {
        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        editAssessmentDate.setText(sdf.format(myCalendar.getTime()));
    }

}
