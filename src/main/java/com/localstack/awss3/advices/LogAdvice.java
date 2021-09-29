package com.localstack.awss3.advices;

import com.localstack.awss3.api.models.NewPersonRequest;
import com.localstack.awss3.api.models.NewPersonSaveDone;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class LogAdvice {
    @Before("execution(* com.localstack.awss3.services.impls.SavePersonServiceImpl.savePersonData(..))"
            + " && args(newPersonRequest)")
    public void viewRequest(NewPersonRequest newPersonRequest) {
        log.info("Service is ready to run with value of ten: " + newPersonRequest.getTen());
    }

    @AfterReturning(pointcut = "execution(* com.localstack.awss3.services.impls.SavePersonServiceImpl.savePersonData(..))",
            returning = "newPersonSaveDone")
    public void viewReturn(NewPersonSaveDone newPersonSaveDone) {
        log.info("Service is done with value of code: " + newPersonSaveDone.getCode());
    }
}
