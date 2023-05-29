package com.example.c196_app.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.c196_app.Database.Repository;
import com.example.c196_app.R;
import com.example.c196_app.entities.Instructor;
import com.example.c196_app.entities.Term;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    public static int numAlert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button=findViewById(R.id.buttonEnter);

//        Term term=new Term(0,"Spring", null,null);
//        Repository repository=new Repository(getApplication());
//        repository.insert(term);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                Intent intent=new Intent(MainActivity.this, TermList.class);
                startActivity(intent);
            }
        });
    }
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.addSampleData:
                String myFormat = "MM/dd/yy";
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                Date termStartDate = new Date();
                Term term = new Term(0, "Spring", termStartDate, termStartDate);
                Instructor instructor=new Instructor(0,"Carl", "Sagan", "123-123-1234","CSagan@email.com");
                Instructor instructor2=new Instructor(0,"Stephen", "Hawking", "321-321-4321","SHawking@email.com");
                Repository repository=new Repository(getApplication());
                repository.insert(term);
                repository.insert(instructor);
                repository.insert(instructor2);
                return true;

        }
        return super.onOptionsItemSelected(item);
    }
}