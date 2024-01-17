package com.ecommerce.serviceImpl;

import com.ecommerce.Exception.PaymentException;
import com.ecommerce.model.Payment;
import com.ecommerce.repository.PaymentRepository;
import com.ecommerce.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class PaymentServiceImpl implements PaymentService {


    @Autowired
    PaymentRepository paymentRepository;

    @Override
    public List<Payment> getAll() {
        return paymentRepository.findAll();
    }

    @Override
    public Payment savePayment(Payment payment) throws PaymentException {
        return paymentRepository.save(payment);
    }

    @Override
    public List<Payment> getByUserId(Integer userId) throws PaymentException {

        List<Payment> paymentList = paymentRepository.findByUserId(userId);
        if (paymentList.isEmpty())
            throw new PaymentException("No pament is done by this user");
        return paymentList;
    }

    @Override
    public Payment getByOrderId(Integer orderId) throws PaymentException {
        Optional<Payment> optionalPayment = paymentRepository.findByOrderId(orderId);
        if (optionalPayment.isPresent())
            throw new PaymentException("Invalid order Id!");
        return optionalPayment.get();
    }

    @Override
    public Payment updatePaymentByOrderId(Payment payment) throws PaymentException {

        Optional<Payment> optionalPayment = paymentRepository.findById(payment.getId());
        if (optionalPayment.isPresent()){
            return optionalPayment.get();
        }
        throw new PaymentException("Invalid Payment Id!");
    }

    @Override
    public String deletePaymentByOrderId(Integer orderId) throws PaymentException {
        Payment payment = getByOrderId(orderId);
        paymentRepository.deleteById(payment.getId());
        return "payment delete successfully!";
    }

    @Override
    public String deletePaymentById(Integer paymentId) throws PaymentException {
        Payment payment = getPaymentById(paymentId);
        paymentRepository.deleteById(paymentId);
        return "payment delete successfully!";
    }

    @Override
    public Payment getPaymentById(Integer paymentId) throws PaymentException {
        Optional<Payment> optionalPayment = paymentRepository.findById(paymentId);
        if (optionalPayment.isPresent()){
            return optionalPayment.get();
        }
        throw new PaymentException("Invalid Payment Id!");
    }
}
