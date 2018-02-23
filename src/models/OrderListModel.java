/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import handler.OrderList;
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
public class OrderListModel {

    /**
     *
     * @param invoiceId
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public void deleteInvoice(int invoiceId) throws ClassNotFoundException, SQLException {

        DBUtil dbObject = new DBUtil();
        Connection connection = dbObject.DbConnection();
        connection.setAutoCommit(false);
        // try and catch.
        try (Statement statementObject = connection.createStatement()) {
            // Delete Invoice Query 
            String deleteInvoiceQuery = "Delete from invoice where id=" + invoiceId + ";";
            // Delete Invoice product id Query.
            String deleteInvoiceProductQuery = "Delete from invoiceproduct where invoiceid='" + invoiceId + "';";

            // Delete invoice query execute update.
            statementObject.executeUpdate(deleteInvoiceQuery);
            // Delete invoice product query execute update
            statementObject.executeUpdate(deleteInvoiceProductQuery);

            connection.commit();
        }

    }

    /**
     *
     * @param orderList
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public ResultSet getAllInvoiceProductById(OrderList orderList) throws ClassNotFoundException, SQLException {

        DBUtil dbObject = new DBUtil();
        Connection connection = dbObject.DbConnection();
        connection.setAutoCommit(false);
        Statement statementObject = connection.createStatement();
        // Select Invoice Query 
        String selectInvoiceProductQuery = "Select * from invoiceproduct where invoiceid='" + orderList.getOrderId() + "';";
        ResultSet resultSet = statementObject.executeQuery(selectInvoiceProductQuery);

        return resultSet;

    }

    /**
     *
     * @param productId
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public ResultSet getProductById(String productId) throws ClassNotFoundException, SQLException {
        DBUtil dbObject = new DBUtil();
        Connection connection = dbObject.DbConnection();
        connection.setAutoCommit(false);

        ArrayList<String> productDetail = new ArrayList<String>();

        Statement statementObject = connection.createStatement();
        // Select Invoice Query 
        String productDetailQuery = "Select * from products where id=" + productId;
        //System.out.println(productDetailQuery);
        // Select invoice query execute update.
        ResultSet resultSet = statementObject.executeQuery(productDetailQuery);

        connection.commit();

        return resultSet;
    }
}
