package com.oreste.productjunittest.repository;

import com.oreste.productjunittest.dto.ProductDto;
import com.oreste.productjunittest.model.Product;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.opentest4j.AssertionFailedError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RunWith(SpringRunner.class)
@DataJpaTest
public class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Test
    public void getAll__success(){
        List<Product> products = productRepository.findAll();
        assertEquals(products.size(),4);
    }

    @Test
    public void findOne__success(){
        Optional<Product> product = productRepository.findById(1L);
        assertTrue(product.isPresent());
    }

    @Test
    public void findOne__fail(){
        Optional<Product> product = productRepository.findById(10L);
        assertFalse(product.isPresent());
    }

    @Test
    public void create__success(){
        ProductDto productDto = new ProductDto("Laptop", 100000.0,24);
        Product createdProduct = productRepository.save(new Product(productDto));
        assertNotNull(createdProduct.getId());
        assertEquals(productDto.getName(),createdProduct.getName());
        assertEquals(productDto.getPrice(),createdProduct.getPrice());
        assertEquals(productDto.getQuantity(),createdProduct.getQuantity());
    }

    @Test
    public void update__success(){
        Optional<Product> findById = productRepository.findById(1L);
        Product product = findById.get();
        product.setName("Keyboard");
        product.setPrice(20000.0);
        Product savedProduct = productRepository.save(product);
        assertTrue(findById.isPresent());
        assertEquals(savedProduct.getName(),product.getName());
        assertEquals(savedProduct.getPrice(),product.getPrice());
    }

    @Test(expected = NoSuchElementException.class)
    public void update__fail(){
        Optional<Product> findById = productRepository.findById(10L);
        Product product = findById.get();
        product.setName("Keyboard");
        product.setPrice(20000.0);
        Product savedProduct = productRepository.save(product);
        assertFalse(findById.isPresent());
        assertNotEquals(savedProduct.getName(),product.getName());
        assertNotEquals(savedProduct.getPrice(),product.getPrice());
    }

    @Test()
    public void delete__success(){
        List<Product> products = productRepository.findAll();
        productRepository.deleteById(2L);
        List<Product> newProducts = productRepository.findAll();
        assertFalse(products.size() == newProducts.size());
    }

    @Test(expected = EmptyResultDataAccessException.class)
    public void delete__fail(){
        List<Product> products = productRepository.findAll();
        productRepository.deleteById(10L);
        List<Product> newProducts = productRepository.findAll();
        assertTrue(products.size() == newProducts.size());
    }

}
