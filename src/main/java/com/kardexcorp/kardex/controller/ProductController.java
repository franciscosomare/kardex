package com.kardexcorp.kardex.controller;

import com.kardexcorp.kardex.exception.ProductNotFoundException;
import com.kardexcorp.kardex.model.Product;
import com.kardexcorp.kardex.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/kardex")
public class ProductController {

    @Autowired
    private ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    /**
     * Get all products list.
     *
     * @return the list
     */
    @GetMapping("/products")
    public List<Product> getAll() {
        return productService.getAll();
    }

    /**
     * Gets products by id.
     *
     * @param productId the product id
     * @return the product by id
     * @throws ProductNotFoundException the resource not found exception
     */
    @GetMapping("/products/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable(value = "id") long productId) throws ProductNotFoundException {
        Product product = productService.getProduct(productId);
        return ResponseEntity.ok().body(product);
    }

    /**
     * Create product product.
     *
     * @param product the product
     * @return the product
     */
    @PostMapping("/products")
    public Product addProduct(@Valid @RequestBody Product product) {
        return productService.addProduct(product);
    }

    /**
     * Delete user map.
     *
     * @param productId the product id
     * @return the map
     * @throws Exception the exception
     */
    @DeleteMapping("/products/{id}")
    public Map<String, Boolean> deleteProduct(@PathVariable(value = "id") long productId) {
        productService.deleteProduct(productId);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }

    /**
     * Update product response entity.
     *
     * @param productId      the user id
     * @param productDetails the user details
     * @return the response entity
     * @throws ProductNotFoundException the resource not found exception
     */
    @PutMapping("products/{id}")
    public ResponseEntity<Product> updateProduct(
            @PathVariable(value = "id") long productId, @Valid @RequestBody Product productDetails) throws ProductNotFoundException {
        Product updatedProduct = productService.updateProduct(productId, productDetails);
        return ResponseEntity.ok().body(updatedProduct);
    }
}