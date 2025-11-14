package dev.matichelo.crud.products.services;

import dev.matichelo.crud.products.models.Product;

import java.util.List;

public interface ProductService {
    List<Product> products();
    List<Product> productsCategory(String category);
    Product productById(int codProduct);
    void createProduct(Product product);
    Product updatePrice(int codProduct, double price);
    void deleteProduct(int codProduct);


}
