package com.inditex.pricing.adapter.out.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDateTime;
import java.util.List;

import com.inditex.pricing.adapter.out.entity.PriceEntity;

public interface PriceRepository extends JpaRepository<PriceEntity, Long> {

    @Query("SELECT p FROM Price p WHERE p.brandId = :brandId AND p.productId = :productId AND :applicationDate BETWEEN p.startDate AND p.endDate ORDER BY p.priority DESC")
    List<PriceEntity> findApplicablePrices(@Param("brandId") Long brandId,
                                           @Param("productId") Long productId,
                                           @Param("applicationDate") LocalDateTime applicationDate);
}
