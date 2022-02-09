package tr.com.mek.bookshelf.service.log;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class MethodLogger {

    /**
     * Pointcut definition for all RestController methods.
     */
    @Pointcut("within(@org.springframework.web.bind.annotation.RestController *)")
    public void cutRestControllerMethods() {

    }

    /**
     * Pointcut definition for all methods under the root (bookshelf) package.
     */
    @Pointcut("execution(* tr.com.mek.bookshelf..*(..))")
    public void cutAllMethods() {

    }

    /**
     * Creates log for all called REST endpoints.
     * Log level is INFO for this aspect.
     *
     * @param joinPoint JoinPoint
     */
    @Before("cutRestControllerMethods()")
    public void logRestControllerMethodCall(JoinPoint joinPoint) {
        String methodAndClassNameCombination = getMethodAndClassNameCombination(joinPoint);
        log.info("Called REST method is " + methodAndClassNameCombination);
    }

    /**
     * Creates log for all called methods.
     * Log level is DEBUG for this aspect.
     *
     * @param joinPoint ProceedingJoinPoint
     */
    @Around("cutAllMethods()")
    public Object logMethodCallAndReturn(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodAndClassNameCombination = getMethodAndClassNameCombination(joinPoint);
        log.debug("Called method is " + methodAndClassNameCombination);

        Object returnValue = joinPoint.proceed();

        log.debug("Returned method is " + methodAndClassNameCombination);

        return returnValue;
    }

    private String getMethodAndClassNameCombination(JoinPoint joinPoint) {
        return joinPoint.getSignature().getName() + " from " +
                joinPoint.getSignature().getDeclaringType().getSimpleName() + " class.";
    }
}
