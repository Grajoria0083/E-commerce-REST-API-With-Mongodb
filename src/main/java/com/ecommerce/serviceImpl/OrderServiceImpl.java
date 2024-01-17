package com.ecommerce.serviceImpl;

import com.ecommerce.DTO.OrderFilterRequestModal;
import com.ecommerce.DTO.OrderRequestModal;
import com.ecommerce.Exception.CartException;
import com.ecommerce.Exception.OrderException;
import com.ecommerce.Exception.PaymentException;
import com.ecommerce.Exception.WalletException;
import com.ecommerce.model.*;
import com.ecommerce.repository.*;
import com.ecommerce.service.OrderService;
import com.ecommerce.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;


@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    Order_detailsRepo orderDetailsRepo;

    @Autowired
    CustomSequences sequences;

    @Autowired
    OrderRepo orderRepo;

    @Autowired
    MongoTemplate mongoTemplate;

    @Autowired
    CartCheckoutRepository cartCheckoutRepository;


    @Autowired
    CartServiceImpl cartService;


    @Autowired
    WalletRepository walletRepository;


    @Autowired
    PaymentRepository paymentRepository;


    @Autowired
    PaymentService paymentService;



    @Override
    public Order orderByUser(OrderRequestModal orm) throws CartException, WalletException {

        CartCheckout cartCheckout = cartService.checkoutCartDetails(orm.getUserId());
        if (cartCheckout!=null){

            Payment payment = new Payment();
            Order order = new Order();
            if (orm.getPaymentType() == null || orm.getPaymentType().toUpperCase().equals("COD")){
                payment.setStatus(PaymentStatus.PENDING.name().toLowerCase());
                payment.setType("COD");
//                order.setPaymentStatus(PaymentStatus.PENDING.name().toLowerCase());

            } else if (orm.getPaymentType().toLowerCase().equals("wallet")) {
                Optional<Wallet> optionalWallet = walletRepository.findByUserId(orm.getUserId());
                if (optionalWallet.isPresent()){
                    Wallet wallet = optionalWallet.get();
                    if (wallet.getBalance()>=cartCheckout.getTotalAmount()){
                        wallet.setBalance(wallet.getBalance()-cartCheckout.getTotalAmount());
                        walletRepository.save(wallet);
                        payment.setStatus(PaymentStatus.DONE.name().toLowerCase());
                        payment.setDetails("Payment done successfully!");
                        payment.setType("Wallet");
//                        order.setPaymentStatus("confirm");
                    }else {
                        payment.setStatus(PaymentStatus.FAILED.name().toLowerCase());
                        payment.setDetails("Insuficient balance in wallet!");
                    }
                }else {
                    payment.setStatus(PaymentStatus.FAILED.name().toLowerCase());
                    payment.setDetails("invalid user wallet details!");
                }
                payment.setType("Wallet");
            }
            else {
                payment.setStatus(PaymentStatus.FAILED.name().toLowerCase());
//                order.setPaymentStatus(PaymentStatus.FAILED.name().toLowerCase());
                payment.setType("Wallet");
                payment.setDetails("Invalid payment type!");
            }
            payment.setId(sequences.getNextSequence("payment"));
            cartCheckout.setActive(false);
            order.setId(sequences.getNextSequence("order"));
            order.setUserId(orm.getUserId());
            order.setOrderDetails(cartCheckout);
            order.setActive(true);
            order.setOrderStatus(OrderStatus.PROGRESS.name().toLowerCase());
            order.setCreatedAt(LocalDateTime.now());
            order.setPaymentId(payment.getId());
            payment.setOrderId(order.getId());
            payment.setUserId(orm.getUserId());
            payment.setCreatedAt(LocalDateTime.now());
            paymentRepository.save(payment);
            return orderRepo.save(order);
        }
        throw new CartException("No product is added to cart");
    }

    @Override
    public Order getOrderById(Integer orderId) throws OrderException {
        Optional<Order> optionalOrder =  orderRepo.findById(orderId);
        if (optionalOrder.isPresent()){
             Order order = optionalOrder.get();
             order.getCreatedAt().toLocalDate();
            System.out.println("order.getCreatedAt().toLocalDate();"+order.getCreatedAt().toLocalDate());
            System.out.println("order.getCreatedAt()"+order.getCreatedAt());
            return optionalOrder.get();
        }
        throw new OrderException("Invalid Order Id!");
    }

    @Override
    public List<Order> getOrders() throws OrderException {
        return orderRepo.findAll();
    }

    @Override
    public String deleteOrder(Integer orderId) throws OrderException {

        Optional<Order> optionalOrder =  orderRepo.findById(orderId);
        if (optionalOrder.isPresent()){
            Order order = optionalOrder.get();
            order.setActive(false);
            orderRepo.save(order);
            return "order canceled";
        }
        throw new OrderException("Invalid Order Id!");
    }

    @Override
    public String updateOrder(OrderRequestModal orm) throws OrderException, PaymentException {

        Order order = getOrderById(orm.getOrderId());
        order.setOrderStatus(!orm.getOrderStatus().isBlank() ? OrderStatus.valueOf(orm.getOrderStatus().toUpperCase()).name().toLowerCase() : order.getOrderStatus());
        order.setActive(!orm.isActive() ? orm.isActive() : order.isActive());

        Payment payment = paymentService.getByOrderId(orm.getOrderId());
        payment.setStatus(!orm.getPaymentStatus().isBlank() ? PaymentStatus.valueOf(orm.getPaymentStatus().toUpperCase()).name().toLowerCase() : payment.getStatus());

        paymentRepository.save(payment);
        return "order update successfully!";
    }


    @Override
    public List<Order> getOrdersByUserId (Integer userId) throws OrderException {
        return orderRepo.findByUserId(userId);
    }

    @Override
    public List<Order> orderFilter(OrderFilterRequestModal rfrm) throws OrderException {

        String paymentStatus=rfrm.getPaymentStatus();
        String orderStatus = rfrm.getOrderStatus();

        String productName = rfrm.getProductName();
        Integer userId = rfrm.getUserId();

        Criteria c1 =new Criteria(),c2 =new Criteria() ;

        Criteria criteria =new Criteria();

        if (paymentStatus != null && !paymentStatus.isEmpty()) {
            criteria.and("paymentStatus").is(paymentStatus);
        }

        if (orderStatus != null && !orderStatus.isEmpty()) {
            criteria.and("orderStatus").is(orderStatus);
        }

        if (productName != null && !productName.isEmpty()) {
            criteria.and("cartCheckout.productList.name").is(productName);
        }

        if (userId != null && userId!=0) {
            criteria.and("userId").is(userId);
        }

        if (rfrm.getStartDate() != null) {
            c1 = Criteria.where("createdAt").gte(rfrm.getStartDate().atTime(LocalTime.MIN));
        }

        if (rfrm.getEndDate() != null) {
            c2 = Criteria.where("createdAt").lte(rfrm.getEndDate().atTime(LocalTime.MAX));
        }
        Criteria c = new Criteria().andOperator(c1, c2, criteria);


        Query query = new Query(c);

        return mongoTemplate.find(query, Order.class);
    }



    @Override
    public String updateOrderStatusByOrderId(OrderRequestModal orm) throws OrderException {

        Optional<Order> optionalOrder = orderRepo.findById(orm.getOrderId());
        if (optionalOrder.isPresent()) {
            Order order = optionalOrder.get();
            order.setOrderStatus(OrderStatus.valueOf(orm.getOrderStatus().toUpperCase()).name().toLowerCase());

            orderRepo.save(order);
            return "Order status is updated successfully!";
        }
        throw new OrderException("Invalid Order Id!");
    }
}





