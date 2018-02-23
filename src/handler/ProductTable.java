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
public class ProductTable {
    
    // Declare Table View Attribute.
    private final IntegerProperty productCode;
    private final IntegerProperty productPacking;
    private final StringProperty productName;
    private final StringProperty brandName;
    private final FloatProperty purchasedPrice;
    private final FloatProperty tradePrice;
    private final StringProperty supplierName;
    private final StringProperty createdDate;
    
    
    // Constructor Product Table.
    public ProductTable( int productCode,int productPacking, float tradePrice, String productName, String brandName, float purchasedPrice, String supplierName, String createdDate ) {
        
        this.tradePrice = new SimpleFloatProperty( tradePrice );
        this.productCode = new SimpleIntegerProperty( productCode );
        this.productPacking = new SimpleIntegerProperty( productPacking );
        this.productName = new SimpleStringProperty( productName );
        this.brandName = new SimpleStringProperty( brandName );
        this.purchasedPrice = new SimpleFloatProperty( purchasedPrice );
        this.supplierName = new SimpleStringProperty( supplierName );   
        this.createdDate = new SimpleStringProperty( createdDate );
    }
    
    public float getTradePrice() {
        return tradePrice.get();
    }
    
    public int getProductCode() {
        return productCode.get();
    }
    
    public String getCreatedDate() {
        return createdDate.get();
    }
    
    public int getProductPacking() {
        return productPacking.get();
    }

    public String getProductName() {
        return productName.get();
    }

    public String getBrandName() {
        return brandName.get();
    }
    
    public float getPurchasedPrice(){
        return purchasedPrice.get();
    }
    
    public String getSupplierName() {
        return supplierName.get();
    }
   
    public void setTradePrice( float trade ) {
        this.tradePrice.set(trade);
    }
    
    public void setProductName ( String productName ) {
        this.productName.set(productName);
    }
    
    public void setBrandName ( String brandName ) {
        this.brandName.set(brandName);
    }
    
    public void getPurchasedPrice ( float purchasePrice ) {
        this.purchasedPrice.set( purchasePrice);
    }
   
    public void setProductPacking ( int productPacking ) {
        this.productPacking.set(productPacking);
    }
    
    public void setSupplierName( String supplierName ) {
        this.supplierName.set( supplierName );
    }
   
    public void setCreatedDate( String createdDate  ) {
        this.createdDate.set( createdDate );
    }

    public void setProductCode(int productCode) {
        this.productCode.set( productCode);
    }

}
