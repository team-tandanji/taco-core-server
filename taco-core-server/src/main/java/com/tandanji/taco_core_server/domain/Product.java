package com.tandanji.taco_core_server.domain;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;



//TODO: Valid check
@Getter
@Setter
public class Product {
    private Long id;

    @NotNull
    @Size(min = 1, message = "Title Must be at least one character long")
    private String title;

    private String imagePath;
    private String tradeMethod;

    @NotNull
    private String category;

    @PositiveOrZero(message = "Price can not be negative")
    private Long price;

    private boolean priceOffer;

    @NotNull
    @Size(min = 5, message = "Description Must be at least five character long")
    private String description;
    private String location;
}
