package com.tandanji.taco_core_server.infrastructure;

import com.tandanji.taco_core_server.domain.Product;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Repository
public class JDBCRepository implements ProductRepository {

    //TODO 0. change column(add, remove, change) property
    //TODO 1. change jdbc to mybatis
    //TODO 2. spring data jpa
    //TODO 3. add redis
    //TODO 4. jwt
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    JDBCRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate, JdbcTemplate jdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int createProduct(Product product) {
        log.info("Inserting product into database: {}", product.getTitle());
        final String sql = "INSERT INTO PRODUCTS (title, imagePath, tradeMethod, category, price, priceOffer, description, location)"
                + "VALUES (:title, :imagePath, :tradeMethod, :category, :price, :priceOffer, :description, :location)";

        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource(product);
        return namedParameterJdbcTemplate.update(sql, parameterSource);
    }

    @Override
    public List<Product> getProducts() {
        log.info("Fetching products from database");
        final String sql = "SELECT * FROM PRODUCTS";

        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Product.class));
    }

    @Override
    public Product getProductById(Long id) {
        log.info("Fetching product with ID {} from database",id);
        final String sql = "SELECT * FROM PRODUCTS WHERE id = ?";

        return jdbcTemplate.queryForObject(sql,
                new Object[]{id},
                new BeanPropertyRowMapper<>(Product.class));
    }

    @Override
    public List<Product> getProductsByKeyword(String keyword) {
        log.info("Fetching products with keyword {} from database",keyword);
        final String sql = "SELECT * FROM PRODUCTS WHERE TITLE LIKE ?";

        String includeKeyword = "%" + keyword + "%";

        return jdbcTemplate.query(sql,
                new Object[]{includeKeyword},
                new BeanPropertyRowMapper<>(Product.class));
    }

    @Override
    @Transactional
    public int deleteProductById(Long id) {
        log.info("Deleting product with ID {} from database", id);

        final String sql = "DELETE FROM PRODUCTS WHERE id = ?";

        return jdbcTemplate.update(sql,id);
    }

    @Override
    public int updateProduct(Product product) {
        log.info("Updating product with ID {} in the database", product.getId());

        final String sql = "UPDATE PRODUCTS SET title = :title, imagePath = :imagePath, tradeMethod = :tradeMethod, "
                + "category = :category, price = :price, priceOffer = :priceOffer, description = :description, "
                + "location = :location "
                + "WHERE id = :id";

        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource(product);
        return namedParameterJdbcTemplate.update(sql, parameterSource);
    }

    @Override
    public List<Product> getProductsByConditions(String title, int price, String location) {
        log.info("Fetching products with title {}, price {}, location {} from database",title,price,location);

        StringBuilder sql = new StringBuilder("SELECT * FROM PRODUCTS WHERE 1 = 1");
        List<Object> params = new ArrayList<>();

        if(title != null && !title.isEmpty()) {
            sql.append(" AND title like ?");
            params.add("%" + title + "%");
        }

        if(price > 0) {
            sql.append(" AND price <= ?");
            params.add(price);
        }

        if(location != null && !location.isEmpty()) {
            sql.append(" AND location like ?");
            params.add("%" + location + "%");
        }

        log.info(sql.toString());

        return jdbcTemplate.query(sql.toString(), params.toArray(), new BeanPropertyRowMapper<>(Product.class));
    }
}
