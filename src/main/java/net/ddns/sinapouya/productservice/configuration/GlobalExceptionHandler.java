package net.ddns.sinapouya.productservice.configuration;

import net.ddns.sinapouya.productservice.dto.Response;
import net.ddns.sinapouya.productservice.exception.ProductNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<Response<String>> handleProductNotFoundException(ProductNotFoundException ex) {
        Response<String> response = new Response<>(0, "Error: " + ex.getMessage(), null);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }


}
