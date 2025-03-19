package com.inditex.pricing.port.out;

import com.inditex.pricing.domain.model.Price;

import java.time.LocalDateTime;

public interface PriceRepositoryPort {
    Price findApplicablePrices(Long brandId, Long productId, LocalDateTime applicationDate);
}
