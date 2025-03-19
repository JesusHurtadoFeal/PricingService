package com.inditex.pricing.port.in;

import com.inditex.pricing.domain.model.Price;

import java.time.LocalDateTime;

public interface PriceUseCase {
    Price getPrice(LocalDateTime applicationDate, Long productId, Long brandId);
}
