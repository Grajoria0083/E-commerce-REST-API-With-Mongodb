package com.ecommerce.serviceImpl;

import com.ecommerce.Exception.CartException;
import com.ecommerce.Exception.ProductException;
import com.ecommerce.Exception.UserException;
import com.ecommerce.model.*;
import com.ecommerce.repository.*;
import com.ecommerce.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class CartServiceImpl implements CartService {


    @Autowired
    CartRepo cartRepo;

    @Autowired
    UserRepository userRepository;

    @Autowired
    CartDetailsRepo cartDetailsRepo;

    @Autowired
    UserCartRepository userCartRepository;


    @Autowired
    CartCheckoutRepository cartCheckoutRepository;

    @Autowired
    ProductRepo productRepo;


    @Autowired
    CustomSequences sequences;



    public Cart addToCart(CartCreateRequestModal ccrm) throws UserException, ProductException, CartException {
        Optional<User> optionalUser = userRepository.findById(ccrm.getUserId());
        if (optionalUser.isPresent()){
            Optional<Product> optionalProduct = productRepo.findById(ccrm.getProductId());
            if (optionalProduct.isPresent()) {
                CartDetails cartDetails = new CartDetails();
                Optional<Cart> optionalCart = cartRepo.findByUserId(ccrm.getUserId());
                if (optionalCart.isPresent()) {
                    Cart cart2 = optionalCart.get();
                    boolean flag = false;
                    for (CartDetails c : cart2.getCartDetailsList()) {
                        if (c.getProduct().getId() == ccrm.getProductId()) {
                            c.setQuantity(c.getQuantity() + ccrm.getQuantity());
                            flag = true;
                            break;
                        }
                    }
                    if (!flag) {
                        cartDetails.setId(sequences.getNextSequence("cartDetails"));
                        cartDetails.setProduct(optionalProduct.get());
                        cartDetails.setQuantity(ccrm.getQuantity());
                        cart2.getCartDetailsList().add(cartDetails);
                    }
                    cart2.setUpdated_at(LocalDateTime.now());
                    cartRepo.save(cart2);
                    return cart2;
                } else {
                    Cart cart = new Cart();
                    cart.setId(sequences.getNextSequence("cart"));
                    cart.setUserId(ccrm.getUserId());
                    cart.setCreated_at(LocalDateTime.now());
                    cart.setUpdated_at(LocalDateTime.now());
                    List<CartDetails> cartDetailsList = new ArrayList<>();
                    cartDetails.setId(sequences.getNextSequence("cartDetails"));
                    cartDetails.setProduct(optionalProduct.get());
                    cartDetails.setQuantity(ccrm.getQuantity());
                    cartDetailsList.add(cartDetails);
                    cart.setCartDetailsList(cartDetailsList);
                    cartRepo.save(cart);
                    return cart;
                }
            }
            throw new ProductException("invalid product id");
        }
        throw new UserException("invalid user id");
    }

    @Override
    public Cart getCart(Integer userId) throws CartException {
        Optional<Cart> optionalCart = cartRepo.findByUserId(userId);
        if (optionalCart.isPresent()) {
            return optionalCart.get();
        }
        throw new CartException("invalid cart id");
    }

    @Override
    public Cart updateCart(CartCreateRequestModal ccrm) throws CartException {
        Optional<Cart> optionalCart = cartRepo.findByUserId(ccrm.getUserId());
        if (optionalCart.isPresent()) {
            Cart cart = optionalCart.get();
            for (CartDetails c : cart.getCartDetailsList()) {
                if (c.getProduct().getId()==ccrm.getProductId()){
                    if (c.getQuantity() >= ccrm.getQuantity()) {
                        c.setQuantity(c.getQuantity() - ccrm.getQuantity());
                        cart.setUpdated_at(LocalDateTime.now());
                        return cartRepo.save(cart);
                    }
                    throw new CartException("insuficient number of products");
                }
            }
            throw new CartException("Invalid product id!");
        }
        throw new CartException("Invalid user id!");
    }



    @Override
    public CartCheckout checkout(Integer userId) throws CartException {
        Optional<Cart> cart = cartRepo.findByUserId(userId);
        if (cart.isPresent()){
            List<CartDetails> cartDetailsList = cart.get().getCartDetailsList();
            if (cartDetailsList.size()>0){
                int amount = 0;
                int productCount = 0;
                CartCheckout cartCheckout = new CartCheckout();
                List<ProductD> productList = new ArrayList<>();
                for (CartDetails c:cartDetailsList){
                    productCount += c.getQuantity();
                    amount += productRepo.findById(c.getProduct().getId()).get().getPrice()*c.getQuantity();
                    Product product = productRepo.findById(c.getProduct().getId()).get();
                    productList.add(new ProductD(product.getId(), product.getName(), product.getPrice(), c.getQuantity()));
                }
                cartCheckout.setId(sequences.getNextSequence("cartDetails"));
                cartCheckout.setTotalAmount(amount);
                cartCheckout.setQuantity(productCount);
                cartCheckout.setProductList(productList);
                cartCheckout.setUserId(userId);
                return cartCheckoutRepository.save(cartCheckout);

            }
        }
        throw new CartException("Your cart is empty now!");
    }



    @Override
    public String removeProductToCart(Integer userId, Integer productId) throws ProductException, CartException {
        Optional<Cart> optionalCart = cartRepo.findByUserId(userId);
        if (optionalCart.isPresent()){
            Cart cart = optionalCart.get();
            List<CartDetails> cartDetailsList = cart.getCartDetailsList();
            for (CartDetails c:cartDetailsList){
                if (c.getProduct().getId()==productId){
                    cartDetailsList.remove(c);
                    cart.setCartDetailsList(cartDetailsList);
                    cart.setUpdated_at(LocalDateTime.now());
                    cartRepo.save(cart);
                    return "product is removed from cart";
                }
            }
            throw new ProductException("invalid product id");
        }
        throw new CartException("invalid user id");
    }


}
