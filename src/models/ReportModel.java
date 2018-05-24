/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import util.DBUtil;

/**
 *
 * @author Ahmed
 */
public class ReportModel {

    /**
     * getCustomerInvoice.
     *
     * @author Ahmed
     * @param customerId
     * @param startDate
     * @param endDate
     * @return
     * @throws java.sql.SQLException
     * @throws java.lang.ClassNotFoundException
     */
    public ResultSet getCustomerInvoice(String customerId, String startDate, String endDate) throws SQLException, ClassNotFoundException {
        // Initialize Db Instance.
        DBUtil dbObject = new DBUtil();
        Connection connection = dbObject.DbConnection();
        connection.setAutoCommit(false);
        Statement statementObject = connection.createStatement();

        // Select Invoice Query.  
        String getCustomerInvoice = "Select * from invoice where customerid='" + customerId + "' and date(modifieddate) between '" + startDate + "' and '" + endDate + "';";
        //System.out.println( getCustomerInvoice );
        ResultSet resultSet = statementObject.executeQuery(getCustomerInvoice);
        connection.commit();
        return resultSet;
    }

    /**
     * getSalesInvoice
     *
     * @author Ahmed
     * @param startDate
     * @param endDate
     * @return
     * @throws java.sql.SQLException
     * @throws java.lang.ClassNotFoundException
     */
    public ResultSet getSalesInvoice(String startDate, String endDate) throws SQLException, ClassNotFoundException {
        // Initialize Db Instance.
        DBUtil dbObject = new DBUtil();
        Connection connection = dbObject.DbConnection();
        connection.setAutoCommit(false);
        Statement statementObject = connection.createStatement();

        // Select Invoice Query.  
        String getCustomerInvoice = "Select * from invoice where date(modifieddate) between '" + startDate + "' and '" + endDate + "';";
        //System.out.println( getCustomerInvoice );
        ResultSet resultSet = statementObject.executeQuery(getCustomerInvoice);
        connection.commit();
        return resultSet;
    }

    /**
     *
     * @param productId
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public ResultSet getProductInfo(String productId) throws ClassNotFoundException, SQLException {
        // Initialize Db Instance.
        DBUtil dbObject = new DBUtil();
        Connection connection = dbObject.DbConnection();
        connection.setAutoCommit(false);
        Statement statementObject = connection.createStatement();
        // Select Invoice Query.  
        String getCustomerInvoice = "Select * from products where id='" + productId + "';";
        //System.out.println( getCustomerInvoice );
        ResultSet resultSet = statementObject.executeQuery(getCustomerInvoice);
        connection.commit();
        return resultSet;
    }

    /**
     * @param productId
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public ResultSet getProductInvoice(String productId) throws ClassNotFoundException, SQLException {
        // Initialize Db Instance
        DBUtil dbObject = new DBUtil();
        Connection connection = dbObject.DbConnection();
        connection.setAutoCommit(false);
        Statement statementObject = connection.createStatement();
        // Select Invoice Query.  
        String getProductInvoice = "Select * from invoiceproduct where productid='" + productId + "';";
        //System.out.println( getProductInvoice );
        ResultSet resultSet = statementObject.executeQuery(getProductInvoice);
        connection.commit();
        return resultSet;
    }
}
