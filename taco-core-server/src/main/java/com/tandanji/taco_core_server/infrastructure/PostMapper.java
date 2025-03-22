package com.tandanji.taco_core_server.infrastructure;

import com.tandanji.taco_core_server.domain.Product;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PostMapper {
    int createProduct(Product product);
    List<Product> getProducts();
    Product getProductById(Long id);
    List<Product> getProductsByKeyword(String keyword);
    int deleteProductById(Long id);
    int updateProduct(Product product);
    List<Product> getProductsByConditions(String title, int price, String location);
}
