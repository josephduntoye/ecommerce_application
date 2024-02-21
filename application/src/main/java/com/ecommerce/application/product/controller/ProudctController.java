package com.ecommerce.application.product.controller;

import com.ecommerce.application.exception.BusinessLogicException;
import com.ecommerce.application.product.Product;
import com.ecommerce.application.product.dto.ProductDto;
import com.ecommerce.application.product.service.ProductService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
public class ProudctController {
    @Autowired
    ProductService productService;

    @GetMapping()
    public ResponseEntity<?> findAllProduct() {
        List<Product> productList = productService.getAllProduct();
        return ResponseEntity.ok().body(productList);

    }

    @PostMapping
    public ResponseEntity<?> createProduct(@RequestBody ProductDto productDto, @RequestBody MultipartFile productImage) {


        try {
            Product savedProduct = productService.createProduct(productDto);
            return ResponseEntity.ok().body(savedProduct);

        } catch (BusinessLogicException | IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());

        }


    }

    @PatchMapping(path = "/{id}", consumes = "application/json-patch+json")
    public ResponseEntity<?> updateProduct(@PathVariable Long id, @RequestBody JsonPatch productPatch) {
        try {
            Product updateProduct = productService.updateProduct(id, productPatch);
            return ResponseEntity.status(HttpStatus.OK).body(updateProduct);
        } catch (BusinessLogicException | JsonPatchException | JsonProcessingException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
        //  catch()

    }
}