package com.example.bolinwang.tudar;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

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
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        final TrainerQuickAnswerListItem listItem = ListItems.get(position);
        holder.textViewQuestionContent.setText(listItem.getQuestionContent());
        holder.textViewTimeStamp.setText(getDateCurrentTimeZone(listItem.getTimeStamp()));

        holder.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // Toast.makeText(context, "You clicked"+listItem.getQuestionContent(),Toast.LENGTH_LONG).show();
                //check if the question still exists.
                /*if(ListItems.contains(listItem)){
                    Toast.makeText(context, "You clicked"+listItem.getQuestionContent(),Toast.LENGTH_LONG).show();
                }else{
                    //user deleted: have problem!
                    Toast.makeText(context, "Question Deleted",Toast.LENGTH_LONG).show();
                    //if (ListItems != null && ListItems.size() > 0) {
                        ListItems.clear();
                        ListItems.add(listItem);
                        notifyDataSetChanged();
                    //}
                }*/
                Intent intent = new Intent(context, TrainerAnswerQuestionActivity.class);
                //TrainerQuickAnswerListItem trainerQuickAnswerListItem = listItem.getPhotos();
                intent.putExtra("Photo1Location", listItem.getPhotos().getPhoto1Location());
                intent.putExtra("Photo2Location", listItem.getPhotos().getPhoto2Location());
                intent.putExtra("Photo3Location", listItem.getPhotos().getPhoto3Location());
                intent.putExtra("QuestionContent", listItem.getQuestionContent());
                intent.putExtra("TimeStamp", listItem.getTimeStamp().toString());
                context.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return ListItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView textViewQuestionContent;
        public TextView textViewTimeStamp;
        public ConstraintLayout constraintLayout;


        public ViewHolder(View itemView) {
            super(itemView);
            textViewQuestionContent = (TextView) itemView.findViewById(R.id.textViewQuestionContent);
            textViewTimeStamp = (TextView) itemView.findViewById(R.id.textViewTimeStamp);
            constraintLayout = (ConstraintLayout) itemView.findViewById(R.id.constraintLayout);


        }
    }
    //needs to calculate in a better way later
    private  String getDateCurrentTimeZone(long timestamp) {
        try{
            Calendar calendar = Calendar.getInstance();
            TimeZone tz = TimeZone.getTimeZone("Canada/Pacific");
            calendar.setTimeInMillis(timestamp * 1000);
            long timeDiff = (System.currentTimeMillis()/1000 - timestamp)/ 3600;
            calendar.add(Calendar.MILLISECOND, tz.getOffset(calendar.getTimeInMillis()));
            if(timeDiff >= 24){
                SimpleDateFormat sdf = new SimpleDateFormat("MM-dd");
                Date currenTimeZone = (Date) calendar.getTime();
                return sdf.format(currenTimeZone);
            }else if(timeDiff >= 8760){
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Date currenTimeZone = (Date) calendar.getTime();
                return sdf.format(currenTimeZone);
            }
            else {
                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
                Date currenTimeZone = (Date) calendar.getTime();
                return sdf.format(currenTimeZone);
            }


        }catch (Exception e) {
        }
        return "";
    }
}
