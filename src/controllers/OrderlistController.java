/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import com.jfoenix.controls.JFXTextField;
import handler.OrderList;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import models.OrderListModel;
import models.OrderModel;

/**
 * FXML Controller class
 *
 * @author Ahmed
 */
public class OrderlistController implements Initializable {

    // Declare Order List Table View.
    @FXML
    private TableView<OrderList> orderListTableView = new TableView<OrderList>();
    // Declare table columns order id.
    @FXML
    private TableColumn<OrderList, Integer> orderId;
    //Declare table columns order name.
    @FXML
    private TableColumn<OrderList, String> orderName;
    // Declare table columns amount.
    @FXML
    private TableColumn<OrderList, String> orderAmount;
    //Declare table order date.
    @FXML
    private TableColumn<OrderList, String> orderDate;
    //Declare table order area.
    @FXML
    private TableColumn<OrderList, String> orderArea;
    
    // Declare Observabilelist.
    @FXML
    private  ObservableList<OrderList> orderListData = FXCollections.observableArrayList();
    
    // Declare view - delete button.
    @FXML
    private Button viewButton, deleteButton;
    
    @FXML
    private JFXTextField voiceFilterTextField;
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        // Onload View Button Functionality.
        viewButton.disableProperty().bind(Bindings.isEmpty(orderListTableView.getSelectionModel().getSelectedItems()));
        
        // Onload Delete Button Functionality.
        deleteButton.disableProperty().bind(Bindings.isEmpty(orderListTableView.getSelectionModel().getSelectedItems()));
        
        OrderModel orderModelObject = new OrderModel(); 
        try {
            // Get all invoice in onload.
            ResultSet resultSet = orderModelObject.getAllInvoiceOnload();    
            
            while ( resultSet.next() ) {
                // Adding in invoice table.
                OrderList orderListObject = new OrderList(resultSet.getInt("id"),  resultSet.getString("customername"), resultSet.getString("datepicker"), resultSet.getString("totalamount"), resultSet.getString("area"));
                orderListData.add(orderListObject);
            }
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(OrderlistController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        orderArea.setCellValueFactory(new PropertyValueFactory<OrderList,String>("orderArea"));
        orderId.setCellValueFactory(new PropertyValueFactory<OrderList, Integer>("orderId"));
        orderName.setCellValueFactory(new PropertyValueFactory<OrderList, String>("orderName"));
        orderAmount.setCellValueFactory(new PropertyValueFactory<OrderList, String>("orderAmount"));
        orderDate.setCellValueFactory(new PropertyValueFactory<OrderList, String>("orderDate"));
        
        orderListTableView.setItems(orderListData);
        
        FilteredList<OrderList> filteredData;
        filteredData = new FilteredList<>(orderListData, p -> true);
        
        
        // 2. Set the filter Predicate whenever the filter changes.
        voiceFilterTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(OrderList -> {
                // If filter text is empty, display all persons.
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                // Compare first name and last name of every person with filter text.
                String lowerCaseFilter = newValue.toLowerCase();
                System.out.println( lowerCaseFilter );
                
                if (String.valueOf(OrderList.getOrderId()).contains(lowerCaseFilter)) {
                    System.out.println(String.valueOf(OrderList.getOrderId()).contains(lowerCaseFilter));
                    return true; // Filter matches first name.
                } else if (OrderList.getOrderName().toLowerCase().contains(lowerCaseFilter)) {
                    System.out.println(OrderList.getOrderName().toLowerCase().contains(lowerCaseFilter));
                    return true; // Filter matches last name.
                }
                return false; // Does not match.
            });
        });
        
        // 3. Wrap the FilteredList in a SortedList. 
        SortedList<OrderList> sortedData = new SortedList<>(filteredData);
        
        // 4. Bind the SortedList comparator to the TableView comparator.
        sortedData.comparatorProperty().bind(orderListTableView.comparatorProperty());

        // 5. Add sorted (and filtered) data to the table.
        orderListTableView.setItems(sortedData);
    }    
    
    // delete button function onclick.
    @FXML
    private void deleteButtonFunction() throws ClassNotFoundException, SQLException {
        OrderList orderListObject = orderListTableView.getSelectionModel().getSelectedItem();
        // declare orderlist object.
        OrderListModel orderListModelObject = new OrderListModel();
       
        // delete invoice functon call.
        orderListModelObject.deleteInvoice( orderListObject.getOrderId() );
        orderListData.remove( orderListObject );
        
    }
    
    // view button function onclick.
    @FXML
    private void viewButtonFunction( ActionEvent event  ) throws ClassNotFoundException, IOException, SQLException {
        // Declare order list object.
        OrderList orderListObject = orderListTableView.getSelectionModel().getSelectedItem();
        ((Node) (event.getSource()) ).getScene().getWindow().hide();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/views/order.fxml"));
        
        try {
            loader.load();
        } catch ( IOException exception ) {
            //Logger.getLogger(FXMLDocumentController.class.getName()).
        }
        
        OrderController orderController =  loader.getController();
        // set order controller function call;
        orderController.setInvoiceDataInView(orderListObject);
        
        Parent parent = loader.getRoot();
        Stage stage  = new Stage();
        stage.setTitle("Main Dashboard");
        stage.getIcons().add( new Image("file:user-icon.png"));
        Scene scene = new Scene( parent );
        scene.getStylesheets().add(getClass().getResource("/styles/order.css").toExternalForm());
        stage.setScene(scene);
        stage.showAndWait();
        
    }
    
    
    
}
