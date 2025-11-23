package com.springboot_concepts.transactional_and_aop.repo;

import com.springboot_concepts.transactional_and_aop.entity.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryRepo extends JpaRepository<Inventory, Long> {}
