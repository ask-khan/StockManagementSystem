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
        // Initialize Db Instance.
        DBUtil dbObject = new DBUtil();
        Connection connection = dbObject.DbConnection();
        connection.setAutoCommit(false);
        Statement statementObject = connection.createStatement();
        
        // Insert Product Query  
        String insertInvoiceQuery = "Insert into invoice  ( customername , area , datepicker , totalamount , createddate , modifieddate, receivedamount, customerid, warranty  )";
        String insertValueInvoiceQuery = " values(  '" + productData.get(0) + "' , '" + productData.get(1) + "' , '" + productData.get(2) + "','" + productData.get(3) + "'," + "current_timestamp, current_timestamp," + productData.get(4) + "," + productData.get(5) + "," + productData.get(6) + " ) RETURNING *;";
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
     * @param customerData
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public ResultSet updateInvoiceData(ArrayList<String> customerData) throws ClassNotFoundException, SQLException {
        // Initialize Db instance.
        DBUtil dbObject = new DBUtil();
        Connection connection = dbObject.DbConnection();
        connection.setAutoCommit(false);
        Statement statementObject = connection.createStatement();
        // update invoice query.
        String updateInvoiceQuery = "Update invoice SET customername='" + customerData.get(0) + "', area='" + customerData.get(1) + "', datepicker='" + customerData.get(2) + "',totalamount=" + customerData.get(3) + ",customerid=" + customerData.get(6) + ",modifieddate= current_timestamp, receivedamount=" + customerData.get(5)  + ",warranty=" + customerData.get(7) + " WHERE id=" + customerData.get(4) + "RETURNING * ;";
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
        //System.out.println( invoiceProductList  );
        double productPurchasePrice  = 0;
        DashboardModel dashboardModel = new DashboardModel();
        // get product detail from product id.
        ResultSet productInfo = dashboardModel.getProductById(invoiceProductList.get(0));
        
        if ( productInfo.next() ) {
            productPurchasePrice = productInfo.getDouble("purchaseprice");
        }
        
        // Initialize Db instance.
        DBUtil dbObject = new DBUtil();
        Connection connection = dbObject.DbConnection();
        connection.setAutoCommit(false);
        Statement statementObject = connection.createStatement();
        double profitamount = Float.valueOf(invoiceProductList.get(3)) - (productPurchasePrice * Float.valueOf(invoiceProductList.get(2)));
        
        // Insert Product Query  
        String insertInvoiceProductListQuery = "Insert into invoiceproduct   ( invoiceid , productid  , tradeprice  , quanlity  , amount , productdiscount , purchaseprice, profitamount, expireddate, batchno, createddate , modifieddate )";
        String insertValueInvoiceProductListQuery = " values(  '" + invoiceProductList.get(4) + "' , '" + invoiceProductList.get(0) + "' ," + invoiceProductList.get(1) + ",'" + invoiceProductList.get(2) + "','" + invoiceProductList.get(3) + "','" + invoiceProductList.get(5) + "','" + productPurchasePrice + "','"  + profitamount + "','" + invoiceProductList.get(6) + "','" + invoiceProductList.get(7) + "'," + "current_timestamp, current_timestamp ) RETURNING *;";
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
        double productPurchasePrice  = 0;
        DashboardModel dashboardModel = new DashboardModel();
        // get product detail by product id function.
        ResultSet productInfo = dashboardModel.getProductById(invoiceProductList.get(0));
        
        if ( productInfo.next() ) {
            productPurchasePrice = productInfo.getDouble("purchaseprice");
        }
        // Initialize Db Instance.
        //System.out.println( productPurchasePrice );
        DBUtil dbObject = new DBUtil();
        Connection connection = dbObject.DbConnection();
        connection.setAutoCommit(false);
        Statement statementObject = connection.createStatement();
        double profitamount = Float.valueOf(invoiceProductList.get(3)) - productPurchasePrice * Float.valueOf(invoiceProductList.get(2));
        // Insert Product Query  
        String insertInvoiceProductListQuery = "Insert into invoiceproduct   ( invoiceid , productid  , tradeprice  , quanlity  , amount , productdiscount, purchaseprice, profitamount, expireddate, batchno, createddate , modifieddate )";
        String insertValueInvoiceProductListQuery = " values(  '" + invoiceProductList.get(4) + "' , '" + invoiceProductList.get(0) + "' ," + invoiceProductList.get(1) + ",'" + invoiceProductList.get(2) + "','" + invoiceProductList.get(3) + "','" + invoiceProductList.get(5) + "','" + productPurchasePrice + "','" + profitamount + "','"  + invoiceProductList.get(6) + "','" + invoiceProductList.get(7) + "'," + "current_timestamp, current_timestamp ) RETURNING *;";
        String finalInvoiceProductListQuery = insertInvoiceProductListQuery + insertValueInvoiceProductListQuery;
        //System.out.println(finalInvoiceProductListQuery);
        ResultSet resultSet = statementObject.executeQuery(finalInvoiceProductListQuery);

        connection.commit();

    }

    /**
     *
     * @param invoiceId
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public void deleteInvoiceProductList(String invoiceId) throws ClassNotFoundException, SQLException {
        // Initialize Db Instance.
        DBUtil dbObject = new DBUtil();
        Connection connection = dbObject.DbConnection();
        connection.setAutoCommit(false);
        Statement statementObject = connection.createStatement();
        // Delete invoice product Query.
        String deleteInvoiceProductQuery = "Delete from invoiceproduct where invoiceid='" + invoiceId + "'";
        // Delete Query Executed.
        statementObject.executeUpdate(deleteInvoiceProductQuery);
        connection.commit();
    }

    /**
     *
     * @return @throws ClassNotFoundException
     * @throws SQLException
     */
    public ResultSet getAllInvoiceOnload() throws ClassNotFoundException, SQLException {
        // Initialize Db Instance.
        DBUtil dbObject = new DBUtil();
        Connection connection = dbObject.DbConnection();
        connection.setAutoCommit(false);
        Statement statementObject = connection.createStatement();
        // select all query
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
    public ResultSet getProductBatchNo(String productId) throws ClassNotFoundException, SQLException {
        // Intialize Db Instance.
        DBUtil dbObject = new DBUtil();
        Connection connection = dbObject.DbConnection();
        connection.setAutoCommit(false);
        Statement statementObject = connection.createStatement();
        // select query.
        String getAllInvoiceQuery ="Select batchno\n"  
               + " FROM stock where productid='" + productId +"'\n" 
               + "GROUP BY batchno;";

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
    public ResultSet getProductStockById ( String productId ) throws ClassNotFoundException, SQLException {
        // Initialize Db Instance.
        DBUtil dbObject = new DBUtil();
        Connection connection = dbObject.DbConnection();
        connection.setAutoCommit(false);
        Statement statementObject = connection.createStatement();
        
        // left out query
        String getAllInvoiceQuery = "Select\n"
                + "(Select SUM(stockquanlity) \n"
                + " FROM stock \n"
                + " WHERE productid='" + productId + "') -\n"
                + "coalesce((Select SUM(quanlity::integer) \n"
                + "From invoiceproduct \n"
                + "WHERE productid='" + productId + "'),0) as productStock ;"; 
        
        System.out.println( getAllInvoiceQuery );
        ResultSet resultSet = statementObject.executeQuery(getAllInvoiceQuery);

        return resultSet;
        
    }
    
    /**
     *
     * @param productId
     * @param batchNo
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public ResultSet getQuanlityByBatchId ( String productId, String batchNo ) throws ClassNotFoundException, SQLException {
        // Initialize Db Instance.
        DBUtil dbObject = new DBUtil();
        Connection connection = dbObject.DbConnection();
        connection.setAutoCommit(false);
        Statement statementObject = connection.createStatement();
        
        String getAllInvoiceQuery = "Select\n"
                + "(Select SUM(stockquanlity) \n"
                + " FROM stock \n"
                + " WHERE productid='" + productId + "' AND batchno='" +  batchNo +"' ) -\n"
                + "coalesce((Select SUM(quanlity::integer) \n"
                + "From invoiceproduct \n"
                + "WHERE productid='" + productId + "' AND batchno='" +  batchNo +"' ),0) as productStock ;";
        
        //System.out.println(getAllInvoiceQuery );
        ResultSet resultSet = statementObject.executeQuery(getAllInvoiceQuery);

        return resultSet;
        
    }
    
    /**
     *
     * @param batchNo
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public ResultSet getExpiredDateByBatchId( String batchNo ) throws ClassNotFoundException, SQLException {
        // Initialize Db Instance.
        DBUtil dbObject = new DBUtil();
        Connection connection = dbObject.DbConnection();
        connection.setAutoCommit(false);
        Statement statementObject = connection.createStatement();
        
        // get stock date according to the batch no.
        String getExpiredDate = "Select * from stock\n" 
                + " WHERE batchno='" + batchNo + "';";
        
        //System.out.println(getAllInvoiceQuery );
        ResultSet resultSet = statementObject.executeQuery(getExpiredDate);

        return resultSet;
    }
    
    /**
     *
     * @param invoiceId
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public void updateInvoiceProfitAmount ( int invoiceId ) throws ClassNotFoundException, SQLException {
        // Initialize Db Instance.
        DBUtil dbObject = new DBUtil();
        Connection connection = dbObject.DbConnection();
        connection.setAutoCommit(false);
        Statement statementObject = connection.createStatement();
        String updateInvoiceQuery = "";
        
        // sum invoice query.
        String profitAmountQuery = "Select SUM(profitamount) as profit \n" +
            "from invoiceproduct \n" +
            "where invoiceid='" + invoiceId + "';";
        ResultSet resultSetProfitAmountQuery = statementObject.executeQuery(profitAmountQuery);
        
        if ( resultSetProfitAmountQuery.next() ) {
            // update query
            updateInvoiceQuery = "Update invoice SET profit=" + resultSetProfitAmountQuery.getString("profit") +  " WHERE id=" + invoiceId + "RETURNING * ;";
            statementObject.executeQuery(updateInvoiceQuery);
        }
        connection.commit();
    }
    
    
}
