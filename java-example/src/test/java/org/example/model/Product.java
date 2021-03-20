package org.example.model;

import org.openqa.selenium.Keys;

import java.util.Objects;
import java.util.Random;

import static org.example.TestBase.*;

public class Product {

     String name = "velocity" + randomString(4);
     String code = randomNumber(5);
     int quantity = new Random().nextInt(100);
     String shortDescription = "Детский зеленый велосипед";
     String filePath = System.getProperty("user.dir") + "\\src\\test\\resources\\product1.jpg";
     String description = "Детский велосипед" + Keys.RETURN + "Зеленого цвета";
     String price = new Random().nextInt(100) + ",0";
    
    PriceVisualProperties priceVisualProperties;
    Product productDetails;

    public Product getProductDetails() {
        return productDetails;
    }

    public Product withProductDetails(Product productDetails) {
        this.productDetails = productDetails;
        return this;
    }

    public Product(){

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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
