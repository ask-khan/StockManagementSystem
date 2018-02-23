/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import com.itextpdf.text.DocumentException;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RequiredFieldValidator;
import handler.GeneratePDF;
import handler.InvoiceProduct;
import handler.OrderList;
import handler.ProductTable;
import handler.ValidationDialog;
import handler.customerTable;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import models.CustomerModel;
import models.DashboardModel;
import models.OrderListModel;
import models.OrderModel;

/**
 * FXML Controller class
 *
 * @author Ahmed
 */
public class OrderController implements Initializable {
    
    // Declare Customer Name, Area Text, Quanlity, Product Packing, Trade Price.

    /**
     *
     */
    @FXML
    public JFXTextField areaTextField, stockTextField,

    /**
     *
     */
    quanlityTextField,

    /**
     *
     */
    productPackingTextField,

    /**
     *
     */
    tradePriceTextField;
    
    // Declare Date Picker.
    @FXML
    private DatePicker datePicker;
    
    // Declare Product Line.
    @FXML
    private JFXComboBox<String> productLineComboBox = new JFXComboBox<String>();
    // Declare Product Line.
    @FXML
    private JFXComboBox<String> customerNameTextField = new JFXComboBox<String>();
    
    @FXML
    private Button GenerateInvoiceButton;
    
    // Declare Add Product.
    @FXML
    private Button AddProductButton;
    
    // Declare Total Amount.
    @FXML
    private TextField totalAmountTextField, invoiceId;
    
    @FXML
    private Button deleteProductButton;
    
    // Declare Invoice Product Table.
    @FXML
    private TableView<InvoiceProduct> invoiceProductTable = new TableView<InvoiceProduct>();
    
    // Declare Product Name, Product Packing, Product Trade Price, Product Quanlity. Product Amount.
    @FXML
    private TableColumn<InvoiceProduct, String> productName;
    @FXML
    private TableColumn<InvoiceProduct, Integer> productPacking, productId;
    @FXML
    private TableColumn<InvoiceProduct, Float> productTradePrice;
    @FXML
    private TableColumn<InvoiceProduct, Integer> productQuanlity;
    @FXML
    private TableColumn<InvoiceProduct, Float> productAmount;
    
    // Declare arraylist productPriceList.

    /**
     *
     */
    public ArrayList<String> productPriceList = new ArrayList<String>();
    
    // Declare arraylist productPackingList.

    /**
     *
     */
    public ArrayList<String> productPackingList = new ArrayList<String>();
    
    // Declare arraylist productSelectedId

    /**
     *
     */
    public ArrayList<String> productSelectedId = new ArrayList<String>();
    
    // Declare arraylist customer contact.

    /**
     *
     */
    public ArrayList<String> customerContact = new ArrayList<String>();
    
    // Declare arraylist customer area.

    /**
     *
     */
    public ArrayList<String> customerArea = new ArrayList<String>();
    
    // Declare Observabilelist.
    @FXML
    private final ObservableList<InvoiceProduct> productInvoiceData = FXCollections.observableArrayList();

    
    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Declare Date Object and set to right now date.
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDate now = LocalDate.now();
        
        // Total Amount.
        totalAmountTextField.setText("0.0");
        
        // Set Current Date on Date Picker.
        datePicker.setValue(now);
        
        totalAmountTextField.setDisable(true);

