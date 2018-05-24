/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import handler.CustomerView;
import handler.InvoiceProduct;
import handler.OrderList;
import handler.customerTable;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import models.CustomerModel;
import models.CustomerViewModel;
import models.OrderListModel;
import org.controlsfx.control.textfield.TextFields;

/**
 * FXML Controller class
 *
 * @author Ahmed
 */
public class CustomerViewController implements Initializable {

    // Declare Customer Text Field.
    @FXML
    private TextField customerViewName, contactCustomerView, areaCustomerView, emailAddressCustomerView, searchInvoiceNumber;

    @FXML
    private TableView<CustomerView> customerViewTable = new TableView<CustomerView>();

    @FXML
    private TableColumn<CustomerView, String> invoiceIdColumns, customerIdColumns, productNoColumns, totalAmountColumns, receivedAmountColumns, createdDateColumns;

    @FXML
    private ObservableList<CustomerView> customerViewInvoiceData = FXCollections.observableArrayList();

    @FXML
    private Button deleteButton, viewButton;

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        // Onload View Button Functionality.
        viewButton.disableProperty().bind(Bindings.isEmpty(customerViewTable.getSelectionModel().getSelectedItems()));

        // Onload Delete Button Functionality.
        deleteButton.disableProperty().bind(Bindings.isEmpty(customerViewTable.getSelectionModel().getSelectedItems()));

