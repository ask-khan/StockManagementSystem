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
public class CustomerModel {

    /**
     * insertProductFunctionality.
     *
     * @param customerData
     * @author Ahmed
     * @return
     * @throws java.sql.SQLException
     * @throws java.lang.ClassNotFoundException
     */
    public ResultSet insertCustomerFunctionality(ArrayList<String> customerData) throws SQLException, ClassNotFoundException {

        DBUtil dbObject = new DBUtil();
        Connection connection = dbObject.DbConnection();
        connection.setAutoCommit(false);
        Statement statementObject = connection.createStatement();
        // Insert Customer Query  
        String insertCustomerQuery = "Insert into customer ( customername, customercontact, customerarea, customeremailaddress, customeraddress, createddate , modifieddate )";
        String insertValueCustomerQuery = " values(  '" + customerData.get(0) + "' ,'" + customerData.get(1) + "', '" + customerData.get(2) + "', '" + customerData.get(3) + "', '" + customerData.get(4) + "', current_timestamp, current_timestamp ) RETURNING *;";
        String finalInsertCustomerQuery = insertCustomerQuery + insertValueCustomerQuery;
        //System.out.println(finalInsertCustomerQuery);
        ResultSet resultSet = statementObject.executeQuery(finalInsertCustomerQuery);
        connection.commit();
        if (resultSet.next()) {
            System.out.println(resultSet.getInt("id"));
            return resultSet;
        } else {
            return null;
        }
    }

    /**
     * deleteCustomer.
     *
     * @param customerCode integer customer code.
     * @author Ahmed
     * @return
     * @throws java.sql.SQLException
     * @throws java.lang.ClassNotFoundException
     */
    public boolean deleteCustomer(int customerCode) throws SQLException, ClassNotFoundException {

        boolean checkExist = true;

        DBUtil dbObject = new DBUtil();
        Connection connection = dbObject.DbConnection();
        connection.setAutoCommit(false);
        try (Statement statementObject = connection.createStatement()) {
            // User Login Query 
            String deleteSql = "delete from customer where id=" + customerCode + ";";

            statementObject.executeUpdate(deleteSql);
            connection.commit();
        }
        return checkExist;
    }

    /**
     * updateCustomerFunctionality.
     *
     * @param customerData ArrayList username/ password.
     * @author Ahmed
     * @return
     * @throws java.sql.SQLException
     * @throws java.lang.ClassNotFoundException
     */
    public ResultSet updateCustomerFunctionality(ArrayList<String> customerData) throws ClassNotFoundException, SQLException {
        System.out.println(customerData);
        DBUtil dbObject = new DBUtil();
        Connection connection = dbObject.DbConnection();
        connection.setAutoCommit(false);
        Statement statementObject = connection.createStatement();
        // Update Product Query  
        String updateProductQuery = "UPDATE customer SET customername = '" + customerData.get(0) + "', customercontact = '" + customerData.get(1) + "', customerarea = '" + customerData.get(2) + "', customeremailaddress ='" + customerData.get(3) + "', customeraddress = '" + customerData.get(4) + "', modifieddate = current_timestamp  WHERE id = " + customerData.get(5) + "RETURNING *;";
        //System.err.println(updateProductQuery);
        ResultSet resultSet = statementObject.executeQuery(updateProductQuery);
        connection.commit();
        if (resultSet.next()) {
            //System.out.println(resultSet.getInt("id"));
            return resultSet;
        } else {
            return null;
        }
    }

    /**
     *
     * @return @throws ClassNotFoundException
     * @throws SQLException
     */
    public ResultSet getAllCustomerOnLoad() throws ClassNotFoundException, SQLException {
        boolean checkExist = false;

        DBUtil dbObject = new DBUtil();
        Connection connection = dbObject.DbConnection();
        connection.setAutoCommit(false);
        Statement statementObject = connection.createStatement();
        // User Login Query 
        String userLoginSql = "SELECT * from customer;";
        ResultSet resultSet = statementObject.executeQuery(userLoginSql);

        return resultSet;
    }
}
