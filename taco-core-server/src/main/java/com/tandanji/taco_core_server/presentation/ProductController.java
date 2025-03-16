package com.tandanji.taco_core_server.presentation;

import com.tandanji.taco_core_server.application.ProductsService;
import com.tandanji.taco_core_server.domain.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

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

        productsService.createProduct(product, image);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


    @GetMapping("/products")
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = productsService.getProducts();

        return ResponseEntity.ok().body(products);
    }

    @GetMapping("/products/search/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        Product product = productsService.getProductById(id);

        return ResponseEntity.ok().body(product);
    }

    @GetMapping("/products/search/")
    public ResponseEntity<List<Product>> getProductsByKeyword(@RequestParam("keyword") String keyword) {
        List<Product> products = productsService.getProductsByKeyword(keyword);

        return ResponseEntity.ok().body(products);
    }

    @PutMapping("/products/{id}")
    public ResponseEntity<String> updateProduct(@PathVariable Long id, @RequestPart(value = "image", required = false) MultipartFile image,
                                                @RequestPart("product") Product product) throws IOException{
        productsService.updateProduct(id,image,product);

        return ResponseEntity.status(HttpStatus.OK).body("Successfully Updated");
    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id) {
        int isDeleted = productsService.deleteProductById(id);

        if(isDeleted == 1) {
            return ResponseEntity.status(HttpStatus.OK).body("ID: " + id + " is deleted");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product with ID : " + id + " does not exist");
        }
    }

}

