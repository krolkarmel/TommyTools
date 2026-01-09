package com.tommytools.order;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<ShopOrder, Long> {
    List<ShopOrder> findAllByOrderByCreatedAtDesc();
    Optional<ShopOrder> findByOrderNo(String orderNo);
}
