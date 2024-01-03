package com.ecommerce.serviceImpl;

import com.ecommerce.DTO.OrderFilterRequestModal;
import com.ecommerce.DTO.OrderRequestModal;
import com.ecommerce.Exception.CartException;
import com.ecommerce.Exception.OrderException;
import com.ecommerce.Exception.WaletException;
import com.ecommerce.model.*;
import com.ecommerce.repository.*;
import com.ecommerce.service.OrderService;
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



    @Override
    public Order orderByUser(OrderDetails orderDetails) throws CartException, WaletException {

//        Optional<CartCheckout> optionalCartCheckout = cartCheckoutRepository.findByUserId(orderDetails.getUserId());
//        if (optionalCartCheckout.isPresent()){
//            CartCheckout cartCheckout = optionalCartCheckout.get();
        CartCheckout cartCheckout = cartService.checkoutCartDetails(orderDetails.getUserId());
        if (cartCheckout!=null){

            Payment payment = new Payment();
            Order order = new Order();
            if (orderDetails.getPaymentType() == null || orderDetails.getPaymentType().equals("COD")){
                payment.setStatus("pending");
                payment.setType("COD");
                order.setPaymentStatus("pending");

            } else if (orderDetails.getPaymentType().equals("Wallet")) {
                Optional<Wallet> optionalWallet = walletRepository.findByUserId(orderDetails.getUserId());
                if (optionalWallet.isPresent()){
                    Wallet wallet = optionalWallet.get();
                    if (wallet.getBalance()>=cartCheckout.getTotalAmount()){
                        wallet.setBalance(wallet.getBalance()-cartCheckout.getTotalAmount());
                        walletRepository.save(wallet);
                        payment.setStatus("Payment done successfully!");
                        payment.setType("Wallet");
                        order.setPaymentStatus("Payment done successfully!");
                    }else
                        throw new WaletException("Insuficien balance in wallet!");
                }else
                    throw new WaletException("invalid user wallet details!");
            }
            orderDetails.setId(sequences.getNextSequence("orderDetails"));
            payment.setId(sequences.getNextSequence("payment"));
            payment.setType(orderDetails.getPaymentType());
//            payment.setStatus();
            cartCheckout.setActive(false);
            order.setId(sequences.getNextSequence("order"));
            order.setUserId(orderDetails.getUserId());
            order.setCartCheckout(cartCheckout);
            order.setOrderDetaildsId(orderDetails.getId());
            order.setActive(true);
            order.setPaymentStatus("pending");
            order.setOrderStatus(OrderStatus.PROGRESS.name());
            order.setCreatedAt(LocalDateTime.now());
            order.setUpdatedAt(LocalDateTime.now());
            payment.setOrderId(order.getId());
            paymentRepository.save(payment);
            orderDetailsRepo.save(orderDetails);
            return orderRepo.save(order);
        }
        throw new CartException("No product is added to cart");
    }

    @Override
    public Order getOrderById(Integer orderId) throws OrderException {
        Optional<Order> optionalOrder =  orderRepo.findById(orderId);
        if (optionalOrder.isPresent()){
            return optionalOrder.get();
        }
        throw new OrderException("Invalid Order Id!");
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
    public List<Order> getOrdersByUserId (Integer userId) throws OrderException {
        return orderRepo.findByUserId(userId);
    }

    @Override
    public List<Order> orderFilter(OrderFilterRequestModal rfrm) throws OrderException {

        String paymentStatus=rfrm.getPaymentStatus();
        String orderStatus = rfrm.getOrderStatus();
        LocalDate sDate = rfrm.getStartDate();
        LocalDateTime startDate = sDate.atTime(LocalTime.MIN);
        LocalDate eDate = rfrm.getEndDate();
        LocalDateTime endDate = eDate.atTime(LocalTime.MAX);

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

        if (startDate != null) {
             c1 = Criteria.where("createdAt").gte(startDate);
        }

        if (endDate != null) {
             c2 = Criteria.where("createdAt").lte(endDate);
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
