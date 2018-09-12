/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package handler;

import javafx.scene.control.Alert;

/**
 *
 * @author Ahmed
 */
public class ValidationDialog {
    
    /**
     * This Method is used to find the Print Service
     *
     * @param attributeName Used for attribute Name
     * @param errorIntCode Used for error Code
     * @param filePath
     */
    public void ShowDialog(String attributeName, int errorIntCode, String filePath) {
        String errorMessage = "", errorTitle = "";

        switch (errorIntCode) {
            case 1:
                errorTitle = "Validation Error";
                errorMessage = attributeName + " is Empty.";
                break;
            case 2:
                errorTitle = "Validation Error";
                errorMessage = "User information or Bill no is not filled Properly.";
                break;
            case 3:
                errorTitle = "Validation Error";
                errorMessage = "User information or Bill no have invalid entry.";
                break;
            case 4:
                errorTitle = "Warming Error";
                errorMessage = "Discount Package is not select yet.";
                break;
            case 5:
                errorTitle = "Table Error";
                errorMessage = "Product Table cannot be empty.";
                break;
            case 6:
                errorTitle = "Table Error";
                errorMessage = "Table fields entry are empty or invalid.";
                break;
            case 7:
                errorTitle = "Validation Error";
                errorMessage = "Combine discount field is empty.";
                break;
            case 8:
                errorTitle = "Alert";
                errorMessage = "PDF created Successfully at: " + filePath;
                break;
            case 9:
                errorTitle = "Stock Error";
                errorMessage = "Stock is not available.";
                break;
            case 10:
                errorTitle = "Unable Delete Stock Error";
                errorMessage = "Stock cannot be deleted.";
                break;
            case 11:
                errorTitle = "Invalid Customer Name";
                errorMessage = "Customer name is invalid";
                break;
            case 12:
                errorTitle = "Invalid date patteren";
                errorMessage = "Start date and end date is invalid";
                break;
            case 13:
                errorTitle = "Alert";
                errorMessage = "Product PDF created Sucessfully at: " + filePath;
                break;
            case 14:
                errorTitle = "Alert";
                errorMessage = "Sales PDF created Sucessfully at: " + filePath;
                break;
            case 15:
                errorTitle = "Alert";
                errorMessage = "Customer PDF created Sucessfully at: " + filePath;
                break;
            case 16:
                errorTitle = "Validation Alert";
                errorMessage = "Product type is empty.";
                break;
            case 17:
                errorTitle = "Alert";
                errorMessage = "Discount Package cannot be empty.";
                break;
            default:
                break;
        }

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(attributeName);
        alert.setHeaderText(null);
        alert.setContentText(errorMessage);

        alert.showAndWait();
    }
}
