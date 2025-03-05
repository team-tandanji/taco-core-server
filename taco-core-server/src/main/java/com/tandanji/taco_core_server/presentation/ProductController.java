package com.tandanji.taco_core_server.presentation;

import com.tandanji.taco_core_server.application.ProductsService;
import com.tandanji.taco_core_server.domain.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ProductController {

    private ProductsService productsService;

    @Autowired
    ProductController(ProductsService productsService) {
        this.productsService = productsService;
    }

    @PostMapping(value = "/product/create")
    public ResponseEntity<?> createProduct(@RequestBody Product product) {
        Product product1 = productsService.createProduct(product);
        System.out.println(product.toString());
        return ResponseEntity.ok().body(product1);
    }

    @GetMapping("/product/read")
    public ResponseEntity<?> getProducts() {
        return ResponseEntity.ok().body(null);
    }

    @PutMapping("/product/update/{id}")
    public ResponseEntity<?> updateProduct() {
        return ResponseEntity.ok().body(null);
    }

    @DeleteMapping("/product/delete/{id}")
    public ResponseEntity<?> deleteProduct() {
        return ResponseEntity.ok().body(null);
    }

}

