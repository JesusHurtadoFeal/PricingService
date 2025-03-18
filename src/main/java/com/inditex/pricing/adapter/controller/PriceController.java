package com.inditex.pricing.adapter.controller;

import com.inditex.pricing.domain.model.PriceResponse;
import com.inditex.pricing.port.in.PriceUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class PriceController {
    private final PriceUseCase priceUseCase;

    @Operation(summary = "Get the applicable price for a product and brand on a given date")
    @GetMapping("/prices")
    public ResponseEntity<PriceResponse> getPrice(
            @Parameter(description = "Application date in ISO format (yyyy-MM-dd'T'HH:mm:ss)")
            @RequestParam("applicationDate")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime applicationDate,
            @RequestParam("productId") Long productId,
            @RequestParam("brandId") Long brandId) {

        PriceResponse response = priceUseCase.getPrice(applicationDate, productId, brandId);
        if (response == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(response);
    }
}
