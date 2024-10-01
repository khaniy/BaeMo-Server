package hotil.baemo.config.socket.error;

import hotil.baemo.core.common.response.ResponseCode;
import hotil.baemo.core.common.response.exception.CustomException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.MessageDeliveryException;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class HandleCustomExceptionAspect {
    private static final Logger log = LoggerFactory.getLogger(HandleCustomExceptionAspect.class);

    @Around("@annotation(HandleCustomException)")
    public Object handleCustomException(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            return joinPoint.proceed();
        } catch (CustomException ex) {
            throw new MessageDeliveryException(ex.getResponseCode().getCode());
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new MessageDeliveryException(ResponseCode.ETC_ERROR.getCode());
        }
    }
}
