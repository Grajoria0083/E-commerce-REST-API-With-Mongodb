package com.ecommerce.service;

import com.ecommerce.DTO.OrderFilterRequestModal;
import com.ecommerce.DTO.OrderRequestModal;
import com.ecommerce.Exception.CartException;
import com.ecommerce.Exception.OrderException;
import com.ecommerce.Exception.WaletException;
import com.ecommerce.model.Order;
import com.ecommerce.model.OrderDetails;

import java.util.List;

public interface OrderService {

    public Order orderByUser (OrderDetails orderDetails) throws CartException, WaletException;

    public Order getOrderById(Integer orderId) throws OrderException;

    public String deleteOrder(Integer orderId) throws OrderException;

    public String updateOrderStatusByOrderId(OrderRequestModal orm) throws OrderException;

    List<Order> getOrdersByUserId(Integer userId) throws OrderException;

    List<Order> orderFilter(OrderFilterRequestModal rfrm) throws OrderException;
}