        // Onload Delete Button Hide Functionality.
        deleteProductButton.disableProperty().bind(Bindings.isEmpty(invoiceProductTable.getSelectionModel().getSelectedItems()));
        try {
            // Onload customer data on table view.
            CustomerModel customerModel = new CustomerModel();
            ResultSet customerResult = customerModel.getAllCustomerOnLoad();
            ObservableList<String> customerList = FXCollections.observableArrayList();
            while (customerResult.next()) {
                // Declare customer table object.
                customerTable customerTableObject = new customerTable( customerResult.getInt("id"), customerResult.getString("customercontact"), customerResult.getString("customername"), customerResult.getString("customeraddress"), customerResult.getString("customeremailaddress"), customerResult.getString("customerarea")  );
                customerList.add( customerTableObject.getCustomerNameTableColumns() );
                customerContact.add( customerTableObject.getCustomerContactTableColumns() );
                customerArea.add( customerTableObject.getCustomerAreaTableColumns() );
            }
            
            customerNameTextField.setItems(customerList);
            
            DashboardModel dashboardModel = new DashboardModel();
            ResultSet resultSet = dashboardModel.getAllProductOnLoad();
            ObservableList<String> productList = FXCollections.observableArrayList();
            
            while ( resultSet.next() ){
                ProductTable productObject = new ProductTable( resultSet.getInt("id"), resultSet.getInt("productpacking"),(float) resultSet.getDouble("tradeprice") , resultSet.getString("productname"), resultSet.getString("brandname"), (float) resultSet.getDouble("purchaseprice"), resultSet.getString("suppliername"), resultSet.getString("modifieddate"));
                productList.add(productObject.getProductName());
                productPriceList.add( String.valueOf( productObject.getPurchasedPrice()) );
                productPackingList.add( String.valueOf( productObject.getProductPacking()) );
                productSelectedId.add( String.valueOf( productObject.getProductCode()) );
            }
            
            productLineComboBox.setItems(productList);
            
            // Set Values for table
            
            productTradePrice.setCellValueFactory(new PropertyValueFactory<InvoiceProduct, Float>("productTradePrice"));
            productName.setCellValueFactory(new PropertyValueFactory<InvoiceProduct, String>("productName"));
            productPacking.setCellValueFactory(new PropertyValueFactory<InvoiceProduct, Integer>("productPacking"));
            productAmount.setCellValueFactory(new PropertyValueFactory<InvoiceProduct, Float>("productAmount"));
            productQuanlity.setCellValueFactory(new PropertyValueFactory<InvoiceProduct, Integer>("productQuanlity"));
            productId.setCellValueFactory(new PropertyValueFactory<InvoiceProduct, Integer>("productId"));
            
            invoiceProductTable.setItems(productInvoiceData);
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(OrderController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //productLineComboBox.getSelectionModel().selectedIndexProperty().addListener(listener);
        
        
    }    
    
    // Add Product Invoice Function.
    @FXML
    private void addProductInvoiceFunction(ActionEvent event) {
        ArrayList<String>  productInvoiceList = new ArrayList<String>();
        
        // Product Packing Validator.
        RequiredFieldValidator productPackingValidator = new RequiredFieldValidator(); 
        // Product Trade Price Validator.
        RequiredFieldValidator tradePriceValidator = new RequiredFieldValidator(); 
        // Quanlity Validator.
        RequiredFieldValidator productQuanlityValidator = new RequiredFieldValidator(); 
        // Product Name Validator.
        RequiredFieldValidator productNameValidator = new RequiredFieldValidator();
       
        
        // Product Packing Message.
        productPackingValidator.setMessage("Product packing cannot be empty.");
        productPackingTextField.getValidators().add(productPackingValidator);
        
        // Product Quanlity Message.
        productQuanlityValidator.setMessage("Product quanlity cannot be empty");
        quanlityTextField.getValidators().add(productQuanlityValidator);
        
        tradePriceValidator.setMessage("Trade price cannot be empty.");
        tradePriceTextField.getValidators().add(tradePriceValidator);
        
        // Product Packing Validation.
        if ( !productPackingTextField.getText().isEmpty() && this.validationInteger(productPackingTextField.getText()) ) {
        
            productInvoiceList.add( productPackingTextField.getText()  );
        
        } else {
            productPackingTextField.validate();
        }
        
        // Product Quanlity Validation.
        if ( !quanlityTextField.getText().isEmpty() && this.validationInteger( quanlityTextField.getText() ) ) {
            productInvoiceList.add( quanlityTextField.getText()  );
        } else {
            quanlityTextField.validate();
        }
        
        // Trade Price Validation.
        if ( !tradePriceTextField.getText().isEmpty() && this.validationIntegerOrFloat(tradePriceTextField.getText()) ) {
            productInvoiceList.add( tradePriceTextField.getText()  );
        } else {
            tradePriceTextField.validate();
        }
        
        // Product Line Combo box Validation.
        if ( productLineComboBox.getSelectionModel().getSelectedIndex() != -1 ) {
            productInvoiceList.add( productLineComboBox.getSelectionModel().getSelectedItem()  );
        } else {
            //productLineComboBox.validate();
        }
        
        // Quanlity Validation.
        if (  !quanlityTextField.getText().isEmpty() && this.validationInteger( quanlityTextField.getText() ) && !tradePriceTextField.getText().isEmpty() && this.validationIntegerOrFloat(tradePriceTextField.getText()) ) {
           productInvoiceList.add( String.valueOf(Float.parseFloat(tradePriceTextField.getText()) * Integer.parseInt( quanlityTextField.getText()   ))  ); 
        }
        
        
        // if product voice list is valid.
        if ( productInvoiceList.size() == 5 ) {
            //System.out.println(productSelectedId + " " + productLineComboBox.getSelectionModel().getSelectedIndex() );
            if ( Integer.parseInt( stockTextField.getText() ) > Integer.parseInt( quanlityTextField.getText() ) ) {
                InvoiceProduct invoiceProduct = new InvoiceProduct( productInvoiceList.get(3), Integer.valueOf( productInvoiceList.get(0)), Integer.valueOf( productInvoiceList.get(1)), Float.valueOf( productInvoiceList.get(2)), Float.valueOf( productInvoiceList.get(4)), Integer.valueOf( productSelectedId.get(productLineComboBox.getSelectionModel().getSelectedIndex()) )  );
                productInvoiceData.add( invoiceProduct);
            
                if ( !totalAmountTextField.getText().isEmpty()) {
                    float total = Float.valueOf( totalAmountTextField.getText() ) + Float.valueOf( productInvoiceList.get(4));
                    totalAmountTextField.setText( String.valueOf(total) );
                } else {
                    float total = Float.valueOf( productInvoiceList.get(4));
                    totalAmountTextField.setText( String.valueOf(total) );
                }
            } else {
                ValidationDialog validationDialog = new ValidationDialog();
                validationDialog.ShowDialog("Stock Error", 9);
            }
            
        } else {
            
        }
    }
    
    // product cumbo onclick function. 
    @FXML
    private void productListComboBoxFunctionality() throws ClassNotFoundException, SQLException {
         
        productPackingTextField.setText( productPackingList.get( productLineComboBox.getSelectionModel().getSelectedIndex()) );
        tradePriceTextField.setText( productPriceList.get( productLineComboBox.getSelectionModel().getSelectedIndex() ) );
        OrderModel orderModel = new OrderModel();
        ResultSet resultSet  = orderModel.getProductStockById( productSelectedId.get(productLineComboBox.getSelectionModel().getSelectedIndex()));
        if ( resultSet.next() ) {
            
            if ( resultSet.getString("productStock") == null  ) {
                stockTextField.setText( "0" );
            }
            else if ( Integer.parseInt( resultSet.getString("productStock")) > 0 ) {
                stockTextField.setText( resultSet.getString("productStock") );
            } else if ( Integer.parseInt( resultSet.getString("productStock")) < 0 )  {
                stockTextField.setText( resultSet.getString("productStock") );
            }
        }
        
    }
    
    // customer cumbo box onclick function.
    @FXML
    private void customerCumboBoxFunction() {
        areaTextField.setText( customerArea.get(customerNameTextField.getSelectionModel().getSelectedIndex()) );
    }
    
    /*
     * validation - interger
     * @param: text: String
     **/

    /**
     *
     * @param text
     * @return
     */

    public boolean validationInteger( String text ){
        return text.matches("[0-9]*");
    }
    
    /*
     * validation - interger
     * @param: text: String
     **/

    /**
     *
     * @param text
     * @return
     */

    public boolean validationIntegerOrFloat( String text ){
        return !text.matches(".*[a-z].*");
    }
    
    
    /**
     * Add Delete Button
     *
     * @param event
     */
    @FXML
    private void ProductDeleteInvoiceButton(ActionEvent event) {
        // Alert Dialog Code.
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Product");
        alert.setContentText("Are you sure you want to delete this Product?");
        Optional<ButtonType> result = alert.showAndWait();

        // If Button is Yes.
        if (result.get() == ButtonType.OK) {
            //System.out.println("OK");
            InvoiceProduct invoiceProductTableObject = invoiceProductTable.getSelectionModel().getSelectedItem();
            
            float totalAmount =  Float.valueOf(totalAmountTextField.getText()) - invoiceProductTableObject.getProductAmount();
            totalAmountTextField.setText(String.valueOf(totalAmount));
            
            // Remove Product
            productInvoiceData.remove( invoiceProductTableObject );
            
        } else {
            //System.out.println("Delete");
        }
    }
    
    // GenerateInvoiceButtonFunction
    @FXML
    private void GenerateInvoiceButtonFunction() throws SQLException, ClassNotFoundException, IOException, DocumentException {
        ArrayList<String>  customerInvoiceData = new ArrayList<String>();
        
        // Product customer Name Validator.
        RequiredFieldValidator customerNameValidator = new RequiredFieldValidator(); 
        // Product customer Name Validator.
        RequiredFieldValidator areaValidator = new RequiredFieldValidator();
        // Date Picker Validator.
        RequiredFieldValidator datePickerValidator = new RequiredFieldValidator();
        
        // Area Validator.
        areaValidator.setMessage("Area cannot be empty.");
        areaTextField.getValidators().add(areaValidator);
        System.out.println(customerNameTextField.getValue());
        // Customer Name is validator
        if ( customerNameTextField.getValue() != null && !this.validationIntegerOrFloat(customerNameTextField.getValue())  ) {
            customerInvoiceData.add( customerNameTextField.getValue() );
        } else if ( customerNameTextField.getValue() == null ) {
            //customerNameTextField.validate();
            
            
        }
        
        // Area is validator.
        if ( !areaTextField.getText().isEmpty() && !this.validationIntegerOrFloat(areaTextField.getText())  ) {
            customerInvoiceData.add( areaTextField.getText() );
        } else {
            areaTextField.validate();
        }
        
        // DatePicker Validator.
        if ( !datePicker.getValue().toString().isEmpty() ){
            customerInvoiceData.add( datePicker.getValue().toString() );
        } else {
            
        }
        // Total Amount Validator.
        if ( !totalAmountTextField.getText().isEmpty() ) {
            customerInvoiceData.add( totalAmountTextField.getText());
        }
        
        // invoice validator
        if ( !invoiceId.getText().isEmpty() ) {
            customerInvoiceData.add( invoiceId.getText());
        }
        if ( invoiceProductTable.getItems().size() > 0 ) {
            
        if ( customerInvoiceData.size() == 4) {
            OrderModel orderModelObject = new OrderModel();
            ResultSet resultSet =  orderModelObject.insertInvoiceData(customerInvoiceData);        
            this.insertInvoiceProductTable( resultSet.getInt("id") , true );
            GeneratePDF generatePDF = new GeneratePDF();
            String path =  generatePDF.generatePDFInvoice( String.valueOf(resultSet.getInt("id")));
            // if path is not empty.
            if ( !path.isEmpty() ){
                ValidationDialog validationDialog = new ValidationDialog();
                validationDialog.ShowDialog("Good News", 8);
            }
        } else if ( customerInvoiceData.size() == 5 )  {
            OrderModel orderModelObject = new OrderModel();
            ResultSet resultSet = orderModelObject.updateInvoiceData(customerInvoiceData);
            this.insertInvoiceProductTable( resultSet.getInt("id") , false );
            GeneratePDF generatePDF = new GeneratePDF();
            String path =  generatePDF.generatePDFInvoice( String.valueOf(resultSet.getInt("id")));
            // if path is not empty.
            if ( !path.isEmpty() ){
                ValidationDialog validationDialog = new ValidationDialog();
                validationDialog.ShowDialog("Good News", 8);
            } 
        }
        } else {
            ValidationDialog validationDialog = new ValidationDialog();
            validationDialog.ShowDialog("", 2);
        }
        
    }
    
    /**
     *
     * @param invoiceId
     * @param checkInsert
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public void  insertInvoiceProductTable( int invoiceId, boolean checkInsert ) throws SQLException, ClassNotFoundException {
        
        // Declare Invoice Product Table Size.
        int InvoiceProductTableSize = invoiceProductTable.getItems().size();
        
        // If InvoiceProductTableSize is greater than zero.
        if ( InvoiceProductTableSize > 0 ) {
            // For loops.
            for (int i = 0; i < InvoiceProductTableSize; i++) {
                // Declare ArrayList productInvoiceList.
                ArrayList<String>  productInvoiceList = new ArrayList<String>();
                // Declare InvoiceProductTable Object.
                InvoiceProduct invoiceProductObject = invoiceProductTable.getItems().get(i);
                productInvoiceList.add( String.valueOf(invoiceProductObject.getProductId()) );
                productInvoiceList.add( String.valueOf(invoiceProductObject.getProductTradePrice()));
                productInvoiceList.add( String.valueOf(invoiceProductObject.getProductQuanlity()));
                productInvoiceList.add( String.valueOf(invoiceProductObject.getProductAmount()));
                productInvoiceList.add( String.valueOf( invoiceId ) );    
                
                // Declare Order Model Object.
                OrderModel orderModel = new OrderModel();
                // Order Insert Invoice Product List Function Call.
                if ( checkInsert == true ) {
                    orderModel.insertInvoiceProductList(productInvoiceList, Integer.toString(invoiceId));
                } else {
                    orderModel.updateInvoiceProductList(productInvoiceList, Integer.toString(invoiceId));
                }
                
            }
        }
    }

    /**
     *
     * @param orderListObject
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public void setInvoiceDataInView( OrderList orderListObject) throws SQLException, ClassNotFoundException {
    
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-d");
        String productName = "";
        int productPacking = 0;
        LocalDate localDate = LocalDate.parse(orderListObject.getOrderDate(), formatter);
        // set text field.
        this.invoiceId.setText( String.valueOf(orderListObject.getOrderId()) );
        this.customerNameTextField.setValue( orderListObject.getOrderName() );
        this.areaTextField.setText( orderListObject.getOrderArea() );
        this.datePicker.setValue( localDate );
        this.totalAmountTextField.setText( orderListObject.getOrderAmount() );
        OrderListModel listModel = new OrderListModel();
        // get all invoice product by id function call.
        ResultSet resultSet = listModel.getAllInvoiceProductById(orderListObject);
        // while loops
        while (resultSet.next()) {
            // function call get product detail by id.
            ResultSet productDetail = listModel.getProductById( resultSet.getString("productid"));
            // if product detail have next.
            if ( productDetail.next()) {
                // get trade price float.
                float tradePrice = (float) resultSet.getDouble("tradeprice");
                // invoice product object.
                InvoiceProduct invoiceProduct = new InvoiceProduct(productDetail.getString("productname"), productDetail.getInt("productpacking"), Integer.valueOf(resultSet.getString("quanlity")), tradePrice, Float.valueOf(resultSet.getString("amount")), Integer.valueOf(resultSet.getString("productid")));
                // add object in table.
                productInvoiceData.add(invoiceProduct);
            }
        }
    }
    
}
