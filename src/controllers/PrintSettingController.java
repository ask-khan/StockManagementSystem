/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import com.jfoenix.controls.JFXComboBox;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import models.PrintModel;

/**
 * FXML Controller class
 *
 * @author Ahmed
 */
public class PrintSettingController implements Initializable {
    
    @FXML
    private JFXComboBox<String> printerComboBox = new JFXComboBox<String>();
    
    @FXML
    private Button savePrinterButton;
    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        try {
            this.onloadSetPrintName();
        } catch (SQLException ex) {
            Logger.getLogger(PrintSettingController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(PrintSettingController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        ObservableList<String> printerList = FXCollections.observableArrayList();
        // TODO code application logic here
        PrintService[] printServices = PrintServiceLookup.lookupPrintServices(null, null);
        
        for (PrintService printer : printServices)
            printerList.add(printer.getName());      
        
        // Set Printer List.
        printerComboBox.setItems(printerList);
    }    
    // print button functionality
    @FXML
    private void printButtonFunctionality(ActionEvent event) throws SQLException, ClassNotFoundException{
        if ( !printerComboBox.getValue().isEmpty() ) {
            PrintModel  printModel = new PrintModel();
            ResultSet printData = printModel.insertPrint(printerComboBox.getValue());
            if ( printData.next() ) {
                printerComboBox.setValue(printData.getString("printname"));
            }
            
        }
    }
    
    private void onloadSetPrintName () throws SQLException, ClassNotFoundException {
        PrintModel  printModel = new PrintModel();
        String printName =  printModel.getPrintName();
        if ( !printName.isEmpty() ) {
            printerComboBox.setValue(printName);
        }
        
    }
}
