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

import java.util.List;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.CourseViewHolder> {
    class CourseViewHolder extends RecyclerView.ViewHolder {
        private final TextView courseItemView;
        private final TextView courseItemView2;
        private CourseViewHolder(View itemView){
            super(itemView);
            courseItemView=itemView.findViewById(R.id.textViewCourseName);
            courseItemView2=itemView.findViewById(R.id.textViewCourseStatus);
            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view){
                    int position = getAdapterPosition();
                    final Course current = mCourses.get(position);
                    Intent intent = new Intent(context, CourseDetails.class);
                    intent.putExtra("title", current.getTitle());
                    intent.putExtra("status", current.getStatus());
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
            holder.courseItemView.setText(title);
            holder.courseItemView2.setText(status);
        }
        else {
            holder.courseItemView.setText("no title");
            holder.courseItemView2.setText("no status");
        }
    }
    @Override
    public int getItemCount() { return mCourses.size(); }
    public void setCourses(List<Course> courses){
        mCourses = courses;
        notifyDataSetChanged();
    }

}
