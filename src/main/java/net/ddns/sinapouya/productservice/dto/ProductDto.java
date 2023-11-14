package net.ddns.sinapouya.productservice.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {
    private String id;
    @NotEmpty(message = "name cannot be empty")
    @NotNull(message = "name cannot be null")
    private String name;
    @NotEmpty(message = "description cannot be empty")
    @NotNull(message = "description cannot be null")
    private String description;
    @NotNull(message = "price cannot be null")
    @DecimalMin(value = "0.00", message = "price must be greater than to 0.00")
    @DecimalMax(value = "10000000000.00", message = "price must be less than to 10000000000.00")
    private BigDecimal price;
}
