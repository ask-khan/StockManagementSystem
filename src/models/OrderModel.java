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
import java.util.ArrayList;
import util.DBUtil;

/**
 *
 * @author Ahmed
 */
public class OrderModel {

    /**
     * insertInvoiceData.
     *
     * @param productData ArrayList username/ password.
     * @author Ahmed
     * @return
     * @throws java.sql.SQLException
     * @throws java.lang.ClassNotFoundException
     */
    public ResultSet insertInvoiceData(ArrayList<String> productData) throws SQLException, ClassNotFoundException {

        DBUtil dbObject = new DBUtil();
        Connection connection = dbObject.DbConnection();
        connection.setAutoCommit(false);
        Statement statementObject = connection.createStatement();
        //System.out.println( productData );
        // Insert Product Query  
        String insertInvoiceQuery = "Insert into invoice  ( customername , area , datepicker , totalamount , createddate , modifieddate )";
        String insertValueInvoiceQuery = " values(  '" + productData.get(0) + "' , '" + productData.get(1) + "' , '" + productData.get(2) + "','" + productData.get(3) + "'," + "current_timestamp, current_timestamp ) RETURNING *;";
        String finalInsertInvoiceQuery = insertInvoiceQuery + insertValueInvoiceQuery;
        //System.out.println(finalInsertInvoiceQuery);
        ResultSet resultSet = statementObject.executeQuery(finalInsertInvoiceQuery);
        connection.commit();
        if (resultSet.next()) {
            //System.out.println( resultSet.getInt("id") );
            return resultSet;
        } else {
            return null;
        }
    }

    /**
     *
     * @param productData
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public ResultSet updateInvoiceData(ArrayList<String> productData) throws ClassNotFoundException, SQLException {

        DBUtil dbObject = new DBUtil();
        Connection connection = dbObject.DbConnection();
        connection.setAutoCommit(false);
        Statement statementObject = connection.createStatement();
        String updateInvoiceQuery = "Update invoice SET customername='" + productData.get(0) + "', area='" + productData.get(1) + "', datepicker='" + productData.get(2) + "',totalamount=" + productData.get(3) + ",modifieddate= current_timestamp  WHERE id=" + productData.get(4) + "RETURNING * ;";
        //System.out.println(updateInvoiceQuery);
        ResultSet resultSet = statementObject.executeQuery(updateInvoiceQuery);
        connection.commit();
        if (resultSet.next()) {
            //System.out.println( resultSet.getInt("id") );
            return resultSet;
        } else {
            return null;
        }
    }

    /**
     * insertInvoiceProductList.
     *
     * @author Ahmed
     * @param invoiceProductList
     * @param invoiceId
     * @throws java.sql.SQLException
     * @throws java.lang.ClassNotFoundException
     */
    public void insertInvoiceProductList(ArrayList<String> invoiceProductList, String invoiceId) throws SQLException, ClassNotFoundException {

        DBUtil dbObject = new DBUtil();
        Connection connection = dbObject.DbConnection();
        connection.setAutoCommit(false);
        Statement statementObject = connection.createStatement();
        // Insert Product Query  
        String insertInvoiceProductListQuery = "Insert into invoiceproduct   ( invoiceid , productid  , tradeprice  , quanlity  , amount , createddate , modifieddate )";
        String insertValueInvoiceProductListQuery = " values(  '" + invoiceProductList.get(4) + "' , '" + invoiceProductList.get(0) + "' ," + invoiceProductList.get(1) + ",'" + invoiceProductList.get(2) + "','" + invoiceProductList.get(3) + "'," + "current_timestamp, current_timestamp ) RETURNING *;";
        String finalInvoiceProductListQuery = insertInvoiceProductListQuery + insertValueInvoiceProductListQuery;
        //System.out.println(finalInvoiceProductListQuery);
        // Delete Query.

        ResultSet resultSet = statementObject.executeQuery(finalInvoiceProductListQuery);

        connection.commit();

    }

    /**
     * updateInvoiceProductList.
     *
     * @author Ahmed
     * @param invoiceProductList
     * @param invoiceId
     * @throws java.sql.SQLException
     * @throws java.lang.ClassNotFoundException
     */
    public void updateInvoiceProductList(ArrayList<String> invoiceProductList, String invoiceId) throws SQLException, ClassNotFoundException {
        //System.out.println( invoiceProductList );
        DBUtil dbObject = new DBUtil();
        Connection connection = dbObject.DbConnection();
        connection.setAutoCommit(false);
        Statement statementObject = connection.createStatement();
        String deleteInvoiceProductQuery = "Delete from invoiceproduct where invoiceid='" + invoiceId + "'";
        // Insert Product Query  
        String insertInvoiceProductListQuery = "Insert into invoiceproduct   ( invoiceid , productid  , tradeprice  , quanlity  , amount , createddate , modifieddate )";
        String insertValueInvoiceProductListQuery = " values(  '" + invoiceProductList.get(4) + "' , '" + invoiceProductList.get(0) + "' ," + invoiceProductList.get(1) + ",'" + invoiceProductList.get(2) + "','" + invoiceProductList.get(3) + "'," + "current_timestamp, current_timestamp ) RETURNING *;";
        String finalInvoiceProductListQuery = insertInvoiceProductListQuery + insertValueInvoiceProductListQuery;
        //System.out.println(finalInvoiceProductListQuery);
        // Delete Query.
        statementObject.executeUpdate(deleteInvoiceProductQuery);

        ResultSet resultSet = statementObject.executeQuery(finalInvoiceProductListQuery);

        connection.commit();

    }

    /**
     *
     * @return @throws ClassNotFoundException
     * @throws SQLException
     */
    public ResultSet getAllInvoiceOnload() throws ClassNotFoundException, SQLException {
        DBUtil dbObject = new DBUtil();
        Connection connection = dbObject.DbConnection();
        connection.setAutoCommit(false);
        Statement statementObject = connection.createStatement();

        String getAllInvoiceQuery = "Select * from invoice;";
        ResultSet resultSet = statementObject.executeQuery(getAllInvoiceQuery);

        return resultSet;
    }

    /**
     *
     * @param productId
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public ResultSet getProductStockById(String productId) throws ClassNotFoundException, SQLException {
        DBUtil dbObject = new DBUtil();
        Connection connection = dbObject.DbConnection();
        connection.setAutoCommit(false);
        Statement statementObject = connection.createStatement();

        String getAllInvoiceQuery = "Select\n"
                + "(Select SUM(stockquanlity) \n"
                + " FROM stock \n"
                + " WHERE productid='" + productId + "') -\n"
                + "(Select SUM(quanlity::integer) \n"
                + "From invoiceproduct \n"
                + "WHERE productid='" + productId + "') as productStock ;";
        ResultSet resultSet = statementObject.executeQuery(getAllInvoiceQuery);

        return resultSet;
    }
}
