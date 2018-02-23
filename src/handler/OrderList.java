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
public class OrderList {

    // Declare Table View Attribute.
    private final IntegerProperty orderId;
    private final StringProperty orderName;
    private final StringProperty orderDate;
    private final StringProperty orderAmount;
    private final StringProperty orderArea;

    /**
     *
     * @param orderId
     * @param orderName
     * @param orderDate
     * @param orderAmount
     * @param orderArea
     */
    public OrderList(int orderId, String orderName, String orderDate, String orderAmount, String orderArea) {
        this.orderId = new SimpleIntegerProperty(orderId);
        this.orderName = new SimpleStringProperty(orderName);
        this.orderDate = new SimpleStringProperty(orderDate);
        this.orderAmount = new SimpleStringProperty(orderAmount);
        this.orderArea = new SimpleStringProperty(orderArea);
    }

    /**
     * @return the orderId
     */
    public int getOrderId() {
        return orderId.get();
    }

    /**
     * @param orderId the orderId to set
     */
    public void setOrderId(int orderId) {
        this.orderId.set(orderId);
    }

    /**
     * @return the orderName
     */
    public String getOrderName() {
        return orderName.get();
    }

    /**
     * @param orderName the orderName to set
     */
    public void setOrderName(String orderName) {
        this.orderName.set(orderName);
    }

    /**
     * @return the orderDate
     */
    public String getOrderDate() {
        return orderDate.get();
    }

    /**
     * @param orderDate the orderDate to set
     */
    public void setOrderDate(String orderDate) {
        this.orderDate.set(orderDate);
    }

    /**
     * @return the orderAmount
     */
    public String getOrderAmount() {
        return orderAmount.get();
    }

    /**
     * @param orderAmount the orderAmount to set
     */
    public void setOrderAmount(String orderAmount) {
        this.orderAmount.set(orderAmount);
    }

    /**
     * @return the orderArea
     */
    public String getOrderArea() {
        return orderArea.get();
    }

    /**
     * @param orderArea the orderArea to set
     */
    public void setOrderArea(String orderArea) {
        this.orderArea.set(orderArea);
    }

}
