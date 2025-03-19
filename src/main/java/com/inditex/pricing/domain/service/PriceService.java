package com.inditex.pricing.domain.service;

import com.inditex.pricing.domain.model.Price;
import com.inditex.pricing.port.in.PriceUseCase;
import com.inditex.pricing.port.out.PriceRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PriceService implements PriceUseCase {

    private final PriceRepositoryPort priceRepositoryPort;

    @Override
    public Price getPrice(LocalDateTime applicationDate, Long productId, Long brandId) {
        return priceRepositoryPort.findApplicablePrices(brandId, productId, applicationDate);
    }
}
