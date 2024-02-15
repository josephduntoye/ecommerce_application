package com.ecommerce.application.product.service;

import com.ecommerce.application.product.Product;
import com.ecommerce.application.product.dto.ProductDto;
import com.ecommerce.application.product.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class ProductServiceImpl implements ProductService{
    @Autowired
    ProductRepository productRepository;

    @Autowired
    CloudinaryService cloudinaryService;
    @Override
    public List<Product> getAllProduct() { return productRepository.findAll(); }

    @Override
    public Product findProductByID(Long productID) throws  ProductDoesNotExistException {
        if (productID == null){
            throw new IllegalArgumentException("Id cannot be null");
        }
        Optional<Product> queryResult = productRepository.findById(productID);
        if ( queryResult.isPresent()){
            return  queryResult.get();
        }

        throw new ProductDoesNotExistException("Product with ID : " + productID + " : does not exist");
    }

    @Override
    public Product createProduct(ProductDto productDto) throws BusinessLogicException {
        if(productDto == null){
            throw  new IllegalArgumentException("Argument cannot be null");
        }
        Optional<Product> query = productRepository.findByName(productDto.getName());
        if(query.isPresent()){
            throw new BusinessLogicException("Product with name " + productDto.getName() + "already exist");
        }

        // cloudinaryService.upload(productDto.g
    }
}
