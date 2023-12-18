package com.ecommerce.controller;


import com.ecommerce.Exception.ProductException;
import com.ecommerce.Exception.UserException;
import com.ecommerce.model.Product;
import com.ecommerce.model.Product_details;
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

    @GetMapping("")
    ResponseEntity<List<Product>> getProducts() throws UserException, ProductException {
        return new ResponseEntity<>(productService.viewAllproducts(), HttpStatus.ACCEPTED);
    }

    @PostMapping("")
    ResponseEntity<Product> addProduct(@RequestBody Product product) throws UserException, ProductException {
        return new ResponseEntity<>(productService.addProduct(product), HttpStatus.ACCEPTED);
    }

    @PutMapping("")
    ResponseEntity<Product> updateProduct(@RequestBody Product product) throws UserException, ProductException {
        return new ResponseEntity<>(productService.updateProduct(product), HttpStatus.ACCEPTED);
    }

    @GetMapping("/{prodId}")
    ResponseEntity<Product> getProductById(@PathVariable Integer prodId) throws UserException, ProductException {
        return new ResponseEntity<>(productService.getProductById(prodId) , HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/{prodId}")
    ResponseEntity<String> deleteProductById(@PathVariable Integer prodId) throws UserException, ProductException {
        return new ResponseEntity<>(productService.deleteProductById(prodId) , HttpStatus.ACCEPTED);
    }






    @PostMapping("/details")
    ResponseEntity<Product_details> addProduct_details(@RequestBody Product_details productDetails) throws UserException, ProductException {
        return new ResponseEntity<>(productService.addProduct_details(productDetails), HttpStatus.ACCEPTED);
    }

    @PutMapping("/details")
    ResponseEntity<Product_details> updateProduct_details(@RequestBody Product_details productDetails) throws UserException, ProductException {
        return new ResponseEntity<>(productService.updateProduct_details(productDetails), HttpStatus.ACCEPTED);
    }

    @GetMapping("/details/{prodId}")
    ResponseEntity<Product_details> getProduct_detailsById(@PathVariable Integer prodId) throws UserException, ProductException {
        return new ResponseEntity<>(productService.getProduct_detailsById(prodId) , HttpStatus.ACCEPTED);
    }

}
