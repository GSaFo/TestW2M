package com.java.testw2m.logging;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ShipLoggingAspect {

    @Before("execution(* com.java.testw2m.controller.ShipController.getById(..)) && args(id,..)")
    public void logNegativeIdQuery(Long id) {
        if (id < 0) {
            System.out.println("Attempted to query a ship with a negative ID: " + id);
        }
    }
}