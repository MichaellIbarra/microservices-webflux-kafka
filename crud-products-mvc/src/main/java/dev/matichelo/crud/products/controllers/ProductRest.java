package dev.matichelo.crud.products.controllers;

import dev.matichelo.crud.products.models.Product;
import dev.matichelo.crud.products.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductRest {
    @Autowired
    private ProductService productService;

    @GetMapping
    public List<Product> products(){
        return productService.products();
    }

    @GetMapping("/category/{category}")
    public List<Product> productsCategory(@PathVariable String category){
        return productService.productsCategory(category);
    }

    @GetMapping("/{codProduct}")
    public Product productById(@PathVariable int codProduct){
        return productService.productById(codProduct);
    }
    @PostMapping
    public void createProduct(@RequestBody Product product){
        productService.createProduct(product);
    }

    @PutMapping("/{codProduct}/price/{price}")
    public Product updatePrice(@PathVariable int codProduct, @PathVariable double price){
        return productService.updatePrice(codProduct, price);
    }

    @DeleteMapping("/{codProduct}")
    public void deleteProduct(@PathVariable int codProduct){
        productService.deleteProduct(codProduct);
    }

}
