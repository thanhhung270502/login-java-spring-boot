package com.example.demo.controllers;

import com.example.demo.models.Product;
import com.example.demo.models.ResponseObject;
import com.example.demo.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {
    @Autowired
    private ProductRepository productRepository;

    @GetMapping("")
    List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @PostMapping("")
    ResponseEntity<ResponseObject> createProduct(@RequestBody Product product) {
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("200", "Successfully", productRepository.save(product)));
    }

}
