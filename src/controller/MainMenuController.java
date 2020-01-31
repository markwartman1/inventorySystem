/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Inventory;
import model.Part;
import model.Product;

/**
 *
 * @author markwartman
 */
public class MainMenuController implements Initializable {
    
    Stage stage;
    Parent scene;
    
    @FXML
    private TextField partSearchTxt;
    @FXML
    private TableColumn<Part, Integer> part_id;
    @FXML
    private TableColumn<Part, String> partName;
    @FXML
    private TableColumn<Part, Integer> partCount;
    @FXML
    private TableColumn<Part, Double> partPrice;
    @FXML
    private TextField prodSearchTxt;
    @FXML
    private TableColumn<Product, Integer> prod_id;
    @FXML
    private TableColumn<Product, String> prodName;
    @FXML
    private TableColumn<Product, Integer> prodCount;
    @FXML
    private TableColumn<Product, Double> prodPrice;
    @FXML
    private TableView<Part> partTableView;
    @FXML
    private TableView<Product> prodTableView;
    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        // POPULATE PARTS TABLE
        partTableView.setItems(Inventory.getAllParts());
        
        part_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        partName.setCellValueFactory(new PropertyValueFactory<>("name"));
        partCount.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        
        // POPULATE PRODUCTS TABLE
        prodTableView.setItems(Inventory.getAllProducts());
        
        prod_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        prodName.setCellValueFactory(new PropertyValueFactory<>("name"));
        prodCount.setCellValueFactory(new PropertyValueFactory<>("stock"));
        prodPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        
    }    
    
    /*
    * Decipher if passed an Integer then pass an Integer,
    * or assume letters are passed then pass a Sting to search method. 
    */
    @FXML
    private void partSearch(ActionEvent event) {
        
        String nums = "[0-9]+";
        if(partSearchTxt.getText().matches(nums))
        partTableView.getSelectionModel().select(Inventory.lookupPart(Integer.parseInt(partSearchTxt.getText())));
        else
        partTableView.setItems(Inventory.lookupPart(partSearchTxt.getText()));
        
    }

    @FXML
    private void partAdd(ActionEvent event) throws IOException {
        
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/AddPart.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
        
    }
    
    // PASS SELECTED PART & navigate TO ModifyProductController
    @FXML
    private void partModify(ActionEvent event) throws IOException {
        
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/ModifyPart.fxml"));
        loader.load();
        
        ModifyPartController mpc = loader.getController();
        mpc.sendPart(partTableView.getSelectionModel().getSelectedItem());
        
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        Parent scene = loader.getRoot();
        stage.setScene(new Scene(scene));
        stage.show();
        
    }

    @FXML
    private void partDelete(ActionEvent event) {
        
        Inventory.deletePart(partTableView.getSelectionModel().getSelectedItem());
        
    }

    /*
    * Decipher if passed an Integer then pass an Integer,
    * or assume letters are passed then pass a Sting to search method. 
    */
    @FXML
    private void prodSearch(ActionEvent event) {
        
        String nums = "[0-9]+";
        if(prodSearchTxt.getText().matches(nums))
        {
            //SEARCH BY ID : PASS AN INTEGER
        prodTableView.getSelectionModel().select(Inventory.lookupProduct(Integer.parseInt(prodSearchTxt.getText())));
        }
        else
        {
            //SEARCH BY NAME : PASS A STRING
            prodTableView.setItems(Inventory.lookupProduct(prodSearchTxt.getText()));
        
        }
        
    }

    @FXML
    private void prodAdd(ActionEvent event) throws IOException {
        
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/AddProduct.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
        
    }
    
    // PASS SELECTED PART & navigate TO ModifyPartController
    @FXML
    private void prodModify(ActionEvent event) throws IOException {
        
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/ModifyProduct.fxml"));
        loader.load();
        
        ModifyProductController mpc = loader.getController();
        mpc.sendProduct(prodTableView.getSelectionModel().getSelectedItem());
        
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        Parent scene = loader.getRoot();
        stage.setScene(new Scene(scene));
        stage.show();
        
    }

    @FXML
    private void prodDelete(ActionEvent event) {
        
        Inventory.deleteProduct(prodTableView.getSelectionModel().getSelectedItem());
    }

    @FXML
    private void mainExit(ActionEvent event) {
        
        System.exit(0);
        
    }
    
}
