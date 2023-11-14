package net.ddns.sinapouya.productservice.util;

import net.ddns.sinapouya.productservice.dto.ProductDto;
import net.ddns.sinapouya.productservice.entity.Product;

public class ProductMapper {
    public static ProductDto convertToProductDto(Product product){
        ProductDto productDto = ProductDto.builder().id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .build();
        return productDto;
    }
    public static Product convertToProduct(ProductDto productDto){
        Product product = Product.builder().id(productDto.getId())
                .name(productDto.getName())
                .price(productDto.getPrice())
                .description(productDto.getDescription())
                .build();
        return product;
    }
}
