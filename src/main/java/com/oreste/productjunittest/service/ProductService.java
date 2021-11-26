package com.oreste.productjunittest.service;

import com.oreste.productjunittest.dto.ProductDto;
import com.oreste.productjunittest.model.Product;
import com.oreste.productjunittest.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public List<Product> getAll(){
        return productRepository.findAll();
    }

    public Product getOneById(Long id){
        Optional<Product> product = productRepository.findById(id);
        if(product.isPresent()){
            return product.get();
        }
        return null;
    }

    public Product create(ProductDto dto){
        Product product = new Product(dto);
        return productRepository.save(product);
    }

    public ResponseEntity<?> update(Long id, ProductDto productDto){
        Optional<Product> productFoundById = productRepository.findById(id);
        if(productFoundById.isPresent()){
            Product product = productFoundById.get();
            if(productRepository.existsByName(productDto.getName()) && !productDto.getName().equalsIgnoreCase(product.getName())){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Product with same name exists");
            }else {
                product.setName(productDto.getName());
                product.setPrice(product.getPrice());
                product.setQuantity(productDto.getQuantity());
                
                productRepository.save(product);
                return ResponseEntity.ok(product);
            }
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product Not Found");
        }
    }
    
    public ResponseEntity<?> delete(Long id){
       Optional<Product> product = productRepository.findById(id);
       if(product.isPresent()){
           productRepository.deleteById(id);
           return ResponseEntity.ok("Product deleted");
       }else {
           return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product Not Found");
       }
    }
}
