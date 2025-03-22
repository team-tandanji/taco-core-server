package com.tandanji.taco_core_server.infrastructure;

import com.tandanji.taco_core_server.domain.Product;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Repository
public class MyBatisRepository implements ProductRepository {

    private final PostMapper postMapper;

    @Override
    public int createProduct(Product product) {
        log.info("Inserting product into database: {}", product.getTitle());
        return postMapper.createProduct(product);
    }

    @Override
    public List<Product> getProducts() {
        log.info("Fetching products from database");
        return postMapper.getProducts();
    }

    @Override
    public Product getProductById(Long id) {
        log.info("Fetching product with ID {} from database",id);
        return postMapper.getProductById(id);
    }

    @Override
    public List<Product> getProductsByKeyword(String keyword) {
        log.info("Fetching products with keyword {} from database",keyword);
        return postMapper.getProductsByKeyword(keyword);
    }

    @Override
    public int deleteProductById(Long id) {
        log.info("Deleting product with ID {} from database", id);
        return postMapper.deleteProductById(id);
    }

    @Override
    public int updateProduct(Product product) {
        log.info("Updating product with ID {} in the database", product.getId());
        return postMapper.updateProduct(product);
    }

    @Override
    public List<Product> getProductsByConditions(String title, int price, String location) {
        log.info("Fetching products with title {}, price {}, location {} from database",title,price,location);
        return postMapper.getProductsByConditions(title,price,location);
    }
}
