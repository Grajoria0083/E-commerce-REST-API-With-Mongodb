package com.ecommerce.serviceImpl;

import com.ecommerce.DTO.CartCreateRequestModal;
import com.ecommerce.Exception.CartException;
import com.ecommerce.Exception.ProductException;
import com.ecommerce.Exception.UserException;
import com.ecommerce.model.*;
import com.ecommerce.repository.*;
import com.ecommerce.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
                Product product = optionalProduct.get();
                CartDetails cartDetails = new CartDetails();
                Optional<Cart> optionalCart = cartRepo.findByUserId(ccrm.getUserId());
                if (optionalCart.isPresent()) {
                   return updateExistingCart(optionalCart.get(), ccrm, cartDetails, product);
                }
                else {
                    return createNewCart(ccrm, cartDetails, product);
                }
            }
            throw new ProductException("invalid product id");
        }
        throw new UserException("invalid user id");
    }

    @Override
    public Cart getCartByUserId (Integer userId) throws CartException {
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
            List<CartDetails> cartDetailsList = cart.getCartDetailsList();
            for (CartDetails c : cartDetailsList) {
                if (c.getProduct().getId()==ccrm.getProductId()){
                    if (c.getQuantity() > ccrm.getQuantity()) {
                        c.setQuantity(c.getQuantity() - ccrm.getQuantity());
                        return cartRepo.save(cart);
                    } else if (c.getQuantity() == ccrm.getQuantity()) {
                        cartDetailsList.remove(c);
                        cart.setCartDetailsList(cartDetailsList);
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
    public CartCheckout checkoutCartDetails (Integer userId) throws CartException {
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
    public String removeProductToCart(CartCreateRequestModal ccrm) throws ProductException, CartException {
        Optional<Cart> optionalCart = cartRepo.findByUserId(ccrm.getUserId());
        if (optionalCart.isPresent()){
            Cart cart = optionalCart.get();
            List<CartDetails> cartDetailsList = cart.getCartDetailsList();
            for (CartDetails c:cartDetailsList){
                if (c.getProduct().getId()== ccrm.getProductId()){
                    cartDetailsList.remove(c);
                    cart.setCartDetailsList(cartDetailsList);
                    cartRepo.save(cart);
                    return "product is removed from cart";
                }
            }
            throw new ProductException("invalid product id");
        }
        throw new CartException("invalid user id");
    }



    Cart createNewCart(CartCreateRequestModal ccrm, CartDetails cartDetails, Product product) {
        Cart cart = new Cart();
        cart.setId(sequences.getNextSequence("cart"));
        cart.setUserId(ccrm.getUserId());
        cart.setCreated_at(LocalDateTime.now());
        List<CartDetails> cartDetailsList = new ArrayList<>();
        cartDetails.setId(sequences.getNextSequence("cartDetails"));
        cartDetails.setProduct(product);
        cartDetails.setQuantity(ccrm.getQuantity());
        cartDetailsList.add(cartDetails);
        cart.setCartDetailsList(cartDetailsList);
        cartRepo.save(cart);
        return cart;
    }


    Cart updateExistingCart(Cart cart, CartCreateRequestModal ccrm, CartDetails cartDetails, Product product) {
        boolean flag = false;
        for (CartDetails c : cart.getCartDetailsList()) {
            if (c.getProduct().getId() == ccrm.getProductId()) {
                c.setQuantity(c.getQuantity() + ccrm.getQuantity());
                flag = true;
                break;
            }
        }
        if (!flag) {
            cartDetails.setId(sequences.getNextSequence("cartDetails"));
            cartDetails.setProduct(product);
            cartDetails.setQuantity(ccrm.getQuantity());
            cart.getCartDetailsList().add(cartDetails);
        }
        cartRepo.save(cart);
        return cart;
    }

}
