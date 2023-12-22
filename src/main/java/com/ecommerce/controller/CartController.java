package com.ecommerce.controller;


import com.ecommerce.DTO.CartCreateRequestModal;
import com.ecommerce.Exception.CartException;
import com.ecommerce.Exception.ProductException;
import com.ecommerce.Exception.UserException;
import com.ecommerce.model.*;
import com.ecommerce.serviceImpl.CartServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    CartServiceImpl cartService;

    @GetMapping("")
    ResponseEntity<Cart> getCart(@RequestParam(name = "userId") Integer userId) throws UserException, ProductException, CartException {
        return new ResponseEntity<>(cartService.getCart(userId), HttpStatus.ACCEPTED);
    }

    @PostMapping("")
    ResponseEntity<Cart> addToCart(@RequestBody CartCreateRequestModal cartCreateRequestModal) throws UserException, ProductException, CartException {
        return new ResponseEntity<>(cartService.addToCart(cartCreateRequestModal), HttpStatus.ACCEPTED);
    }

    @PutMapping("")
    ResponseEntity<Cart> updateToCart(@RequestBody CartCreateRequestModal cartCreateRequestModal) throws UserException, ProductException, CartException {
        return new ResponseEntity<>(cartService.updateCart(cartCreateRequestModal), HttpStatus.ACCEPTED);
    }

    @DeleteMapping("")
    ResponseEntity<String> removeProductToCart(@RequestParam(name = "pId") Integer productId, @RequestParam(name = "uId") Integer userId) throws UserException, ProductException, CartException {
        return new ResponseEntity<>(cartService.removeProductToCart(userId, productId), HttpStatus.ACCEPTED);
    }

    @GetMapping("/view")
    ResponseEntity<CartCheckout> viewCartDetails(@RequestParam(name = "userId") Integer userId) throws UserException, ProductException, CartException {
        return new ResponseEntity<>(cartService.checkout(userId), HttpStatus.ACCEPTED);
    }



}
