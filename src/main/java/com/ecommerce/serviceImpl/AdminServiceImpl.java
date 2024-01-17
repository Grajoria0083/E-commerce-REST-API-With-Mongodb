package com.ecommerce.serviceImpl;

import com.ecommerce.DTO.PaymentRequestModal;
import com.ecommerce.DTO.RecordCountDAO;
import com.ecommerce.model.Order;
import com.ecommerce.model.Payment;
import com.ecommerce.model.User;
import com.ecommerce.repository.OrderRepo;
import com.ecommerce.repository.PaymentRepository;
import com.ecommerce.repository.UserRepository;
import com.ecommerce.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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

    @Autowired
    MongoTemplate mongoTemplate;

    @Override
    public RecordCountDAO viewRecordCount() {

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

    @Override
    public List<Payment> viewPaymentHistory(PaymentRequestModal prm) {

        Integer orderId = prm.getOrderId();
        Integer userId = prm.getUserId();
        String paymentStatus = prm.getPaymentStatus();

        Criteria c1 =new Criteria(),c2 =new Criteria() ;

        Criteria criteria =new Criteria();

        if (userId != null && userId!=0) {
            criteria.and("userId").is(userId);
        }

        if (orderId != null && orderId!=0) {
            criteria.and("orderId").is(orderId);
        }

        if (paymentStatus != null) {
            criteria.and("status").is(paymentStatus);
        }

        if (prm.getStartDate() != null) {
            c1 = Criteria.where("updatedAt").gte(prm.getStartDate().atTime(LocalTime.MIN));
        }

        if (prm.getEndDate() != null) {
            c2 = Criteria.where("updatedAt").lte(prm.getEndDate().atTime(LocalTime.MAX));
        }

        Criteria c = new Criteria().andOperator(c1, c2, criteria);


        Query query = new Query(c);

        List<Payment> paymentList = mongoTemplate.find(query, Payment.class);

        return paymentList;

    }
}

