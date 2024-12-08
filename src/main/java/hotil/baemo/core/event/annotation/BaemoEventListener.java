package hotil.baemo.core.event.annotation;

import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Async
@EventListener
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface BaemoEventListener {
}
