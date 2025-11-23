package com.springboot_concepts.transactional_and_aop.service;

import com.springboot_concepts.transactional_and_aop.entity.OrderRecord;
import com.springboot_concepts.transactional_and_aop.repo.OrderRepo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderService {
    private final OrderRepo orderRepo;

    public OrderService(OrderRepo orderRepo) { this.orderRepo = orderRepo; }

    @Transactional(propagation = Propagation.REQUIRED)
    public OrderRecord saveOrder(String status) {
        OrderRecord o = new OrderRecord();
        o.setStatus(status);
        return orderRepo.save(o);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public OrderRecord saveOrderInNewTx(String status) {
        OrderRecord o = new OrderRecord();
        o.setStatus(status);
        return orderRepo.save(o);
    }

    @Transactional(propagation = Propagation.NESTED)
    public OrderRecord saveOrderNested(String status) {
        OrderRecord o = new OrderRecord();
        o.setStatus(status);
        return orderRepo.save(o);
    }
}
