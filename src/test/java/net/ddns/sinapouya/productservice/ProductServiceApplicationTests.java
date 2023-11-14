package net.ddns.sinapouya.productservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.ddns.sinapouya.productservice.dto.ProductDto;
import net.ddns.sinapouya.productservice.entity.Product;
import net.ddns.sinapouya.productservice.repository.ProductRepository;
import net.ddns.sinapouya.productservice.util.ProductMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
class ProductServiceApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;
	@Container
	static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:6");
	@Autowired
	private ProductRepository productRepository;

	@DynamicPropertySource
	static void setProperties(DynamicPropertyRegistry dynamicPropertyRegistry){
		dynamicPropertyRegistry.add("spring.data.mongodb.url",mongoDBContainer::getReplicaSetUrl);
	}

	@AfterEach
	void makeEmptyRepository(){
		productRepository.deleteAll();
	}

	@Test
	void createProduct() throws Exception {
		ProductDto productDto = getProductRequest(1);
		String content = objectMapper.writeValueAsString(productDto);
		mockMvc.perform(MockMvcRequestBuilders.post("/api/product")
		.contentType(MediaType.APPLICATION_JSON)
		.content(content))
		.andExpect(status().isCreated())
		.andExpect(jsonPath("$.info.name").value(productDto.getName()))
		.andExpect(jsonPath("$.info.description").value(productDto.getDescription()))
		.andExpect(jsonPath("$.info.price").value(productDto.getPrice()));
	}

	@Test
	void getAllProducts() throws Exception {
		// Assuming you have some products in your repository
		List<ProductDto> existingProducts = Arrays.asList(
				getProductRequest(1),
				getProductRequest(2),
				getProductRequest(3)
		);

		List<Product> productList = existingProducts.stream().map(ProductMapper::convertToProduct).collect(Collectors.toList());
		productRepository.saveAll(productList);

		mockMvc.perform(MockMvcRequestBuilders.get("/api/product"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.resultCode").value(1))
				.andExpect(jsonPath("$.info", hasSize(existingProducts.size())));
	}

	@Test
	void getProductsById() throws Exception {
		ProductDto productDto = getProductRequest(1);
		Product savedProduct = productRepository.save(ProductMapper.convertToProduct(productDto));

		mockMvc.perform(MockMvcRequestBuilders.get("/api/product/{id}", savedProduct.getId()))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.resultCode").value(1))
				.andExpect(jsonPath("$.info.name").value(productDto.getName()))
				.andExpect(jsonPath("$.info.description").value(productDto.getDescription()))
				.andExpect(jsonPath("$.info.price").value(productDto.getPrice().intValue()));
	}
	private ProductDto getProductRequest(int i) {
		return ProductDto.builder().name("product test"+i)
				.description("product test"+i)
				.price(new BigDecimal(3000))
				.build();
	}

}
