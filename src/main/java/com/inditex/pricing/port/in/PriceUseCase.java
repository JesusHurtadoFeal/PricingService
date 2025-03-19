package com.inditex.pricing.port.in;

import com.inditex.pricing.domain.model.Price;

import java.time.LocalDateTime;

public interface PriceUseCase {
    Price getPrice(final LocalDateTime applicationDate,
                   final Long productId,
                   final Long brandId);
}
