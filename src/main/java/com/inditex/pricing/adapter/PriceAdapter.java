package com.inditex.pricing.adapter;

import com.inditex.pricing.adapter.entity.Price;
import com.inditex.pricing.adapter.repository.PriceRepository;
import com.inditex.pricing.port.out.PriceRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class PriceAdapter implements PriceRepositoryPort {
    private final PriceRepository priceRepository;

    @Override
    public List<Price> findApplicablePrices(Long brandId, Long productId, LocalDateTime applicationDate) {
        return priceRepository.findApplicablePrices(brandId, productId, applicationDate);
    }
}
