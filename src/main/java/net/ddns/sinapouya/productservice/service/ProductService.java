package net.ddns.sinapouya.productservice.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.ddns.sinapouya.productservice.dto.ProductDto;
import net.ddns.sinapouya.productservice.entity.Product;
import lombok.AllArgsConstructor;
import net.ddns.sinapouya.productservice.exception.ProductNotFoundException;
import net.ddns.sinapouya.productservice.util.ProductMapper;
import org.springframework.stereotype.Service;
import net.ddns.sinapouya.productservice.repository.ProductRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final ObjectMapper objectMapper;

    public ProductDto createProduct(ProductDto productDto){
        Product product = ProductMapper.convertToProduct(productDto);
        Product savedProduct = productRepository.save(product);
        return ProductMapper.convertToProductDto(savedProduct);
    }

    public List<ProductDto> findAllProducts() {
        return productRepository.findAll().stream().map(ProductMapper::convertToProductDto).collect(Collectors.toList());
    }
    public ProductDto findById(String id) {
        Product product = productRepository.findById(id)
                .orElseThrow(()->new ProductNotFoundException("Product not found with this id "+id));
        return ProductMapper.convertToProductDto(product);
    }

}
