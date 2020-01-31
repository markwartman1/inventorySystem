/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mw_inventory_system_fpc482;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.InHouse;
import model.Inventory;
import model.Outsourced;
import model.Part;
import model.Product;

/**
 *
 * @author markwartman
 */
public class MW_Inventory_System_fpC482 extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        ObservableList<Part> tempParts = FXCollections.observableArrayList();
        
        //PRODUCTS: instantiate data
        Product prod1 = new Product(1, "product 1", 1.99, 4, 100, 1, tempParts);
        Product prod2 = new Product(2, "product 2", 2.99, 8, 100, 1, tempParts);
        Product prod3 = new Product(3, "product 3", 3.99, 12, 100, 1, tempParts);
        Product prod4 = new Product(4, "product 4", 4.99, 16, 100, 1, tempParts);
        
        Inventory.addProduct(prod1);
        Inventory.addProduct(prod2);
        Inventory.addProduct(prod3);
        Inventory.addProduct(prod4);
        
        //PARTS: InHouse type: instantiate data
        InHouse part1 = new InHouse(1, "inHsePart 1", 1.99, 4, 100, 1, 4321);
        InHouse part2 = new InHouse(2, "inHsePart 2", 2.99, 8, 100, 1, 4321);
        
        Inventory.addPart(part1);
        Inventory.addPart(part2);
        
        //PARTS: Outsourced type: instantiate data
        Outsourced partOut1 = new Outsourced(3, "outPart 3", 1.99, 4, 100, 1, "Shimano");
        Outsourced partOut2 = new Outsourced(4, "outPart 4", 2.99, 4, 100, 1, "Shimano");
        
        Inventory.addPart(partOut1);
        Inventory.addPart(partOut2);
        
        launch(args);
    }
    
}
