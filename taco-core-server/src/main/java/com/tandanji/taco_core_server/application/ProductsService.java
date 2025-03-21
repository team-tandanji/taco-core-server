package com.tandanji.taco_core_server.application;

import com.tandanji.taco_core_server.domain.Product;
import com.tandanji.taco_core_server.infrastructure.ProductRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Slf4j
@Service
public class ProductsService {

    private final ProductRepository productRepository;
    private final ValidationService validationService;

    @Autowired
    ProductsService(ProductRepository productRepository, ValidationService validationService) {
        this.productRepository = productRepository;
        this.validationService = validationService;
    }

    @Transactional
    public void createProduct(Product product, MultipartFile image) throws IOException {
        log.info("Start processing product creation");
        validationService.checkValid(product);

        product = saveImage(product, image);
        productRepository.createProduct(product);

        log.info("Product creation completed successfully");
    }

    public Product saveImage(Product product, MultipartFile image) throws IOException {
        validationService.checkValid(product);

        if (image != null && !image.isEmpty()) {
            log.info("Image exists for product: {}", product.getTitle());
            String imageSaveDir = Paths.get(System.getProperty("user.dir"),"image").toString();

            String imageFileName = System.currentTimeMillis() + "-" + image.getOriginalFilename();
            Path imagePath = Paths.get(imageSaveDir, imageFileName);

            Files.createDirectories(imagePath.getParent());
            image.transferTo(imagePath.toFile());

            product.setImagePath(imagePath.toString());

            log.debug("Image saved at path: {}",imagePath);
        } else {
            log.info("No image provided for product: {}", product.getTitle());
            product.setImagePath(product.getImagePath());
        }

        return product;
    }

    public List<Product> getProducts() {
        log.info("Fetching products from repository");
        return productRepository.getProducts();
    }

    public Product getProductById(Long id) {
        log.info("Fetching product with ID {} from repository",id);
        return productRepository.getProductById(id);
    }


    public List<Product> getProductsByKeyword(String keyword) {
        log.info("Fetching products with Keyword {} from repository",keyword);
        validationService.checkValidBlank(keyword);

        return productRepository.getProductsByKeyword(keyword.trim());
    }

    @Transactional
    public int deleteProductById(Long id) {
        log.info("Deleting product with ID {} from repository", id);
        return productRepository.deleteProductById(id);
    }

    @Transactional
    public void updateProduct(Long id, MultipartFile image, Product updateProduct) throws IOException {
        log.info("Updating product with ID {}", id);
        validationService.checkValid(updateProduct);

        updateProduct.setId(id);
        updateProduct.setImagePath(productRepository.getProductById(id).getImagePath());

        updateProduct = saveImage(updateProduct, image);

        productRepository.updateProduct(updateProduct);
        log.info("Successfully updated product with ID {}", id);
    }
}
