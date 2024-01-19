package com.ecommerce.service;

import com.ecommerce.DTO.ProductForm;
import com.ecommerce.Exception.ProductException;
import com.ecommerce.model.Product;
import com.ecommerce.DTO.ProductFilterRequestModal;
import com.ecommerce.model.ProductDetails;
import com.ecommerce.model.User;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ProductService {


    public Product addProduct(Product product) throws ProductException, IOException;
    public Product getProductById(Integer productId) throws ProductException;
    public Product updateProduct(Product product) throws ProductException;
    public ProductDetails addProductDetails(ProductDetails productDetails) throws ProductException;
    public ProductDetails getProductDetailsById(Integer productId) throws ProductException;
    public ProductDetails updateProductDetails(ProductDetails productDetails) throws ProductException;
    public List<Product> getProductByName(String productName) throws ProductException;
    public List<Product> viewAllproducts () throws ProductException;
    public String deleteProductById(Integer prodId) throws ProductException;
    public List<Product> productFilter(ProductFilterRequestModal pfrm) throws ProductException;
    public String uploadImage(String path, MultipartFile multipartFile);

    public Product addProductForm(ProductForm productForm) throws ProductException, IOException;
}
