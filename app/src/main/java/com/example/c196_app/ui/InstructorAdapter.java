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
import com.example.c196_app.entities.Instructor;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class InstructorAdapter extends RecyclerView.Adapter<InstructorAdapter.InstructorViewHolder> {
    class InstructorViewHolder extends RecyclerView.ViewHolder{
        //TODO: put instructor_list_item variables here - reference CourseAdapter
        private final TextView firstNameIV;
        private final TextView lastNameIV;
        private InstructorViewHolder(View itemView){
            super(itemView);
            //TODO: put instructor_list_item variables here - reference CourseAdapter
            firstNameIV = itemView.findViewById(R.id.InstructorFirstName);
            lastNameIV = itemView.findViewById(R.id.InstructorLastName);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    final Instructor current = mInstructors.get(position);
                    Intent intent = new Intent(context, InstructorDetails.class);
                    intent.putExtra("id", current.getID());
                    intent.putExtra("firstName", current.getFirstName());
                    intent.putExtra("lastName", current.getLastName());
                    intent.putExtra("email", current.getEmail());
                    intent.putExtra("phone", current.getPhoneNumber());
                    context.startActivity(intent);
                }
            });
        }
    }
    private List<Instructor> mInstructors;
    private final Context context;
    private final LayoutInflater mInflater;
    public InstructorAdapter(Context context){
        mInflater= LayoutInflater.from(context);
        this.context=context;
    }
    @NonNull
    @Override
    public InstructorAdapter.InstructorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View itemView = mInflater.inflate(R.layout.instructor_list_item,parent,false);

        return new InstructorAdapter.InstructorViewHolder(itemView);
    }
    @Override
    public void onBindViewHolder(@NonNull InstructorAdapter.InstructorViewHolder holder, int position) {
        if(mInstructors!=null){
            Instructor current = mInstructors.get(position);
            String firstName = current.getFirstName();
            String lastName = current.getLastName();
            holder.firstNameIV.setText(firstName);
            holder.lastNameIV.setText(lastName);
        }
        else {
            holder.firstNameIV.setText("no title");
            holder.lastNameIV.setText("no status");
        }
    }
    @Override
    public int getItemCount() { return mInstructors.size(); }
    public void setInstructors(List<Instructor> instructors){
        mInstructors = instructors;
        notifyDataSetChanged();
    }
}
