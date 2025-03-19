package com.inditex.pricing.adapter;

import com.inditex.pricing.adapter.out.entity.PriceEntity;
import com.inditex.pricing.adapter.out.repository.PriceRepository;
import com.inditex.pricing.domain.model.Price;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PriceAdapterTest {

    @Mock
    private PriceRepository priceRepository;

    @InjectMocks
    private PriceAdapter priceAdapter;

    @Test
    public void testFindApplicablePrices_whenEntityExists_thenReturnsMappedPrice() {
        final LocalDateTime applicationDate = LocalDateTime.of(2020, 6, 14, 10, 0);
        final PriceEntity priceEntity = PriceEntity.builder()
                .brandId(1L)
                .productId(35455L)
                .priceList(1)
                .startDate(LocalDateTime.of(2020, 6, 14, 0, 0))
                .endDate(LocalDateTime.of(2020, 12, 31, 23, 59, 59))
                .price(BigDecimal.valueOf(35.50))
                .priority(1)
                .curr("EUR")
                .build();
        final List<PriceEntity> priceEntityList = Collections.singletonList(priceEntity);

        when(priceRepository.findApplicablePrices(1L, 35455L, applicationDate))
                .thenReturn(priceEntityList);

        final Price result = priceAdapter.findApplicablePrices(1L, 35455L, applicationDate);

        assertNotNull(result, "El resultado no debe ser nulo cuando existe una entidad");
        assertAll("Mapping PriceEntity to Price",
                () -> assertEquals(priceEntity.getBrandId(), result.getBrandId(), "El brandId se mapea incorrectamente"),
                () -> assertEquals(priceEntity.getProductId(), result.getProductId(), "El productId se mapea incorrectamente"),
                () -> assertEquals(priceEntity.getPriceList(), result.getPriceOrder(), "El priceOrder no coincide con priceList"),
                () -> assertEquals(priceEntity.getStartDate(), result.getStartDate(), "La startDate se mapea incorrectamente"),
                () -> assertEquals(priceEntity.getEndDate(), result.getEndDate(), "La endDate se mapea incorrectamente"),
                () -> assertEquals(priceEntity.getPrice(), result.getPrice(), "El price se mapea incorrectamente"),
                () -> assertEquals(priceEntity.getCurr(), result.getCurrency(), "La currency no coincide con curr")
        );
    }

    @Test
    public void testFindApplicablePrices_whenNoEntityExists_thenReturnsNull() {
        final LocalDateTime applicationDate = LocalDateTime.of(2020, 6, 14, 10, 0);
        when(priceRepository.findApplicablePrices(1L, 35455L, applicationDate))
                .thenReturn(Collections.emptyList());

        final Price result = priceAdapter.findApplicablePrices(1L, 35455L, applicationDate);
        assertNull(result, "El resultado debe ser nulo cuando no se encuentra ninguna entidad");
    }
}
