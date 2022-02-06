package tr.com.mek.bookshelf.service.log;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class MethodLogger {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

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
        logger.info("Called REST method is " + joinPoint.getSignature().getName() +
                " from " + joinPoint.getSignature().getDeclaringType().getSimpleName() + " class.");
    }

    /**
     * Creates log for all called methods.
     * Log level is DEBUG for this aspect.
     *
     * @param joinPoint ProceedingJoinPoint
     */
    @Around("cutAllMethods()")
    public Object logMethodCallAndReturn(ProceedingJoinPoint joinPoint) throws Throwable {
        logger.debug("Called method is " + joinPoint.getSignature().getName() +
                " from " + joinPoint.getSignature().getDeclaringType().getSimpleName() + " class.");

        Object returnValue = joinPoint.proceed();

        logger.debug("Returned method is " + joinPoint.getSignature().getName() +
                " from " + joinPoint.getSignature().getDeclaringType().getSimpleName() + " class.");

        return returnValue;
    }
}
