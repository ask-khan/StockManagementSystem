/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RequiredFieldValidator;
import handler.ProductTable;
import handler.StockTable;
import handler.ValidationDialog;
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
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import models.CustomerModel;
import models.DashboardModel;
import models.OrderModel;
import models.StockModel;

/**
 * FXML Controller class
 *
 * @author Ahmed
 */
public class DashboardController implements Initializable {

    // Declare product ,stock, customer and dashboard panel.
    @FXML
    private Tab tabPanelProduct, tabPanelStock, tabPanelCustomers, tabPanelDashboard;

    // Declare product price list.

    /**
     *
     */
    @FXML
    public ArrayList<String> productPriceList = new ArrayList<>();

    // Declare product packing list.

    /**
     *
     */
    @FXML
    public ArrayList<String> productPackingList = new ArrayList<>();

    // Declare product selected id.

    /**
     *
     */
    @FXML
    public ArrayList<String> productSelectedId = new ArrayList<>();

    // Declare image icon.
    @FXML
    ImageView OrderImage;
    @FXML
    ImageView ImageReceivePayment;
    @FXML
    ImageView ImageOrderList;
    @FXML
    ImageView ImagePaymentLog;
    @FXML
    ImageView imageLogout;
    @FXML
    ImageView ImageAddUser, ImagePrintSettings, customerViewImage;

    // Declare delete customer button.
    @FXML
    JFXButton deleteCustomerButton, newStockButton;

    // Declare tableview productTable.
    @FXML
    private TableView<ProductTable> productTable = new TableView<ProductTable>();

    // Declare product name.
    @FXML
    private JFXComboBox<String> productNameComboBox = new JFXComboBox<String>();

    // Declare tableview customerTable.
    @FXML
    private TableView<customerTable> customerTableView = new TableView<customerTable>();

    // Declare stock id columns for stock table columns. 
    @FXML
    private TableColumn<StockTable, Integer> stockIdColumns;
    // Declare stock product name columns for stock table columns. 
    @FXML
    private TableColumn<StockTable, String> stockProductNameColumns, stockModifiedDateColumns, stockCreatedDateColumns, stockFunctionColumns, stockExpiredDateColumns, stockBatchNoColumns;
    // Declare stock product name columns for stock quanlity columns.
    @FXML
    private TableColumn<StockTable, Integer> stockQuanlityColumns;
    // Declare tableview customerTable.
    @FXML
    private TableView<StockTable> stockTableView = new TableView<StockTable>();

    // Declare customer id for table columns.
    @FXML
    private TableColumn<customerTable, Integer> customerIdTableColumns;
    // Declare contact table columns.
    @FXML
    private TableColumn<customerTable, String> customerContactTableColumns;
    // Declare customer name table columns.
    @FXML
    private TableColumn<customerTable, String> customerNameTableColumns;
    // Declare adress table columns
    @FXML
    private TableColumn<customerTable, String> customerAddressTableColumns;
    // Declare email address table columns.
    @FXML
    private TableColumn<customerTable, String> customerEmailAddressTableColumns;
    // Declare customer area table columens.
    @FXML
    private TableColumn<customerTable, String> customerAreaTableColumns;

    // Declare customer attribute.
    @FXML
    private JFXTextField customerNameTextField, customerContactTextField, customerAreaTextField, customerEmailAddress, customerAddress, customerIdTextField, currentStockTextField, addStockTextField;

    // Product Code.
    @FXML
    private TableColumn<ProductTable, Float> tradePrice;
    // Product Id.
    @FXML
    private TableColumn<ProductTable, Integer> productCode;
    // Product Name.
    @FXML
    private TableColumn<ProductTable, String> productName;
    // Product Packing.
    @FXML
    private TableColumn<ProductTable, Integer> productPacking;
    // Brand Name.
    @FXML
    private TableColumn<ProductTable, String> brandName;
    // Purchased Price. 
    @FXML
    private TableColumn<ProductTable, Float> purchasedPrice;
    // Supplier Name
    @FXML
    private TableColumn<ProductTable, String> supplierName;
    // Created Date
    @FXML
    private TableColumn<ProductTable, String> createdDate;
    // Prodcut Type.
    @FXML
    private TableColumn<ProductTable, String> productTypes;

    // Declare Observabilelist.
    @FXML
    private final ObservableList<ProductTable> productData = FXCollections.observableArrayList();
    // Declare Observabilelist for customer data.
    @FXML
    private final ObservableList<customerTable> customerData = FXCollections.observableArrayList();
    // Declare Observabilelist for stock data.
    @FXML
    private final ObservableList<StockTable> stockData = FXCollections.observableArrayList();

    // Declare Delete, Reset Button.
    @FXML
    private Button deleteButton, resetCustomerButton;

