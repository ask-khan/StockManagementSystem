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
public class customerTable {

   
    // Declare variable properties.
    private final IntegerProperty customerIdTableColumns;
    private final StringProperty  customerNameTableColumns, customerAddressTableColumns, customerEmailAddressTableColumns, customerAreaTableColumns, customerContactTableColumns;
    
    // Declare constructor.
    public customerTable( int customerIdTableColumns, String customerContactTableColumns, String customerNameTableColumns, String customerAddressTableColumns, String customerEmailAddressTableColumns , String customerAreaTableColumns   ){
        this.customerIdTableColumns = new SimpleIntegerProperty( customerIdTableColumns );
        this.customerContactTableColumns = new SimpleStringProperty( customerContactTableColumns );
        this.customerNameTableColumns = new SimpleStringProperty( customerNameTableColumns);
        this.customerAddressTableColumns = new SimpleStringProperty( customerAddressTableColumns );
        this.customerEmailAddressTableColumns = new SimpleStringProperty( customerEmailAddressTableColumns  );
        this.customerAreaTableColumns = new SimpleStringProperty( customerAreaTableColumns );
                
    }
    // Set customer id function.
    public void setCustomerIdTableColumns( int customerIdTableColumns ) {
        this.customerIdTableColumns.set( customerIdTableColumns );
    }
    
    // Get customer id function.
    public int getCustomerIdTableColumns(){
        return customerIdTableColumns.get();
    }
    
    // Set customer contact table columns.
    public void setCustomerContactTableColumns( String customerContactTableColumns ) {
        this.customerContactTableColumns.set(customerContactTableColumns);
    }
    
    // Get customer contact table columns.
    public String getCustomerContactTableColumns() {
        return customerContactTableColumns.get();
    }
    
    // Set customer name table columns.
    public void setCustomerNameTableColumns( String customerNameTableColumns ) {
        this.customerNameTableColumns.set(customerNameTableColumns);
    }
    
    // Get customer name table columns.
    public String getCustomerNameTableColumns() {
         return customerNameTableColumns.get();
    }
    
    // set  customer address table columns.
    public void setCustomerAddressTableColumns( String customerAddressTableColumns ) {
        this.customerAddressTableColumns.set( customerAddressTableColumns );
    }
    
    // get customer address table columns.
    public String getCustomerAddressTableColumns(){
        return customerAddressTableColumns.get();
    }
    
    // set customer email address.
    public void setCustomerEmailAddressTableColumns( String customerEmailAddressTableColumns ){
        this.customerEmailAddressTableColumns.set(customerEmailAddressTableColumns);
    }
    
    // get customer email address.
    public String getCustomerEmailAddressTableColumns(){
        return customerEmailAddressTableColumns.get();
    }
    
    // set customer area table columns.
    public void setCustomerAreaTableColumns( String customerAreaTableColumns ) {
        this.customerAreaTableColumns.set(customerAreaTableColumns);
    }
    
    // get custimer area table columns.
    public String getCustomerAreaTableColumns(){
        return customerAreaTableColumns.get();
    }
    
}

