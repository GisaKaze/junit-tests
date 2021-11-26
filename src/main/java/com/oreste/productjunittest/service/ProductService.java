package com.oreste.productjunittest.service;

import com.oreste.productjunittest.dto.ProductDto;
import com.oreste.productjunittest.model.Product;
import com.oreste.productjunittest.repository.ProductRepository;
import com.oreste.productjunittest.utils.APIResponse;
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

    public ResponseEntity<?> getOneById(Long id){
        Optional<Product> product = productRepository.findById(id);
        if(product.isPresent()){
            return ResponseEntity.ok(new APIResponse(true,"",product.get()));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new APIResponse(false,"Product Not Found"));
    }

    public ResponseEntity<?> create(ProductDto dto){
        Product product = new Product(dto);
        Product createdProduct =  productRepository.save(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(new APIResponse(true, "Product Successfully created", createdProduct));
    }

    public ResponseEntity<?> update(Long id, ProductDto productDto){
        Optional<Product> productFoundById = productRepository.findById(id);
        if(productFoundById.isPresent()){
            Product product = productFoundById.get();
            if(productRepository.existsByName(productDto.getName()) && !(productDto.getName().equalsIgnoreCase(product.getName()))){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new APIResponse(false,"Product with same name exists"));
            }else {
                product.setName(productDto.getName());
                product.setPrice(productDto.getPrice());
                product.setQuantity(productDto.getQuantity());

                productRepository.save(product);
                return ResponseEntity.ok(new APIResponse(true,"Product Updated Successfully",product));
            }
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new APIResponse(false,"Product Not Found"));
        }
    }
    
    public ResponseEntity<?> delete(Long id){
       Optional<Product> product = productRepository.findById(id);
       if(product.isPresent()){
           productRepository.deleteById(id);
           return ResponseEntity.ok(new APIResponse(true,"Product deleted"));
       }else {
           return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new APIResponse(false,"Product Not Found"));
       }
    }
}
