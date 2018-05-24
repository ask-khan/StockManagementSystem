/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package handler;

import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Ahmed
 */
public class CustomerView {
    // Declare Table View Attribute.
    private final StringProperty invoiceIdColumns, customerIdColumns, productNoColumns, totalAmountColumns, receivedAmountColumns, createdDateColumns;
    
    /**
     *
     * @param invoiceIdColumns
     * @param customerIdColumns
     * @param productNoColumns
     * @param totalAmountColumns
     * @param receivedAmountColumns
     * @param createdDateColumns
     */
    public CustomerView ( String invoiceIdColumns, String customerIdColumns, String productNoColumns,  String totalAmountColumns, String receivedAmountColumns, String createdDateColumns) {
        System.out.println( "invoiceIdColumns" + invoiceIdColumns );
        this.invoiceIdColumns = new SimpleStringProperty( invoiceIdColumns );
        this.customerIdColumns = new SimpleStringProperty( customerIdColumns );
        this.productNoColumns = new SimpleStringProperty( productNoColumns );
        this.totalAmountColumns = new SimpleStringProperty( totalAmountColumns );
        this.receivedAmountColumns = new SimpleStringProperty( receivedAmountColumns );
        this.createdDateColumns = new SimpleStringProperty( createdDateColumns );
    }
    
    /**
     *
     * @return
     */
    public String getReceivedAmountColumns() {
        return receivedAmountColumns.get();
    }
    
    /**
     *
     * @param ReceivedAmountColumn
     */
    public void setReceivedAmountColumns( String  ReceivedAmountColumn) {
        this.receivedAmountColumns.set( ReceivedAmountColumn );
    }
    
    /**
     *
     * @return
     */
    public String getInvoiceIdColumns() {
        return invoiceIdColumns.get();
    }
    
    /**
     *
     * @param invoiceIdColumns
     */
    public void setInvoiceIdColumns( String  invoiceIdColumns) {
        this.invoiceIdColumns.set( invoiceIdColumns );
    }
    
    /**
     *
     * @return
     */
    public String getCustomerIdColumns() {
        return customerIdColumns.get();
    }
    
    /**
     *
     * @param customerIdColumns
     */
    public void setCustomerIdColumns( String  customerIdColumns) {
        this.customerIdColumns.set( customerIdColumns );
    }        
    
    /**
     *
     * @param productNoColumns
     */
    public void setProductNoColumns( String productNoColumns) {
        this.productNoColumns.set(productNoColumns);
    }
    
    /**
     *
     * @return
     */
    public String getProductNoColumns() {
        return productNoColumns.get();
    }
    
    /**
     *
     * @param totalAmountColumns
     */
    public void setTotalAmountColumns( String totalAmountColumns) {
        this.totalAmountColumns.set(totalAmountColumns);
    }
    
    /**
     *
     * @return
     */
    public String getTotalAmountColumns() {
        return totalAmountColumns.get();
    }
    
    /**
     *
     * @return
     */
    public String getCreatedDateColumns() {
        return createdDateColumns.get();
    }
    /**
     *
     * @param createdDateColumns
     */
    public void setCreatedDateColumns( String createdDateColumns ) {
        this.createdDateColumns.set(createdDateColumns);
    }
    
}
