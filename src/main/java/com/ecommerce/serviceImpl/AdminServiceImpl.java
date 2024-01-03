package com.ecommerce.serviceImpl;

import com.ecommerce.DTO.RecordCountDAO;
import com.ecommerce.model.Order;
import com.ecommerce.model.Payment;
import com.ecommerce.model.User;
import com.ecommerce.repository.OrderRepo;
import com.ecommerce.repository.PaymentRepository;
import com.ecommerce.repository.UserRepository;
import com.ecommerce.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Service
public class AdminServiceImpl implements AdminService {


    @Autowired
    UserRepository userRepository;

    @Autowired
    OrderRepo orderRepo;


    @Autowired
    PaymentRepository paymentRepository;

    @Override
    public RecordCountDAO viewRecordCount() {

        Integer deliveredOrderCount = 0;
        Integer inProgressOrderCount = 0;
        Integer failedOrderCount = 0;
        Integer paymentDone = 0;
        Integer paymentFail = 0;
        Integer totalPendingPayment = 0;
        RecordCountDAO rcd = new RecordCountDAO();
        List<User> userList  = userRepository.findByRole("ROLE_USER");
        rcd.setTotalResisterdUser(userList.size());

        List<Order> orderList = orderRepo.findAll();
        rcd.setTotalOders(orderList.size());
        Set<Integer> users = new HashSet<>();
        for (Order order:orderList)
            users.add(order.getUserId());
        rcd.setTotalUsersHavePlacedOders(users.size());


        List<Order> orderList1 = orderRepo.findByOrderStatus("progress");
        List<Order> orderList2 = orderRepo.findByOrderStatus("failed");
        List<Order> orderList3 = orderRepo.findByOrderStatus("deliverd");
        rcd.setTotalSuccessfullOrders(orderList3.size());
        rcd.setTotalFailedOrders(orderList2.size());
        rcd.setTotalProgressingOrders(orderList1.size());

        rcd.setTotalSuccessPayment(paymentRepository.findByStatus("done").size());
        rcd.setTotalFailedPayment(paymentRepository.findByStatus("failed").size());
        rcd.setTotalPendingPayment(paymentRepository.findByStatus("pending").size());
        rcd.setTotalPayment(paymentRepository.findAll().size());

        return rcd;

    }
}

