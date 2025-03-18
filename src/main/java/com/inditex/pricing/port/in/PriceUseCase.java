package com.inditex.pricing.port.in;

import com.inditex.pricing.domain.model.PriceResponse;

import java.time.LocalDateTime;

public interface PriceUseCase {
    PriceResponse getPrice(LocalDateTime applicationDate, Long productId, Long brandId);
}
