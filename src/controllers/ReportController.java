/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import com.itextpdf.text.DocumentException;
import handler.GenerateCustomerPDF;
import handler.GenerateProductPDF;
import handler.GenerateSalesPDF;
import handler.ValidationDialog;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import models.CustomerModel;
import models.DashboardModel;
import models.ReportModel;

/**
 * FXML Controller class
 *
 * @author Ahmed
 */
public class ReportController implements Initializable {

    // Declare Customer name combo box.
    @FXML
    private ComboBox<String> customerNameComboBox = new ComboBox<>();

    // Declare product name combo box.
    @FXML
    private ComboBox<String> productNameComboBox = new ComboBox<>();

    // Declare start and end date picker.
    @FXML
    DatePicker startDatePicker, endDatePicker, productStartDate, productEndDate, salesStartDatePicker, saleEndDatePicker;

    //  Declare product, customer, sale button.
    @FXML
    Button productSaleButton, customerSaleButton, saleButton;

    //Declare  Customer Name, Customer Id 
    private ArrayList<String> customerNameList = new ArrayList<>();
    private ArrayList<String> customerId = new ArrayList<>();
    // Declare Product Name, Product Id
    private ArrayList<String> productNameList = new ArrayList<>();
    private ArrayList<String> productId = new ArrayList<>();

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        // Declare Date Object and set to right now date.
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDate now = LocalDate.now();

        // Set start date picker.
        startDatePicker.setValue(now);
        // Set end date picker.
        endDatePicker.setValue(now);
        // Declare arraylist for customerList.

        // Onload customer data on table view.
        CustomerModel customerModel = new CustomerModel();
        try {
            // get all customer function call.
            ResultSet customerResult = customerModel.getAllCustomerOnLoad();

            while (customerResult.next()) {
                customerNameList.add(customerResult.getString("customername"));
                customerId.add(String.valueOf(customerResult.getInt("id")));
            }

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ReportController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(ReportController.class.getName()).log(Level.SEVERE, null, ex);
        }

        customerNameComboBox.getItems().addAll(customerNameList);

        // Set product end date picker.
        productEndDate.setValue(now);
        // Set product start date picker.
        productStartDate.setValue(now);

        DashboardModel dashboardModel = new DashboardModel();
        try {
            ResultSet productResult = dashboardModel.getAllProductOnLoad();

            while (productResult.next()) {
                productNameList.add(productResult.getString("productname"));
                productId.add(String.valueOf(productResult.getInt("id")));
            }

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ReportController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(ReportController.class.getName()).log(Level.SEVERE, null, ex);
        }

        
        productNameComboBox.getItems().addAll(productNameList);

        // Set Sale start date picker.
        salesStartDatePicker.setValue(now);

        // Set Sale end date picker.
        saleEndDatePicker.setValue(now);
    }

    @FXML
    private void generateCustomerFunction(ActionEvent event) throws SQLException, ClassNotFoundException, IOException, DocumentException {
        ValidationDialog validation = new ValidationDialog();
        //System.out.println( customerNameComboBox.getValue() );
        boolean Ifvalid = true;
        if (customerNameComboBox.getValue() != null) {
            //System.out.println(customerId.get(customerNameComboBox.getSelectionModel().getSelectedIndex()));
            Ifvalid = true;
        } else {
            Ifvalid = false;
            validation.ShowDialog("", 11, null);
        }
        // check end date is after start date.
        if (startDatePicker.getValue().isBefore(endDatePicker.getValue()) == true) {
            Ifvalid = true;

        } else {
            Ifvalid = false;
            validation.ShowDialog("", 12, null);
        }

        //System.out.println(startDatePicker.getValue() + "  " + endDatePicker.getValue());
        if (Ifvalid == true) {
            ReportModel reportModel = new ReportModel();
            ResultSet getCustomerInvoice = reportModel.getCustomerInvoice(customerId.get(customerNameComboBox.getSelectionModel().getSelectedIndex()), String.valueOf(startDatePicker.getValue()), String.valueOf(endDatePicker.getValue()));
            GenerateCustomerPDF newCustomerPDf = new GenerateCustomerPDF();
            String filePath = newCustomerPDf.generatePDFCustomer(customerId.get(customerNameComboBox.getSelectionModel().getSelectedIndex()), getCustomerInvoice, String.valueOf(startDatePicker.getValue()), String.valueOf(endDatePicker.getValue()));
            if (!filePath.isEmpty()) {

                ValidationDialog validationDialog = new ValidationDialog();
                validationDialog.ShowDialog("Good News", 15, filePath);
            }
        }

    }

    @FXML
    private void generateProductFunction(ActionEvent event) throws SQLException, ClassNotFoundException, IOException, DocumentException {
        boolean ifValid = false;
        ValidationDialog validation = new ValidationDialog();
        // If product name is not null
        if (productNameComboBox.getValue() != null) {
            ifValid = true;
        } else {
            ifValid = false;
        }

        // check end date is after start date.
        if (productStartDate.getValue().isBefore(productEndDate.getValue()) == true) {
            ifValid = true;

        } else {
            ifValid = false;
            validation.ShowDialog("", 12, null);
        }

        if (ifValid == true) {
            ReportModel reportModel = new ReportModel();
            ResultSet getproductInfo = reportModel.getProductInfo(productId.get(productNameComboBox.getSelectionModel().getSelectedIndex()));
            ResultSet getProductInvoice = reportModel.getProductInvoice(productId.get(productNameComboBox.getSelectionModel().getSelectedIndex()));
            GenerateProductPDF newProductPDF = new GenerateProductPDF();
            String filePath = newProductPDF.generatePDFProduct(productId.get(productNameComboBox.getSelectionModel().getSelectedIndex()), getproductInfo, getProductInvoice);
            if (!filePath.isEmpty()) {

                ValidationDialog validationDialog = new ValidationDialog();
                validationDialog.ShowDialog("Good News", 13, filePath);
            }
        }

    }

    @FXML
    private void generateSalesFunction(ActionEvent event) throws SQLException, ClassNotFoundException, IOException, DocumentException {
        ValidationDialog validation = new ValidationDialog();
        // check end date is after start date.
        if (salesStartDatePicker.getValue().isBefore(saleEndDatePicker.getValue()) == true) {
            ReportModel reportModel = new ReportModel();
            //get sales invoice function call.
            ResultSet getSalesInvoices = reportModel.getSalesInvoice(String.valueOf(salesStartDatePicker.getValue()), String.valueOf(saleEndDatePicker.getValue()));

            GenerateSalesPDF newSalesPDF = new GenerateSalesPDF();
            String filePath = newSalesPDF.generatePDFSales(String.valueOf(salesStartDatePicker.getValue()), String.valueOf(saleEndDatePicker.getValue()), getSalesInvoices);
            // if path is not empty.
            if (!filePath.isEmpty()) {

                ValidationDialog validationDialog = new ValidationDialog();
                validationDialog.ShowDialog("Good News", 14, filePath);
            }
        } else {

            validation.ShowDialog("", 12, null);
        }

    }
}
