package com.quid.inserter.Pay.usecase;

import com.quid.inserter.Pay.domain.Payment;
import com.quid.inserter.Pay.domain.TransactionKey;
import com.quid.inserter.Pay.listener.PaymentProcessor.PaymentEvent;
import com.quid.inserter.Pay.repository.PaymentRepository;
import java.math.BigDecimal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class PlaceOrder {

    private final PaymentRepository repository;
    private final ApplicationEventPublisher publisher;

    public PlaceOrder(PaymentRepository repository, ApplicationEventPublisher publisher) {
        this.repository = repository;
        this.publisher = publisher;
    }

    public void execute() {
        log.info("== EXECUTE PAYMENT ==");
        Payment payment = new Payment(new TransactionKey().issue(), BigDecimal.valueOf(10_000), "KRW");
        repository.save(payment);
        publisher.publishEvent(new PaymentEvent(this, payment.transactionKey()));
    }
}
