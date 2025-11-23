package com.springboot_concepts.transactional_and_aop.repo;

import com.springboot_concepts.transactional_and_aop.entity.OrderRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepo extends JpaRepository<OrderRecord, Long> {}