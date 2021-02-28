package org.example;

import java.util.Objects;

public class Product {

    String name;
    PriceVisualProperties priceVisualProperties;
    Product productDetails;

    public Product getProductDetails() {
        return productDetails;
    }

    public Product withProductDetails(Product productDetails) {
        this.productDetails = productDetails;
        return this;
    }


    public Product(String name, PriceVisualProperties priceVisualProperties, Product productDetails) {
        this.name = name;
        this.priceVisualProperties = priceVisualProperties;
        this.productDetails = productDetails;
    }

    public Product(String name, PriceVisualProperties priceVisualProperties) {
        this(name, priceVisualProperties,null);
    }

    public String getName() {
        return name;
    }

    public Product withName(String name) {
        this.name = name;
        return this;
    }

    public PriceVisualProperties getPriceVisualProperties() {
        return priceVisualProperties;
    }

    public Product withPrice(PriceVisualProperties priceVisualProperties) {
        this.priceVisualProperties = priceVisualProperties;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(name, product.name) &&
                Objects.equals(priceVisualProperties, product.priceVisualProperties);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, priceVisualProperties);
    }

    @Override
    public String toString() {
        return "Product{" +
                "name='" + name + '\'' +
                ", price=" + priceVisualProperties +
                '}';
    }
}
