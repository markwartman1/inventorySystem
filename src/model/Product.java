/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author markwartman
 */
public class Product {
    
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    
    private int id;
    private String name;
    private double price;
    private int stock;
    private int max;
    private int min;

    public Product(int id, String name, double price, int stock, int max, int min, ObservableList<Part> associatedParts) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.max = max;
        this.min = min;
        this.associatedParts = associatedParts;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }
    
    public void addAssociatedPart(Part part){
        associatedParts.add(part);
    }
    
    public void deleteAssociatedPart(Part part){
        associatedParts.remove(part);
    }
    
    public ObservableList<Part> getAllAssociatedParts(){
        return associatedParts;
    }
    
}
