package com.example.shopproject21514586.users;

import com.example.shopproject21514586.Product.Product;

import java.util.ArrayList;
import java.util.List;

//class
public class Users {
    public String firstName;
    public String lastName;
    public String email;
    public String UID;
    public String password;
    public String imageUrl;
    public List<Product> cart;


    public Users() {
        //empty constructor
    }
    public Users(String UID, String firstName, String lastName, String email, String password, String imageUrl) {
        this.UID = UID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.imageUrl = imageUrl;
        this.cart = new ArrayList<Product>();
    }

    public void addToCart(Product product) {
        this.cart.add(product);
    }

    public void removeFromCart(Product product) {
        this.cart.remove(product);
    }

    public List<Product> viewCart() {
        return this.cart;
    }

    public void checkOut() {
        // code to process payment and clear cart goes here
        this.cart.clear();
    }
}
