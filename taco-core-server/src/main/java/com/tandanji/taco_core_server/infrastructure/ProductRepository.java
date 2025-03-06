package com.tandanji.taco_core_server.infrastructure;

import com.tandanji.taco_core_server.domain.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ProductRepository {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    ProductRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public int createProduct(Product product) {
        String sql = "INSERT INTO PRODUCTS (title, imagePath, tradeMethod, price, priceOffer, description, location)"
                + "VALUES (:title, :imagePath, :tradeMethod, :price, :priceOffer, :description, :location)";

        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource(product);
        return namedParameterJdbcTemplate.update(sql, parameterSource);
    }

}