        // TODO
        try {
            // Declare Customer Model Instance.
            CustomerModel customerModel = new CustomerModel();
            // Function call get all customer on load.
            ResultSet customerResult = customerModel.getAllCustomerOnLoad();
            ArrayList<String> customerList = new ArrayList<>();
            // while next loops.
            while (customerResult.next()) {
                // Declare customer table object.
                customerTable customerTableObject = new customerTable(customerResult.getInt("id"), customerResult.getString("customercontact"), customerResult.getString("customername"), customerResult.getString("customeraddress"), customerResult.getString("customeremailaddress"), customerResult.getString("customerarea"));
                customerList.add(customerTableObject.getCustomerIdTableColumns() + ": " + customerTableObject.getCustomerNameTableColumns());

            }
            // Auto Complete of customer view name.
            TextFields.bindAutoCompletion(customerViewName, customerList);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(CustomerViewController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(CustomerViewController.class.getName()).log(Level.SEVERE, null, ex);
        }

        invoiceIdColumns.setCellValueFactory(new PropertyValueFactory<CustomerView, String>("invoiceIdColumns"));
        customerIdColumns.setCellValueFactory(new PropertyValueFactory<CustomerView, String>("customerIdColumns"));
        productNoColumns.setCellValueFactory(new PropertyValueFactory<CustomerView, String>("productNoColumns"));
        totalAmountColumns.setCellValueFactory(new PropertyValueFactory<CustomerView, String>("totalAmountColumns"));
        receivedAmountColumns.setCellValueFactory(new PropertyValueFactory<CustomerView, String>("receivedAmountColumns"));
        createdDateColumns.setCellValueFactory(new PropertyValueFactory<CustomerView, String>("createdDateColumns"));

        customerViewTable.setItems(customerViewInvoiceData);

        FilteredList<CustomerView> filteredData;
        filteredData = new FilteredList<>(customerViewInvoiceData, p -> true);

        // 2. Set the filter Predicate whenever the filter changes.
        searchInvoiceNumber.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(CustomerView -> {
                // If filter text is empty, display all persons.
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                // Compare first name and last name of every person with filter text.
                String lowerCaseFilter = newValue.toLowerCase();
                //System.out.println( lowerCaseFilter );

                if (String.valueOf(CustomerView.getInvoiceIdColumns()).contains(lowerCaseFilter)) {
                    System.out.println(String.valueOf(CustomerView.getInvoiceIdColumns()).contains(lowerCaseFilter));
                    return true; // Filter matches first name.
                } else if (CustomerView.getCustomerIdColumns().toLowerCase().contains(lowerCaseFilter)) {
                    System.out.println(CustomerView.getCustomerIdColumns().toLowerCase().contains(lowerCaseFilter));
                    return true; // Filter matches last name.
                } else if (String.valueOf(CustomerView.getProductNoColumns()).contains(lowerCaseFilter)) {
                    System.out.println(String.valueOf(CustomerView.getProductNoColumns()).contains(lowerCaseFilter));
                    return true; // Filter matches first name.
                }
                return false; // Does not match.
            });
        });

        // 3. Wrap the FilteredList in a SortedList. 
        SortedList<CustomerView> sortedData = new SortedList<>(filteredData);

        // 4. Bind the SortedList comparator to the TableView comparator.
        sortedData.comparatorProperty().bind(customerViewTable.comparatorProperty());

        // 5. Add sorted (and filtered) data to the table.
        customerViewTable.setItems(sortedData);

    }

    // delete button function onclick.
    @FXML
    private void deleteButtonFunction() throws ClassNotFoundException, SQLException {
        CustomerView customerViewObject = customerViewTable.getSelectionModel().getSelectedItem();
        // declare orderlist object.
        OrderListModel orderListModelObject = new OrderListModel();

        // delete invoice functon call.
        orderListModelObject.deleteInvoice(Integer.valueOf(customerViewObject.getInvoiceIdColumns()));
        customerViewInvoiceData.remove(customerViewObject);

    }

    // view button function onclick.
    @FXML
    private void viewButtonFunction(ActionEvent event) throws ClassNotFoundException, IOException, SQLException {
        // Declare order list object.
        CustomerView customerViewObject = customerViewTable.getSelectionModel().getSelectedItem();
        ((Node) (event.getSource())).getScene().getWindow().hide();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/views/order.fxml"));

        try {
            loader.load();
        } catch (IOException exception) {
            //Logger.getLogger(FXMLDocumentController.class.getName()).
        }

        OrderController orderController = loader.getController();
        // set order controller function call;
        orderController.setInvoiceDataInCustomerView(customerViewObject.getInvoiceIdColumns());

        Parent parent = loader.getRoot();
        Stage stage = new Stage();
        stage.setTitle("Main Dashboard");
        stage.getIcons().add(new Image("file:user-icon.png"));
        Scene scene = new Scene(parent);
        scene.getStylesheets().add(getClass().getResource("/styles/order.css").toExternalForm());
        stage.setScene(scene);
        stage.showAndWait();

    }

    @FXML
    private void customerViewNameFunction() throws ClassNotFoundException, SQLException {
        // refresh customer view table.
        refreshCustomerViewTable();
        // Declare Customer Model
        CustomerModel customerModel = new CustomerModel();
        // Customer Info Result Set.
        ResultSet customerInfoResultSet = customerModel.getCustomerInfoById(customerViewName.getText().substring(0, customerViewName.getText().indexOf(":")));

        // Customer Info Next.
        if (customerInfoResultSet.next()) {

            CustomerViewModel CustomerModelViewInstance = new CustomerViewModel();
            ResultSet customerInvoiceCountData = CustomerModelViewInstance.getCustomerInvoiceData(customerViewName.getText().substring(0, customerViewName.getText().indexOf(":")));

            // Set Customer Name.
            customerViewName.setText(customerInfoResultSet.getString("customername"));
            // Set Customer Contact.
            contactCustomerView.setText(customerInfoResultSet.getString("customercontact"));
            // Set Customer Area.
            areaCustomerView.setText(customerInfoResultSet.getString("customerarea"));
            // Set Customer Email Address.
            emailAddressCustomerView.setText(customerInfoResultSet.getString("customeremailaddress"));

            while (customerInvoiceCountData.next()) {

                CustomerView customerView = new CustomerView(Integer.toString(customerInvoiceCountData.getInt("id")), Integer.toString(customerInvoiceCountData.getInt("customerid")), Integer.toString(customerInvoiceCountData.getInt("invoicecount")), Double.toString(customerInvoiceCountData.getDouble("totalamount")), Double.toString(customerInvoiceCountData.getDouble("receivedamount")), customerInvoiceCountData.getString("modifieddate"));
                customerViewInvoiceData.add(customerView);
            }

        }
    }

    private void refreshCustomerViewTable() {
        customerViewInvoiceData.clear();
    }
}
