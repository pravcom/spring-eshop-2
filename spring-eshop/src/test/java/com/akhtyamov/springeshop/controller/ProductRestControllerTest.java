package com.akhtyamov.springeshop.controller;

import com.akhtyamov.springeshop.controllers.ProductController;
import com.akhtyamov.springeshop.controllers.ProductRestController;
import com.akhtyamov.springeshop.dto.ProductDTO;
import com.akhtyamov.springeshop.service.ProductService;
import com.akhtyamov.springeshop.service.SessionObjectHolder;
import com.akhtyamov.springeshop.service.UserService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.util.Arrays;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ProductRestController.class)
public class ProductRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;
    @MockBean
    private UserService userService;
    @MockBean
    private SessionObjectHolder sessionObjectHolder;

    private ProductDTO dto1 = new ProductDTO(998L, "TestProduct998", BigDecimal.valueOf(888.88));

    @BeforeEach
    void setUp() {
        given(productService.getById(dto1.getId())).willReturn(dto1);
    }

    @Test
    void checkList() throws Exception {
        mockMvc.perform(
                        MockMvcRequestBuilders.get("/api/v1/products/{id}",dto1.getId())
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers
                        .jsonPath("$.id",Matchers.is(998)))
                .andExpect(MockMvcResultMatchers
                        .jsonPath("$.title",Matchers.is("TestProduct998")))
                .andExpect(MockMvcResultMatchers
                        .jsonPath("$.price",Matchers.is(999.99)));
    }
}
