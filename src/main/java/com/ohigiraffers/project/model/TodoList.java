package com.ohigiraffers.project.model;


import java.time.LocalDateTime;

public class TodoList {

    private long userId ;
    private String task ;
    private LocalDateTime localDateTime;
    private Status status ;
    private long categoryId;

    public TodoList(LocalDateTime localDateTime, Status status, long userId, String task , long categoryId) {
        this.localDateTime = localDateTime;
        this.status = status;
        this.userId = userId;
        this.task = task;
        this.categoryId = categoryId;
    }

    public long getCategoryId() {
        return categoryId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
