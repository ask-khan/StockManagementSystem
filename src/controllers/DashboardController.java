/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RequiredFieldValidator;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Ahmed
 */
public class DashboardController implements Initializable {
    
    @FXML
    ImageView OrderImage;
    @FXML
    ImageView ImageReceivePayment;
    @FXML
    ImageView ImageSalesSummary;
    @FXML
    ImageView ImagePaymentLog;
    @FXML
    ImageView imageLogout;
    @FXML
    ImageView ImageAddUser;
    
    // Product Tab Declaration;
    @FXML
    private JFXButton SaveProductButton, ResetProductButton;
    
    @FXML
    private JFXTextField ProductIdTextField, ProductPriceTextField, ProductNameTextField, BrandTextField, SupplierTextField;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Image image = new Image("/Images/invoice.png");
        OrderImage.setImage(image);
        
        OrderImage.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {
                    Parent root = FXMLLoader.load(DashboardController.this.getClass().getResource("/views/order.fxml"));
                    Stage stage  = new Stage();
                    stage.setTitle("Order Dashboard");
                    stage.getIcons().add( new Image("file:user-icon.png"));
                    Scene scene = new Scene(root, 1000, 700);
                    scene.getStylesheets().add(DashboardController.this.getClass().getResource("/styles/order.css").toExternalForm());
                    stage.setScene(scene);
                    stage.show();
                }catch (IOException ex) {
                    Logger.getLogger(DashboardController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        
        Image paymentImage = new Image("/Images/payment.png");
        ImageReceivePayment.setImage(paymentImage);
        
        Image salesSummaryImage = new Image("/Images/sale-report.png");
        ImageSalesSummary.setImage(salesSummaryImage);
        
        Image paymentLogImage = new Image("/Images/credit-card.png");
        ImagePaymentLog.setImage(paymentLogImage);
        
        Image logoutImages = new Image("/Images/logout.png");
        imageLogout.setImage(logoutImages);
        
        // Image Logout On Mouse Clicked.        
        imageLogout.setOnMouseClicked((MouseEvent event) -> {
            try {
                ((Node) (event.getSource()) ).getScene().getWindow().hide();
                Parent root = FXMLLoader.load(getClass().getResource("/views/login.fxml"));
                Stage stage  = new Stage();
                stage.setTitle("Main Dashboard");
                stage.getIcons().add( new Image("file:user-icon.png"));
                Scene scene = new Scene(root);
                scene.getStylesheets().add(getClass().getResource("/styles/dashboard.css").toExternalForm());
                stage.setScene(scene);
                stage.show();
            } catch (IOException ex) {
                Logger.getLogger(DashboardController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        Image AddNewImages = new Image("/Images/add-user.png");
        ImageAddUser.setImage(AddNewImages);
    }    
    
    /*
     * Save Product Functionality.
     * @param: event: ActionEvent
     **/
    @FXML
    private void saveProductFunction(ActionEvent event) {
        ArrayList<String>  productInformation = new ArrayList<String>();
        
        // Product Id Validator.
        RequiredFieldValidator productIdValidator = new RequiredFieldValidator(); 
        // Product Price Validator.
        RequiredFieldValidator productPriceValidator = new RequiredFieldValidator(); 
        // Product Name Validator.
        RequiredFieldValidator productNameValidator = new RequiredFieldValidator(); 
        // Brand Name Validator.
        RequiredFieldValidator BrandNameValidator = new RequiredFieldValidator();
        // Supplier Name Validator.
        RequiredFieldValidator SupplierNameValidator = new RequiredFieldValidator();
        
        // Supplier Name Message.
        SupplierNameValidator.setMessage("Supplier Name Cannot be Empty.");
        SupplierTextField.getValidators().add(SupplierNameValidator);
        
        //Brand Name Is validation.
        BrandNameValidator.setMessage("Brand Name Cannot Be Empty."); 
        BrandTextField.getValidators().add(BrandNameValidator);
        
        //Product Name Is validation.
        productNameValidator.setMessage("Product Name Cannot Be Empty."); 
        ProductNameTextField.getValidators().add(productNameValidator);
        
        //Product Id Is validation.
        productPriceValidator.setMessage("Invalid Product Price."); 
        ProductPriceTextField.getValidators().add(productPriceValidator);
        
        //Product Id Is validation.
        productIdValidator.setMessage("Invalid Product Id."); 
        ProductIdTextField.getValidators().add(productIdValidator);
        
        // Product Id Text Field Validation.
        if ( !ProductIdTextField.getText().isEmpty() && this.validateInteger(ProductIdTextField.getText()) ) {
            productInformation.add(ProductIdTextField.getText()  );
        } else {
            ProductIdTextField.validate();
        }
        // Product Price Text Field.
        if ( !ProductPriceTextField.getText().isEmpty() && this.validateInteger(ProductPriceTextField.getText()) ) {
            productInformation.add(ProductPriceTextField.getText()  );
        } else {
            ProductPriceTextField.validate();
        }
        
        // Product Name Text Field.
        if ( !ProductNameTextField.getText().isEmpty()) {
            productInformation.add(ProductNameTextField.getText()  );
        } else {
            ProductNameTextField.validate();
        }
        
        // Brand Name Text Field.
        if ( !BrandTextField.getText().isEmpty()) {
            productInformation.add(BrandTextField.getText()  );
        } else {
            BrandTextField.validate();
        }
        
        // Brand Name Text Field.
        if ( !SupplierTextField.getText().isEmpty()) {
            productInformation.add(SupplierTextField.getText()  );
        } else {
            SupplierTextField.validate();
        }
        
        if ( productInformation.size() == 4 ) {
            
        } else {
            ProductPriceTextField.validate();
            ProductNameTextField.validate();
            BrandTextField.validate();
            SupplierTextField.validate();
        }
    }
    
    
    /*
     * Reset Product Functionality.
     * @param: event: ActionEvent
     **/
    @FXML   
    private void resetProductFunction(ActionEvent event) {
        // Product Fields.                
        ProductIdTextField.setText("");
        ProductPriceTextField.setText("");
        ProductNameTextField.setText("");
        BrandTextField.setText("");
        SupplierTextField.setText("");
       
        System.out.println("controllers.DashboardController.resetProductFunction()");
    }
    
    /*
     * validate Integer.
     * @param: text: String
     **/
    public boolean validateInteger( String text ){
        return text.matches("[0-9]*");
    }
}
