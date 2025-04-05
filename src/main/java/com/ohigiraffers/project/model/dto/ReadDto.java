package com.ohigiraffers.project.model.dto;

public class ReadDto {

    private long taskId;

    private long userId ;

    private String contents ;

    private String categoryContets ;

    public ReadDto(long taskId, long userId, String contents) {
        this.taskId = taskId;
        this.userId = userId;
        this.contents = contents;
    }

    public ReadDto(long taskId, long userId, String contents, String categoryContets) {
        this.taskId = taskId;
        this.userId = userId;
        this.contents = contents;
        this.categoryContets = categoryContets;
    }

    public ReadDto(String categoryContets, String contents) {
        this.categoryContets = categoryContets;
        this.contents = contents;
    }

    public long getTaskId() {
        return taskId;
    }

    public long getUserId() {
        return userId;
    }

    public String getContents() {
        return contents;
    }

    public String getCategoryContets() {
        return categoryContets;
    }
}

