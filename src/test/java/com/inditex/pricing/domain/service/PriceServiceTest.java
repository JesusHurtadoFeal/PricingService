package com.inditex.pricing.domain.service;

import com.inditex.pricing.adapter.entity.Price;
import com.inditex.pricing.domain.model.PriceResponse;
import com.inditex.pricing.port.out.PriceRepositoryPort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PriceServiceTest {
    @Mock
    private PriceRepositoryPort priceRepositoryPort;

    @InjectMocks
    private PriceService priceService;

    @Test
    public void testGetPriceFound() {
        LocalDateTime applicationDate = LocalDateTime.of(2020, 6, 14, 10, 0);
        Price price = Price.builder()
                .brandId(1L)
                .productId(35455L)
                .priceList(1)
                .startDate(LocalDateTime.of(2020, 6, 14, 0, 0))
                .endDate(LocalDateTime.of(2020, 12, 31, 23, 59, 59))
                .price(BigDecimal.valueOf(35.50))
                .priority(1)
                .curr("EUR")
                .build();

        LinkedList<Price> prices = new LinkedList<>();
        prices.add(price);

        when(priceRepositoryPort.findApplicablePrices(1L, 35455L, applicationDate))
                .thenReturn(prices);

        PriceResponse response = priceService.getPrice(applicationDate, 35455L, 1L);
        assertNotNull(response);
        assertEquals(1L, response.getBrandId());
        assertEquals(35455L, response.getProductId());
        assertEquals(1, response.getPriceList());
        assertEquals(price.getPrice(), response.getPrice());
    }

    @Test
    public void testGetPriceNotFound() {
        LocalDateTime applicationDate = LocalDateTime.of(2020, 6, 14, 10, 0);
        when(priceRepositoryPort.findApplicablePrices(1L, 35455L, applicationDate))
                .thenReturn(new LinkedList<>());

        PriceResponse response = priceService.getPrice(applicationDate, 35455L, 1L);
        assertNull(response);
    }
}