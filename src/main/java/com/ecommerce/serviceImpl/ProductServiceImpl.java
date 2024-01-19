package com.ecommerce.serviceImpl;

import com.ecommerce.DTO.ProductForm;
import com.ecommerce.Exception.ProductException;
import com.ecommerce.model.CustomSequences;
import com.ecommerce.model.Product;
import com.ecommerce.DTO.ProductFilterRequestModal;
import com.ecommerce.model.ProductDetails;
import com.ecommerce.model.User;
import com.ecommerce.repository.ProductRepo;
import com.ecommerce.repository.Product_detailsRepo;
import com.ecommerce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.*;


@Service
public class ProductServiceImpl implements ProductService {


    @Autowired
    ProductRepo productRepo;

    @Autowired
    MongoTemplate mongoTemplate;

    @Autowired
    Product_detailsRepo productDetailsRepo;

    @Autowired
    CustomSequences sequences;

    @Value("${project.image}")
    String path;


    @Override
    public Product addProduct(Product product) throws ProductException, IOException {
        product.setId(sequences.getNextSequence("product"));
        product.setCreated_at(LocalDateTime.now());
        return productRepo.save(product);
    }


    @Override
    public Product addProductForm(ProductForm productForm) throws ProductException, IOException {
        Product product = new Product();
        product.setId(sequences.getNextSequence("product"));
        product.setCreated_at(LocalDateTime.now());
        product.setName(productForm.getName());
        product.setCategory(productForm.getCategory());
        product.setPrice(productForm.getPrice());
        product.setSize(productForm.getSize());
        product.setDescription(productForm.getDescription());

        MultipartFile multipartFile = productForm.getMultipartFile();

        String name = multipartFile.getOriginalFilename();
        String filePath = path+name;
        System.out.println("path "+path);
        System.out.println("filePath "+filePath);
        File file = new File(path);

        if (!file.exists()){
            file.mkdir();
        }
        Files.copy(multipartFile.getInputStream(), Paths.get(filePath));

        product.setFile(multipartFile.getBytes());
        product.setFilePath(name);
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
            product.setCreated_at(optionalProduct.orElseThrow().getCreated_at());
            return productRepo.save(product);
        }
        throw new ProductException("Invalid product id!");
    }

    @Override
    public ProductDetails addProductDetails(ProductDetails productDetails) throws ProductException {
        productDetails.setId(sequences.getNextSequence("productDetails"));
        Optional<Product> optionalProduct = productRepo.findById(productDetails.getProductId());
        if (optionalProduct.isPresent()) {
            Optional<ProductDetails> optionalProductDetails = productDetailsRepo.findByProductId(productDetails.getProductId());
            if (!optionalProductDetails.isPresent()){
                return productDetailsRepo.save(productDetails);
            }
            throw new ProductException("product_details is already saved for this product id!");
        }
        throw new ProductException("Invalid product_details id!");
    }

    @Override
    public ProductDetails getProductDetailsById(Integer productId) throws ProductException {
        Optional<ProductDetails> optionalProduct = productDetailsRepo.findById(productId);
        if (optionalProduct.isPresent()){
            return optionalProduct.get();
        }
        throw new ProductException("Invalid product_details id!");
    }

    @Override
    public ProductDetails updateProductDetails(ProductDetails productDetails) throws ProductException {
        Optional<ProductDetails> optionalProductDetails = productDetailsRepo.findById(productDetails.getId());
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
            Optional<ProductDetails> optionalProductDetails = productDetailsRepo.findByProductId(prodId);
            if (optionalProductDetails.isPresent()){
                productDetailsRepo.deleteById(optionalProductDetails.get().getId());
            }
            return "Product of id "+prodId+" deleted! ";
        }
        throw new ProductException("Invalid product id!");
    }

    @Override
    public List<Product> productFilter(ProductFilterRequestModal pfrm) throws ProductException {

         String name = pfrm.getName();
         String category = pfrm.getCategory();
         Integer minPrice = pfrm.getMinPrice();
         Integer maxPrice = pfrm.getMaxPrice();
         String size = pfrm.getSize();

        Query query = new Query();

        List<Criteria> criteriaList = new ArrayList<>();

        if (name != null && !name.isEmpty()) {
            criteriaList.add(Criteria.where("name").regex(name, "i"));
        }

        if (category != null && !category.isEmpty()) {
            criteriaList.add(Criteria.where("category").is(category));
        }

        if (size != null && !size.isEmpty()) {
            criteriaList.add(Criteria.where("size").is(size));
        }

        if (minPrice != null || maxPrice != null) {
            List<Criteria> priceConditions = new ArrayList<>();

            if (minPrice != null) {
                priceConditions.add(Criteria.where("price").gte(minPrice));
            }

            if (maxPrice != null) {
                priceConditions.add(Criteria.where("price").lte(maxPrice));
            }

            if (!priceConditions.isEmpty()) {
                criteriaList.add(new Criteria().andOperator(priceConditions.toArray(new Criteria[0])));
            }
        }

        if (!criteriaList.isEmpty()) {
            query.addCriteria(new Criteria().andOperator(criteriaList.toArray(new Criteria[0])));
        }

        return mongoTemplate.find(query, Product.class);

    }

    @Override
    public String uploadImage(String path, MultipartFile multipartFile) {
        return null;
    }

}
