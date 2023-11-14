package net.ddns.sinapouya.productservice.controller;

import net.ddns.sinapouya.productservice.dto.ProductDto;
import net.ddns.sinapouya.productservice.dto.Response;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import net.ddns.sinapouya.productservice.util.ProductMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import net.ddns.sinapouya.productservice.service.ProductService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<Response<ProductDto>> createProduct(@Valid @RequestBody ProductDto productDto){
        ProductDto savedProduct = productService.createProduct(productDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(new Response<>(savedProduct));
    }
    @GetMapping
    public ResponseEntity<Response<List<ProductDto>>> getAllProducts(){
        List<ProductDto> productDtos = productService.findAllProducts().stream().collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK).body(new Response<>(productDtos));
    }
    @GetMapping("{id}")
    public ResponseEntity<Response<ProductDto>> getProducts(@PathVariable("id") String id){
        ProductDto productDto = productService.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(new Response<>(productDto));
    }
}
