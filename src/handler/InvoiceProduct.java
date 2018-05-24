/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package handler;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.FloatProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
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
    private final StringProperty productName, productExpiredDate, productBatchNo;
    private final FloatProperty productTradePrice;
    private final FloatProperty productamount;
    private final DoubleProperty productDiscount;
    
    // Declare Constructor.

    /**
     *
     * @param productName
     * @param productPacking
     * @param productQuanlity
     * @param productTradePrice
     * @param productamount
     * @param productId
     * @param productDiscount
     * @param productExpiredDate
     * @param productBatchNo
     */
    public InvoiceProduct ( String productName, int productPacking, int productQuanlity,  float productTradePrice, float productamount, int productId, double productDiscount, String productExpiredDate, String productBatchNo) {
        this.productPacking = new SimpleIntegerProperty( productPacking );
        this.productExpiredDate = new SimpleStringProperty( productExpiredDate );
        this.productBatchNo = new SimpleStringProperty( productBatchNo );
        this.productName = new SimpleStringProperty( productName );
        this.productTradePrice = new SimpleFloatProperty( productTradePrice );
        this.productamount = new SimpleFloatProperty( productamount );
        this.productQuanlity = new SimpleIntegerProperty( productQuanlity );
        this.productId = new SimpleIntegerProperty( productId );
        this.productDiscount = new SimpleDoubleProperty( productDiscount );
    }

    /**
     *
     * @return
     */
    public String getProductExpiredDate() {
        return productExpiredDate.get();
    }
    
    /**
     *
     * @param productExpiredDates
     */
    public void setProductExpiredDate( String  productExpiredDates ) {
        this.productExpiredDate.set(productExpiredDates);
    }
    
    /**
     *
     * @param productBatchNoo
     */
    public void setProductBatchNo( String  productBatchNoo ) {
        this.productBatchNo.set(productBatchNoo);
    }
    
    /**
     *
     * @return
     */
    public String getProductBatchNo() {
         return productBatchNo.get();
    }
    
    
    // Declare get Product Discount.

    /**
     *
     * @return
     */
    public Double getProductDiscount() {
        return productDiscount.get();
    }
    
    /**
     *
     * @param productDiscount
     */
    public void setProductDiscount( double productDiscount ) {
        this.productDiscount.set(productDiscount);
    }
    
   // Declare get Product Quanlity 

    /**
     *
     * @return
     */
    public int getProductId () {
        return productId.get();
    }
    
    // Declare set Product Packing 

    /**
     *
     * @param productId
     */
    public void setProductId ( int productId ) {
        this.productId.set( productId );
    }
    
    // Declare get Product Quanlity 

    /**
     *
     * @return
     */
    public int getProductQuanlity () {
        return productQuanlity.get();
    }
    
    // Declare set Product Packing 

    /**
     *
     * @param productQuanlity
     */
    public void setProductQuanlity ( int productQuanlity ) {
        this.productQuanlity.set( productQuanlity );
    }
    
    // Declare get Product Packing 

    /**
     *
     * @return
     */
    public int getProductPacking () {
        return productPacking.get();
    }
    
    // Declare set Product Packing 

    /**
     *
     * @param productPacking
     */
    public void setProductPacking ( int productPacking ) {
        this.productPacking.set( productPacking );
    }
    
    // Declare get Product Name

    /**
     *
     * @return
     */
    public String getProductName() {
        return productName.get();
    }
    
    // Declare set Product Name

    /**
     *
     * @param productName
     */
    public void setProductName( String productName ) {
        this.productName.set(productName);
    }
    
    // Declare get Trade Price.

    /**
     *
     * @return
     */
    public float getProductTradePrice () {
        return productTradePrice.get();
    }
   
    // Declare set Trade Price.

    /**
     *
     * @param tradePrice
     */
    public void setProductTradePrice ( float tradePrice ) {
        this.productTradePrice.set(tradePrice);
    }
    
    // Declare get amount.

    /**
     *
     * @return
     */
    public float getProductAmount () {
        return productamount.get();
    }
   
    // Declare product amount.

    /**
     *
     * @param amount
     */
    public void setProductAmount ( float amount ) {
        this.productamount.set(amount);
    }
}
