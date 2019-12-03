package com.kardexcorp.kardex.controller;

import com.kardexcorp.kardex.KardexApplication;
import com.kardexcorp.kardex.model.Product;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.HttpClientErrorException;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = KardexApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProductControllerTest {

    private static final String URL_HOST = "http://localhost:";

    @Autowired
    TestRestTemplate testRestTemplate;

    @LocalServerPort
    private int port;

    private String getRootUrl() {
        return URL_HOST + port;
    }

    @Test
    public void shouldGetAllProductsCorrectly() {
        // given
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> entity = new HttpEntity<>(null, headers);

        // when
        ResponseEntity<String> response = testRestTemplate.exchange(getRootUrl() + "/products",
                HttpMethod.GET, entity, String.class);

        // then
        Assert.assertNotNull(response.getBody());
    }

    @Test
    public void shouldGetASpecificProductCorrectly() {
        // when
        Product user = testRestTemplate.getForObject(getRootUrl() + "/products/1", Product.class);

        // then
        Assert.assertNotNull(user);
    }

    @Test
    public void shouldCreateAProductCorrectly() {
        // given
        Product product = getProduct();

        // when
        ResponseEntity<Product> postResponse = testRestTemplate.postForEntity(getRootUrl() + "/products", product, Product.class);

        // then
        Assert.assertNotNull(postResponse);
        Assert.assertNotNull(postResponse.getBody());
    }

    @Test
    public void shouldUpdateAProductCorrectly() {
        // given
        long id = 1;
        Product product = testRestTemplate.getForObject(getRootUrl() + "/products/" + id, Product.class);
        product.setDescription("glasses fake");
        product.setMaxQuantity(65);

        // when
        testRestTemplate.put(getRootUrl() + "/products/" + id, product);
        Product updatedProduct = testRestTemplate.getForObject(getRootUrl() + "/products/" + id, Product.class);

        // then
        Assert.assertNotNull(updatedProduct);
    }


    @Test
    public void shouldDeleteAProductCorrectly() {
        // given
        int id = 2;
        Product product = testRestTemplate.getForObject(getRootUrl() + "/products/" + id, Product.class);
        Assert.assertNotNull(product);

        // when
        testRestTemplate.delete(getRootUrl() + "/products/" + id);

        try {
            product = testRestTemplate.getForObject(getRootUrl() + "/products/" + id, Product.class);
        } catch (final HttpClientErrorException e) {
            Assert.assertEquals(e.getStatusCode(), HttpStatus.NOT_FOUND);
        }
    }

    private Product getProduct() {
        Product product = new Product();
        product.setMinQuantity(10);
        product.setMaxQuantity(50);
        product.setDescription("glasses");
        return product;
    }
}