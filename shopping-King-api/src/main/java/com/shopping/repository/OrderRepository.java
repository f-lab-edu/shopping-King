package com.shopping.repository;

import com.shopping.entity.Orders;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Orders, Long> {

  List<Orders> findByMemberId(Long memberId);
}
