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
public class DashboardModel {

    /**
     * insertProductFunctionality.
     *
     * @param productData ArrayList username/ password.
     * @author Ahmed
     * @return
     * @throws java.sql.SQLException
     * @throws java.lang.ClassNotFoundException
     */
    public ResultSet insertProductFunctionality(ArrayList<String> productData) throws SQLException, ClassNotFoundException {

        DBUtil dbObject = new DBUtil();
        Connection connection = dbObject.DbConnection();
        connection.setAutoCommit(false);
        Statement statementObject = connection.createStatement();
        // Insert Product Query  
        String insertProductQuery = "Insert into products ( productname, brandname, purchaseprice, suppliername, productpacking, tradeprice, createddate , modifieddate )";
        String insertValueProductQuery = " values(  '" + productData.get(1) + "' , '" + productData.get(2) + "' , " + productData.get(0) + ", '" + productData.get(3) + "', " + productData.get(4) + ", " + productData.get(5) + ", current_timestamp, current_timestamp ) RETURNING *;";
        String finalInsertProductQuery = insertProductQuery + insertValueProductQuery;
        //System.out.println(finalInsertProductQuery);
        ResultSet resultSet = statementObject.executeQuery(finalInsertProductQuery);
        connection.commit();
        if (resultSet.next()) {
            System.out.println(resultSet.getInt("id"));
            return resultSet;
        } else {
            return null;
        }
    }

    /**
     * deleteProduct.
     *
     * @param productCode integer product code.
     * @author Ahmed
     * @return
     * @throws java.sql.SQLException
     * @throws java.lang.ClassNotFoundException
     */
    public boolean deleteProduct(int productCode) throws SQLException, ClassNotFoundException {

        boolean checkExist = true;

        DBUtil dbObject = new DBUtil();
        Connection connection = dbObject.DbConnection();
        connection.setAutoCommit(false);
        try (Statement statementObject = connection.createStatement()) {
            // User Login Query 
            String deleteSql = "delete from products where id=" + productCode + ";";

            statementObject.executeUpdate(deleteSql);
            connection.commit();
        }
        return checkExist;
    }

    /**
     * updateProductFunctionality.
     *
     * @param productData ArrayList username/ password.
     * @author Ahmed
     * @return
     * @throws java.sql.SQLException
     * @throws java.lang.ClassNotFoundException
     */
    public ResultSet updateProductFunctionality(ArrayList<String> productData) throws ClassNotFoundException, SQLException {
        DBUtil dbObject = new DBUtil();
        Connection connection = dbObject.DbConnection();
        connection.setAutoCommit(false);
        Statement statementObject = connection.createStatement();
        // Update Product Query  
        String updateProductQuery = "UPDATE products SET productname = '" + productData.get(1) + "', brandname = '" + productData.get(2) + "', purchaseprice = " + productData.get(0) + ", tradeprice =" + productData.get(5) + ", productpacking = " + productData.get(4) + ", suppliername = '" + productData.get(3) + "', modifieddate = current_timestamp  WHERE id = " + productData.get(6) + "RETURNING *;";

        ResultSet resultSet = statementObject.executeQuery(updateProductQuery);
        connection.commit();
        if (resultSet.next()) {
            System.out.println(resultSet.getInt("id"));
            return resultSet;
        } else {
            return null;
        }
    }
    // get all product on load function.

    /**
     *
     * @return @throws ClassNotFoundException
     * @throws SQLException
     */
    public ResultSet getAllProductOnLoad() throws ClassNotFoundException, SQLException {
        boolean checkExist = false;

        DBUtil dbObject = new DBUtil();
        Connection connection = dbObject.DbConnection();
        connection.setAutoCommit(false);
        Statement statementObject = connection.createStatement();
        // User Login Query 
        String userLoginSql = "SELECT * from products;";
        ResultSet resultSet = statementObject.executeQuery(userLoginSql);

        return resultSet;
    }
}