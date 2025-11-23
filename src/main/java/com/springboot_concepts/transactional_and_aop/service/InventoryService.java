package com.springboot_concepts.transactional_and_aop.service;

import com.springboot_concepts.transactional_and_aop.entity.Inventory;
import com.springboot_concepts.transactional_and_aop.repo.InventoryRepo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class InventoryService {
    private final InventoryRepo inventoryRepo;
    public InventoryService(InventoryRepo inventoryRepo) { this.inventoryRepo = inventoryRepo; }

    @Transactional(propagation = Propagation.REQUIRED)
    public Inventory decrement(String item, int delta) {
        Inventory i = new Inventory();
        i.setItem(item);
        i.setQty(100 - delta);
        return inventoryRepo.save(i);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Inventory decrementNewTx(String item, int delta) {
        Inventory i = new Inventory();
        i.setItem(item);
        i.setQty(100 - delta);
        return inventoryRepo.save(i);
    }

    @Transactional(propagation = Propagation.NESTED)
    public Inventory decrementNested(String item, int delta) {
        Inventory i = new Inventory();
        i.setItem(item);
        i.setQty(100 - delta);
        return inventoryRepo.save(i);
    }
}
