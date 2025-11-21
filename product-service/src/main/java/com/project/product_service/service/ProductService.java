package com.project.product_service.service;

import com.project.product_service.dto.ProductRequest;
import com.project.product_service.dto.ProductResponse;
import com.project.product_service.entity.Product;
import com.project.product_service.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public ProductResponse createProduct(ProductRequest productRequest) {
        Product product = new Product();
        mapToProduct(product, productRequest);
        Product savedEntity = productRepository.save(product);
        return mapToProductResponse(savedEntity);
    }

    public Optional<ProductResponse> updateProduct(Long id, ProductRequest productRequest) {
        return productRepository.findById(id)
                .map(existingProduct -> {
                    mapToProduct(existingProduct, productRequest);
                    Product savedEntity = productRepository.save(existingProduct);
                    return mapToProductResponse(savedEntity);
                });
    }

    public List<ProductResponse> getAllProducts() {
        return productRepository.findByActiveTrue()
                .stream()
                .map(this::mapToProductResponse)
                .toList();
    }

    public boolean deleteProduct(Long id) {
        return productRepository.findById(id)
                .map(product -> {
                    product.setActive(false);
                    productRepository.save(product);
                    return true;
                }).orElse(false);
    }

    public List<ProductResponse> searchProduct(String keyword) {
        return productRepository.searchProducts(keyword)
                .stream()
                .map(this::mapToProductResponse)
                .toList();
    }

    private void mapToProduct(Product product, ProductRequest productRequest) {
        product.setName(productRequest.getName());
        product.setDescription(productRequest.getDescription());
        product.setPrice(productRequest.getPrice());
        product.setCategory(productRequest.getCategory());
        product.setImageUrl(productRequest.getImageUrl());
        product.setStockQuantity(productRequest.getStockQuantity());
    }

    private ProductResponse mapToProductResponse(Product savedEntity) {
        ProductResponse productResponse = new ProductResponse();
        productResponse.setName(savedEntity.getName());
        productResponse.setId(savedEntity.getId());
        productResponse.setDescription(savedEntity.getDescription());
        productResponse.setPrice(savedEntity.getPrice());
        productResponse.setCategory(savedEntity.getCategory());
        productResponse.setImageUrl(savedEntity.getImageUrl());
        productResponse.setStockQuantity(savedEntity.getStockQuantity());
        productResponse.setActive(savedEntity.getActive());

        return productResponse;
    }
}
