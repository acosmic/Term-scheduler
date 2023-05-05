package com.example.c196_app.ui;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.recyclerview.widget.RecyclerView;

import com.example.c196_app.R;
import com.example.c196_app.entities.Term;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;




public class TermAdapter extends RecyclerView.Adapter<TermAdapter.TermViewHolder> {
    class TermViewHolder extends RecyclerView.ViewHolder {
        private final TextView termItemView;
        private TermViewHolder(View itemview){
            super(itemview);
            termItemView=itemview.findViewById(R.id.textView2);
            itemview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    final Term current = mTerms.get(position);
                    Intent intent = new Intent(context, TermDetails.class);
                    intent.putExtra("id", current.getID());
                    intent.putExtra("name", current.getName());
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
    private List<Term> mTerms;
    private final Context context;
    private final LayoutInflater mInflater;
    public TermAdapter(Context context){
        mInflater= LayoutInflater.from(context);
        this.context=context;
    }
    @NonNull
    @Override
    public TermAdapter.TermViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.term_list_item,parent, false);

        return new TermViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TermAdapter.TermViewHolder holder, int position) {
        if(mTerms!=null){
            Term current = mTerms.get(position);
            String name = current.getName();
            holder.termItemView.setText(name);
        }
        else{
            holder.termItemView.setText("No Term Name");
        }
    }

    @Override
    public int getItemCount() {
        return mTerms.size();
    }
    public void setTerms(List<Term> terms){
        mTerms = terms;
        notifyDataSetChanged();
    }

}
