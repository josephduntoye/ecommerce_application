package com.ecommerce.application.product.service;

import com.cloudinary.utils.ObjectUtils;
import com.ecommerce.application.exception.BusinessLogicException;
import com.ecommerce.application.exception.ProductDoesNotExistException;
import com.ecommerce.application.product.Product;
import com.ecommerce.application.product.dto.ProductDto;
import com.ecommerce.application.product.repository.ProductRepository;
import com.ecommerce.application.utility.cloud.CloudinaryService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class ProductServiceImpl implements ProductService{
    @Autowired
    ProductRepository productRepository;

    @Autowired
    CloudinaryService cloudinaryService;
    @Override
    public List<Product> getAllProduct() { return productRepository.findAll(); }

    @Override
    public Product findProductByID(Long productID) throws ProductDoesNotExistException {
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

        // cloudinaryService.upload(productDto.getImage().)

        Product product = new Product();
        try{
            if(productDto.getImage() != null){
                Map<?, ?> uploadResult = cloudinaryService.upload(productDto.getImage().getBytes(), ObjectUtils.asMap());
                product.setImageURL(uploadResult.get("urls").toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        product.setName(product.getName());
        product.setPrice(product.getId());
        product.setQuantity(product.getQuantity());
        product.setDescription(product.getDescription());

        return productRepository.save(product);
    }

    private  Product savedorUpdate(Product product) throws BusinessLogicException {
        if(product== null){
            throw new BusinessLogicException("product cannot be null");
        }
        return productRepository.save(product);
    }

    @Override
    public Product updateProduct(Long productName, JsonPatch productPatch) throws BusinessLogicException, JsonProcessingException, JsonPatchException  {
        Optional<Product> productQuery = productRepository.findById(productName);
        if(productQuery.isEmpty()){
            throw new BusinessLogicException("Product with ID"+ productName + "does not exit");
        }
        Product targetProduct = productQuery.get();
        try {
            targetProduct = applyPatchToProduct(productPatch, targetProduct);
            return savedorUpdate(targetProduct);

        } catch (JsonPatchException | JsonProcessingException | BusinessLogicException e) {
            throw new BusinessLogicException("update failed");
        }
    }
    private  Product applyPatchToProduct(JsonPatch productPatch, Product targetProduct) throws JsonPatchException, JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode patched = productPatch.
                apply(objectMapper.convertValue(targetProduct, JsonNode.class));
        return objectMapper.treeToValue(patched, Product.class);
    }
}
