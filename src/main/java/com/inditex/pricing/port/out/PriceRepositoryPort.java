package com.inditex.pricing.port.out;

import com.inditex.pricing.adapter.entity.Price;

import java.time.LocalDateTime;
import java.util.List;

public interface PriceRepositoryPort {
    List<Price> findApplicablePrices(Long brandId, Long productId, LocalDateTime applicationDate);
}