// Working***********
//        LocalDate sDate = rfrm.getStartDate();
//        LocalDate eDate = rfrm.getEndDate();
//        LocalDateTime startDate = null;
//        LocalDateTime endDate = null;
//
//        if (sDate!=null)
//            startDate = sDate.atTime(LocalTime.MIN);
//        if (eDate!=null)
//         endDate = eDate.atTime(LocalTime.MAX);

//        if (startDate != null) {
//             c1 = Criteria.where("createdAt").gte(startDate);
//        }
//
//        if (endDate != null) {
//             c2 = Criteria.where("createdAt").lte(endDate);
//        }
// Working***********






//    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd H:m:s");
//    Date fromDate = dateFormat.parse("2020-09-23 00:00:00");
//    Date toDate = dateFormat.parse("2020-09-23 23:59:59");
//
//    Criteria c1 = Criteria.where("someDate").gte(fromDate);
//    Criteria c2 = Criteria.where("someDate").lte(toDate);
//    Criteria c = new Criteria().andOperator(c1, c2);
//
//    Query qry = Query.query(c);
//
//    MongoOperations mongoOps = new MongoTemplate(MongoClients.create(), "testDB");
//    List<Document> result = mongoOps.find(qry, Document.class, "testCollection");





//    Criteria c1 =null,c2 =null ;
//    Criteria criteria =new Criteria();
//
//        if (paymentStatus != null && !paymentStatus.isEmpty()) {
//                criteria.and("paymentStatus").is(paymentStatus);
//                }
//
//                if (orderStatus != null && !orderStatus.isEmpty()) {
//                criteria.and("orderStatus").is(orderStatus);
//                }
//
//                if (productName != null && !productName.isEmpty()) {
//                criteria.and("cartCheckout.productList.name").is(productName);
//                }
//
//                if (userId != null && userId!=0) {
//                criteria.and("userId").is(userId);
//                }
//
//                if (startDate != null) {
//                c1 = Criteria.where("createdAt").gte(startDate);
//                }
//
//                if (endDate != null) {
//                c2 = Criteria.where("createdAt").lte(endDate);
//                }
//
//                Criteria c = new Criteria().andOperator(c1, c2, criteria);
//
//                Query query = new Query(c);
