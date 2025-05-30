package com.springmicroservice.productservice.service;

import com.springmicroservice.productservice.dto.ProductRequest;
import com.springmicroservice.productservice.dto.ProductRespone;
import com.springmicroservice.productservice.model.Product;
import com.springmicroservice.productservice.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {

    private final ProductRepository productRepository;

    public void createProduct(ProductRequest productRequest) {
        Product product = Product.builder()
                .name(productRequest.getName())
                .description(productRequest.getDescription())
                .price(productRequest.getPrice())
                .build();

        productRepository.save(product);
        log.info("Product created: {}", product);
    }

    @Transactional(readOnly = true)
    public List<ProductRespone> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream().map(this::getProductRespone).toList();
    }

    private ProductRespone getProductRespone(Product product) {
        return ProductRespone.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .build();
    }
}
