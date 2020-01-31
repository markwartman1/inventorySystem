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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import static model.ExceptionUtility.exceptionSet1;
import static model.ExceptionUtility.exceptionSet2;
import model.InHouse;
import model.Inventory;
import model.Outsourced;

/**
 * FXML Controller class
 *
 * @author markwartman
 */
public class AddPartController implements Initializable {
    
    Stage stage;
    Parent scene;

    @FXML
    private TextField addPart_id;
    @FXML
    private TextField addPartName;
    @FXML
    private TextField addPartQty;
    @FXML
    private TextField addPartPrice;
    @FXML
    private TextField addPartMax;
    @FXML
    private TextField addPartMin;
    @FXML
    private TextField addPartMachine_id;
    @FXML
    private ToggleGroup addPartSourceTG;
    @FXML
    private RadioButton addPartSrc_in;
    @FXML
    private RadioButton addPartSrc_out;
    @FXML
    private Label srcLabel;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        // DEFAULT RADIO SELECTION
        addPartSourceTG.selectToggle(addPartSrc_in);
    }    
    
    
    @FXML
    private void addPartCancel(ActionEvent event) throws IOException {
        
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
        
    }
    
    @FXML
    void radioChange(ActionEvent event){
        
        if(addPartSourceTG.getSelectedToggle().equals(addPartSrc_in))
            srcLabel.setText("Machine ID:");
        
        if(addPartSourceTG.getSelectedToggle().equals(addPartSrc_out))
            srcLabel.setText("Co. Name:");
        
    }
    
    @FXML
    private void addPartSave(ActionEvent event) throws IOException {
        
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
            eSet2 = exceptionSet2(addPartName.getText(), addPartPrice.getText(), addPartQty.getText());
            
            if(eSet2)
            {
                errors--;
                
                id = Integer.parseInt(addPart_id.getText());
                name = addPartName.getText();
                stock = Integer.parseInt(addPartQty.getText());
                price = Double.parseDouble(addPartPrice.getText());
                max = Integer.parseInt(addPartMax.getText());
                min = Integer.parseInt(addPartMin.getText());
                
                boolean eSet1;
                eSet1 = exceptionSet1( stock, max, min);
                if(eSet1)
                {
                    errors--;
                }
                
                //IF RUBRIC EXCEPTIONS CHECK-OUT TRUE, INSTANTIATE & NAVIGATE
                if(errors == 0)
                {
                    if(addPartSrc_in.isSelected())
                    {
                        int machineID = Integer.parseInt(addPartMachine_id.getText());
                        Inventory.addPart(new InHouse(id, name, price, stock, max, min, machineID));
                    }
                    else if(addPartSrc_out.isSelected())
                    {
                        String companyName = addPartMachine_id.getText();
                        Inventory.addPart(new Outsourced(id, name, price, stock, max, min, companyName));
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
