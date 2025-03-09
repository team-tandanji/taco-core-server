package com.tandanji.taco_core_server.presentation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tandanji.taco_core_server.application.ProductsService;
import com.tandanji.taco_core_server.domain.Product;
import jakarta.validation.Valid;
import org.apache.tomcat.util.http.parser.HttpParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
public class ProductController {

    private ProductsService productsService;

    @Autowired
    ProductController(ProductsService productsService) {
        this.productsService = productsService;
    }

    @PostMapping(value = "/product/create")
    public ResponseEntity<Void> createProduct(@RequestParam("product") String productJson,
                                              @RequestParam(value = "image", required = false) MultipartFile image) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        Product product = objectMapper.readValue(productJson, Product.class);

        if(image!=null) {
            String imageDir = "/Users/jung-youngmok/SwDevlopement/Backend/taco-core-server/image/";
            String imageFileName = image.getOriginalFilename();
            Path imagePath = Paths.get(imageDir, imageFileName);

            Files.createDirectories(imagePath.getParent());
            image.transferTo(imagePath.toFile());

            product.setImagePath(imagePath.toString());
        } else {
            product.setImagePath(null);
        }


        productsService.createProduct(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(null);
    }

    //TODO: make a get method
    @GetMapping("/product/read")
    public ResponseEntity<?> getProducts() {
        return ResponseEntity.ok().body(null);
    }
    //TODO: make a put method
    @PutMapping("/product/update/{id}")
    public ResponseEntity<?> updateProduct() {
        return ResponseEntity.ok().body(null);
    }
    //TODO: make a delete method
    @DeleteMapping("/product/delete/{id}")
    public ResponseEntity<?> deleteProduct() {
        return ResponseEntity.ok().body(null);
    }

}

