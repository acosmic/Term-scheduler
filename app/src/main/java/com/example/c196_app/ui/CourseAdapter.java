package com.example.c196_app.ui;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.c196_app.R;
import com.example.c196_app.entities.Course;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.CourseViewHolder> {
    class CourseViewHolder extends RecyclerView.ViewHolder {
        private final TextView courseNameItemView;
        private final TextView courseStatusItemView;
        private CourseViewHolder(View itemView){
            super(itemView);
            courseNameItemView =itemView.findViewById(R.id.textViewCourseName); // course_list_item.xml
            courseStatusItemView =itemView.findViewById(R.id.textViewCourseStatus); // course_list_item.xml
            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view){
                    int position = getAdapterPosition();
                    final Course current = mCourses.get(position);
                    Intent intent = new Intent(context, CourseDetails.class);
                    intent.putExtra("id", current.getID());
                    intent.putExtra("title", current.getTitle());
                    intent.putExtra("status", current.getStatus());
                    intent.putExtra("termID", current.getTermID());
                    intent.putExtra("instructorID", current.getInstructorID());
                    SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yy", Locale.US);
                    String startDateString;
                    String endDateString;
                    if (current.getStartDate() == null) {
                        startDateString = "";
                    }
                    else {
                        startDateString = sdf.format(current.getStartDate());
                    }
                    if (current.getEndDate() == null){
                        endDateString = "";
                    }
                    else {
                        endDateString = sdf.format(current.getEndDate());
                    }
                    intent.putExtra("startDate", startDateString);
                    intent.putExtra("endDate", endDateString);
                    context.startActivity(intent);
                }
            });
        }
    }
    private List<Course> mCourses;
    private final Context context;
    private final LayoutInflater mInflater;
    public CourseAdapter(Context context){
        mInflater= LayoutInflater.from(context);
        this.context=context;
    }
    @NonNull
    @Override
    public CourseAdapter.CourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View itemView = mInflater.inflate(R.layout.course_list_item,parent,false);

        return new CourseViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseAdapter.CourseViewHolder holder, int position) {
        if(mCourses!=null){
            Course current = mCourses.get(position);
            String title = current.getTitle();
            String status = current.getStatus();
            holder.courseNameItemView.setText(title);
            holder.courseStatusItemView.setText(status);
        }
        else {
            holder.courseNameItemView.setText("no title");
            holder.courseStatusItemView.setText("no status");
        }
    }
    @Override
    public int getItemCount() { return mCourses.size(); }
    public void setCourses(List<Course> courses){
        mCourses = courses;
        notifyDataSetChanged();
    }

}
