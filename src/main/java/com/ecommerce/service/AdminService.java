package com.ecommerce.service;

import com.ecommerce.DTO.PaymentRequestModal;
import com.ecommerce.DTO.RecordCountDAO;
import com.ecommerce.model.Payment;

import java.util.List;

public interface AdminService {

     RecordCountDAO viewRecordCount();

     List<Payment> viewPaymentHistory(PaymentRequestModal prm);
}
