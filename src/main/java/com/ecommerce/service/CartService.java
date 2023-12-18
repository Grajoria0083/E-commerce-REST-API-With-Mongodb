package com.ecommerce.service;

import com.ecommerce.Exception.CartException;
import com.ecommerce.Exception.ProductException;
import com.ecommerce.Exception.UserException;
import com.ecommerce.model.Cart;
import com.ecommerce.model.CartCheckout;
import com.ecommerce.model.CartCreateRequestModal;
import com.ecommerce.model.CartDetails;

public interface CartService {

    public Cart addToCart (CartCreateRequestModal cartCreateRequestModal) throws UserException, ProductException, CartException;

    public Cart getCart (Integer userId) throws CartException;

    public Cart updateCart (CartCreateRequestModal ccrm) throws CartException;

    public String removeProductToCart (Integer productId, Integer userId) throws ProductException, CartException;

    public CartCheckout checkout (Integer userId) throws CartException;
}
