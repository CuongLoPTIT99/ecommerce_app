package com.ecommerceapp.commonmodule.aop;

import com.ecommerceapp.commonmodule.base.entity.BaseEntity;
import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.StatusCode;
import io.opentelemetry.api.trace.Tracer;
import io.opentelemetry.context.Scope;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Aspect
@Component
@RequiredArgsConstructor
public class AspectConfig {
    private final Tracer tracer;

    @Pointcut("@annotation(com.ecommerceapp.commonmodule.annotation.TracingHandler))")
    public void targetTracingMethods() {}

    @Pointcut("@annotation(com.ecommerceapp.commonmodule.annotation.LoggingHandler))")
    public void targetLoggingMethods() {}

    @Around("targetTracingMethods()")
    public Object tracingHandler(ProceedingJoinPoint joinPoint) throws Throwable {
        Class serviceClass = joinPoint.getTarget().getClass();
        Object argObj = joinPoint.getArgs()[0];
        String spanName = Optional.ofNullable(serviceClass.getAnnotation(Service.class))
                .map(x -> ((Service) x).value()).orElse(serviceClass.getSimpleName());
        Span span = tracer.spanBuilder(spanName + "." + joinPoint.getSignature().getName()).startSpan();
        try (Scope scope = span.makeCurrent()) {
            Object returnObj = joinPoint.proceed();
            if (returnObj == null) {
                if (argObj != null) {
                    if (argObj instanceof BaseEntity<?>) {
                        span.setAttribute(
                                "id",
                                Optional.ofNullable(
                                        ((BaseEntity) argObj).getId()
                                ).map(Object::toString).orElse(argObj.toString())
                        );
                    } else if (argObj instanceof Long) {
                        span.setAttribute("id", argObj.toString());
                    } else {
                        span.setAttribute("data", argObj.toString());
                    }
                }
            } else {
                if (returnObj instanceof BaseEntity) {
                    span.setAttribute(
                            "id",
                            Optional.ofNullable(
                                    ((BaseEntity) returnObj).getId()
                            ).map(Object::toString).orElse(returnObj.toString())
                    );
                } else if (returnObj instanceof Long) {
                    span.setAttribute("id", returnObj.toString());
                } else {
                    span.setAttribute("data", returnObj.toString());
                }
            }
            return returnObj;
        } catch (Exception e) {
            // Ghi nhận lỗi vào span
            span.recordException(e);
            span.setStatus(StatusCode.ERROR, String.format("Error when %s object", joinPoint.getSignature().getName()));
            throw e;
        } finally {
            // Kết thúc span
            span.end();
        }
    }

    @Around("targetLoggingMethods()")
    public Object loggingHandler(ProceedingJoinPoint joinPoint) throws Throwable {
        Class serviceClass = joinPoint.getTarget().getClass();
        Object argObj = joinPoint.getArgs()[0];
        String spanName = Optional.ofNullable(serviceClass.getAnnotation(Service.class))
                .map(x -> ((Service) x).value()).orElse(serviceClass.getSimpleName());
        Span span = tracer.spanBuilder(spanName + "." + joinPoint.getSignature().getName()).startSpan();
        try (Scope scope = span.makeCurrent()) {
            Object returnObj = joinPoint.proceed();
            if (returnObj == null) {
                if (argObj != null) {
                    if (argObj instanceof BaseEntity<?>) {
                        span.setAttribute(
                                "id",
                                Optional.ofNullable(
                                        ((BaseEntity) argObj).getId()
                                ).map(Object::toString).orElse(argObj.toString())
                        );
                    } else if (argObj instanceof Long) {
                        span.setAttribute("id", argObj.toString());
                    } else {
                        span.setAttribute("data", argObj.toString());
                    }
                }
            } else {
                if (returnObj instanceof BaseEntity) {
                    span.setAttribute(
                            "id",
                            Optional.ofNullable(
                                    ((BaseEntity) returnObj).getId()
                            ).map(Object::toString).orElse(returnObj.toString())
                    );
                } else if (returnObj instanceof Long) {
                    span.setAttribute("id", returnObj.toString());
                } else {
                    span.setAttribute("data", returnObj.toString());
                }
            }
            return returnObj;
        } catch (Exception e) {
            // Ghi nhận lỗi vào span
            span.recordException(e);
            span.setStatus(StatusCode.ERROR, String.format("Error when %s object", joinPoint.getSignature().getName()));
            throw e;
        } finally {
            // Kết thúc span
            span.end();
        }
    }
}
