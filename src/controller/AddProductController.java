/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import javax.swing.JOptionPane;
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
import javafx.scene.control.Alert.AlertType;
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
public class AddProductController implements Initializable {
    
    Stage stage;
    Parent scene;
    
    ObservableList<Part> tempParts = FXCollections.observableArrayList();
    ObservableList<Part> empty = FXCollections.observableArrayList();

    @FXML
    private TextField addProd_id;
    @FXML
    private TextField addProdName;
    @FXML
    private TextField addProdQty;
    @FXML
    private TextField addProdPrice;
    @FXML
    private TextField addProdMax;
    @FXML
    private TextField addProdMin;
    @FXML
    private TextField addProdSearchTxt;
    @FXML
    private TableView<Part> ap_partInv_tableView;
    @FXML
    private TableColumn<Part, Integer> ap_id_partInventory;
    @FXML
    private TableColumn<Part, String> ap_name_partInventory;
    @FXML
    private TableColumn<Part, Integer> ap_count_partInventory;
    @FXML
    private TableColumn<Part, Double> ap_price_partInventory;
    @FXML
    private TableView<Part> assocTableView; 
    @FXML
    private TableColumn<Part, Integer> ap_id_assProdPart;
    @FXML
    private TableColumn<Part, String> ap_name_assProdPart;
    @FXML
    private TableColumn<Part, Integer> ap_count_assProdPart;
    @FXML
    private TableColumn<Part, Double> ap_price_assProdPart;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        //top table
        ap_partInv_tableView.setItems(Inventory.getAllParts());
        
        ap_id_partInventory.setCellValueFactory(new PropertyValueFactory<>("id"));
        ap_name_partInventory.setCellValueFactory(new PropertyValueFactory<>("name"));
        ap_count_partInventory.setCellValueFactory(new PropertyValueFactory<>("stock"));
        ap_price_partInventory.setCellValueFactory(new PropertyValueFactory<>("price"));
        
        //bottom table
        assocTableView.setItems(tempParts);
        
        ap_id_assProdPart.setCellValueFactory(new PropertyValueFactory<>("id"));
        ap_name_assProdPart.setCellValueFactory(new PropertyValueFactory<>("name"));
        ap_count_assProdPart.setCellValueFactory(new PropertyValueFactory<>("stock"));
        ap_price_assProdPart.setCellValueFactory(new PropertyValueFactory<>("price"));
        
    }    

    @FXML
    private void addProdCancel(ActionEvent event) throws IOException {
        
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
    private void addProductSearch(ActionEvent event) {
        
        String nums = "[0-9]+";
        if(addProdSearchTxt.getText().matches(nums))
        ap_partInv_tableView.getSelectionModel().select(Inventory.lookupPart(Integer.parseInt(addProdSearchTxt.getText())));
        else
        ap_partInv_tableView.setItems(Inventory.lookupPart(addProdSearchTxt.getText()));
    }

    @FXML
    private void addProd_InvToProd_partAdd(ActionEvent event) {
        
        tempParts.add(ap_partInv_tableView.getSelectionModel().getSelectedItem()); 
    }
    
    @FXML
    private void addProd_ProdPart_partDelete(ActionEvent event) {
        
        tempParts.remove(assocTableView.getSelectionModel().getSelectedItem());
    }
    
    /**
     * 
     * Pass user input to ExceptionUtility, 
     * catch exceptions, instantiate, and navigate back to Main.
     * 
     */
    @FXML
    private void addProdSave(ActionEvent event) throws IOException {
        
        
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
            eSet2 = exceptionSet2(addProdName.getText(), addProdPrice.getText(), addProdQty.getText());
            
            if(eSet2)
            {
                errors--;
                
                id = Integer.parseInt(addProd_id.getText());
                name = addProdName.getText();
                stock = Integer.parseInt(addProdQty.getText());
                price = Double.parseDouble(addProdPrice.getText());
                max = Integer.parseInt(addProdMax.getText());
                min = Integer.parseInt(addProdMin.getText());
                
                boolean eSet1;
                eSet1 = exceptionSet1( stock, max, min);
                if(eSet1)
                {
                    errors--;
                }
                
                //IF RUBRIC EXCEPTIONS CHECK-OUT TRUE, INSTANTIATE & NAVIGATE
                if(errors == 0)
                {
                    
                    Product prod = new Product(id, name, price, stock, max, min, empty);
                    Inventory.addProduct(prod);

                    stage = (Stage)((Button)event.getSource()).getScene().getWindow();
                    scene = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
                    stage.setScene(new Scene(scene));
                    stage.show();
                    
                    for(Part part : tempParts)
                    {
                        prod.addAssociatedPart(part);
                        
                    }
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
