package com.oreste.productjunittest.controller;

import com.oreste.productjunittest.dto.ProductDto;
import com.oreste.productjunittest.model.Product;
import com.oreste.productjunittest.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping()
    public List<Product> listProducts(){
        return productService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable(name = "id") Long id){
        return productService.getOneById(id);
    }

    @PostMapping()
    public ResponseEntity<?> create(@RequestBody @Valid ProductDto productDto){
        return productService.create(productDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable(name = "id") Long id,@RequestBody @Valid ProductDto productDto){
        return productService.update(id,productDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable(name = "id") Long id){
        return productService.delete(id);
    }

}
