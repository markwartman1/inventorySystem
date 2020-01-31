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
public class Inventory {
    
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();
    
    private static ObservableList<Product> filteredProducts = FXCollections.observableArrayList();
    private static ObservableList<Part> filteredParts = FXCollections.observableArrayList();
    
    public static void addPart(Part part){
        allParts.add(part);
    }
    
    public static void addProduct(Product product){
        allProducts.add(product);
    }
    
    public static Part lookupPart(int partId){
        
        for(Part part : Inventory.getAllParts())
        {
            if(part.getId() == partId)
            {
                return part;
            }
        }
        return null;
    }
    
    public static Product lookupProduct(int productId){
        
        for(Product product : Inventory.getAllProducts())
        {
            if(product.getId() == productId)
            {
                return product;
            }
        }
        return null;     
    }
    
    public static ObservableList<Part> lookupPart(String partName){
        
        if(!(Inventory.filteredParts.isEmpty()))
            Inventory.filteredParts.clear();
        
        for(Part part : Inventory.getAllParts())
        {
            if(part.getName().contains(partName))
                Inventory.filteredParts.add(part);
        }
        return Inventory.filteredParts;
    }
    
    public static ObservableList<Product> lookupProduct(String productName){
        
        if(!(Inventory.filteredProducts.isEmpty()))
            Inventory.filteredProducts.clear();
        
        for(Product prod : Inventory.getAllProducts())
        {
            if(prod.getName().contains(productName))
                Inventory.filteredProducts.add(prod);
        }
        return Inventory.filteredProducts;
    }
    
    public static void updatePart(int index, Part part){
        Inventory.getAllParts().set(index, part);
    }
    
    public static void updateProduct(int index, Product product){
        Inventory.getAllProducts().set(index, product); 
    }
    
    public static void deletePart(Part part){
        allParts.remove(part);
    }
    
    public static void deleteProduct(Product product){
        allProducts.remove(product);
    }
    
    public static ObservableList<Part> getAllParts(){
        return allParts;
    }
    
    public static ObservableList<Product> getAllProducts(){
        return allProducts;
    }
    
    
}
