package com.java.testw2m.logging;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class SpaceShipLoggingAspect {

    @Before("execution(* com.java.testw2m.controller.*.*(..))")
    public void logApiCall(JoinPoint joinPoint) {
        // Obtener el nombre del método
        String methodName = joinPoint.getSignature().getName();

        // Obtener los parámetros del método
        Object[] methodArguments = joinPoint.getArgs();

        // Imprimir en consola los detalles de la llamada
        StringBuilder log = new StringBuilder("Llamada realizada al metodo: " + methodName + " con los siguientes parametros ");
        for (Object arg : methodArguments) {
            log.append(" - ").append(arg);
        }
        System.out.println(log);
    }

    @Before("execution(* com.java.testw2m.controller.SpaceShipController.getById(..)) && args(id,..)")
    public void logNegativeIdQuery(Long id) {
        if (id < 0) {
            System.out.println("Attempted to query a ship with a negative ID: " + id);
        }
    }
}