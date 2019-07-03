package com.example.androidtest.model;

import com.google.gson.annotations.SerializedName;

public class Rows {
    @SerializedName("title")
    private String title;
    @SerializedName("description")
    private String description;
    @SerializedName("imageHref")
    private String imageHref;

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return this.title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return this.description;
    }

    public void setImageHref(String imageHref) {
        this.imageHref = imageHref;
    }

    public String getImageHref() {
        return this.imageHref;
    }
}
