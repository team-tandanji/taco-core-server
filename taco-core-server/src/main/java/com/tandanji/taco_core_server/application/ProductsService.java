package com.tandanji.taco_core_server.application;

import com.tandanji.taco_core_server.domain.Product;
import org.springframework.stereotype.Service;

@Service
public class ProductsService {
    public Product createProduct(Product product) {
        return product;
    }
}
