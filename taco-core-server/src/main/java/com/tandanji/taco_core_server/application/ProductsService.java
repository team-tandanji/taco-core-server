package com.tandanji.taco_core_server.application;

import com.tandanji.taco_core_server.domain.Product;
import com.tandanji.taco_core_server.infrastructure.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class ProductsService {

    private ProductRepository productRepository;
    private ValidationService validationService;

    @Autowired
    ProductsService(ProductRepository productRepository, ValidationService validationService) {
        this.productRepository = productRepository;
        this.validationService = validationService;
    }

    public void createProduct(Product product, MultipartFile image) throws IOException {
        validationService.checkValid(product);

        product = saveImage(product, image);

        productRepository.createProduct(product);
    }

    public static Product saveImage(Product product, MultipartFile image) throws IOException {
        if (image != null) {
            String imageSaveDir = System.getProperty("user.dir") + "/image";

            String imageFileName = System.currentTimeMillis() + "-" + image.getOriginalFilename();
            Path imagePath = Paths.get(imageSaveDir, imageFileName);

            Files.createDirectories(imagePath.getParent());
            image.transferTo(imagePath.toFile());

            product.setImagePath(imagePath.toString());
        } else {
            product.setImagePath(null);
        }

        return product;
    }
}
