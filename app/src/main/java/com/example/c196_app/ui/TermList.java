package com.example.c196_app.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.c196_app.Adapter.TermAdapter;
import com.example.c196_app.Database.Repository;
import com.example.c196_app.R;
import com.example.c196_app.entities.Course;
import com.example.c196_app.entities.Term;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class TermList extends AppCompatActivity {
    private Repository repository;
    private SearchView searchView;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_list);
        RecyclerView recyclerView=findViewById(R.id.termrecyclerview);
        TermAdapter termAdapter = new TermAdapter(this);
        recyclerView.setAdapter(termAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        repository = new Repository(getApplication());
        List<Term> allTerms = repository.getAllTerms();
        FloatingActionButton fab=findViewById(R.id.addTermButton);
        termAdapter.setTerms(allTerms);
        searchView = findViewById(R.id.searchBar);
        searchView.clearFocus();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterList(newText, termAdapter);
                return true;
            }
        });


        // Add Term
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                Intent intent=new Intent(TermList.this, TermDetails.class);
                startActivity(intent);
            }
        });
        Button instructorList=findViewById(R.id.gotoInstructorList);
        instructorList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(TermList.this, InstructorList.class);
                startActivity(intent);
            }
        });
    }

    private void filterList(String text, TermAdapter termAdapter) {
        List<Term> filteredTerms = new ArrayList<>();
        for (Term term : repository.getAllTerms()){
            if (term.getName().toLowerCase().contains(text.toLowerCase())){
                filteredTerms.add(term);
            }
        }
        if (!(filteredTerms.isEmpty())){
            RecyclerView recyclerView=findViewById(R.id.termrecyclerview);
            recyclerView.setAdapter(termAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            termAdapter.setTerms(filteredTerms);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        List<Term> allTerms = repository.getAllTerms();
        RecyclerView recyclerView=findViewById(R.id.termrecyclerview);
        final TermAdapter termAdapter = new TermAdapter(this);
        recyclerView.setAdapter(termAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        termAdapter.setTerms(allTerms);
    }


    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_termlist_reports, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.report:
                StringBuilder courseReport = new StringBuilder();
                String finalReportString;
                String courseTerm = "";
                String courseTitle;
                String courseStatus;
                int courseTermID;
                //Format Course Report: Term - Course Title - Status
                for (Course c : repository.getAllCourses()){
                    courseTermID = c.getTermID();
                    courseTitle = c.getTitle();
                    courseStatus = c.getStatus();

                    for (Term t : repository.getAllTerms()){
                        if (courseTermID == t.getID()){
                            courseTerm = t.getName();
                        }
                    }
                    courseReport
                            .append(courseTerm)
                            .append(" - ")
                            .append(courseTitle)
                            .append(" - ")
                            .append(courseStatus)
                            .append("\n");
                }
                Date timeStamp = new Date();
                finalReportString = "Status Report - Courses: " + "\n\n"
                        +courseReport.toString()
                        +"\n\nGenerated at: "+timeStamp;



                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, finalReportString);
                sendIntent.putExtra(Intent.EXTRA_TITLE, "Course Report");
                sendIntent.setType("text/plain");
                Intent shareIntent = Intent.createChooser(sendIntent, null);
                startActivity(shareIntent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}