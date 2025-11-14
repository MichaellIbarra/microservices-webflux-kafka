package dev.matichelo.crud.products.services;

import dev.matichelo.crud.products.models.Product;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private static List<Product> productList = new ArrayList<>(List.of(new Product(100, "Azucar", "Alimentación", 1.10,
                    20),
            new Product(101, "Leche", "Alimentación", 1.20, 15),
            new Product(102, "Jabón", "Limpieza", 0.89, 30),
            new Product(103, "Mesa", "Hogar", 125, 4),
            new Product(104, "Televisión", "Hogar", 650, 10),
            new Product(105, "Huevos", "Alimentación", 2.20, 30),
            new Product(106, "Fregona", "Limpieza", 3.40, 6),
            new Product(107, "Detergente", "Limpieza", 8.7, 12)));
    @Override
    public List<Product> products() {
        return productList;
    }

    @Override
    public List<Product> productsCategory(String category) {
        /*
        stream() -> convierte la lista en un flujo de datos
        filter() -> filtra los datos del flujo
        collect() -> convierte el flujo de datos en una lista
        Collectors.toList() -> convierte el flujo de datos en una lista
         */
        return productList.stream().filter(p -> p.getCategory().equals(category)).collect(Collectors.toList());
    }

    @Override
    public Product productById(int codProduct) {
        return productList.stream().filter(p -> p.getCodProduct() == codProduct).findFirst().orElse(null);
    }

    @Override
    public void createProduct(Product product) {
        if(productById(product.getCodProduct()) == null) {
            productList.add(product);
        } else {
            System.out.println("El producto ya existe");
        }

    }

    @Override
    public Product updatePrice(int codProduct, double price) {
        Product product = productById(codProduct);
        if(product != null){
            product.setPriceUnit(price);
        }else {
            System.out.println("El producto no existe");
        }
        return product;

    }

    @Override
    public void deleteProduct(int codProduct) {
        Product product = productById(codProduct);
        if(product != null){
            productList.remove(product);
        }else {
            System.out.println("El producto no existe");
        }
    }

}