    // Product Tab Declaration.
    @FXML
    private JFXButton SaveProductButton, ResetProductButton, deleteStockButton;

    // Product Text Field.
    @FXML
    private JFXTextField ProductPriceTextField, ProductNameTextField, BrandTextField, SupplierTextField, TradePriceTextField, ProductPackingTextField, ProductIdTextField, batchNoTextField;
    
    @FXML
    private DatePicker expiredDateDatePicker;
    
    // Declare Cumbo Box Product Type.
    @FXML
    private JFXComboBox  productType;
    
    public ValidationDialog validationDialog = new ValidationDialog();
    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        ObservableList<String> options = 
        FXCollections.observableArrayList(
            "Nutraceutical",
            "Pharmaceutical"
        );
        
        productType.setItems(options);
        // get on select stock data function call.
        getOnSelectStockData();

        // get on select product data function call.
        getOnSelectProductData();

        // get Select Customer Data Function Call.
        getOnSelectCustomerData();

        // Onload Delete Stock Button Functionality.
        deleteStockButton.disableProperty().bind(Bindings.isEmpty(stockTableView.getSelectionModel().getSelectedItems()));

        // Onload Delete Customer Button Functionality.
        deleteCustomerButton.disableProperty().bind(Bindings.isEmpty(customerTableView.getSelectionModel().getSelectedItems()));

        // Onload Delete Button Hide Functionality.
        deleteButton.disableProperty().bind(Bindings.isEmpty(productTable.getSelectionModel().getSelectedItems()));

        Image image = new Image("/Images/invoice.png");
        OrderImage.setImage(image);

        Image image1 = new Image("/Images/printer-.png");
        ImagePrintSettings.setImage(image1);

        Image customerView = new Image("/Images/customer-view.png");
        customerViewImage.setImage(customerView);

