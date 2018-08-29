package com.example.bolinwang.tudar;


import java.util.ArrayList;
import java.util.List;


public class TrainerQuickAnswerListItem {
   // private String head;
    private Long TimeStamp;
    private String QuestionContent;
    private TrainerViewQuestionImageListItem Photos;
    /*public TrainerQuickAnswerListItem ( String QuestionContent, Long TimeStamp){
        this.QuestionContent = QuestionContent;
        this.TimeStamp = TimeStamp;
    }*/

    public String getQuestionContent(){
        return QuestionContent;
    }

    public Long getTimeStamp(){
        return TimeStamp;
    }

    public TrainerViewQuestionImageListItem getPhotos() {
        return Photos;
    }
}
