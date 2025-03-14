package com.tandanji.taco_core_server.application;

import com.tandanji.taco_core_server.domain.Product;
import com.tandanji.taco_core_server.infrastructure.ProductRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

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
        validationService.checkValid(product);

        product = saveImage(product, image);

        productRepository.createProduct(product);
    }

    public Product saveImage(Product product, MultipartFile image) throws IOException {
        validationService.checkValid(product);

        if (image != null && !image.isEmpty()) {
            String imageSaveDir = Paths.get(System.getProperty("user.dir"),"image").toString();

            String imageFileName = System.currentTimeMillis() + "-" + image.getOriginalFilename();
            Path imagePath = Paths.get(imageSaveDir, imageFileName);

            Files.createDirectories(imagePath.getParent());
            image.transferTo(imagePath.toFile());

            product.setImagePath(imagePath.toString());
        } else {
            product.setImagePath(product.getImagePath());
        }

        return product;
    }

    public List<Product> getProducts() {
        return productRepository.getProducts();
    }

    public Product getProductById(Long id) {
        return productRepository.getProductById(id);
    }


    public List<Product> getProductsByKeyword(String keyword) {
        validationService.checkValidBlank(keyword);

        return productRepository.getProductsByKeyword(keyword.trim());
    }

    @Transactional
    public int deleteProductById(Long id) {
        return productRepository.deleteProductById(id);
    }

    @Transactional
    public void updateProduct(Long id, MultipartFile image, Product updateProduct) throws IOException {
        validationService.checkValid(updateProduct);

        updateProduct.setId(id);
        updateProduct.setImagePath(productRepository.getProductById(id).getImagePath());

        updateProduct = saveImage(updateProduct, image);

        productRepository.updateProduct(updateProduct);
    }
}
