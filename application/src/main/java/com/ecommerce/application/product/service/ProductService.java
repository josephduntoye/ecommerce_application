package com.ecommerce.application.product.service;

import com.ecommerce.application.product.Product;
import com.ecommerce.application.product.dto.ProductDto;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;

public interface ProductService {
    List<Product> getAllProduct();
    Product findProductByID(Long productId) throws ProductDoesNotExistException;
    Product createProduct(ProductDto productDto) throws BusinessLogicException  ;
    Product updateProduct(Long productName, JsonPatch patch) throws BusinessLogicException, JsonProcessingException;
}
