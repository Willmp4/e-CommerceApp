package com.example.shopproject21514586.Product;
public class Product {
    private String name;
    private String price;
    private String description;
    private String imageUrl;
    private String category;
    private String brand;
    private String id;
    private String quantity;
    public Product() {
        // Default constructor required for calls to DataSnapshot.getValue(Product.class)
    }

    public Product(String name, String price, String description, String imageUrl, String category, String brand, String quantity, String id) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.imageUrl = imageUrl;
        this.category = category;
        this.brand = brand;
        this.quantity = quantity;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    //Get category
    public String getCategory() {
        return category;
    }
    // Set category
    public void setCategory(String category) {
        this.category = category;
    }

    public String getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getBrand() {
        return brand;
    }

    public String getQuantity() {
        return quantity;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
}

