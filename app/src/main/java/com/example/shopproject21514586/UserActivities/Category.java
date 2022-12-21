package com.example.shopproject21514586.UserActivities;

public class Category {
    private String name;
    private String imageUrl;
    private String description;

    public Category() {
        // Default constructor required for calls to DataSnapshot.getValue(Category.class)
    }

    public Category(String name, String imageUrl, String description) {
        this.name = name;
        this.imageUrl = imageUrl;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

