package com.tandanji.taco_core_server.infrastructure;

import com.tandanji.taco_core_server.domain.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProductRepository {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    ProductRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public int createProduct(Product product) {
        final String sql = "INSERT INTO PRODUCTS (title, imagePath, tradeMethod, category, price, priceOffer, description, location)"
                + "VALUES (:title, :imagePath, :tradeMethod, :category, :price, :priceOffer, :description, :location)";

        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource(product);
        return namedParameterJdbcTemplate.update(sql, parameterSource);
    }

    public List<Product> readProducts() {
        final String sql = "SELECT * FROM PRODUCTS";

        return namedParameterJdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Product.class));
    }
}
