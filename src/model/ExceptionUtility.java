/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import javafx.scene.control.Alert;

/**
 *
 * @author markwartman
 * 
 * This class handles the Rubric Exception checking requirements for the
 * four Controllers that handle user input.
 */
public class ExceptionUtility {
    
    /**
     *
     * Exceptions set 2 of the Rubric requests that these entities below and 'category'
     * which is essentially the InHouse or Outsourced radio button selection
     * be handled to ensure input is present. 
     *
     */
    public static boolean exceptionSet2(String name, String price, String inv){
        
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Validate Fields: All fields must be filled out");
        alert.setHeaderText(null);
        int errors = 3;
        
        try
        {
            String nums = "[0-9]+";
            if(name != null && name.length() > 0 && !name.matches(nums))
            errors--;
            else
            {
                alert.setContentText("Error in Name field\n Can not be only numbers\n\n Please enter letters in the Name field");
                alert.showAndWait();
            }

            if(price != null && price.length() > 0)
            errors--;
            else
            {
                alert.setContentText("Error in Product Price field:\n\n Please enter a Price");
                alert.showAndWait();
            }

            if(inv != null && inv.length() > 0 && inv.matches(nums))
            errors--;
            else
            {
                alert.setContentText("Error in Inv Quantity field:\n\n Must be a value between Max & Min\n\n Please enter a value for Inv Quantity");
                alert.showAndWait();
            }
           
        }
        catch(NumberFormatException E)
        {
            alert.setContentText("Error: NumberFormatException\n Fill in all fields\n" + E.getMessage());
            alert.showAndWait();
        }
    
        if(errors == 0)
            return true;
        else
            return false;
    }
    
    
    
    /**
     *
     * Exceptions set 1 of the Rubric requests that these entities below be 
     * handled to ensure input for Quantity/Stock is logically within the 
     * range between Max and Min. 
     *
     */
    public static boolean exceptionSet1(int stock, int max, int min){
        
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Validate Fields: All fields must be filled out");
        alert.setHeaderText(null);
        int errors = 1;
        
        if(stock >= min && stock <= max && stock >= 0 && min >= 0)
        errors--;
        else
        {
            alert.setContentText("Error Inv Quantity field: \nInv needs a value between Max & Min \n\nNo Negative numbers for: Quantity, Min, or Max");
            alert.showAndWait();
        }
        
        if(errors == 0)
            return true;
        else
            return false;
    }
    
}
