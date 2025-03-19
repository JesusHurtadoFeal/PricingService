package com.inditex.pricing.adapter.out.mapper;

import com.inditex.pricing.adapter.out.entity.PriceEntity;
import com.inditex.pricing.domain.model.Price;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PriceMapper {
    @Mapping(source = "priceList", target = "priceOrder")
    @Mapping(source = "curr", target = "currency")
    Price toPrice(final PriceEntity priceEntity);
}
