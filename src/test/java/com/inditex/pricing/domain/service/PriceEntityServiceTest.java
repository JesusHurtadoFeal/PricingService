package com.inditex.pricing.domain.service;

import com.inditex.pricing.domain.model.Price;
import com.inditex.pricing.port.out.PriceRepositoryPort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PriceServiceTest {

    @Mock
    private PriceRepositoryPort priceRepositoryPort;

    @InjectMocks
    private PriceService priceService;

    @Test
    public void testGetPriceFound() {
        final LocalDateTime applicationDate = LocalDateTime.of(2020, 6, 14, 10, 0);
        final Price expectedPrice = Price.builder()
                .brandId(1L)
                .productId(35455L)
                .priceOrder(1)
                .startDate(LocalDateTime.of(2020, 6, 14, 0, 0))
                .endDate(LocalDateTime.of(2020, 12, 31, 23, 59, 59))
                .price(BigDecimal.valueOf(35.50))
                .priority(1)
                .currency("EUR")
                .build();

        when(priceRepositoryPort.findApplicablePrices(1L, 35455L, applicationDate))
                .thenReturn(expectedPrice);

        final Price result = priceService.getPrice(applicationDate, 35455L, 1L);
        assertNotNull(result, "El precio no debe ser nulo cuando se encuentra una entidad");
        assertAll("Verificando el mapeo de Price",
                () -> assertEquals(expectedPrice.getBrandId(), result.getBrandId(), "brandId mapeado incorrectamente"),
                () -> assertEquals(expectedPrice.getProductId(), result.getProductId(), "productId mapeado incorrectamente"),
                () -> assertEquals(expectedPrice.getPriceOrder(), result.getPriceOrder(), "priceOrder mapeado incorrectamente"),
                () -> assertEquals(expectedPrice.getStartDate(), result.getStartDate(), "startDate mapeado incorrectamente"),
                () -> assertEquals(expectedPrice.getEndDate(), result.getEndDate(), "endDate mapeado incorrectamente"),
                () -> assertEquals(expectedPrice.getPrice(), result.getPrice(), "price mapeado incorrectamente"),
                () -> assertEquals(expectedPrice.getCurrency(), result.getCurrency(), "currency mapeado incorrectamente")
        );
    }

    @Test
    public void testGetPriceNotFound() {
        final LocalDateTime applicationDate = LocalDateTime.of(2020, 6, 14, 10, 0);
        when(priceRepositoryPort.findApplicablePrices(1L, 35455L, applicationDate))
                .thenReturn(null);

        final Price result = priceService.getPrice(applicationDate, 35455L, 1L);
        assertNull(result, "El resultado debe ser nulo cuando no se encuentra ninguna entidad");
    }
}
