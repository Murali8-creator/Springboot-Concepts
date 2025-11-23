package com.springboot_concepts.transactional_and_aop.service;

import com.springboot_concepts.transactional_and_aop.annotation.Loggable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FacadeService {
    private final OrderService orderService;
    private final InventoryService inventoryService;
    // Self-injection for proxy demo
//    private final FacadeService self;

    public FacadeService(OrderService orderService, InventoryService inventoryService) {
        this.orderService = orderService;
        this.inventoryService = inventoryService;
//        this.self = self; // spring will inject proxy
    }

    // Case A: REQUIRED (default)
    @Transactional
    @Loggable
    public void placeOrderRequired_then_fail() {
        orderService.saveOrder("PENDING");
        inventoryService.decrement("itemA", 1);
        throw new RuntimeException("fail after updates");
    }

    // Case B: demonstrate REQUIRES_NEW by calling saveOrderInNewTx
    @Transactional
    public void placeOrderWithRequiresNewLog_then_fail() {
        orderService.saveOrder("PENDING"); // part of main tx
        orderService.saveOrderInNewTx("LOG-ALWAYS-COMMIT"); // separate tx
        throw new RuntimeException("outer fails");
    }

    // Case C: Nested (uses savepoints)
    @Transactional
    public void placeOrderNested_then_fail() {
        orderService.saveOrder("PENDING");
        try {
            inventoryService.decrementNested("itemA", 1);
            throw new RuntimeException("force fail within nested");
        } catch (Exception e) {
            // swallow to continue outer
        }
        // outer tx continues and will commit
    }

    // Proxy caveat demo: calling method internally vs via proxy
    @Transactional
    public void callerInternal() {
        // This will NOT apply transactional semantics of internalTxn() if internal call bypasses proxy
        internalTxn();
    }

    @Transactional
    public void internalTxn() {
        orderService.saveOrder("INTERNAL");
    }

//    public void callerViaSelf() {
//        // call via injected proxy to ensure annotation is applied
//        self.internalTxn();
//    }
}

