package com.inditex.pricing.adapter;

import com.inditex.pricing.adapter.out.mapper.PriceMapper;
import com.inditex.pricing.adapter.out.repository.PriceRepository;
import com.inditex.pricing.domain.model.Price;
import com.inditex.pricing.port.out.PriceRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
class PriceAdapter implements PriceRepositoryPort {
    private final PriceRepository priceRepository;

    @Override
    public Price findApplicablePrices(final Long brandId,
                                      final Long productId,
                                      final LocalDateTime applicationDate) {
        return priceRepository.findApplicablePrices(brandId, productId, applicationDate)
                .stream()
                .findFirst()
                .map(Mappers.getMapper(PriceMapper.class)::toPrice)
                .orElse(null);
    }
}
