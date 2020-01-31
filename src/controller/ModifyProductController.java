/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import static model.ExceptionUtility.exceptionSet1;
import static model.ExceptionUtility.exceptionSet2;
import model.InHouse;
import model.Inventory;
import model.Outsourced;
import model.Part;
import model.Product;

/**
 * FXML Controller class
 *
 * @author markwartman
 */
public class ModifyProductController implements Initializable {
    
    Stage stage;
    Parent scene;
    
    /**
     * tempParts: local instance that holds objects of lower table 
     * that adds or removes objects of associatedParts. 
     */ 
    ObservableList<Part> tempParts = FXCollections.observableArrayList();

    @FXML
    private TextField modProd_id;
    @FXML
    private TextField modProdName;
    @FXML
    private TextField modProdQty;
    @FXML
    private TextField modProdPrice;
    @FXML
    private TextField modProdMax;
    @FXML
    private TextField modProdMin;
    @FXML
    private TextField modProdSearchTxt;
    @FXML
    private TableView<Part> partInv;
    @FXML
    private TableColumn<Part, Integer> mp_id_partInventory;
    @FXML
    private TableColumn<Part, String> mp_name_partInventory;
    @FXML
    private TableColumn<Part, Integer> mp_count_partInventory;
    @FXML
    private TableColumn<Part, Double> mp_price_partInventory;
    @FXML
    private TableView<Part> assicProdPartInv; 
    @FXML
    private TableColumn<Part, Integer> mp_id_assProdPart;
    @FXML
    private TableColumn<Part, String> mp_name_assProdPart;
    @FXML
    private TableColumn<Part, Integer> mp_count_assProdPart;
    @FXML
    private TableColumn<Part, Double> mp_price_assProdPart;
    
    
    public void sendProduct(Product product)
    {
        //populate textfields on left
        modProd_id.setText(String.valueOf(product.getId()));
        modProdName.setText(product.getName());
        modProdQty.setText(String.valueOf(product.getStock()));
        modProdPrice.setText(String.valueOf(product.getPrice()));
        modProdMax.setText(String.valueOf(product.getMax()));
        modProdMin.setText(String.valueOf(product.getMin()));
        
        //LOAD tempParts that will later load: bottom table
        for(Part part : product.getAllAssociatedParts())
        {
            tempParts.add(part);
        }
        
    }
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        //populate top table's data
        partInv.setItems(Inventory.getAllParts());
        
        mp_id_partInventory.setCellValueFactory(new PropertyValueFactory<>("id"));
        mp_name_partInventory.setCellValueFactory(new PropertyValueFactory<>("name"));
        mp_count_partInventory.setCellValueFactory(new PropertyValueFactory<>("stock"));
        mp_price_partInventory.setCellValueFactory(new PropertyValueFactory<>("price"));
        
        //populate bottom table
        assicProdPartInv.setItems(tempParts);
        
        mp_id_assProdPart.setCellValueFactory(new PropertyValueFactory<>("id"));
        mp_name_assProdPart.setCellValueFactory(new PropertyValueFactory<>("name"));
        mp_count_assProdPart.setCellValueFactory(new PropertyValueFactory<>("stock"));
        mp_price_assProdPart.setCellValueFactory(new PropertyValueFactory<>("price"));
        
    }    

    @FXML
    private void modProdCancel(ActionEvent event) throws IOException {
        
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
        
    }
    
    /*
    * Decipher if passed an Integer then pass an Integer,
    * or assume letters are passed then pass a Sting to search method. 
    */
    @FXML
    private void modProdSearch(ActionEvent event) {
        
        String nums = "[0-9]+";
        if(modProdSearchTxt.getText().matches(nums))
        partInv.getSelectionModel().select(Inventory.lookupPart(Integer.parseInt(modProdSearchTxt.getText())));
        else
        partInv.setItems(Inventory.lookupPart(modProdSearchTxt.getText()));
    }

    @FXML
    private void modProd_InvToProd_partAdd(ActionEvent event) {
        
        tempParts.add(partInv.getSelectionModel().getSelectedItem());
    }
    
    @FXML
    private void modProd_ProdPart_partDelete(ActionEvent event) {
        
        tempParts.remove(assicProdPartInv.getSelectionModel().getSelectedItem());
        
    }
    
    /**
     * 
     * Pass user input to ExceptionUtility, 
     * catch exceptions, instantiate, and navigate back to Main.
     * 
     */
    @FXML
    private void modProdSave(ActionEvent event) throws IOException {
        
        
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Validate Fields: All fields must be filled out");
        alert.setHeaderText(null);
        
        int errors = 2;
        int id;
        String name;
        int stock;
        double price;
        int max;
        int min;
        
        try
        {
            boolean eSet2;
            eSet2 = exceptionSet2(modProdName.getText(), modProdPrice.getText(), modProdQty.getText());
            
            if(eSet2)
            {
                errors--;
                
                id = Integer.parseInt(modProd_id.getText());
                name = modProdName.getText();
                stock = Integer.parseInt(modProdQty.getText());
                price = Double.parseDouble(modProdPrice.getText());
                max = Integer.parseInt(modProdMax.getText());
                min = Integer.parseInt(modProdMin.getText());
                
                boolean eSet1;
                eSet1 = exceptionSet1( stock, max, min);
                if(eSet1)
                {
                    errors--;
                }
                
                //IF RUBRIC EXCEPTIONS CHECK-OUT TRUE, INSTANTIATE & NAVIGATE
                if(errors == 0)
                {
                    //instantiate tempParts
                    Product updteProd = new Product(id, name, price, stock, max, min, tempParts);

                    int index = -1;
                    for(Product prod : Inventory.getAllProducts())
                    {
                        index++;
                        if(prod.getId() == Integer.parseInt(modProd_id.getText()))
                        {
                            Inventory.updateProduct(index, updteProd);
                        }
                    }

                    stage = (Stage)((Button)event.getSource()).getScene().getWindow();
                    scene = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
                    stage.setScene(new Scene(scene));
                    stage.show();
                }
            }
                
        }
        catch(NumberFormatException E)
        {
            alert.setContentText("Error: NumberFormatException\n Fill in all fields\n" + E.getMessage());
            alert.showAndWait();
        }
        
        
    }

    
    
}
