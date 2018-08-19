package com.example.bolinwang.tudar;

public class TrainerQuickAnswerListItem {
    private String head;
    private String time;
    private String desc;

    public TrainerQuickAnswerListItem (String head, String time, String desc){
        this.head = head;
        this.desc = desc;
        this.time = time;
    }
    public String getHead(){
        return head;
    }

    public String getDesc(){
        return desc;
    }

    public String getTime(){
        return time;
    }


}
