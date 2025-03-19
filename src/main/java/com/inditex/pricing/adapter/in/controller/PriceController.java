package com.inditex.pricing.adapter.in.controller;

import com.inditex.pricing.adapter.in.dto.PriceResponse;
import com.inditex.pricing.adapter.in.mapper.PriceDTOMapper;
import com.inditex.pricing.domain.model.Price;
import com.inditex.pricing.port.in.PriceUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
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
class PriceController {

    private final PriceUseCase priceUseCase;

    @Operation(summary = "Get the applicable price for a product and brand on a given date")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Price found"),
            @ApiResponse(responseCode = "404", description = "Price not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/prices")
    public ResponseEntity<PriceResponse> getPrice(
            @Parameter(description = "Application date in ISO format (yyyy-MM-dd'T'HH:mm:ss)")
            @RequestParam("applicationDate")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) final LocalDateTime applicationDate,
            @Parameter(description = "Product ID")
            @RequestParam("productId") final Long productId,
            @Parameter(description = "Brand ID")
            @RequestParam("brandId") final Long brandId) {

        final Price price = priceUseCase.getPrice(applicationDate, productId, brandId);
        if (price == null) {
            return ResponseEntity.notFound().build();
        }
        final PriceResponse response = Mappers.getMapper(PriceDTOMapper.class).toPriceResponse(price);
        return ResponseEntity.ok(response);
    }
}
