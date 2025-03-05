package com.tandanji.taco_core_server.presentation;

import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.web.bind.annotation.RequestMethod.*;

@RestController
public class ProductsRestController {

    @PostMapping("/products/create")
    public ResponseEntity<?> createProduct() {
        return ResponseEntity.ok().body(null);
    }

    @GetMapping("/products/read")
    public ResponseEntity<?> getProducts() {
        return ResponseEntity.ok().body(null);
    }

    @PutMapping("/products/update/{id}")
    public ResponseEntity<?> updateProduct() {
        return ResponseEntity.ok().body(null);
    }

    @DeleteMapping("/products/delete/{id}")
    public ResponseEntity<?> deleteProduct() {
        return ResponseEntity.ok().body(null);
    }

}
