package com.ecommerce.serviceImpl;

import com.ecommerce.Exception.CartException;
import com.ecommerce.Exception.OrderException;
import com.ecommerce.Exception.WaletException;
import com.ecommerce.model.*;
import com.ecommerce.repository.*;
import com.ecommerce.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    CartRepo cartRepo;

    @Autowired
    UserRepository userRepository;

    @Autowired
    Order_detailsRepo orderDetailsRepo;
    @Autowired
    ProductRepo productRepo;

    @Autowired
    CustomSequences sequences;

    @Autowired
    OrderRepo orderRepo;

    @Autowired
    UserCartRepository userCartRepository;


    @Autowired
    CartCheckoutRepository cartCheckoutRepository;


    @Autowired
    CartServiceImpl cartService;


    @Autowired
    WalletRepository walletRepository;


    @Autowired
    PaymentRepository paymentRepository;



    @Override
    public Order order(Order_details orderDetails, Integer userId) throws CartException, WaletException {

        Optional<CartCheckout> optionalCartCheckout = cartCheckoutRepository.findByUserId(userId);
        if (optionalCartCheckout.isPresent()){
            CartCheckout cartCheckout = optionalCartCheckout.get();

//            CartCheckout cartCheckout = cartService.checkout(userId);
            Payment payment = new Payment();
            Order order = new Order();
            if (orderDetails.getPaymentType().equals("COD")){
                payment.setStatus("pending");

            } else if (orderDetails.getPaymentType().equals("Wallet")) {
                if (cartCheckout.getTotalAmount().equals(orderDetails.getTotalAmount())){
                    Optional<Wallet> optionalWallet = walletRepository.findByUserId(userId);
                    if (optionalWallet.isPresent()){
                        Wallet wallet = optionalWallet.get();
                        if (wallet.getPassword().equals(orderDetails.getPassword())){
                            if (wallet.getBalance()>=cartCheckout.getTotalAmount()){
                                wallet.setBalance(wallet.getBalance()-cartCheckout.getTotalAmount());
                                walletRepository.save(wallet);
                                payment.setStatus("Payment is done successfully!");
                            }else
                                throw new WaletException("Insuficien balance in wallet!");
                        }else
                            throw new WaletException("wrong password!");
                    }else
                        throw new WaletException("invalid user wallet details!");
                }else
                    throw new WaletException("invalid amount!");
            }
            orderDetails.setId(sequences.getNextSequence("orderDetails"));
            payment.setId(sequences.getNextSequence("payment"));
            payment.setType(orderDetails.getPaymentType());
//            payment.setUserId(userId);

            cartCheckout.setActive(false);
            order.setId(sequences.getNextSequence("order"));
            order.setUserId(userId);
            order.setCartCheckout(cartCheckout);
            order.setCreated_at(LocalDate.now());
            order.setUpdated_at(LocalDate.now());
            order.setOrderDetaildsId(orderDetails.getId());
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
            orderRepo.deleteById(orderId);
            Optional<Payment> optionalPayment = paymentRepository.findByOrderId(orderId);
            if (optionalOrder.isPresent()){
                paymentRepository.deleteById(optionalPayment.get().getId());
                Optional<Order_details> optionalOrderDetails = orderDetailsRepo.findById(optionalOrder.get().getOrderDetaildsId());
                orderDetailsRepo.deleteById(optionalOrderDetails.get().getId());
            }
            Optional<Wallet> optionalWallet = walletRepository.findByUserId(order.getUserId());
            if (optionalWallet.isPresent()){
                Wallet wallet = optionalWallet.get();
                wallet.setBalance(wallet.getBalance()+ order.getCartCheckout().getTotalAmount());
                walletRepository.save(wallet);
            }
            return "Your order is canceled successfully and amount is refunded into your account";
        }
        throw new OrderException("Invalid Order Id!");
    }


//
//      List<Cart> cartList = cartRepo.findByUserId(userId);
//        if (!cartList.isEmpty()){
//            int amount = 0;
//            int productCount = 0;
//            Order order = new Order();
//            Order_details orderDetails = new Order_details();
//            for (Cart cart:cartList){
//                order.setId(sequences.getNextSequence("order"));
//                order.setProductId(cart.getProductId());
//                order.setUserId(cart.getUserId());
//                order.setCreated_at(LocalDate.now());
//                order.setUpdated_at(LocalDate.now());
//                orderRepo.save(order);
//
//                productCount = cart.getQuantity();
//                amount = productRepo.findById(cart.getProductId()).get().getPrice()*cart.getQuantity();
//                orderDetails.setId(sequences.getNextSequence("orderDetails"));
//                orderDetails.setTotalAmount(amount);
//                orderDetails.setTotalQuantity(productCount);
//                orderDetails.setOrderId(order.getId());
//                orderDetailsRepo.save(orderDetails);
//
//            }
//            return "Items order successfully!";
//        }
//        throw new CartException("No product is added to cart");
//    }

//    @Override
//    public String order(Integer userId) throws CartException {
////
////        List<Cart> cartList = cartRepo.findByUserId(userId);
////        if (!cartList.isEmpty()){
////            int amount = 0;
////            int productCount = 0;
////            Order order = new Order();
////            Order_details orderDetails = new Order_details();
////            for (Cart cart:cartList){
////                order.setId(sequences.getNextSequence("order"));
////                order.setProductId(cart.getProductId());
////                order.setUserId(cart.getUserId());
////                order.setCreated_at(LocalDate.now());
////                order.setUpdated_at(LocalDate.now());
////                orderRepo.save(order);
////
////                productCount = cart.getQuantity();
////                amount = productRepo.findById(cart.getProductId()).get().getPrice()*cart.getQuantity();
////                orderDetails.setId(sequences.getNextSequence("orderDetails"));
////                orderDetails.setTotalAmount(amount);
////                orderDetails.setTotalQuantity(productCount);
////                orderDetails.setOrderId(order.getId());
////                orderDetailsRepo.save(orderDetails);
////
////            }
////            return "Items order successfully!";
////        }
//        throw new CartException("No product is added to cart");
//    }









//    =============================================================================


//    @Override
//    public String order(Integer userId, Integer cartId) {
//
//        List<Cart> cartList = cartRepo.findByUserId(userId);
//        if (!cartList.isEmpty()){
//            int amount = 0;
//            int productCount = 0;
//            Order order = new Order();
//            List<Product> productList =new ArrayList<>();
//            for (Cart cart:cartList){
//                productList.add(productRepo.findById(cart.getProductId()).get());
//                productCount = cart.getQuantity();
//                amount += productRepo.findById(cart.getProductId()).get().getPrice()*cart.getQuantity();
//                orderRepo.save(order);
//                Optional<Cart> cart1 = cartRepo.findById(userCart.get().getCartId());
//                List<CartDetails> cartDetailsList = cart1.get().getCartDetailsList();
//                if (!cartDetailsList.isEmpty()){
//                    order.setId(sequences.getNextSequence("cartDetails"));
//                    for (CartDetails cd:cartDetailsList){
//
//                    }
//                }
//            }
//            order.setId(sequences.getNextSequence("order"));
////            order.setProduct_id(cart.getProductId());
//            order.setUserId(cart.getUserId());
//            order.setCreated_at(LocalDate.now());
//            order.setUpdated_at(LocalDate.now());
//        }
//        return "";
//    }
}
