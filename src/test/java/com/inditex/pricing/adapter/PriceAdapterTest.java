package com.inditex.pricing.adapter;

import com.inditex.pricing.adapter.entity.Price;
import com.inditex.pricing.adapter.repository.PriceRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PriceAdapterTest {
    @Mock
    private PriceRepository priceRepository;

    @InjectMocks
    private PriceAdapter priceAdapter;

    @Test
    public void testFindApplicablePrices() {
        LocalDateTime applicationDate = LocalDateTime.of(2020, 6, 14, 10, 0);
        Price price = Price.builder()
                .brandId(1L)
                .productId(35455L)
                .priceList(1)
                .startDate(LocalDateTime.of(2020, 6, 14, 0, 0))
                .endDate(LocalDateTime.of(2020, 12, 31, 23, 59, 59))
                .price(BigDecimal.valueOf(35.50))
                .priority(1)
                .curr("EUR")
                .build();

        List<Price> priceList = Collections.singletonList(price);

        when(priceRepository.findApplicablePrices(1L, 35455L, applicationDate))
                .thenReturn(priceList);

        List<Price> result = priceAdapter.findApplicablePrices(1L, 35455L, applicationDate);
        assertEquals(1, result.size());
        assertEquals(price, result.get(0));
    }
}
