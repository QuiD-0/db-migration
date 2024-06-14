package com.quid.inserter.pay.listener;

import com.quid.inserter.pay.domain.Payment;
import com.quid.inserter.pay.repository.PaymentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class PaymentProcessor {

    private final PaymentRepository repository;

    public PaymentProcessor(PaymentRepository repository) {
        this.repository = repository;
    }

    @Async
    @EventListener
    public void processPayment(PaymentEvent paymentEvent) {
        log.info("== PROCESS PAYMENT ==");
        Payment payment = repository.findByTransactionKey(paymentEvent.transactionKey)
            .orElseThrow(() -> new IllegalArgumentException("Payment not found"));
        randomDelay();
        Payment done = payment.done();
        repository.done(done);
    }

    private void randomDelay() {
        try {
            Thread.sleep((long) (Math.random() * 5000));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public static class PaymentEvent extends ApplicationEvent {

        private final String transactionKey;

        public PaymentEvent(Object source, String transactionKey) {
            super(source);
            this.transactionKey = transactionKey;
        }
    }
}
