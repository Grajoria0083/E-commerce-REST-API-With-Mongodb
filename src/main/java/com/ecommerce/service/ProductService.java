package com.ecommerce.service;

import com.ecommerce.Exception.ProductException;
import com.ecommerce.model.Product;
import com.ecommerce.DTO.ProductFilterRequestModal;
import com.ecommerce.model.Product_details;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface ProductService {


    public Product addProduct(Product product) throws ProductException;
    public Product getProductById(Integer productId) throws ProductException;
    public Product updateProduct(Product product) throws ProductException;

    public Product_details addProduct_details(Product_details productDetails) throws ProductException;
    public Product_details getProduct_detailsById(Integer productId) throws ProductException;
    public Product_details updateProduct_details(Product_details productDetails) throws ProductException;
    public List<Product> getProductByName(String productName) throws ProductException;
    public List<Product> viewAllproducts () throws ProductException;
    public String deleteProductById(Integer prodId) throws ProductException;
    public List<Product> productFilter(ProductFilterRequestModal pfrm) throws ProductException;


}
