package com.ecommerce.controller;


import com.ecommerce.Exception.ProductException;
import com.ecommerce.Exception.UserException;
import com.ecommerce.model.Product;
import com.ecommerce.DTO.ProductFilterRequestModal;
import com.ecommerce.model.ProductDetails;
import com.ecommerce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;



@RestController
@RequestMapping("/product")
public class ProductController {


    @Autowired
    ProductService productService;

    @GetMapping("/view")
    ResponseEntity<List<Product>> getProducts() throws UserException, ProductException {
        return new ResponseEntity<>(productService.viewAllproducts(), HttpStatus.OK);
    }



    @PostMapping("/details")
    ResponseEntity<ProductDetails> addProduct_details(@RequestBody ProductDetails productDetails) throws UserException, ProductException {
        return new ResponseEntity<>(productService.addProductDetails(productDetails), HttpStatus.CREATED);
    }

    @PutMapping("/details")
    ResponseEntity<ProductDetails> updateProduct_details(@RequestBody ProductDetails productDetails) throws UserException, ProductException {
        return new ResponseEntity<>(productService.updateProductDetails(productDetails), HttpStatus.ACCEPTED);
    }

    @GetMapping("/details/{prodId}")
    ResponseEntity<ProductDetails> getProduct_detailsById(@PathVariable Integer prodId) throws UserException, ProductException {
        return new ResponseEntity<>(productService.getProductDetailsById(prodId) , HttpStatus.OK);
    }







    @PostMapping("/filter")
    ResponseEntity<List<Product>> filter(@RequestBody ProductFilterRequestModal pfrm) throws UserException, ProductException {
        return new ResponseEntity<>(productService.productFilter(pfrm) , HttpStatus.ACCEPTED);
    }

}
