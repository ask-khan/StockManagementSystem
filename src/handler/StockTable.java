/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package handler;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Ahmed
 */
public class StockTable {
    
    // Declare variable properties.
    private final IntegerProperty stockIdColumns, stockQuanlityColumns;
    private final StringProperty stockProductNameColumns, stockModifiedDateColumns, stockCreatedDateColumns, stockFunctionColumns, stockExpiredDateColumns, stockBatchNoColumns;
    
    
    // Declare constructor.

    /**
     *
     * @param stockIdColumns
     * @param stockProductNameColumns
     * @param stockQuanlityColumns
     * @param stockModifiedDateColumns
     * @param stockCreatedDateColumns
     * @param stockFunctionColumns
     * @param stockExpiredDateColumns
     * @param stockBatchNoColumns
     */
    public StockTable ( int stockIdColumns, String stockProductNameColumns, int stockQuanlityColumns, String stockModifiedDateColumns, String stockCreatedDateColumns, String stockFunctionColumns, String stockExpiredDateColumns, String stockBatchNoColumns ) {
        
        this.stockIdColumns = new SimpleIntegerProperty( stockIdColumns );
        this.stockProductNameColumns = new SimpleStringProperty( stockProductNameColumns );
        this.stockQuanlityColumns = new SimpleIntegerProperty( stockQuanlityColumns );
        this.stockModifiedDateColumns = new SimpleStringProperty( stockModifiedDateColumns );
        this.stockCreatedDateColumns = new SimpleStringProperty( stockCreatedDateColumns );
        this.stockFunctionColumns = new SimpleStringProperty( stockFunctionColumns );
        this.stockExpiredDateColumns = new SimpleStringProperty( stockExpiredDateColumns );
        this.stockBatchNoColumns = new SimpleStringProperty( stockBatchNoColumns );
    }
    
    /**
     *
     * @return
     */
    public String getStockBatchNoColumns() {
        return stockBatchNoColumns.get();
    }
    
    /**
     *
     * @param stockBatchNoColumns
     */
    public void setStockBatchNoColumns( String stockBatchNoColumns) {
        this.stockBatchNoColumns.set(stockBatchNoColumns);
    }
    
    // get stock function columns.

    /**
     *
     * @return
     */
    public String getStockFunctionColumns() {
        return stockFunctionColumns.get();
    }
    
    // get stock function columns.

    /**
     *
     * @return
     */
    public String getStockExpiredDateColumns() {
        return stockExpiredDateColumns.get();
    }
    
    // get stock function columns.

    /**
     *
     * @param expiredDateColumns
     */
    public void setStockExpiredDateColumns( String expiredDateColumns ) {
         this.stockExpiredDateColumns.set( expiredDateColumns );
    }
    
    
    // set stock function columns.

    /**
     *
     * @param stockFunctionColumns
     */
    public void setStockFunctionColumns( String stockFunctionColumns  ) {
        this.stockFunctionColumns.set( stockFunctionColumns );
    }
    
    // get stock id columns.

    /**
     *
     * @return
     */
    public int getStockIdColumns() {
        return stockIdColumns.get();
    }
    
    // set stock id columns 

    /**
     *
     * @param stockIdColumns
     */
    public void setStockIdColumns( int stockIdColumns ) {
        this.stockIdColumns.set(stockIdColumns);
    }
    
    // set stock product name columns. 

    /**
     *
     * @param stockProductNameColumns
     */
    public void setStockProductNameColumns ( String stockProductNameColumns ) {
        this.stockProductNameColumns.set(stockProductNameColumns);
    }
    
    // get stock product name columns.

    /**
     *
     * @return
     */
    public String getStockProductNameColumns (  ) {
        return stockProductNameColumns.get();
    }
    
    // set stock product quanlity columns.

    /**
     *
     * @param stockQuanlityColumns
     */
    public void setStockQuanlityColumns( int stockQuanlityColumns ) {
        this.stockQuanlityColumns.set(stockQuanlityColumns);
    }
    
    // get stock product quanlity columns.

    /**
     *
     * @return
     */
    public int getStockQuanlityColumns () {
        return stockQuanlityColumns.get();
    }
    
    // get stock modified date columns.

    /**
     *
     * @return
     */
    public String getStockModifiedDateColumns()  {
        return stockModifiedDateColumns.get();
    }
    
    // set stock modified date columns.

    /**
     *
     * @param stockModifiedDateColumns
     */
    public void setStockModifiedDateColumns( String stockModifiedDateColumns )  {
        this.stockModifiedDateColumns.set( stockModifiedDateColumns );
    }
    
    // set stock created date columns.

    /**
     *
     * @param stockCreatedDateColumns
     */
    public void setStockCreatedDateColumns( String stockCreatedDateColumns ) {
        this.stockCreatedDateColumns.set(stockCreatedDateColumns);
    }
    
    // get stock created date columns.

    /**
     *
     * @return
     */
    public String getStockCreatedDateColumns() {
        return stockCreatedDateColumns.get();
    }
    
    
}
