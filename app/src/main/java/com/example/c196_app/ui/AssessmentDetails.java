package com.example.c196_app.ui;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.MenuItem;
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
    EditText editAssessmentStartDate;
    EditText editAssessmentEndDate;
    Spinner spinnerType;

    DatePickerDialog.OnDateSetListener startDate;
    final Calendar myCalendarStart = Calendar.getInstance();
    DatePickerDialog.OnDateSetListener endDate;
    final Calendar myCalendarEnd = Calendar.getInstance();

    int id;
    String title;
    String assessmentStartDate;
    String assessmentEndDate;
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
        editAssessmentStartDate = findViewById(R.id.editAssessmentStartDate);
        editAssessmentEndDate = findViewById(R.id.editAssessmentEndDate);
        spinnerType = findViewById(R.id.editAssessmentType);
        id = getIntent().getIntExtra("id", -1);
        title = getIntent().getStringExtra("title");
        assessmentStartDate = getIntent().getStringExtra("startDate");
        assessmentEndDate =getIntent().getStringExtra("endDate");
        type = getIntent().getStringExtra("type");
        courseID = getIntent().getIntExtra("courseID", -1);

        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        editAssessmentName.setText(title);
        editAssessmentStartDate.setText(assessmentStartDate);
        editAssessmentEndDate.setText(assessmentEndDate);

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


        // START DATE
        editAssessmentStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String info = editAssessmentStartDate.getText().toString();
                if (info.equals("")) info = "02/10/23";
                try {
                    myCalendarStart.setTime(sdf.parse(info));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                new DatePickerDialog(AssessmentDetails.this, startDate, myCalendarStart.get(Calendar.YEAR),
                        myCalendarStart.get(Calendar.MONTH), myCalendarStart.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        startDate = (view, year, monthOfYear, dayOfMonth) -> {
            myCalendarStart.set(Calendar.YEAR, year);
            myCalendarStart.set(Calendar.MONTH, monthOfYear);
            myCalendarStart.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            updateStartLabel();
        };

        // END DATE
        editAssessmentEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String info = editAssessmentEndDate.getText().toString();
                if (info.equals("")) info = "02/10/23";
                try {
                    myCalendarEnd.setTime(sdf.parse(info));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                new DatePickerDialog(AssessmentDetails.this, endDate, myCalendarEnd.get(Calendar.YEAR),
                        myCalendarEnd.get(Calendar.MONTH), myCalendarEnd.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        endDate = (view, year, monthOfYear, dayOfMonth) -> {
            myCalendarEnd.set(Calendar.YEAR, year);
            myCalendarEnd.set(Calendar.MONTH, monthOfYear);
            myCalendarEnd.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            updateEndLabel();
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
                Toast.makeText(AssessmentDetails.this, currentAssessment.getTitle() + " successfully deleted", Toast.LENGTH_LONG).show();
                finishActivity();
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
                                sdf.parse(editAssessmentStartDate.getText().toString()),
                                sdf.parse(editAssessmentEndDate.getText().toString()),
                                courseID);
                        repository.insert(assessment);
                        Toast.makeText(AssessmentDetails.this, assessment.getTitle() + " successfully added", Toast.LENGTH_LONG).show();
                        finishActivity();
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        assessment = new Assessment(id,editAssessmentName.getText().toString(),
                                spinnerType.getSelectedItem().toString(),
                                sdf.parse(editAssessmentStartDate.getText().toString()),
                                sdf.parse(editAssessmentEndDate.getText().toString()),
                                courseID);
                        repository.update(assessment);
                        Toast.makeText(AssessmentDetails.this, assessment.getTitle() + " successfully updated", Toast.LENGTH_LONG).show();
                        finishActivity();
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

    }

    // BACK Button - finish current activity
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            this.finish();
            return true;
        }
        return false;
    }
    private void finishActivity(){
        this.finish();
    }

    private void updateStartLabel() {
        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        editAssessmentStartDate.setText(sdf.format(myCalendarStart.getTime()));
    }
    private void updateEndLabel() {
        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        editAssessmentEndDate.setText(sdf.format(myCalendarEnd.getTime()));
    }

}
