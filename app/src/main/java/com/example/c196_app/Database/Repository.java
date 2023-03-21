package com.example.c196_app.Database;

import android.app.Application;

import com.example.c196_app.dao.AssessmentDAO;
import com.example.c196_app.dao.CourseDAO;
import com.example.c196_app.dao.InstructorDAO;
import com.example.c196_app.dao.TermDAO;
import com.example.c196_app.entities.Assessment;
import com.example.c196_app.entities.Course;
import com.example.c196_app.entities.Instructor;
import com.example.c196_app.entities.Term;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Repository {
    private TermDAO mTermDAO;
    private CourseDAO mCourseDAO;
    private InstructorDAO mInstructorDAO;
    private AssessmentDAO mAssessmentDAO;

    private List<Term> mAllTerms;
    private List<Course> mAllCourses;
    private List<Instructor> mAllInstructors;
    private List<Assessment> mAllAssessments;

    private static int NUMBER_OF_THREADS=4;
    static final ExecutorService databaseExecutor= Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public Repository(Application application){
        DatabaseBuilder db=DatabaseBuilder.getDatabase(application);
        mTermDAO=db.termDAO();
        mCourseDAO=db.courseDAO();
        mInstructorDAO=db.instructorDAO();
        mAssessmentDAO=db.assessmentDAO();
    }
    public List<Term>getAllTerms(){
        databaseExecutor.execute(()->{
            mAllTerms=mTermDAO.getAllTerms();
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return mAllTerms;
    }

    public void insert(Term term){
        databaseExecutor.execute(() ->{
            mTermDAO.insert(term);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }

    public void update(Term term){
        databaseExecutor.execute(() ->{
            mTermDAO.update(term);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }

    public void delete(Term term){
        databaseExecutor.execute(() ->{
            mTermDAO.delete(term);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }


}

