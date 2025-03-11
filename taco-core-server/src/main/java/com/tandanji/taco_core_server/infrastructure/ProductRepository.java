package com.tandanji.taco_core_server.infrastructure;

import com.tandanji.taco_core_server.domain.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

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
        final String sql = "INSERT INTO PRODUCTS (title, imagePath, tradeMethod, category, price, priceOffer, description, location)"
                + "VALUES (:title, :imagePath, :tradeMethod, :category, :price, :priceOffer, :description, :location)";

        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource(product);
        return namedParameterJdbcTemplate.update(sql, parameterSource);
    }

    public List<Product> getProducts() {
        final String sql = "SELECT * FROM PRODUCTS";

        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Product.class));
    }

    public Product getProductById(Long id) {
        final String sql = "SELECT * FROM PRODUCTS WHERE id = ?";

        return jdbcTemplate.queryForObject(sql,
                new Object[]{id},
                new BeanPropertyRowMapper<>(Product.class));
    }

    public List<Product> getProductsByKeyword(String keyword) {
        final String sql = "SELECT * FROM PRODUCTS WHERE TITLE LIKE ?";

        String includeKeyword = "%" + keyword + "%";

        //TODO - If didn't find searched item what message should i send?
        return jdbcTemplate.query(sql,
                new Object[]{includeKeyword},
                new BeanPropertyRowMapper<>(Product.class));
    }

    public int deleteProductById(Long id) {
        final String sql = "DELETE FROM PRODUCTS WHERE id = ?";

        return jdbcTemplate.update(sql,id);
    }
}
