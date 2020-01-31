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
import model.Part;
import model.Product;

/**
 * FXML Controller class
 *
 * @author markwartman
 */
public class ModifyPartController implements Initializable {
    
    Stage stage;
    Parent scene;

    @FXML
    private TextField modPart_id;
    @FXML
    private TextField modPartName;
    @FXML
    private TextField modPartQty;
    @FXML
    private TextField modPartPrice;
    @FXML
    private TextField modPartMax;
    @FXML
    private TextField modPartMin;
    @FXML
    private TextField modPartMachine_id;
    @FXML
    private ToggleGroup modPartSourceTG;
    @FXML
    private RadioButton modPartSrc_in;
    @FXML
    private RadioButton modPartSrc_out;
    @FXML
    private Label srcLabel;
    
    // POPULATE TEXT FIELDS
    public void sendPart(Part part)
    {
        modPart_id.setText(String.valueOf(part.getId()));
        modPartName.setText(part.getName());
        modPartQty.setText(String.valueOf(part.getStock()));
        modPartPrice.setText(String.valueOf(part.getPrice()));
        modPartMax.setText(String.valueOf(part.getMax()));
        modPartMin.setText(String.valueOf(part.getMin()));
        
        // RADIO SELECTION
        // Whether modPartMachine_id is a String or Integer would be dependent on:
        // InHouse or Outsourced subclass instantiation...
        // this essentially detects object-type and selects correlating 
        // radio button.
        if(part instanceof InHouse)
        {
            modPartMachine_id.setText(String.valueOf(((InHouse) part).getMachineId()));
            modPartSourceTG.selectToggle(modPartSrc_in);
        }
        else if(part instanceof Outsourced)
        {
            modPartMachine_id.setText(((Outsourced) part).getCompanyName());
            modPartSourceTG.selectToggle(modPartSrc_out);
            srcLabel.setText("Co. Name:");
        }
        
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

      

    @FXML
    private void modPartCancel(ActionEvent event) throws IOException {
        
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
        
    }
    
    @FXML
    void radioChange(ActionEvent event){
        
        if(modPartSourceTG.getSelectedToggle().equals(modPartSrc_in))
            srcLabel.setText("Machine ID:");
        
        if(modPartSourceTG.getSelectedToggle().equals(modPartSrc_out))
            srcLabel.setText("Co. Name:");
        
    }
    
    /**
     * 
     * Pass user input to ExceptionUtility, 
     * catch exceptions, instantiate, and navigate back to Main.
     * 
     */
    @FXML
    private void modPartSave(ActionEvent event) throws IOException {
        
        
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
            eSet2 = exceptionSet2(modPartName.getText(), modPartPrice.getText(), modPartQty.getText());
            
            if(eSet2)
            {
                errors--;
                
                id = Integer.parseInt(modPart_id.getText());
                name = modPartName.getText();
                stock = Integer.parseInt(modPartQty.getText());
                price = Double.parseDouble(modPartPrice.getText());
                max = Integer.parseInt(modPartMax.getText());
                min = Integer.parseInt(modPartMin.getText());
                
                boolean eSet1;
                eSet1 = exceptionSet1( stock, max, min);
                if(eSet1)
                {
                    errors--;
                }
                
                //IF RUBRIC EXCEPTIONS CHECK-OUT TRUE, INSTANTIATE & NAVIGATE
                if(errors == 0)
                {
                    int index = -1;
                    for(Part part : Inventory.getAllParts())
                    {
                        index++;
                        if(part.getId() == id)
                        {
                            if(modPartSrc_in.isSelected())
                                {
                                    int machineID = Integer.parseInt(modPartMachine_id.getText());
                                    InHouse updtePart = new InHouse(id, name, price, stock, max, min, machineID);
                                    Inventory.updatePart(index, updtePart);
                                }
                            else if(modPartSrc_out.isSelected())
                                {
                                    String companyName = modPartMachine_id.getText();
                                    Outsourced updtePart = new Outsourced(id, name, price, stock, max, min, companyName);
                                    Inventory.updatePart(index, updtePart);
                                }
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
