package com.example.c196_app.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.c196_app.Database.Repository;
import com.example.c196_app.R;
import com.example.c196_app.entities.Term;

public class MainActivity extends AppCompatActivity {

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
                Term term=new Term(0,"Spring", null,null);
                Repository repository=new Repository(getApplication());
                repository.insert(term);
                return true;

        }
        return super.onOptionsItemSelected(item);
    }
}