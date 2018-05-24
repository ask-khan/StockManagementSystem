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
    private final StringProperty productTypes;
    
    
    // Constructor Product Table.

    /**
     *
     * @param productCode
     * @param productPacking
     * @param tradePrice
     * @param productName
     * @param brandName
     * @param purchasedPrice
     * @param supplierName
     * @param createdDate
     */
    public ProductTable( int productCode,int productPacking, float tradePrice, String productName, String brandName, float purchasedPrice, String supplierName, String productTypes, String createdDate ) {
        
        this.tradePrice = new SimpleFloatProperty( tradePrice );
        this.productCode = new SimpleIntegerProperty( productCode );
        this.productPacking = new SimpleIntegerProperty( productPacking );
        this.productName = new SimpleStringProperty( productName );
        this.brandName = new SimpleStringProperty( brandName );
        this.purchasedPrice = new SimpleFloatProperty( purchasedPrice );
        this.supplierName = new SimpleStringProperty( supplierName );   
        this.createdDate = new SimpleStringProperty( createdDate );
        this.productTypes = new SimpleStringProperty( productTypes );
    }
    
    
    public String getProductTypes(){
        return productTypes.get();
    }
    
    /**
     *
     * @return
     */
    public float getTradePrice() {
        return tradePrice.get();
    }
    
    /**
     *
     * @return
     */
    public int getProductCode() {
        return productCode.get();
    }
    
    /**
     *
     * @return
     */
    public String getCreatedDate() {
        return createdDate.get();
    }
    
    /**
     *
     * @return
     */
    public int getProductPacking() {
        return productPacking.get();
    }

    /**
     *
     * @return
     */
    public String getProductName() {
        return productName.get();
    }

    /**
     *
     * @return
     */
    public String getBrandName() {
        return brandName.get();
    }
    
    /**
     *
     * @return
     */
    public float getPurchasedPrice(){
        return purchasedPrice.get();
    }
    
    /**
     *
     * @return
     */
    public String getSupplierName() {
        return supplierName.get();
    }
   
    /**
     *
     * @param producttypes
     */
    public void setProductTypes( String productTypes ) {
        this.productTypes.set( productTypes );
    }
    
    /**
     *
     * @param trade
     */
    public void setTradePrice( float trade ) {
        this.tradePrice.set(trade);
    }
    
    /**
     *
     * @param productName
     */
    public void setProductName ( String productName ) {
        this.productName.set(productName);
    }
    
    /**
     *
     * @param brandName
     */
    public void setBrandName ( String brandName ) {
        this.brandName.set(brandName);
    }
    
    /**
     *
     * @param purchasePrice
     */
    public void getPurchasedPrice ( float purchasePrice ) {
        this.purchasedPrice.set( purchasePrice);
    }
   
    /**
     *
     * @param productPacking
     */
    public void setProductPacking ( int productPacking ) {
        this.productPacking.set(productPacking);
    }
    
    /**
     *
     * @param supplierName
     */
    public void setSupplierName( String supplierName ) {
        this.supplierName.set( supplierName );
    }
   
    /**
     *
     * @param createdDate
     */
    public void setCreatedDate( String createdDate  ) {
        this.createdDate.set( createdDate );
    }

    /**
     *
     * @param productCode
     */
    public void setProductCode(int productCode) {
        this.productCode.set( productCode);
    }

}
