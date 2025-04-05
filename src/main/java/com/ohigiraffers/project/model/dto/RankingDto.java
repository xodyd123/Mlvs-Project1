package com.ohigiraffers.project.model.dto;

import java.util.Comparator;

public class RankingDto  {

    private String name;

    private long total ;

    private long  completed ;

    private double successRate ;



    public void increaseCompleted() {
        this.completed ++ ;
    }

    public void increaseTotal() {
        this.total ++ ;
    }

    public void circulate(){
        if(total == 0){
            return ;
        }
        double successRate = ((double) completed / total) * 100;
        this.successRate = Math.round(successRate * 100.0) / 100.0;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getName() {
        return name;
    }

    public long getTotal() {
        return total;
    }

    public long getCompleted() {
        return completed;
    }

    public double getSuccessRate() {
        return successRate;
    }
}
