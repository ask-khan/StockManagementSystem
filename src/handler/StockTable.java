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
    private final StringProperty stockProductNameColumns, stockModifiedDateColumns, stockCreatedDateColumns, stockFunctionColumns;
    
    
    // Declare constructor.
    public StockTable ( int stockIdColumns, String stockProductNameColumns, int stockQuanlityColumns, String stockModifiedDateColumns, String stockCreatedDateColumns, String stockFunctionColumns ) {
        this.stockIdColumns = new SimpleIntegerProperty( stockIdColumns );
        this.stockProductNameColumns = new SimpleStringProperty( stockProductNameColumns );
        this.stockQuanlityColumns = new SimpleIntegerProperty( stockQuanlityColumns );
        this.stockModifiedDateColumns = new SimpleStringProperty( stockModifiedDateColumns );
        this.stockCreatedDateColumns = new SimpleStringProperty( stockCreatedDateColumns );
        this.stockFunctionColumns = new SimpleStringProperty( stockFunctionColumns );
    }

    // get stock function columns.
    public String getStockFunctionColumns() {
        return stockFunctionColumns.get();
    }
    
    // set stock function columns.
    public void setStockFunctionColumns( String stockFunctionColumns  ) {
        this.stockFunctionColumns.set( stockFunctionColumns );
    }
    
    // get stock id columns.
    public int getStockIdColumns() {
        return stockIdColumns.get();
    }
    
    // set stock id columns 
    public void setStockIdColumns( int stockIdColumns ) {
        this.stockIdColumns.set(stockIdColumns);
    }
    
    // set stock product name columns. 
    public void setStockProductNameColumns ( String stockProductNameColumns ) {
        this.stockProductNameColumns.set(stockProductNameColumns);
    }
    
    // get stock product name columns.
    public String getStockProductNameColumns (  ) {
        return stockProductNameColumns.get();
    }
    
    // set stock product quanlity columns.
    public void setStockQuanlityColumns( int stockQuanlityColumns ) {
        this.stockQuanlityColumns.set(stockQuanlityColumns);
    }
    
    // get stock product quanlity columns.
    public int getStockQuanlityColumns () {
        return stockQuanlityColumns.get();
    }
    
    // get stock modified date columns.
    public String getStockModifiedDateColumns()  {
        return stockModifiedDateColumns.get();
    }
    
    // set stock modified date columns.
    public void setStockModifiedDateColumns( String stockModifiedDateColumns )  {
        this.stockModifiedDateColumns.set( stockModifiedDateColumns );
    }
    
    // set stock created date columns.
    public void setStockCreatedDateColumns( String stockCreatedDateColumns ) {
        this.stockCreatedDateColumns.set(stockCreatedDateColumns);
    }
    
    // get stock created date columns.
    public String getStockCreatedDateColumns() {
        return stockCreatedDateColumns.get();
    }
    
    
}
