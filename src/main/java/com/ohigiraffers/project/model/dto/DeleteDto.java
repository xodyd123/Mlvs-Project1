package com.ohigiraffers.project.model.dto;

public class DeleteDto {

    private long user_id;

    private long project_id;

    public DeleteDto(long user_id, long project_id) {
        this.user_id = user_id;
        this.project_id = project_id;
    }

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    public long getProject_id() {
        return project_id;
    }

    public void setProject_id(long project_id) {
        this.project_id = project_id;
    }
}
