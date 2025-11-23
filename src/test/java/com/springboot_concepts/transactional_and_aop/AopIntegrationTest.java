package com.springboot_concepts.transactional_and_aop;

import com.springboot_concepts.transactional_and_aop.service.FacadeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AopIntegrationTest {

    @Autowired
    FacadeService facade;

    @Test
    void aop_shouldLogAndMeasure() {
        try {
            facade.placeOrderRequired_then_fail();
        } catch (Exception ignored) {
            // expected
        }
        // Check console output manually — you should see AOP ENTER / AOP TIMING / AOP ERROR lines.
    }
}

