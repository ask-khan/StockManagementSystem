/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import com.itextpdf.text.DocumentException;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RequiredFieldValidator;
import handler.GeneratePDF;
import handler.InvoiceProduct;
import handler.OrderList;
import handler.ProductTable;
import handler.ValidationDialog;
import handler.customerTable;
import java.awt.print.PrinterException;
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
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import models.CustomerModel;
import models.DashboardModel;
import models.OrderListModel;
import models.OrderModel;
import org.controlsfx.control.textfield.TextFields;

/**
 * FXML Controller class
 *
 * @author Ahmed
 */
public class OrderController implements Initializable {

    // Declare Customer Name, Area Text, Quanlity, Product Packing, Trade Price.
    @FXML
    public JFXTextField areaTextField,
            stockTextField,
            quanlityTextField,
            productPackingTextField,
            tradePriceTextField,
            productdiscountTextField, customerIdTextField, expiredDate, bonusTextField;

    @FXML
    public ChoiceBox<String> discountPackage = new ChoiceBox<String>();

    // Declare order Print
    @FXML
    public JFXCheckBox orderPrint,
            orderWarranty;

    // Declare Date Picker.
    @FXML
    private DatePicker datePicker;

    private String selectedProductId;

    // Declare Product Line.
    @FXML
    private TextField productListTextfield;
    // Declare Product Line.
    @FXML
    private TextField customerNameTextField;

    @FXML
    private JFXComboBox<String> batchNo = new JFXComboBox<String>();

    @FXML
    private Button GenerateInvoiceButton;

    // Declare Add Product.
    @FXML
    private Button AddProductButton;

    // Declare Total Amount.
    @FXML
    private TextField totalAmountTextField, invoiceId, receivedAmountTextField;

    @FXML
    private Button deleteProductButton;

    // Declare Invoice Product Table.
    @FXML
    private TableView<InvoiceProduct> invoiceProductTable = new TableView<InvoiceProduct>();

    // Declare Product Name, Product Packing, Product Trade Price, Product Quanlity. Product Amount.
    @FXML
    private TableColumn<InvoiceProduct, String> productName, productExpiredDate, productBatchNo;
    @FXML
    private TableColumn<InvoiceProduct, Integer> productPacking, productId;
    @FXML
    private TableColumn<InvoiceProduct, Float> productTradePrice;
    @FXML
    private TableColumn<InvoiceProduct, Integer> productQuanlity;
    @FXML
    private TableColumn<InvoiceProduct, Integer> productBonus;
    @FXML
    private TableColumn<InvoiceProduct, Float> productAmount;
    @FXML
    private TableColumn<InvoiceProduct, Double> productDiscount;

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
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Declare discountPackage variable for choicebox.
        final String[] discountPackages = new String[]{"Individuals", "Bonus"};

        // Default productdiscountTextField, bonusTextField  Hidden.
        productdiscountTextField.setVisible(false);
        bonusTextField.setVisible(false);

        // Declare Packages for choicebox
        ObservableList<String> cursors = FXCollections.observableArrayList("Individuals", "Bonus");
        discountPackage.setItems(cursors);

