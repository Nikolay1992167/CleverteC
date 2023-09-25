package ru.clevertec.aspect;

import org.aspectj.lang.annotation.Pointcut;

public class PointCuts {

    @Pointcut("@annotation(ru.clevertec.aspect.annotation.ServiceLoggable)")
    public void isMethodWithServiceLoggableAnnotation() {
    }

    @Pointcut("@annotation(ru.clevertec.aspect.annotation.ExceptionLoggable)")
    public void isMethodWithExceptionLoggableAnnotation() {
    }

}
