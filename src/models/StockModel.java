/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import handler.StockTable;
import handler.ValidationDialog;
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
public class StockModel {

    /**
     * @param productId
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public int getStockByProductId(String productId) throws ClassNotFoundException, SQLException {
        
        int stockQuanlity = 0;
        
        // Initialize Db instance.
        DBUtil dbObject = new DBUtil();
        Connection connection = dbObject.DbConnection();
        connection.setAutoCommit(false);
        Statement statementObject = connection.createStatement();

        String getStockCountByProductId = "Select SUM(stockquanlity) As StockQuanlity \n"
                + "FROM stock\n"
                + "WHERE productid='" + productId + "'" + "\n"
                + "GROUP BY productid;\n";

        //System.out.println( getStockCountByProductId );
        ResultSet resultSet = statementObject.executeQuery(getStockCountByProductId);

        if (resultSet.next()) {
            stockQuanlity = resultSet.getInt("StockQuanlity");
        }
        connection.commit();

        return stockQuanlity;
    }

    /**
     * @param stockData
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public ResultSet insertStockData(ArrayList stockData) throws ClassNotFoundException, SQLException {
        DBUtil dbObject = new DBUtil();
        Connection connection = dbObject.DbConnection();
        connection.setAutoCommit(false);
        Statement statementObject = connection.createStatement();

        String insertStockQuery = "Insert into stock ( productid, productname, stockquanlity, stockFunction, batchno, expireddate, createddate , modifieddate )";
        String insertValueStockQuery = " values(  '" + stockData.get(1) + "','" + stockData.get(0) + "'," + stockData.get(2) + ",'" + stockData.get(3) + "','"  + stockData.get(4) + "','" + stockData.get(5) + "',current_timestamp, current_timestamp ) RETURNING *;";

        String finalStockInvoiceQuery = insertStockQuery + insertValueStockQuery;
        //System.out.println(finalInsertInvoiceQuery);
        ResultSet resultSet = statementObject.executeQuery(finalStockInvoiceQuery);
        connection.commit();
        if (resultSet.next()) {
            return resultSet;
        } else {
            return null;
        }
    }

    /**
     *
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public ResultSet getAllStockData() throws ClassNotFoundException, SQLException {
        DBUtil dbObject = new DBUtil();
        Connection connection = dbObject.DbConnection();
        connection.setAutoCommit(false);
        Statement statementObject = connection.createStatement();
        String getAllStockQuery = "Select * from stock;";
        ResultSet resultSet = statementObject.executeQuery(getAllStockQuery);
        connection.commit();

        return resultSet;

    }

    /**
     *
     * @param stockTable
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public boolean deleteStockData(StockTable stockTable) throws ClassNotFoundException, SQLException {
        boolean isStockDelete = true;

        DBUtil dbObject = new DBUtil();
        Connection connection = dbObject.DbConnection();
        connection.setAutoCommit(false);
        Statement statementObject = connection.createStatement();
        String getStockValueAfterDeleting = "Select ( Select SUM(stockquanlity)\n"
                + "FROM stock\n"
                + "WHERE productid= ( Select productid from stock where id=" + stockTable.getStockIdColumns() + ")) -" + stockTable.getStockQuanlityColumns() + ";";
        //System.out.println(getStockValueAfterDeleting);
        ResultSet resultSet = statementObject.executeQuery(getStockValueAfterDeleting);
        connection.commit();

        if (resultSet.next()) {
            if (resultSet.getInt(1) >= 0) {
                Statement stockStatement = connection.createStatement();
                String deleteStock = "DELETE \n"
                        + "FROM stock \n"
                        + "WHERE id=" + stockTable.getStockIdColumns() + ";";
                stockStatement.executeUpdate(deleteStock);
                connection.commit();
                isStockDelete = true;
            }
        } else {
            ValidationDialog validationDialog = new ValidationDialog();
            validationDialog.ShowDialog("Unable Delete Stock Error", 10, "");
            isStockDelete = false;
        }
        return isStockDelete;
    }
}
