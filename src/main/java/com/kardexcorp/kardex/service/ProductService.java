package com.kardexcorp.kardex.service;

import com.kardexcorp.kardex.exception.ProductNotFoundException;
import com.kardexcorp.kardex.model.Product;
import com.kardexcorp.kardex.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public Product addProduct(Product product) {
        return productRepository.save(product);
    }

    public Product getProduct(long productId) throws ProductNotFoundException {
        return productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException(productId));
    }

    public List<Product> getAll() {
        return productRepository.findAll();
    }

    public void deleteProduct(long id) {
        productRepository.deleteById(id);
    }

    public Product updateProduct(long productId, Product productDetails) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException(productId));

        product.setDescription(productDetails.getDescription());
        product.setMaxQuantity(productDetails.getMaxQuantity());
        product.setMinQuantity(productDetails.getMinQuantity());

        return productRepository.save(product);
    }
}