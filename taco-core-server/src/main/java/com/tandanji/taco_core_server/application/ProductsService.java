package com.tandanji.taco_core_server.application;

import com.tandanji.taco_core_server.domain.Product;
import com.tandanji.taco_core_server.infrastructure.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductsService {

    private ProductRepository productRepository;
    private ValidationService validationService;

    @Autowired
    ProductsService(ProductRepository productRepository, ValidationService validationService) {
        this.productRepository = productRepository;
        this.validationService = validationService;
    }

    //TODO: Valid check about product
    public Product createProduct(Product product) {
        validationService.checkValid(product);
        int result = productRepository.createProduct(product);

        if(result == 1) {
            return product;
        } else {
            return null;
        }
    }
}
