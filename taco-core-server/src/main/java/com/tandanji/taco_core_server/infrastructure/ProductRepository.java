package com.tandanji.taco_core_server.infrastructure;

import com.tandanji.taco_core_server.domain.Product;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@Repository
public class ProductRepository {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    ProductRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate, JdbcTemplate jdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.jdbcTemplate = jdbcTemplate;
    }

    public int createProduct(Product product) {
        log.info("Inserting product into database: {}", product.getTitle());
        final String sql = "INSERT INTO PRODUCTS (title, imagePath, tradeMethod, category, price, priceOffer, description, location)"
                + "VALUES (:title, :imagePath, :tradeMethod, :category, :price, :priceOffer, :description, :location)";

        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource(product);
        return namedParameterJdbcTemplate.update(sql, parameterSource);
    }

    public List<Product> getProducts() {
        log.info("Fetching products from database");
        final String sql = "SELECT * FROM PRODUCTS";

        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Product.class));
    }

    public Product getProductById(Long id) {
        log.info("Fetching product with ID {} from database",id);
        final String sql = "SELECT * FROM PRODUCTS WHERE id = ?";

        return jdbcTemplate.queryForObject(sql,
                new Object[]{id},
                new BeanPropertyRowMapper<>(Product.class));
    }

    public List<Product> getProductsByKeyword(String keyword) {
        log.info("Fetching products with keyword {} from database",keyword);
        final String sql = "SELECT * FROM PRODUCTS WHERE TITLE LIKE ?";

        String includeKeyword = "%" + keyword + "%";

        //TODO - If didn't find searched item what message should i send?
        return jdbcTemplate.query(sql,
                new Object[]{includeKeyword},
                new BeanPropertyRowMapper<>(Product.class));
    }

    @Transactional
    public int deleteProductById(Long id) {
        log.info("Deleting product with ID {} from database", id);

        final String sql = "DELETE FROM PRODUCTS WHERE id = ?";

        return jdbcTemplate.update(sql,id);
    }

    public int updateProduct(Product product) {
        log.info("Updating product with ID {} in the database", product.getId());

        final String sql = "UPDATE PRODUCTS SET title = :title, imagePath = :imagePath, tradeMethod = :tradeMethod, "
                + "category = :category, price = :price, priceOffer = :priceOffer, description = :description, "
                + "location = :location "
                + "WHERE id = :id";

        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource(product);
        return namedParameterJdbcTemplate.update(sql, parameterSource);
    }
}
