package com.ecommerce.serviceImpl;

import com.ecommerce.Exception.ProductException;
import com.ecommerce.model.CustomSequences;
import com.ecommerce.model.Product;
import com.ecommerce.model.Product_details;
import com.ecommerce.repository.ProductRepo;
import com.ecommerce.repository.Product_detailsRepo;
import com.ecommerce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Service
public class ProductServiceImpl implements ProductService {


    @Autowired
    ProductRepo productRepo;

    @Autowired
    Product_detailsRepo productDetailsRepo;

    @Autowired
    CustomSequences sequences;
    @Override
    public Product addProduct(Product product) throws ProductException {
        product.setId(sequences.getNextSequence("product"));
        product.setCreated_at(LocalDateTime.now());
        product.setUpdated_at(LocalDateTime.now());
        return productRepo.save(product);
    }

    @Override
    public Product getProductById(Integer productId) throws ProductException {

        Optional<Product> optionalProduct = productRepo.findById(productId);
        if (optionalProduct.isPresent()){
            return optionalProduct.get();
        }
        throw new ProductException("Invalid product id!");
    }

    @Override
    public Product updateProduct(Product product) throws ProductException {
        Optional<Product> optionalProduct = productRepo.findById(product.getId());
        if (optionalProduct.isPresent()){
            product.setUpdated_at(LocalDateTime.now());
            product.setCreated_at(optionalProduct.get().getCreated_at());
            return productRepo.save(product);
        }
        throw new ProductException("Invalid product id!");
    }

    @Override
    public Product_details addProduct_details(Product_details productDetails) throws ProductException {
        productDetails.setId(sequences.getNextSequence("product"));
        Optional<Product> optionalProduct = productRepo.findById(productDetails.getProductId());
        if (optionalProduct.isPresent()) {
            Optional<Product_details> optionalProductDetails = productDetailsRepo.findByProductId(productDetails.getProductId());
            if (!optionalProductDetails.isPresent()){
                return productDetailsRepo.save(productDetails);
            }
            throw new ProductException("product_details is already saved for this product id!");
        }
        throw new ProductException("Invalid product_details id!");
    }

    @Override
    public Product_details getProduct_detailsById(Integer productId) throws ProductException {
        Optional<Product_details> optionalProduct = productDetailsRepo.findById(productId);
        if (optionalProduct.isPresent()){
            return optionalProduct.get();
        }
        throw new ProductException("Invalid product_details id!");
    }

    @Override
    public Product_details updateProduct_details(Product_details productDetails) throws ProductException {
        Optional<Product_details> optionalProductDetails = productDetailsRepo.findById(productDetails.getId());
        if (optionalProductDetails.isPresent()){
            Optional<Product> optionalProduct = productRepo.findById(productDetails.getProductId());
            if (optionalProduct.isPresent()){
                productDetailsRepo.save(productDetails);
            }
            throw new ProductException("Invalid product id!");
        }

        throw new ProductException("Invalid product_details id!");
    }

    @Override
    public List<Product> getProductByName(String productName) throws ProductException {
        return null;
    }

    @Override
    public List<Product> viewAllproducts() throws ProductException {
        List<Product> products = productRepo.findAll();
        if (!products.isEmpty()){
            return products;
        }
        throw new ProductException("No product is added");
    }

    @Override
    public String deleteProductById(Integer prodId) throws ProductException {
        Optional<Product> optionalProduct = productRepo.findById(prodId);
        if (optionalProduct.isPresent()){
            productRepo.deleteById(prodId);
            Optional<Product_details> optionalProductDetails = productDetailsRepo.findByProductId(prodId);
            if (optionalProduct.isPresent()){
                productDetailsRepo.deleteById(optionalProductDetails.get().getId());
            }
            return "Product of id "+prodId+" deleted! ";
        }
        throw new ProductException("Invalid product id!");
    }
}
