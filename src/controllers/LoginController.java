/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RequiredFieldValidator;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import models.LoginModel;

/**
 * FXML Controller class
 *
 * @author Ahmed
 */
public class LoginController implements Initializable {
        
    // User Name Text Field
    @FXML
    private JFXTextField userNameTextField;
    // Password Text Field
    @FXML
    private JFXPasswordField passwordTextField;
    
    // Login Button.
    @FXML
    private JFXButton loginButton;
    
    
  
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        RequiredFieldValidator userValidator = new RequiredFieldValidator(); 
        
        // user Name is validation.
        userValidator.setMessage("User name is required."); 
        userNameTextField.getValidators().add(userValidator); 
        // user Name 
        userNameTextField.focusedProperty().addListener((o,oldVal,newVal)->{ 
            if(!newVal) userNameTextField.validate(); 
        });
        RequiredFieldValidator passwordValidator = new RequiredFieldValidator();
        // password is validation.
        passwordValidator.setMessage("Password is required.");
        passwordTextField.getValidators().add(passwordValidator);
        // Password
        passwordTextField.focusedProperty().addListener((o,oldVal,newVal)->{ 
            if(!newVal) passwordTextField.validate(); 
        });
    }    
    
    
    /**
     * Login Button Functionality.
     * @param event action event.
     */
    @FXML
    private void loginButtonFunctionality(ActionEvent event) throws SQLException, ClassNotFoundException, IOException {
        ArrayList<String>  userInformation = new ArrayList<String>();//Creating arraylist
        LoginModel mainModelObject = new LoginModel();
        // If user name and password is not empty.
        if ( !userNameTextField.getText().isEmpty() && !passwordTextField.getText().isEmpty() ) {
            userInformation.add( userNameTextField.getText() );
            userInformation.add( passwordTextField.getText() );
            boolean checkExist = mainModelObject.loginFunctionality( userInformation );
            
            if ( checkExist == true ) {
                // hide the previous one windows.
                ((Node) (event.getSource()) ).getScene().getWindow().hide();
                Parent root = FXMLLoader.load(getClass().getResource("/views/dashboard.fxml"));
                Stage stage  = new Stage();
                stage.setTitle("Main Dashboard");
                stage.getIcons().add( new Image("file:user-icon.png"));
                Scene scene = new Scene(root);
                scene.getStylesheets().add(getClass().getResource("/styles/dashboard.css").toExternalForm());
                stage.setMaximized(true);
                //stage.setFullScreen(true);
                stage.setScene(scene);
                stage.show();
                
                
                
            }
        }         
    }
}
