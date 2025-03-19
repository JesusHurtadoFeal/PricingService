package com.inditex.pricing.adapter.in.mapper;

import com.inditex.pricing.adapter.in.dto.PriceResponse;
import com.inditex.pricing.domain.model.Price;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PriceDTOMapper {
    @Mapping(source = "priceOrder", target = "priceList")
    PriceResponse toPriceResponse(Price price);
}
