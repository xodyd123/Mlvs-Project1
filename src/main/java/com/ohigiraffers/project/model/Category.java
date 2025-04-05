package com.ohigiraffers.project.model;

public class Category {

    private long categoryId;

    private String text ;

    public Category(long categoryId, String text) {
        this.categoryId = categoryId;
        this.text = text;
    }

    public Category(String text) {
        this.text = text;
    }

    public long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(long categoryId) {
        this.categoryId = categoryId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
