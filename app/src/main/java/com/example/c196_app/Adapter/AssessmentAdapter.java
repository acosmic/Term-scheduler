package com.example.c196_app.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.c196_app.R;
import com.example.c196_app.entities.Assessment;
import com.example.c196_app.ui.AssessmentDetails;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class AssessmentAdapter extends RecyclerView.Adapter<AssessmentAdapter.AssessmentViewHolder> {
    class AssessmentViewHolder extends RecyclerView.ViewHolder {
        private final TextView assessmentNameItemView;
        private final TextView assessmentTypeItemView;
        public AssessmentViewHolder(@NonNull View itemView) {
            super(itemView);
            assessmentNameItemView = itemView.findViewById(R.id.textViewAssessmentName);
            assessmentTypeItemView = itemView.findViewById(R.id.textViewAssessmentDate);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    final Assessment current = mAssessments.get(position);
                    Intent intent = new Intent(context, AssessmentDetails.class);
                    intent.putExtra("id", current.getID());
                    intent.putExtra("title", current.getTitle());
                    intent.putExtra("type", current.getType());
                    intent.putExtra("courseID", current.getCourseID());
                    SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yy", Locale.US);
                    String assessmentDate;
                    if (current.getDate() == null){
                        assessmentDate = "";
                    }
                    else {
                        assessmentDate = sdf.format(current.getDate());
                    }
                    intent.putExtra("date", assessmentDate);
                    context.startActivity(intent);
                }
            });
        }
    }
    private List<Assessment> mAssessments;
    private final Context context;
    private final LayoutInflater mInflater;
    public AssessmentAdapter(Context context){
        mInflater = LayoutInflater.from(context);
        this.context=context;
    }
    @NonNull
    @Override
    public AssessmentAdapter.AssessmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View itemView = mInflater.inflate(R.layout.assessment_list_item, parent, false);

        return new AssessmentViewHolder(itemView);
    }
    @Override
    public void onBindViewHolder(@NonNull AssessmentAdapter.AssessmentViewHolder holder, int position){
        if (mAssessments!=null){
            Assessment current = mAssessments.get(position);
            String title = current.getTitle();
            String type = current.getType();
            holder.assessmentNameItemView.setText(title);
            holder.assessmentTypeItemView.setText(type);

        }
        else {
            holder.assessmentNameItemView.setText("No Title");
            holder.assessmentTypeItemView.setText("No Type");
        }
    }
    @Override
    public int getItemCount() {return mAssessments.size();}
    public void setAssessments(List<Assessment> assessments){
        mAssessments = assessments;
        notifyDataSetChanged();
    }
}
