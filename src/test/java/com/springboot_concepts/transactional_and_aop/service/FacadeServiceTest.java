package com.springboot_concepts.transactional_and_aop.service;

import com.springboot_concepts.transactional_and_aop.entity.OrderRecord;
import com.springboot_concepts.transactional_and_aop.repo.InventoryRepo;
import com.springboot_concepts.transactional_and_aop.repo.OrderRepo;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class FacadeServiceTest {
    @Autowired
    FacadeService facade;
    @Autowired
    OrderRepo orderRepo;
    @Autowired
    InventoryRepo inventoryRepo;

    @BeforeEach
    void clean() {
        orderRepo.deleteAll();
        inventoryRepo.deleteAll();
    }

    @Test
    void required_shouldRollbackAllOnException() {
        try {
            facade.placeOrderRequired_then_fail();
        } catch (RuntimeException e) { /* expected */ }

        // assert no order persisted
        Assertions.assertThat(orderRepo.findAll()).isEmpty();
        Assertions.assertThat(inventoryRepo.findAll()).isEmpty();
    }

    @Test
    void requiresNew_shouldCommitInnerEvenIfOuterRollsBack() {
        try {
            facade.placeOrderWithRequiresNewLog_then_fail();
        } catch (RuntimeException e) {}

        // outer order rolled back, but REQUIRES_NEW order should be committed
        List<OrderRecord> orders = orderRepo.findAll();
        assertThat(orders).hasSize(1);
        assertThat(orders.get(0).getStatus()).isEqualTo("LOG-ALWAYS-COMMIT");
    }

    @Test
    void nested_shouldRollbackInnerButOuterMayCommit() {
        facade.placeOrderNested_then_fail();

        // nested failure should roll back nested insert but outer may have persisted main record
        List<OrderRecord> orders = orderRepo.findAll();
        assertThat(orders).hasSize(1); // outer saveOrder
        Assertions.assertThat(inventoryRepo.findAll()).isEmpty(); // nested save rolled back
    }

    @Test
    void proxy_caveat_internal_call_bypasses_tx() {
        // clean up
        orderRepo.deleteAll();

        // callerInternal calls internalTxn directly (same bean) — proxy not used
        facade.callerInternal();

        // If internalTxn was private or direct call bypasses proxy, order may still have been saved depending on transaction setup.
        // To demonstrate, call via self (proxy)
//        facade.callerViaSelf();

        // check saved orders
        List<OrderRecord> orders = orderRepo.findAll();
        // interpret results based on how you implemented internalTxn (private vs public)
    }
}

