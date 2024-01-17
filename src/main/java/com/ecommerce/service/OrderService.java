package com.ecommerce.service;

import com.ecommerce.DTO.OrderFilterRequestModal;
import com.ecommerce.DTO.OrderRequestModal;
import com.ecommerce.Exception.CartException;
import com.ecommerce.Exception.OrderException;
import com.ecommerce.Exception.PaymentException;
import com.ecommerce.Exception.WalletException;
import com.ecommerce.model.Order;

import java.util.List;

public interface OrderService {

    public Order orderByUser (OrderRequestModal orm) throws CartException, WalletException;

    public Order getOrderById(Integer orderId) throws OrderException;

    public List<Order> getOrders() throws OrderException;

    public String deleteOrder(Integer orderId) throws OrderException;

    public String updateOrder(OrderRequestModal orderRequestModal) throws OrderException, PaymentException;

    public String updateOrderStatusByOrderId(OrderRequestModal orm) throws OrderException;

    List<Order> getOrdersByUserId(Integer userId) throws OrderException;

    List<Order> orderFilter(OrderFilterRequestModal rfrm) throws OrderException;
}
