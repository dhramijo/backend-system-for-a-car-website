package com.udacity.pricing.api;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Created by jonad dhrami on 01/09/2020.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class PricingControllerTest {

    @Autowired
    public MockMvc mockMvc;

    @Test
    public void test_getPrice() throws Exception {
        mockMvc.perform(get("/services/price?vehicleId=1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print())
                .andExpect(jsonPath("$.currency").value("USD"))
                .andExpect(jsonPath("$.price").exists())
                .andExpect(jsonPath("$.vehicleId").value(1));
    }


    @Test
    public void test_getPriceBadRequest() throws Exception {
        mockMvc.perform(get("/services/price"))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }


    @Test
    public void test_priceNotFound() throws Exception {
        mockMvc.perform(get("/services/price?vehicleId=30")
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertEquals("Cannot find price for Vehicle 30",
                        result.getResolvedException().getCause().getMessage()));
    }

}