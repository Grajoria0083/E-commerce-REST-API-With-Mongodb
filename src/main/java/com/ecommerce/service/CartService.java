package com.ecommerce.service;

import com.ecommerce.Exception.CartException;
import com.ecommerce.Exception.ProductException;
import com.ecommerce.Exception.UserException;
import com.ecommerce.model.Cart;
import com.ecommerce.model.CartCheckout;
import com.ecommerce.DTO.CartCreateRequestModal;

public interface CartService {

    public Cart addToCart (CartCreateRequestModal cartCreateRequestModal) throws UserException, ProductException, CartException;

    public Cart getCartByUserId (Integer userId) throws CartException;

    public Cart updateCart (CartCreateRequestModal ccrm) throws CartException;

    public String removeProductToCart (CartCreateRequestModal ccrm) throws ProductException, CartException;

    public CartCheckout checkoutCartDetails (Integer userId) throws CartException;
}
