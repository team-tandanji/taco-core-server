package com.tandanji.taco_core_server.presentation;

import com.tandanji.taco_core_server.application.ProductsService;
import com.tandanji.taco_core_server.domain.Product;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
public class ProductController {
    private ProductsService productsService;

    @Autowired
    ProductController(ProductsService productsService) {
        this.productsService = productsService;
    }

    @PostMapping("/products")
    public ResponseEntity<Void> createProduct(@RequestPart("product") Product product,
                                              @RequestPart(value = "image", required = false) MultipartFile image) throws IOException {
        log.info("Received request to create product: {}", product.getTitle());

        productsService.createProduct(product, image);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


    @GetMapping("/products")
    public ResponseEntity<List<Product>> getAllProducts() {
        log.info("Received request to fetch all products");
        List<Product> products = productsService.getProducts();

        log.info("Successfully fetched {} products",products.size());
        return ResponseEntity.ok().body(products);
    }

    @GetMapping("/products/search/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        log.info("Received request to fetch product with ID {}",id);

        Product product = productsService.getProductById(id);

        log.info("Product found with ID {}",id);
        return ResponseEntity.ok().body(product);
    }

    @GetMapping("/products/search")
    public ResponseEntity<List<Product>> getProductsByKeyword(@RequestParam("keyword") String keyword) {
        log.info("Received request to fetch products with keyword {}",keyword);

        List<Product> products = productsService.getProductsByKeyword(keyword);

        log.info("Found {} products containing keyword '{}'", products.size(), keyword);

        return ResponseEntity.ok().body(products);
    }

    @PutMapping("/products/{id}")
    public ResponseEntity<String> updateProduct(@PathVariable Long id, @RequestPart(value = "image", required = false) MultipartFile image,
                                                @RequestPart("product") Product product) throws IOException{
        log.info("Received request to update product with ID {}", id);

        productsService.updateProduct(id,image,product);

        return ResponseEntity.status(HttpStatus.OK).body("Successfully Updated");
    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id) {
        log.info("Received request to delete product with ID {}", id);

        int isDeleted = productsService.deleteProductById(id);

        if(isDeleted == 1) {
            log.info("Successfully deleted product with ID {}", id);
            return ResponseEntity.status(HttpStatus.OK).body("ID: " + id + " is deleted");
        } else {
            log.info("Failed to delete product with ID {}", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product with ID : " + id + " does not exist");
        }
    }

    @GetMapping("/products/search/cond")
    public ResponseEntity<List<Product>> getProductsByConditions(@RequestParam(value = "title", required = false) String title,
                                                                @RequestParam(value = "price", required = false) String price,
                                                                @RequestParam(value = "location", required = false) String location) {
        log.info("User want to search with title : {}, price : {}, location : {}",title,price,location);

        List<Product> products = productsService.getProductsByConditions(title, price, location);

        return ResponseEntity.status(HttpStatus.OK).body(products);
    }

}

