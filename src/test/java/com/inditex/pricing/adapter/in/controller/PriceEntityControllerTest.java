package com.inditex.pricing.adapter.in.controller;

import com.inditex.pricing.domain.model.Price;
import com.inditex.pricing.port.in.PriceUseCase;
import com.inditex.pricing.adapter.in.dto.PriceResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PriceControllerTest {

    @Mock
    private PriceUseCase priceUseCase;

    @InjectMocks
    private PriceController priceController;

    @Test
    void testGetPriceFound() {
        final LocalDateTime applicationDate = LocalDateTime.of(2020, 6, 14, 10, 0);
        final Price price = Price.builder()
                .brandId(1L)
                .productId(35455L)
                .priceOrder(1)
                .startDate(LocalDateTime.of(2020, 6, 14, 0, 0))
                .endDate(LocalDateTime.of(2020, 12, 31, 23, 59, 59))
                .price(BigDecimal.valueOf(35.50))
                .priority(1)
                .currency("EUR")
                .build();

        when(priceUseCase.getPrice(eq(applicationDate), eq(35455L), eq(1L)))
                .thenReturn(price);

        final ResponseEntity<PriceResponse> responseEntity =
                priceController.getPrice(applicationDate, 35455L, 1L);

        assertNotNull(responseEntity, "El ResponseEntity no debe ser nulo");
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode(),
                "El status code debe ser 200 OK cuando se encuentra un precio");

        final PriceResponse body = responseEntity.getBody();
        assertNotNull(body, "El cuerpo de la respuesta no debe ser nulo");

        assertAll("Verificando campos de PriceResponse",
                () -> assertEquals(1L, body.getBrandId(), "brandId incorrecto"),
                () -> assertEquals(35455L, body.getProductId(), "productId incorrecto"),
                () -> assertEquals(1, body.getPriceList(), "priceList incorrecto"),
                () -> assertEquals(BigDecimal.valueOf(35.50), body.getPrice(), "price incorrecto")
        );
    }

    @Test
    void testGetPriceNotFound() {
        final LocalDateTime applicationDate = LocalDateTime.of(2020, 6, 14, 10, 0);

        when(priceUseCase.getPrice(eq(applicationDate), eq(35455L), eq(1L)))
                .thenReturn(null);

        final ResponseEntity<PriceResponse> responseEntity =
                priceController.getPrice(applicationDate, 35455L, 1L);

        assertNotNull(responseEntity, "El ResponseEntity no debe ser nulo");
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode(),
                "El status code debe ser 404 NOT FOUND cuando no se encuentra un precio");
        assertNull(responseEntity.getBody(), "El cuerpo de la respuesta debe ser nulo en este caso");
    }
}
