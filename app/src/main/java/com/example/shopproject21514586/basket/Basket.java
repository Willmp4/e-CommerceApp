package com.example.shopproject21514586.basket;

import static okhttp3.internal.Internal.instance;

import com.example.shopproject21514586.Product.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public class Basket extends Observable {
    private static Basket instance;
    private List<Product> products;

    public Basket() {
        products = new ArrayList<>();
    }

    public static Basket getInstance() {
        if (instance == null) {
            instance = new Basket();
        }
        return instance;
    }

    public void addProduct(Product product) {
        products.add(product);
        // Set the changed flag to true to notify observers
        setChanged();
    }

    public void removeProduct(Product product) {
        products.remove(product);
        // Set the changed flag to true to notify observers
        setChanged();
    }

    public void clear() {
        products.clear();
        // Set the changed flag to true to notify observers
        setChanged();
    }

    public List<Product> getProducts() {
        return products;
    }

    public double getTotal() {
        double total = 0;
        for (Product product : products) {
            total += product.getPrice() * product.getCount();
        }
        return total;
    }

    //Check if product is in basket
    public boolean contains(Product product) {
       //Using a for loop
         for (Product p : products) {
              if (p.getId() == product.getId()) {
                return true;
              }
         }
            return false;
    }
}
