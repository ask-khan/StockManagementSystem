/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package handler;

import javafx.beans.property.FloatProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Ahmed
 */
public class InvoiceProduct {
    // Declare Table View Attribute.
    private final IntegerProperty productPacking, productId;
    private final IntegerProperty productQuanlity;
    private final StringProperty productName;
    private final FloatProperty productTradePrice;
    private final FloatProperty productamount;
    
    // Declare Constructor.
    public InvoiceProduct ( String productName, int productPacking, int productQuanlity,  float productTradePrice, float productamount, int productId ) {
        this.productPacking = new SimpleIntegerProperty( productPacking );
        this.productName = new SimpleStringProperty( productName );
        this.productTradePrice = new SimpleFloatProperty( productTradePrice );
        this.productamount = new SimpleFloatProperty( productamount );
        this.productQuanlity = new SimpleIntegerProperty( productQuanlity );
        this.productId = new SimpleIntegerProperty( productId );
    }

   // Declare get Product Quanlity 
    public int getProductId () {
        return productId.get();
    }
    
    // Declare set Product Packing 
    public void setProductId ( int productId ) {
        this.productId.set( productId );
    }
    
    // Declare get Product Quanlity 
    public int getProductQuanlity () {
        return productQuanlity.get();
    }
    
    // Declare set Product Packing 
    public void setProductQuanlity ( int productQuanlity ) {
        this.productQuanlity.set( productQuanlity );
    }
    
    // Declare get Product Packing 
    public int getProductPacking () {
        return productPacking.get();
    }
    
    // Declare set Product Packing 
    public void setProductPacking ( int productPacking ) {
        this.productPacking.set( productPacking );
    }
    
    // Declare get Product Name
    public String getProductName() {
        return productName.get();
    }
    
    // Declare set Product Name
    public void setProductName( String productName ) {
        this.productName.set(productName);
    }
    
    // Declare get Trade Price.
    public float getProductTradePrice () {
        return productTradePrice.get();
    }
   
    // Declare set Trade Price.
    public void setProductTradePrice ( float tradePrice ) {
        this.productTradePrice.set(tradePrice);
    }
    
    // Declare get amount.
    public float getProductAmount () {
        return productamount.get();
    }
   
    // Declare product amount.
    public void setProductAmount ( float amount ) {
        this.productamount.set(amount);
    }
}
