package com.inditex.pricing.domain.service;

import com.inditex.pricing.adapter.entity.Price;
import com.inditex.pricing.domain.model.PriceResponse;
import com.inditex.pricing.port.in.PriceUseCase;
import com.inditex.pricing.port.out.PriceRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PriceService implements PriceUseCase {

    private final PriceRepositoryPort priceRepositoryPort;

    @Override
    public PriceResponse getPrice(LocalDateTime applicationDate, Long productId, Long brandId) {
        List<Price> prices = priceRepositoryPort.findApplicablePrices(brandId, productId, applicationDate);
        if (prices.isEmpty()) {
            return null;
        }
        Price price = prices.get(0);
        return PriceResponse.builder()
                .brandId(price.getBrandId())
                .productId(price.getProductId())
                .priceList(price.getPriceList())
                .startDate(price.getStartDate())
                .endDate(price.getEndDate())
                .price(price.getPrice())
                .build();
    }
}