        customerViewImage.setOnMouseClicked(new EventHandler<MouseEvent>(){
            
            @Override
            public void handle(MouseEvent event){
                try {
                    Node source = (Node) event.getSource();
                    Parent root = FXMLLoader.load(DashboardController.this.getClass().getResource("/views/customerView.fxml"));
                    Stage stage = new Stage();
                    stage.setTitle("Customer View");
                    stage.getIcons().add(new Image("file:user-icon.png"));
                    stage.setResizable(false);
                    stage.initModality(Modality.WINDOW_MODAL);
                    Scene scene = new Scene(root, 1000, 700);
                    stage.initOwner(source.getScene().getWindow());
                    scene.getStylesheets().add(DashboardController.this.getClass().getResource("/styles/customerview.css").toExternalForm());
                    stage.setScene(scene);
                    stage.show();
                } catch (IOException ex) {
                    Logger.getLogger(DashboardController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
                
            
        });
        
        ImagePrintSettings.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                try {
                    Node source = (Node) event.getSource();
                    Parent root = FXMLLoader.load(DashboardController.this.getClass().getResource("/views/printSetting.fxml"));
                    Stage stage = new Stage();
                    stage.setTitle("Print Setting");
                    stage.getIcons().add(new Image("file:user-icon.png"));
                    stage.setResizable(false);
                    stage.initModality(Modality.WINDOW_MODAL);
                    Scene scene = new Scene(root);
                    stage.initOwner(source.getScene().getWindow());
                    scene.getStylesheets().add(DashboardController.this.getClass().getResource("/styles/order.css").toExternalForm());
                    stage.setScene(scene);
                    stage.show();
                } catch (IOException ex) {
                    Logger.getLogger(DashboardController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        // Customer id set visible
        customerIdTextField.setVisible(false);
        // Set Visible
        ProductIdTextField.setVisible(false);
        // Order On Clicked Functionality.
        OrderImage.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                try {
                    Node source = (Node) event.getSource();
                    Parent root = FXMLLoader.load(DashboardController.this.getClass().getResource("/views/order.fxml"));
                    Stage stage = new Stage();
                    stage.setTitle("Order Dashboard");
                    stage.getIcons().add(new Image("file:user-icon.png"));
                    stage.setResizable(false);
                    stage.initModality(Modality.WINDOW_MODAL);

                    Scene scene = new Scene(root, 1000, 700);
                    stage.initOwner(source.getScene().getWindow());
                    scene.getStylesheets().add(DashboardController.this.getClass().getResource("/styles/order.css").toExternalForm());
                    stage.setScene(scene);
                    stage.show();
                } catch (IOException ex) {
                    Logger.getLogger(DashboardController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        // Set Payment Image.
        Image paymentImage = new Image("/Images/report.png");
        ImageReceivePayment.setImage(paymentImage);
        
        // Image Logout On Mouse Clicked.        
        ImageReceivePayment.setOnMouseClicked((MouseEvent event) -> {
            try {
                //((Node) (event.getSource())).getScene().getWindow().hide();
                Parent root = FXMLLoader.load(getClass().getResource("/views/report.fxml"));
                Stage stage = new Stage();
                stage.setTitle("Generate Report");
                stage.getIcons().add(new Image("file:user-icon.png"));
                stage.setResizable(false);
                Scene scene = new Scene(root);
                scene.getStylesheets().add(getClass().getResource("/styles/report.css").toExternalForm());
                stage.setScene(scene);
                stage.show();
            } catch (IOException ex) {
                Logger.getLogger(DashboardController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        

        // Set Sale Summary Image.
        Image salesSummaryImage = new Image("/Images/sale-report.png");
        ImageOrderList.setImage(salesSummaryImage);

        // Credit Card Image.
        //Image paymentLogImage = new Image("/Images/credit-card.png");
        //ImagePaymentLog.setImage(paymentLogImage);
        // Logout Image.
        Image logoutImages = new Image("/Images/logout.png");
        imageLogout.setImage(logoutImages);

        // Image Logout On Mouse Clicked.        
        imageLogout.setOnMouseClicked((MouseEvent event) -> {
            try {
                ((Node) (event.getSource())).getScene().getWindow().hide();
                Parent root = FXMLLoader.load(getClass().getResource("/views/login.fxml"));
                Stage stage = new Stage();
                stage.setTitle("Main Dashboard");
                stage.getIcons().add(new Image("file:user-icon.png"));
                stage.setResizable(false);
                Scene scene = new Scene(root);
                scene.getStylesheets().add(getClass().getResource("/styles/dashboard.css").toExternalForm());
                stage.setScene(scene);
                stage.show();
            } catch (IOException ex) {
                Logger.getLogger(DashboardController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        ImageOrderList.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {
                    Node source = (Node) event.getSource();
                    //Window theStage = 
                    Parent root = FXMLLoader.load(DashboardController.this.getClass().getResource("/views/orderlist.fxml"));
                    Stage stage = new Stage();
                    stage.setTitle("Order List");
                    stage.getIcons().add(new Image("file:user-icon.png"));
                    stage.setResizable(false);
                    // Specifies the modality for new window.
                    stage.initModality(Modality.WINDOW_MODAL);

                    // Specifies the owner Window (parent) for new window
                    //stage.initOwner(root);
                    Scene scene = new Scene(root);
                    stage.initOwner(source.getScene().getWindow());
                    scene.getStylesheets().add(DashboardController.this.getClass().getResource("/styles/orderList.css").toExternalForm());
                    stage.setScene(scene);
                    stage.show();
                } catch (IOException ex) {
                    Logger.getLogger(DashboardController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        
        // Set Value for stock table.
        stockIdColumns.setCellValueFactory(new PropertyValueFactory<StockTable, Integer>("stockIdColumns"));
        stockProductNameColumns.setCellValueFactory(new PropertyValueFactory<StockTable, String>("stockProductNameColumns"));
        stockQuanlityColumns.setCellValueFactory(new PropertyValueFactory<StockTable, Integer>("stockQuanlityColumns"));
        stockFunctionColumns.setCellValueFactory(new PropertyValueFactory<StockTable, String>("stockFunctionColumns"));
        stockModifiedDateColumns.setCellValueFactory(new PropertyValueFactory<StockTable, String>("stockModifiedDateColumns"));
        stockCreatedDateColumns.setCellValueFactory(new PropertyValueFactory<StockTable, String>("stockCreatedDateColumns"));
        stockExpiredDateColumns.setCellValueFactory(new PropertyValueFactory<StockTable, String>("stockExpiredDateColumns"));
        stockBatchNoColumns.setCellValueFactory(new PropertyValueFactory<StockTable, String>("stockBatchNoColumns"));
        stockTableView.setItems(stockData);

        // Set Values for product table.
        tradePrice.setCellValueFactory(new PropertyValueFactory<ProductTable, Float>("tradePrice"));
        productCode.setCellValueFactory(new PropertyValueFactory<ProductTable, Integer>("productCode"));
        productName.setCellValueFactory(new PropertyValueFactory<ProductTable, String>("productName"));
        productPacking.setCellValueFactory(new PropertyValueFactory<ProductTable, Integer>("productPacking"));
        brandName.setCellValueFactory(new PropertyValueFactory<ProductTable, String>("brandName"));
        purchasedPrice.setCellValueFactory(new PropertyValueFactory<ProductTable, Float>("purchasedPrice"));
        supplierName.setCellValueFactory(new PropertyValueFactory<ProductTable, String>("supplierName"));
        createdDate.setCellValueFactory(new PropertyValueFactory<ProductTable, String>("createdDate"));
        productTypes.setCellValueFactory(new PropertyValueFactory<ProductTable, String>("productTypes"));

        productTable.setItems(productData);

        // set values for customer table.
        customerIdTableColumns.setCellValueFactory(new PropertyValueFactory<customerTable, Integer>("customerIdTableColumns"));
        customerNameTableColumns.setCellValueFactory(new PropertyValueFactory<customerTable, String>("customerNameTableColumns"));
        customerContactTableColumns.setCellValueFactory(new PropertyValueFactory<customerTable, String>("customerContactTableColumns"));
        customerAddressTableColumns.setCellValueFactory(new PropertyValueFactory<customerTable, String>("customerAddressTableColumns"));
        customerEmailAddressTableColumns.setCellValueFactory(new PropertyValueFactory<customerTable, String>("customerEmailAddressTableColumns"));
        customerAreaTableColumns.setCellValueFactory(new PropertyValueFactory<customerTable, String>("customerAreaTableColumns"));

        customerTableView.setItems(customerData);

        // Set On Mouse Clicked Functionalit
        productTable.setOnMouseClicked((MouseEvent event1) -> {
            ProductTable productData1 = productTable.getSelectionModel().getSelectedItem();

            if (event1.getClickCount() == 3) {
                setProductTextField(productData1);
                productData.remove(productData1);
            }
        });

        customerTableView.setOnMouseClicked((MouseEvent event1) -> {
            // Get current table view data.
            customerTable customerTableObject = customerTableView.getSelectionModel().getSelectedItem();

            if (event1.getClickCount() == 3) {
                this.setCustomerFields(customerTableObject);
                customerData.remove(customerTableObject);
            }
        });

        // Tab Product Panel.
        tabPanelProduct.setOnSelectionChanged(new EventHandler<Event>() {
            @Override
            public void handle(Event t) {

                if (tabPanelProduct.isSelected()) {
                    // get on select product data function call.
                    SupplierTextField.setText("Minart Traders");
                    getOnSelectProductData();

                }
            }
        });

        // Tab Stock Panel.
        tabPanelStock.setOnSelectionChanged(new EventHandler<Event>() {
            @Override
            public void handle(Event t) {

                if (tabPanelStock.isSelected()) {
                    // get on select stock data function call.
                    getOnSelectStockData();
                    getOnSelectProductData();
                }
            }
        });
        //tabPanelCustomers
        // Tab Stock Panel.
        tabPanelCustomers.setOnSelectionChanged(new EventHandler<Event>() {
            @Override
            public void handle(Event t) {

                if (tabPanelCustomers.isSelected()) {
                    // get on select customer data function call.
                    getOnSelectCustomerData();

                }
            }
        });
    }

    /**
     *
     */
    public void getOnSelectProductData() {
        // product table clear.
        productTable.getItems().clear();
        productSelectedId.clear();

        // Onload product data on table view.
        DashboardModel dashboardModel = new DashboardModel();
        try {
            ResultSet resultSet = dashboardModel.getAllProductOnLoad();
            ObservableList<String> productList = FXCollections.observableArrayList();
            while (resultSet.next()) {
                // Create Product Object.
                ProductTable productObject = new ProductTable(resultSet.getInt("id"), resultSet.getInt("productpacking"), (float) resultSet.getDouble("tradeprice"), resultSet.getString("productname"), resultSet.getString("brandname"), (float) resultSet.getDouble("purchaseprice"), resultSet.getString("suppliername"), resultSet.getString("productype"), resultSet.getString("modifieddate"));
                // Product Add Table.
                productData.add(productObject);

                productList.add(productObject.getProductName());
                productPriceList.add(String.valueOf(productObject.getPurchasedPrice()));
                productPackingList.add(String.valueOf(productObject.getProductPacking()));
                productSelectedId.add(String.valueOf(productObject.getProductCode()));
            }
            productNameComboBox.setItems(productList);
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(DashboardController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // get on select stock data.

    /**
     *
     */
    public void getOnSelectStockData() {
        System.out.println( "getOnSelectStockData");
        // stock table view clear.
        stockTableView.getItems().clear();

        // Onload stock data on table view.
        StockModel stockModel = new StockModel();
        try {
            ResultSet resultSet = stockModel.getAllStockData();
            while (resultSet.next()) {
                StockTable stockTable = new StockTable(resultSet.getInt("id"), resultSet.getString("productname"), resultSet.getInt("stockquanlity"), resultSet.getString("modifieddate"), resultSet.getString("createddate"), resultSet.getString("stockfunction"), resultSet.getString("expireddate"), resultSet.getString("batchno"));
                
                stockData.add(stockTable);
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DashboardController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(DashboardController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    // get on select customer data.

    /**
     *
     */
    public void getOnSelectCustomerData() {
        // clear customer data.
        customerTableView.getItems().clear();

        // Onload customer data on table view.
        CustomerModel customerModel = new CustomerModel();
        try {
            ResultSet resultSet = customerModel.getAllCustomerOnLoad();
            while (resultSet.next()) {
                // Declare customer table object.
                customerTable customerTableObject = new customerTable(resultSet.getInt("id"), resultSet.getString("customercontact"), resultSet.getString("customername"), resultSet.getString("customeraddress"), resultSet.getString("customeremailaddress"), resultSet.getString("customerarea"));
                // add customer table object.
                customerData.add(customerTableObject);
                // reset customer fields function call.
                resetCustomerFields();
            }
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(DashboardController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    /*
     * Save Product Functionality.
     * @param: event: ActionEvent
     **/
    @FXML
    private void saveProductFunction(ActionEvent event) throws SQLException, ClassNotFoundException {
        ArrayList<String> productInformation = new ArrayList<String>();

        // Product Price Validator.
        RequiredFieldValidator productPriceValidator = new RequiredFieldValidator();
        // Product Name Validator.
        RequiredFieldValidator productNameValidator = new RequiredFieldValidator();
        // Brand Name Validator.
        RequiredFieldValidator BrandNameValidator = new RequiredFieldValidator();
        // Supplier Name Validator.
        RequiredFieldValidator SupplierNameValidator = new RequiredFieldValidator();
        // Product Packing Validator.
        RequiredFieldValidator productPackingValidator = new RequiredFieldValidator();
        // Trade Price Validator.
        RequiredFieldValidator tradePriceValidator = new RequiredFieldValidator();

        // Product Packing Message.
        productPackingValidator.setMessage("Product Packing Cannot be Empty.");
        ProductPackingTextField.getValidators().add(productPackingValidator);

        // Supplier Name Message.
        SupplierNameValidator.setMessage("Supplier Name Cannot be Empty.");
        SupplierTextField.getValidators().add(SupplierNameValidator);

        //Brand Name Is validation.
        BrandNameValidator.setMessage("Brand Name Cannot Be Empty.");
        BrandTextField.getValidators().add(BrandNameValidator);

        //Product Name Is validation.
        productNameValidator.setMessage("Product Name Cannot Be Empty.");
        ProductNameTextField.getValidators().add(productNameValidator);

        //Product Price Is validation.
        productPriceValidator.setMessage("Product Price Cannot Be Empty.");
        ProductPriceTextField.getValidators().add(productPriceValidator);

        // Trade Price Is validation.
        tradePriceValidator.setMessage("Trade Price Cannot Be Empty.");
        TradePriceTextField.getValidators().add(tradePriceValidator);

        // Product Id Text Field Validation.
        if (!ProductNameTextField.getText().isEmpty() && this.validationIntegerOrFloat(ProductNameTextField.getText())) {
            productInformation.add(ProductNameTextField.getText());
        } else {
            ProductNameTextField.validate();
        }
        // Product Price Text Field.
        if (!ProductPriceTextField.getText().isEmpty() && this.validationIntegerOrFloat(ProductPriceTextField.getText())) {
            productInformation.add(ProductPriceTextField.getText());
        } else {
            ProductPriceTextField.validate();
        }

        // Product Name Text Field.
        if (!ProductNameTextField.getText().isEmpty()) {
            productInformation.add(ProductNameTextField.getText());
        } else {
            ProductNameTextField.validate();
        }

        // Brand Name Text Field.
        if (!BrandTextField.getText().isEmpty()) {
            productInformation.add(BrandTextField.getText());
        } else {
            BrandTextField.validate();
        }

        // Brand Name Text Field.
        if (!SupplierTextField.getText().isEmpty()) {
            productInformation.add(SupplierTextField.getText());
        } else {
            SupplierTextField.validate();
        }
        // Product Packing Text Field.
        if (!ProductPackingTextField.getText().isEmpty() && this.validationIntegerOrFloat(ProductPackingTextField.getText())) {
            productInformation.add(ProductPackingTextField.getText());
        } else {
            ProductPackingTextField.validate();
        }

        // Product Packing Text Field.
        if (!TradePriceTextField.getText().isEmpty() && this.validationIntegerOrFloat(TradePriceTextField.getText())) {
            productInformation.add(TradePriceTextField.getText());
        } else {
            TradePriceTextField.validate();
        }

        // Product Packing Text Field.
        if (!ProductIdTextField.getText().isEmpty() && this.validationIntegerOrFloat(ProductIdTextField.getText())) {
            productInformation.add(ProductIdTextField.getText());
        } else {
            //ProductIdTextField.validate();
        }
        
        if ( productType.getValue() != null){
            productInformation.add(productType.getValue().toString());
        } else {
            validationDialog.ShowDialog("", 16, "");
        }
        
        

        if (productInformation.size() == 7) {
            // Dashboard Model Object.
            DashboardModel dashboardModel = new DashboardModel();
            // insertProductFunctionality Function call.
            ResultSet insertProductFunctionality = dashboardModel.insertProductFunctionality(productInformation);
            // Product Object.
            ProductTable productObject = new ProductTable(insertProductFunctionality.getInt("id"), insertProductFunctionality.getInt("productpacking"), (float) insertProductFunctionality.getDouble("tradeprice"), insertProductFunctionality.getString("productname"), insertProductFunctionality.getString("brandname"), (float) insertProductFunctionality.getDouble("purchaseprice"), insertProductFunctionality.getString("suppliername"), insertProductFunctionality.getString("productype") , insertProductFunctionality.getString("modifieddate"));
            // Product Add Table.
            productData.add(productObject);
            // Reset Product Text Field.
            ResetProductTextField();
        } else if (productInformation.size() == 8) {
            //System.out.println("controllers.DashboardController.saveProductFunction() else");
            // Dashboard Model Object.
            DashboardModel dashboardModel = new DashboardModel();
            // updateProductFunctionality Function call.
            ResultSet updateProductFunctionality = dashboardModel.updateProductFunctionality(productInformation);
            // Product Object.
            ProductTable productObject = new ProductTable(updateProductFunctionality.getInt("id"), updateProductFunctionality.getInt("productpacking"), (float) updateProductFunctionality.getDouble("tradeprice"), updateProductFunctionality.getString("productname"), updateProductFunctionality.getString("brandname"), (float) updateProductFunctionality.getDouble("purchaseprice"), updateProductFunctionality.getString("suppliername"), updateProductFunctionality.getString("productype") , updateProductFunctionality.getString("modifieddate"));
            // Product Add Table.
            productData.add(productObject);
            // Reset Product Text Field.
            ResetProductTextField();
        }
    }

    /**
     * Add Delete Button
     *
     * @param event
     */
    @FXML
    private void ButtonDeleteAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        // Declare ProductTable Object.
        ProductTable addProduct = productTable.getSelectionModel().getSelectedItem();
        // Declare DashboardModel Object.
        DashboardModel dashboardModel = new DashboardModel();
        // Delete Product Function Call.
        boolean checkExist = dashboardModel.deleteProduct(addProduct.getProductCode());
        if (checkExist == true) {
            // Remove Product Function Call.
            productData.remove(addProduct);
            // Reset Product Function Call.
            ResetProductTextField();
        }
    }

    /*
     * Reset Product Functionality.
     * @param: event: ActionEvent
     **/
    @FXML
    private void resetProductFunction(ActionEvent event) {
        // Reset Product Function Call.
        ResetProductTextField();
        //System.out.println("controllers.DashboardController.resetProductFunction()");
    }

    // product name cumbo box function.
    @FXML
    private void productCumboBoxFunction() throws SQLException, ClassNotFoundException {
        //StockModel stockModelObject = new StockModel();
        //System.out.println( productSelectedId  +" " + productNameComboBox.getSelectionModel().getSelectedIndex() );
        //int productStockQuanlity = 0;
        // get stock by product id function call;
        OrderModel orderModel = new OrderModel();
        ResultSet productStockQuanlity = orderModel.getProductStockById(productSelectedId.get(productNameComboBox.getSelectionModel().getSelectedIndex()));
        //stockModelObject.getStockByProductId(productSelectedId.get(productNameComboBox.getSelectionModel().getSelectedIndex()));

        if (productStockQuanlity.next()) {
            if (productStockQuanlity.getString("productStock") == null) {
                currentStockTextField.setText("0");
            } else if (Integer.parseInt(productStockQuanlity.getString("productStock")) > 0) {
                currentStockTextField.setText(productStockQuanlity.getString("productStock"));
            } else if (Integer.parseInt(productStockQuanlity.getString("productStock")) < 0) {
                currentStockTextField.setText(productStockQuanlity.getString("productStock"));
            }
        }
    }

    @FXML
    private void newStockFunction(ActionEvent event) throws ClassNotFoundException, SQLException {
        //Declare customer data arraylist.
        ArrayList<String> stockInformation = new ArrayList<>();
        // Product Name Validator.
        RequiredFieldValidator addStockValidator = new RequiredFieldValidator();
        
        // Product Name Validator.
        RequiredFieldValidator batchNoValidator = new RequiredFieldValidator();
        
        //Add Stock Is validation.
        addStockValidator.setMessage("Add Stock Cannot Be Empty.");
        addStockTextField.getValidators().add(addStockValidator);

        //Add Stock Is validation.
        batchNoValidator.setMessage("Add Stock Cannot Be Empty.");
        batchNoTextField.getValidators().add(batchNoValidator);
        
        // if product name is null.
        if (productNameComboBox.getValue() == null) {

        } else {
            stockInformation.add(productNameComboBox.getValue());
        }

        if (productNameComboBox.getSelectionModel().getSelectedIndex() != -1) {
            stockInformation.add(productSelectedId.get(productNameComboBox.getSelectionModel().getSelectedIndex()));
        }

        // if add stock button 
        if (!addStockTextField.getText().isEmpty() && this.validationInteger(addStockTextField.getText())) {
            stockInformation.add(addStockTextField.getText());
        } else {
            addStockTextField.validate();
        }
        
        stockInformation.add("Stock Added");
        
        if (!batchNoTextField.getText().isEmpty()) {
            stockInformation.add(batchNoTextField.getText());
        } 
        
        if ( expiredDateDatePicker.getValue().toString() != null ) {
            stockInformation.add(expiredDateDatePicker.getValue().toString());
        }
        
        if (stockInformation.size() == 6) {
            //System.out.println(stockInformation);
            StockModel stockModel = new StockModel();
            ResultSet resultSet = stockModel.insertStockData(stockInformation);
            StockTable stockTable = new StockTable(resultSet.getInt("id"), resultSet.getString("productname"), resultSet.getInt("stockquanlity"), resultSet.getString("modifieddate"), resultSet.getString("createddate"), resultSet.getString("stockfunction"), resultSet.getString("expireddate"), resultSet.getString("batchno"));
            stockData.add(stockTable);
        }

    }

    @FXML
    private void deleteStockFunction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        StockTable stockTable = stockTableView.getSelectionModel().getSelectedItem();
        StockModel stockModel = new StockModel();
        boolean isStockDelete = stockModel.deleteStockData(stockTable);
        if (isStockDelete == true) {
            stockData.remove(stockTable);
        }
    }

    @FXML
    private void deleteCustomerFunctionality(ActionEvent event) throws SQLException, ClassNotFoundException {
        // Get current table view data.
        customerTable customerTableObject = customerTableView.getSelectionModel().getSelectedItem();
        // Customer Object.
        CustomerModel customerModel = new CustomerModel();
        boolean checkCustomer = customerModel.deleteCustomer(customerTableObject.getCustomerIdTableColumns());

        if (checkCustomer == true) {
            // Remove Customer Function Call.
            customerData.remove(customerTableObject);
            // reset customer fields function call.
            resetCustomerFields();

        } else {

        }
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
    public boolean validationInteger(String text) {
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
    public boolean validationIntegerOrFloat(String text) {
        return !text.matches(".*[a-z].*");
    }

    /*
     * validationEmailAddress - email address validation.
     * @param: email: String
     * @return boolean
     **/
    /**
     *
     * @param email
     * @return
     */
    public boolean validationEmailAddress(String email) {
        return email.matches("^[A-Za-z0-9+_.-]+@(.+)$");
    }

    /*
     * ResetProductTextField
     **/
    /**
     *
     */
    public void ResetProductTextField() {
        // Set Empty Set TextField.
        ProductNameTextField.setText("");
        ProductPriceTextField.setText("");
        BrandTextField.setText("");
        ProductPackingTextField.setText("");
        SupplierTextField.setText("");
        TradePriceTextField.setText("");
        ProductIdTextField.setText("");
    }

    /**
     * setProductTextField
     *
     * @param productTable
     */
    public void setProductTextField(ProductTable productTable) {
        // Set Empty Set TextField.
        ProductNameTextField.setText(productTable.getProductName());
        ProductPriceTextField.setText(String.valueOf(productTable.getPurchasedPrice()));
        BrandTextField.setText(productTable.getBrandName());
        ProductPackingTextField.setText(String.valueOf(productTable.getProductPacking()));
        SupplierTextField.setText(productTable.getSupplierName());
        TradePriceTextField.setText(String.valueOf(productTable.getTradePrice()));
        ProductIdTextField.setText(String.valueOf(productTable.getProductCode()));
        productType.setValue( productTable.getProductTypes() );
    }

    // Save Customer Function
    @FXML
    private void saveCustomerFunction(ActionEvent event) throws SQLException, ClassNotFoundException {
        //System.out.println("controllers.DashboardController.saveCustomerFunction()");
        //Declare customer data arraylist.
        ArrayList<String> customerInformation = new ArrayList<String>();
        // Customer name validator.
        RequiredFieldValidator customerNameValidator = new RequiredFieldValidator();
        // Contact validator.
        RequiredFieldValidator customerContactValidator = new RequiredFieldValidator();
        // Customer name validator.
        RequiredFieldValidator customerAreaValidator = new RequiredFieldValidator();
        // email address Validator.
        RequiredFieldValidator emailAddressValidator = new RequiredFieldValidator();
        // address Validator.
        RequiredFieldValidator addressValidator = new RequiredFieldValidator();

        // Set customer name validator.
        customerNameValidator.setMessage("Customer name cannot be empty.");
        customerNameTextField.getValidators().add(customerNameValidator);
        // if customer name cannot be empty.
        if (!customerNameTextField.getText().isEmpty() && !this.validationIntegerOrFloat(customerNameTextField.getText())) {
            customerInformation.add(String.valueOf(customerNameTextField.getText()));
        } else {
            customerNameTextField.validate();
        }
        // Set customer contact validator.
        customerContactValidator.setMessage("Customer cannot be empty.");
        customerContactTextField.getValidators().add(customerContactValidator);
        // customer contact cannot be empty.
        if (!customerContactTextField.getText().isEmpty() && validationInteger(customerContactTextField.getText())) {
            customerInformation.add(String.valueOf(customerContactTextField.getText()));
        } else {
            customerContactTextField.validate();
        }
        // Set customer area validator.
        customerAreaValidator.setMessage("Customer area cannot be empty.");
        customerAreaTextField.getValidators().add(customerAreaValidator);
        // Customer area text field cannot be empty.
        if (!customerAreaTextField.getText().isEmpty()) {
            customerInformation.add(customerAreaTextField.getText());
        } else {
            customerAreaTextField.validate();
        }
        // email address cannot be validator.
        emailAddressValidator.setMessage("Customer email address cannot be empty.");
        customerEmailAddress.getValidators().add(emailAddressValidator);
        // customer email address cannot be empty.
        if (!customerEmailAddress.getText().isEmpty() && this.validationEmailAddress(customerEmailAddress.getText())) {
            customerInformation.add(customerEmailAddress.getText());
        } else {
            customerEmailAddress.validate();
        }
        // Set customer address cannot be empty.
        addressValidator.setMessage("Address cannot be empty.");
        customerAddress.getValidators().add(addressValidator);
        // if customer address cannot be empty.
        if (!customerAddress.getText().isEmpty()) {
            customerInformation.add(customerAddress.getText());
        } else {
            customerAddress.validate();
        }
        // if customer id is not empty.
        if (!customerIdTextField.getText().isEmpty()) {
            customerInformation.add(customerIdTextField.getText());
        }

        //System.out.println( customerInformation );
        if (customerInformation.size() == 5) {
            //System.out.println( customerInformation );
            // Declare customer model object.
            CustomerModel customerModelObject = new CustomerModel();
            // Function call.
            ResultSet customerDataObject = customerModelObject.insertCustomerFunctionality(customerInformation);
            // Declare customer table object.
            customerTable customerTableObject = new customerTable(customerDataObject.getInt("id"), customerDataObject.getString("customercontact"), customerDataObject.getString("customername"), customerDataObject.getString("customeraddress"), customerDataObject.getString("customeremailaddress"), customerDataObject.getString("customerarea"));
            // add customer table object.
            customerData.add(customerTableObject);
            // reset customer fields function call.
            resetCustomerFields();
        } else if (customerInformation.size() == 6) {
            CustomerModel customerModelObject = new CustomerModel();
            // Function call.
            ResultSet customerDataObject = customerModelObject.updateCustomerFunctionality(customerInformation);
            // Declare customer table object.
            customerTable customerTableObject = new customerTable(customerDataObject.getInt("id"), customerDataObject.getString("customercontact"), customerDataObject.getString("customername"), customerDataObject.getString("customeraddress"), customerDataObject.getString("customeremailaddress"), customerDataObject.getString("customerarea"));
            // add customer table object.
            customerData.add(customerTableObject);
            // reset customer fields function call.
            resetCustomerFields();
        }
    }

    // Reset Customer Field Function.
    /**
     *
     */
    public void resetCustomerFields() {
        customerAreaTextField.setText("");
        customerContactTextField.setText("");
        customerEmailAddress.setText("");
        customerAddress.setText("");
        customerNameTextField.setText("");
        customerIdTextField.setText("");
    }

    // set customer fields
    /**
     *
     * @param table
     */
    public void setCustomerFields(customerTable table) {
        customerAreaTextField.setText(table.getCustomerAreaTableColumns());
        customerContactTextField.setText(table.getCustomerContactTableColumns());
        customerEmailAddress.setText(table.getCustomerEmailAddressTableColumns());
        customerAddress.setText(table.getCustomerAddressTableColumns());
        customerNameTextField.setText(table.getCustomerNameTableColumns());
        customerIdTextField.setText(String.valueOf(table.getCustomerIdTableColumns()));
    }

    /**
     *
     * @param actionEvent
     */
    @FXML
    public void resetCustomerFunction(ActionEvent actionEvent) {
        // reset function call;
        resetCustomerFields();
    }
}
