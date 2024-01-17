package com.ecommerce.service;

import com.ecommerce.Exception.PaymentException;
import com.ecommerce.model.Payment;

import java.util.List;

public interface PaymentService {


    List<Payment> getAll() throws PaymentException;

    Payment savePayment(Payment payment) throws PaymentException;
    List<Payment> getByUserId(Integer userId) throws PaymentException;

    Payment getByOrderId(Integer orderId) throws PaymentException;

    Payment updatePaymentByOrderId(Payment payment) throws PaymentException;

    String deletePaymentByOrderId(Integer orderId) throws PaymentException;

    String deletePaymentById(Integer paymentId) throws PaymentException;

    Payment getPaymentById(Integer paymentId) throws PaymentException;


}
