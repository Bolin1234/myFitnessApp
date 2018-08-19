package com.example.bolinwang.tudar;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class TrainerQuickAnswerAdapter extends RecyclerView.Adapter<TrainerQuickAnswerAdapter.ViewHolder> {

    @NonNull
    private List<TrainerQuickAnswerListItem> ListItems;
    private Context context;

    public TrainerQuickAnswerAdapter(List<TrainerQuickAnswerListItem> listItems, Context context) {
        ListItems = listItems;
        this.context = context;
    }
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_trainer_quick_answer_list_item,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TrainerQuickAnswerListItem listItem = ListItems.get(position);
        holder.textViewHead.setText(listItem.getHead());
        holder.textViewDesc.setText(listItem.getDesc());
        holder.textViewTime.setText(listItem.getTime());

    }

    @Override
    public int getItemCount() {
        return ListItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView textViewHead;
        public TextView textViewDesc;
        public TextView textViewTime;

        public ViewHolder(View itemView) {
            super(itemView);
            textViewHead = (TextView) itemView.findViewById(R.id.textViewHead);
            textViewDesc = (TextView) itemView.findViewById(R.id.textViewDescription);
            textViewTime = (TextView) itemView.findViewById(R.id.textViewTime);


        }
    }
}
