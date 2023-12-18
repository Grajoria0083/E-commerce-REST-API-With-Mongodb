package com.ecommerce.service;

import com.ecommerce.Exception.CartException;
import com.ecommerce.Exception.OrderException;
import com.ecommerce.Exception.WaletException;
import com.ecommerce.model.Order;
import com.ecommerce.model.Order_details;

public interface OrderService {

    public Order order(Order_details orderDetails, Integer cusId) throws CartException, WaletException;

    public Order getOrderById(Integer orderId) throws OrderException;

    public String deleteOrder(Integer orderId) throws OrderException;
}
