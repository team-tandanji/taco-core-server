package com.tandanji.taco_core_server.domain;

public class Product {
    private Long id;
    private String title;
    private String imagePath;
    private String tradeMethod;
    private Long price;
    private boolean priceOffer;
    private String description;
    private String location;


    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getImagePath() {
        return imagePath;
    }

    public String getTradeMethod() {
        return tradeMethod;
    }

    public Long getPrice() {
        return price;
    }

    public boolean isPriceOffer() {
        return priceOffer;
    }

    public String getDescription() {
        return description;
    }

    public String getLocation() {
        return location;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public void setTradeMethod(String tradeMethod) {
        this.tradeMethod = tradeMethod;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public void setPriceOffer(boolean priceOffer) {
        this.priceOffer = priceOffer;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
