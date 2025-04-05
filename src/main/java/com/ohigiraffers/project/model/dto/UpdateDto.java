package com.ohigiraffers.project.model.dto;

import com.ohigiraffers.project.model.Status;

import java.time.LocalDateTime;

public class UpdateDto {

    private long task_id;

    private String text ;

    private LocalDateTime updated_at; ;

    private Status status ;


    public UpdateDto(long task_id, String text, LocalDateTime updated_at) {
        this.task_id = task_id;
        this.text = text;
        this.updated_at = updated_at;
    }

    public UpdateDto(long task_id, LocalDateTime updated_at, Status status) {
        this.task_id = task_id;
        this.updated_at = updated_at;
        this.status = status;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public LocalDateTime getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(LocalDateTime updated_at) {
        this.updated_at = updated_at;
    }


    public long getTask_id() {
        return task_id;
    }

    public Status getStatus() {
        return status;
    }


}
