package com.santiago.inventory.service;

import com.santiago.inventory.model.Product;
import com.santiago.inventory.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }
    public Product getProductById(Long id) {

        return productRepository.findById(id).orElse(null);
    }
    public void deleteProduct(Long id) {

        productRepository.deleteById(id);
    }
    public List<Product> searchProducts(String keyword) {

        return productRepository.findByNameContainingIgnoreCase(keyword);
    }
    public long getTotalProducts() {

        return productRepository.count();
    }
    public double getTotalInventoryValue() {

        return productRepository.findAll()
                .stream()
                .mapToDouble(product ->
                        product.getPrice() * product.getStock())
                .sum();
    }
    public long getLowStockProducts() {

        return productRepository.findAll()
                .stream()
                .filter(product -> product.getStock() < 5)
                .count();
    }
}