        // Get Select Choice Box value whenever its change.
        discountPackage.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue observable, Number value, Number new_value) {
                // Check if discount package is equal to combine
                if (discountPackages[new_value.intValue()].equals("Individuals")) {
                    productdiscountTextField.setVisible(true);
                    bonusTextField.setVisible(false);
                } else if (discountPackages[new_value.intValue()].equals("Bonus")) {
                    productdiscountTextField.setVisible(false);
                    bonusTextField.setVisible(true);
                }
            }
        });

        // Declare Date Object and set to right now date.
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDate now = LocalDate.now();
        customerIdTextField.setVisible(false);
        // Set Product Discount 
        //productdiscountTextField.setText("0");

        // Set Received.
        receivedAmountTextField.setText("0");

        // Total Amount.
        totalAmountTextField.setText("0.0");

        // Set Current Date on Date Picker.
        datePicker.setValue(now);

        // Set totalamount to Disable
        totalAmountTextField.setDisable(true);

        // Hide Expired date and product packing
        expiredDate.setVisible(false);
        productPackingTextField.setVisible(false);

        // Onload Delete Button Hide Functionality.
        deleteProductButton.disableProperty().bind(Bindings.isEmpty(invoiceProductTable.getSelectionModel().getSelectedItems()));
        try {
            // Onload customer data on table view.
            CustomerModel customerModel = new CustomerModel();
            ResultSet customerResult = customerModel.getAllCustomerOnLoad();
            //ObservableList<String> customerList = FXCollections.observableArrayList();
            ArrayList<String> customerList = new ArrayList<>();
            while (customerResult.next()) {
                // Declare customer table object.
                customerTable customerTableObject = new customerTable(customerResult.getInt("id"), customerResult.getString("customercontact"), customerResult.getString("customername"), customerResult.getString("customeraddress"), customerResult.getString("customeremailaddress"), customerResult.getString("customerarea"));
                customerList.add(customerTableObject.getCustomerIdTableColumns() + ": " + customerTableObject.getCustomerNameTableColumns());
                customerContact.add(customerTableObject.getCustomerContactTableColumns());
                customerArea.add(customerTableObject.getCustomerAreaTableColumns());
            }
            TextFields.bindAutoCompletion(customerNameTextField, customerList);
            //customerNameTextField.setItems(customerList);

            DashboardModel dashboardModel = new DashboardModel();
            ResultSet resultSet = dashboardModel.getAllProductOnLoad();
            ObservableList<String> productList = FXCollections.observableArrayList();

            while (resultSet.next()) {
                ProductTable productObject = new ProductTable(resultSet.getInt("id"), resultSet.getInt("productpacking"), (float) resultSet.getDouble("tradeprice"), resultSet.getString("productname"), resultSet.getString("brandname"), (float) resultSet.getDouble("purchaseprice"), resultSet.getString("suppliername"), resultSet.getString("productype"), resultSet.getString("modifieddate"));
                productList.add(productObject.getProductCode() + ":" + productObject.getProductName());
            }
            TextFields.bindAutoCompletion(productListTextfield, productList);
            //productLineComboBox.setItems(productList);

            // Set Values for table
            productTradePrice.setCellValueFactory(new PropertyValueFactory<InvoiceProduct, Float>("productTradePrice"));
            productName.setCellValueFactory(new PropertyValueFactory<InvoiceProduct, String>("productName"));
            productPacking.setCellValueFactory(new PropertyValueFactory<InvoiceProduct, Integer>("productPacking"));
            productAmount.setCellValueFactory(new PropertyValueFactory<InvoiceProduct, Float>("productAmount"));
            productQuanlity.setCellValueFactory(new PropertyValueFactory<InvoiceProduct, Integer>("productQuanlity"));
            productBonus.setCellValueFactory(new PropertyValueFactory<InvoiceProduct, Integer>("productBonus"));
            productId.setCellValueFactory(new PropertyValueFactory<InvoiceProduct, Integer>("productId"));
            productDiscount.setCellValueFactory(new PropertyValueFactory<InvoiceProduct, Double>("productDiscount"));
            productBatchNo.setCellValueFactory(new PropertyValueFactory<InvoiceProduct, String>("productBatchNo"));
            productExpiredDate.setCellValueFactory(new PropertyValueFactory<InvoiceProduct, String>("productExpiredDate"));

            invoiceProductTable.setItems(productInvoiceData);
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(OrderController.class.getName()).log(Level.SEVERE, null, ex);
        }

        //productLineComboBox.getSelectionModel().selectedIndexProperty().addListener(listener);
    }

    // Add Product Invoice Function.
    @FXML
    private void addProductInvoiceFunction(ActionEvent event) {
        ArrayList<String> productInvoiceList = new ArrayList<String>();

        // Product Packing Validator.
        RequiredFieldValidator productPackingValidator = new RequiredFieldValidator();
        // Product Trade Price Validator.
        RequiredFieldValidator tradePriceValidator = new RequiredFieldValidator();
        // Quanlity Validator.
        RequiredFieldValidator productQuanlityValidator = new RequiredFieldValidator();
        // Product Name Validator.
        RequiredFieldValidator productNameValidator = new RequiredFieldValidator();
        // Product Bonus Validator.
        RequiredFieldValidator productBonusValidator = new RequiredFieldValidator();
        // Product Discount Validator.
        RequiredFieldValidator productDiscountValidator = new RequiredFieldValidator();

        // Product Bonus Message.
        productBonusValidator.setMessage("Product Bonus cannot be empty.");
        bonusTextField.getValidators().add(tradePriceValidator);

        // Product Discount Message.
        productDiscountValidator.setMessage("Product Discount cannot be empty.");
        productdiscountTextField.getValidators().add(productDiscountValidator);

        // Product Packing Message.
        productPackingValidator.setMessage("Product packing cannot be empty.");
        productPackingTextField.getValidators().add(productPackingValidator);

        // Product Quanlity Message.
        productQuanlityValidator.setMessage("Product quanlity cannot be empty");
        quanlityTextField.getValidators().add(productQuanlityValidator);

        tradePriceValidator.setMessage("Trade price cannot be empty.");
        tradePriceTextField.getValidators().add(tradePriceValidator);

        // Product Packing Validation.
        if (!productPackingTextField.getText().isEmpty() && this.validationInteger(productPackingTextField.getText())) {
            productInvoiceList.add(productPackingTextField.getText());
        } else {
            productPackingTextField.validate();
        }

        // Product Quanlity Validation.
        if (!quanlityTextField.getText().isEmpty() && this.validationInteger(quanlityTextField.getText())) {
            productInvoiceList.add(quanlityTextField.getText());
        } else {
            quanlityTextField.validate();
        }

        // get product package.
        String productPackage = (String) (discountPackage.getValue());

        if (productPackage == null) {
            ValidationDialog validationDialog = new ValidationDialog();
            validationDialog.ShowDialog("", 17, "");
            return;
        }

        // Trade Price Validation.
        if (!tradePriceTextField.getText().isEmpty() && this.validationIntegerOrFloat(tradePriceTextField.getText())) {
            productInvoiceList.add(tradePriceTextField.getText());
        } else {
            tradePriceTextField.validate();
            return;
        }

        // Product Line Combo box Validation.
        if (!productListTextfield.getText().isEmpty()) {
            productInvoiceList.add(productListTextfield.getText());
        } else {
            //productLineComboBox.validate();
        }

        // Individuals Product Package.
        if (productdiscountTextField.getText().isEmpty() && "Individuals".equals(productPackage)) {
            productdiscountTextField.validate();
            return;
        }

        // Bonus Product Package.
        if (bonusTextField.getText().isEmpty() && "Bonus".equals(productPackage)) {
            bonusTextField.validate();
            return;
        }

        // Quanlity Validation.
        if (!quanlityTextField.getText().isEmpty() && this.validationInteger(quanlityTextField.getText()) && !tradePriceTextField.getText().isEmpty() && this.validationIntegerOrFloat(tradePriceTextField.getText())) {
            // Declare discount.
            double discount = 0;
            if ("Bonus".equals(productPackage)) {
                discount = (double) Double.parseDouble("0") / 100;
            } else if ("Individuals".equals(productPackage)) {
                discount = (double) Double.parseDouble(productdiscountTextField.getText()) / 100;
            }
            double productAmountSelected = (double) ((Float.parseFloat(tradePriceTextField.getText()) * Integer.parseInt(quanlityTextField.getText()) * (double) discount));

            productAmountSelected = Float.parseFloat(tradePriceTextField.getText()) * Integer.parseInt(quanlityTextField.getText()) - productAmountSelected;
            productInvoiceList.add(String.valueOf(productAmountSelected));
        }

        // Product Discount Validation.
        if (!productdiscountTextField.getText().isEmpty() && this.validationIntegerOrFloat(productdiscountTextField.getText()) && "Individuals".equals(productPackage)) {
            productInvoiceList.add(String.valueOf(Double.parseDouble(productdiscountTextField.getText())));
        } else if ("Bonus".equals(productPackage)) {
            productInvoiceList.add("0");
        }

        // Product Batch No Validation.
        if (!batchNo.getValue().isEmpty()) {
            productInvoiceList.add(batchNo.getValue());
        }
        // Expired Date Validation.
        if (!expiredDate.getText().isEmpty()) {
            productInvoiceList.add(expiredDate.getText());
        }

        // Product Package 
        if ("Individuals".equals(productPackage)) {
            productInvoiceList.add("0");
        } else if ("Bonus".equals(productPackage)) {
            productInvoiceList.add(bonusTextField.getText());
        }

        //System.out.println(productInvoiceList);
        // if product voice list is valid.
        if (productInvoiceList.size() == 9) {
            //System.out.println(productInvoiceList);
            if (Integer.parseInt(stockTextField.getText()) >= Integer.parseInt(quanlityTextField.getText())) {
                // Add Product Data Into Table.
                InvoiceProduct invoiceProduct = new InvoiceProduct(productInvoiceList.get(3), Integer.valueOf(productInvoiceList.get(0)), Integer.valueOf(productInvoiceList.get(1)), Float.valueOf(productInvoiceList.get(2)), Float.valueOf(productInvoiceList.get(4)), Integer.valueOf(selectedProductId), Double.valueOf(productInvoiceList.get(5)), productInvoiceList.get(7), productInvoiceList.get(6), Integer.valueOf(productInvoiceList.get(8)));
                productInvoiceData.add(invoiceProduct);
                // Reset Function Call.
                resetProductFields();

                if (!totalAmountTextField.getText().isEmpty()) {
                    float total = Float.valueOf(totalAmountTextField.getText()) + Float.valueOf(productInvoiceList.get(4));
                    totalAmountTextField.setText(String.valueOf(total));
                } else {
                    float total = Float.valueOf(productInvoiceList.get(4));
                    totalAmountTextField.setText(String.valueOf(total));
                }
            } else {
                ValidationDialog validationDialog = new ValidationDialog();
                validationDialog.ShowDialog("Stock Error", 9, "");
            }

        } else {
            System.out.print("else");
        }
    }

    private void resetProductFields() {
        productPackingTextField.setText("");
        productListTextfield.setText("");
        batchNo.setValue("");
        stockTextField.setText("");
        quanlityTextField.setText("");
        productdiscountTextField.setText("");
        tradePriceTextField.setText("");
    }

    @FXML
    private void batchNoFunction() throws ClassNotFoundException, SQLException {
        //System.out.println("controllers.OrderController.batchNoFunction()");
        //System.out.println( selectedProductId + ":" + batchNo.getValue() );
        OrderModel orderModel = new OrderModel();
        ResultSet getStockQuanlity = orderModel.getQuanlityByBatchId(selectedProductId, batchNo.getValue());
        ResultSet batchDetails = orderModel.getExpiredDateByBatchId(batchNo.getValue());
        if (getStockQuanlity.next()) {
            //System.out.println(resultSet.getString("productStock"));
            if (getStockQuanlity.getString("productStock") == null) {
                stockTextField.setText("0");
            } else if (Integer.parseInt(getStockQuanlity.getString("productStock")) > 0) {
                stockTextField.setText(getStockQuanlity.getString("productStock"));
            } else if (Integer.parseInt(getStockQuanlity.getString("productStock")) < 0) {
                stockTextField.setText(getStockQuanlity.getString("productStock"));
            }
        }

        if (batchDetails.next()) {
            expiredDate.setText(batchDetails.getString("expireddate"));
        }
    }

    // product cumbo onclick function. 
    @FXML
    private void productListFunctionality() throws ClassNotFoundException, SQLException {
        // Declare stockQuanlity, stock name.
        ArrayList<String> stockQuanlity = new ArrayList<String>();
        ArrayList<String> stockBatchName = new ArrayList<String>();

        //System.out.println( productListTextfield.getText().substring(0,productListTextfield.getText().indexOf(":")) );
        DashboardModel dashboardModel = new DashboardModel();
        selectedProductId = productListTextfield.getText().substring(0, productListTextfield.getText().indexOf(":"));
        ResultSet productDetail = dashboardModel.getProductById(productListTextfield.getText().substring(0, productListTextfield.getText().indexOf(":")));

        OrderModel orderModel = new OrderModel();
        // System.out.println(productSelectedId.get(productListTextfield.getSelectionModel().getSelectedIndex()));
        ResultSet resultSet = orderModel.getProductBatchNo(productListTextfield.getText().substring(0, productListTextfield.getText().indexOf(":")));

        while (resultSet.next()) {
            if (resultSet.getString("batchno") != null) {
                stockBatchName.add(resultSet.getString("batchno"));
            }
        }

        //System.out.println( stockBatchName );
        batchNo.getItems().clear();
        batchNo.getItems().addAll(stockBatchName);

        if (productDetail.next()) {
            productPackingTextField.setText(Integer.toString(productDetail.getInt("productpacking")));
            tradePriceTextField.setText(Double.toString(productDetail.getDouble("tradeprice")));
            productListTextfield.setText(productDetail.getString("productname"));
        }

    }

    @FXML
    private void onChangeBatchNo() {

    }

    // customer cumbo box onclick function.
    @FXML
    private void customerCumboBoxFunction() throws ClassNotFoundException, SQLException {

        CustomerModel customerModel = new CustomerModel();
        customerIdTextField.setText(customerNameTextField.getText().substring(0, customerNameTextField.getText().indexOf(":")));
        ResultSet customerInfoResultSet = customerModel.getCustomerInfoById(customerNameTextField.getText().substring(0, customerNameTextField.getText().indexOf(":")));
        System.out.println(customerInfoResultSet);
        if (customerInfoResultSet.next()) {
            // set customer name.
            customerNameTextField.setText(customerInfoResultSet.getString("customername"));
            // set customer area.
            areaTextField.setText(customerInfoResultSet.getString("customerarea"));
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

            float totalAmount = Float.valueOf(totalAmountTextField.getText()) - invoiceProductTableObject.getProductAmount();
            totalAmountTextField.setText(String.valueOf(totalAmount));

            // Remove Product
            productInvoiceData.remove(invoiceProductTableObject);

        } else {
            //System.out.println("Delete");
        }
    }

    // GenerateInvoiceButtonFunction
    @FXML
    private void GenerateInvoiceButtonFunction() throws SQLException, ClassNotFoundException, IOException, DocumentException, PrinterException {
        ArrayList<String> customerInvoiceData = new ArrayList<String>();

        // Product customer Name Validator.
        RequiredFieldValidator customerNameValidator = new RequiredFieldValidator();
        // Product customer Name Validator.
        RequiredFieldValidator areaValidator = new RequiredFieldValidator();
        // Date Picker Validator.
        RequiredFieldValidator datePickerValidator = new RequiredFieldValidator();

        // Area Validator.
        areaValidator.setMessage("Area cannot be empty.");
        areaTextField.getValidators().add(areaValidator);
        //System.out.println(customerNameTextField.getText());
        // Customer Name is validator
        if (!customerNameTextField.getText().isEmpty() && !this.validationIntegerOrFloat(customerNameTextField.getText())) {
            customerInvoiceData.add(customerNameTextField.getText());
        } else if (customerNameTextField.getText() == null) {
            //customerNameTextField.validate();
        }

        // Area is validator.
        if (!areaTextField.getText().isEmpty()) {
            customerInvoiceData.add(areaTextField.getText());
        } else {
            areaTextField.validate();
        }

        // DatePicker Validator.
        if (!datePicker.getValue().toString().isEmpty()) {
            customerInvoiceData.add(datePicker.getValue().toString());
        } else {

        }
        // Total Amount Validator.
        if (!totalAmountTextField.getText().isEmpty()) {
            customerInvoiceData.add(totalAmountTextField.getText());
        }

        // Invoice Id validator
        if (!invoiceId.getText().isEmpty()) {
            customerInvoiceData.add(invoiceId.getText());
        }

        // Received Amount Validator.
        if (!receivedAmountTextField.getText().isEmpty() && validationIntegerOrFloat(receivedAmountTextField.getText())) {
            customerInvoiceData.add(receivedAmountTextField.getText());
        }

        // Customer Id Validator.
        if (!customerIdTextField.getText().isEmpty() && validationIntegerOrFloat(customerIdTextField.getText())) {
            customerInvoiceData.add(customerIdTextField.getText());
        }

        // Customer Information. 
        customerInvoiceData.add(String.valueOf(orderWarranty.isSelected()));
        //System.out.println( customerInvoiceData.size() );
        if (invoiceProductTable.getItems().size() > 0) {

            if (customerInvoiceData.size() == 7) {
                OrderModel orderModelObject = new OrderModel();
                ResultSet resultSet = orderModelObject.insertInvoiceData(customerInvoiceData);
                this.insertInvoiceProductTable(resultSet.getInt("id"), true);
                GeneratePDF generatePDF = new GeneratePDF();
                String path = generatePDF.generatePDFInvoice(String.valueOf(resultSet.getInt("id")));
                if (orderPrint.isSelected()) {
                    generatePDF.PrintPDF(path);
                }

                // if path is not empty.
                if (!path.isEmpty()) {
                    ValidationDialog validationDialog = new ValidationDialog();
                    validationDialog.ShowDialog("Good News", 8, path);
                }
            } else if (customerInvoiceData.size() == 8) {
                OrderModel orderModelObject = new OrderModel();
                ResultSet resultSet = orderModelObject.updateInvoiceData(customerInvoiceData);
                this.insertInvoiceProductTable(resultSet.getInt("id"), false);
                GeneratePDF generatePDF = new GeneratePDF();
                String path = generatePDF.generatePDFInvoice(String.valueOf(resultSet.getInt("id")));
                if (orderPrint.isSelected()) {
                    generatePDF.PrintPDF(path);
                }
                // if path is not empty.
                if (!path.isEmpty()) {
                    ValidationDialog validationDialog = new ValidationDialog();
                    validationDialog.ShowDialog("Good News", 8, path);
                }
            }
        } else {
            ValidationDialog validationDialog = new ValidationDialog();
            validationDialog.ShowDialog("", 2, "");
        }

    }

    /**
     *
     * @param invoiceId
     * @param checkInsert
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public void insertInvoiceProductTable(int invoiceId, boolean checkInsert) throws SQLException, ClassNotFoundException {

        // Declare Invoice Product Table Size.
        int InvoiceProductTableSize = invoiceProductTable.getItems().size();
        // Declare Order Model Object.
        OrderModel orderModel = new OrderModel();

        if (checkInsert != true) {
            orderModel.deleteInvoiceProductList(Integer.toString(invoiceId));
        }

        // If InvoiceProductTableSize is greater than zero.
        if (InvoiceProductTableSize > 0) {
            // For loops.
            for (int i = 0; i < InvoiceProductTableSize; i++) {
                // Declare ArrayList productInvoiceList.
                ArrayList<String> productInvoiceList = new ArrayList<String>();
                // Declare InvoiceProductTable Object.
                InvoiceProduct invoiceProductObject = invoiceProductTable.getItems().get(i);
                productInvoiceList.add(String.valueOf(invoiceProductObject.getProductId()));
                productInvoiceList.add(String.valueOf(invoiceProductObject.getProductTradePrice()));
                productInvoiceList.add(String.valueOf(invoiceProductObject.getProductQuanlity()));
                productInvoiceList.add(String.valueOf(invoiceProductObject.getProductAmount()));
                productInvoiceList.add(String.valueOf(invoiceId));
                productInvoiceList.add(String.valueOf(invoiceProductObject.getProductDiscount()));
                productInvoiceList.add(String.valueOf(invoiceProductObject.getProductExpiredDate()));
                productInvoiceList.add(String.valueOf(invoiceProductObject.getProductBatchNo()));
                productInvoiceList.add(String.valueOf(invoiceProductObject.getProductBonus()));

                // Order Insert Invoice Product List Function Call.
                if (checkInsert == true) {
                    orderModel.insertInvoiceProductList(productInvoiceList, Integer.toString(invoiceId));
                } else {
                    orderModel.updateInvoiceProductList(productInvoiceList, Integer.toString(invoiceId));
                }
            }
            orderModel.updateInvoiceProfitAmount(invoiceId);
        }
    }

    /**
     *
     * @param orderListObject
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public void setInvoiceDataInView(OrderList orderListObject) throws SQLException, ClassNotFoundException {
        OrderListModel orderListModel = new OrderListModel();
        // get customer info by order id.
        ResultSet orderListSet = orderListModel.getCustomerId(String.valueOf(orderListObject.getOrderId()));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-d");
        String productName = "";
        int productPacking = 0;
        LocalDate localDate = LocalDate.parse(orderListObject.getOrderDate(), formatter);
        // set text field.
        this.invoiceId.setText(String.valueOf(orderListObject.getOrderId()));
        this.customerNameTextField.setText(orderListObject.getOrderName());
        this.areaTextField.setText(orderListObject.getOrderArea());
        this.datePicker.setValue(localDate);
        this.totalAmountTextField.setText(orderListObject.getOrderAmount());
        this.receivedAmountTextField.setText(orderListObject.getReceivedAmount());

        if (orderListSet.next()) {
            this.customerIdTextField.setText(String.valueOf(orderListSet.getInt("customerid")));
        }

        this.orderWarranty.setSelected(orderListSet.getBoolean("warranty"));
        OrderListModel listModel = new OrderListModel();
        // get all invoice product by id function call.
        ResultSet resultSet = listModel.getAllInvoiceProductById(orderListObject.getOrderId());
        // while loops
        while (resultSet.next()) {
            // function call get product detail by id.
            ResultSet productDetail = listModel.getProductById(resultSet.getString("productid"));
            // if product detail have next.
            if (productDetail.next()) {
                // get trade price float.
                float tradePrice = (float) resultSet.getDouble("tradeprice");
                // invoice product object.
                //System.out.println(resultSet.getString("productid") + resultSet.getString("productdiscount"));
                InvoiceProduct invoiceProduct = new InvoiceProduct(productDetail.getString("productname"), productDetail.getInt("productpacking"), Integer.valueOf(resultSet.getString("quanlity")), tradePrice, Float.valueOf(resultSet.getString("amount")), Integer.valueOf(resultSet.getString("productid")), Double.parseDouble(resultSet.getString("productdiscount")), resultSet.getString("expireddate"), resultSet.getString("batchno"), Integer.valueOf(resultSet.getString("bonus") == null ? "0" : resultSet.getString("bonus")));
                // add object in table.
                productInvoiceData.add(invoiceProduct);
            }
        }
    }

    /**
     *
     * @param invoiceId
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public void setInvoiceDataInCustomerView(String invoiceId) throws SQLException, ClassNotFoundException {
        OrderListModel orderListModel = new OrderListModel();
        // get customer info by order id.
        ResultSet orderListSet = orderListModel.getCustomerId(invoiceId);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-d");

        if (orderListSet.next()) {

            LocalDate localDate = LocalDate.parse(orderListSet.getString("datepicker"), formatter);
            this.invoiceId.setText(invoiceId);
            this.customerNameTextField.setText(orderListSet.getString("customername"));
            this.areaTextField.setText(orderListSet.getString("area"));
            this.datePicker.setValue(localDate);
            this.totalAmountTextField.setText(String.valueOf(orderListSet.getDouble("totalamount")));
            this.receivedAmountTextField.setText(String.valueOf(orderListSet.getDouble("receivedamount")));
            this.customerIdTextField.setText(String.valueOf(orderListSet.getInt("customerid")));
            this.orderWarranty.setSelected(orderListSet.getBoolean("warranty"));
        }

        OrderListModel listModel = new OrderListModel();
        // get all invoice product by id function call.
        ResultSet resultSet = listModel.getAllInvoiceProductById(Integer.valueOf((invoiceId)));

        // while loops
        while (resultSet.next()) {
            // function call get product detail by id.
            ResultSet productDetail = listModel.getProductById(resultSet.getString("productid"));
            // if product detail have next.
            if (productDetail.next()) {
                // get trade price float.
                float tradePrice = (float) resultSet.getDouble("tradeprice");
                // invoice product object.
                //System.out.println(resultSet.getString("productid") + resultSet.getString("productdiscount"));
                InvoiceProduct invoiceProduct = new InvoiceProduct(productDetail.getString("productname"), productDetail.getInt("productpacking"), Integer.valueOf(resultSet.getString("quanlity")), tradePrice, Float.valueOf(resultSet.getString("amount")), Integer.valueOf(resultSet.getString("productid")), (int) Double.parseDouble(resultSet.getString("productdiscount")), resultSet.getString("expireddate"), resultSet.getString("batchno"), resultSet.getInt("productBonus"));
                // add object in table.
                productInvoiceData.add(invoiceProduct);
            }
        }

    }

}


//
