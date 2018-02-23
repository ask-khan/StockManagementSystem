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
     */
    public void ShowDialog(String attributeName, int errorIntCode) {
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
                errorTitle = "Good News";
                errorMessage = "PDF created Successfully:)";
                break;
            case 9:
                errorTitle = "Stock Error";
                errorMessage = "Stock is not available.";
                break;
            case 10:
                errorTitle = "Unable Delete Stock Error";
                errorMessage = "Stock cannot be deleted.";
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
