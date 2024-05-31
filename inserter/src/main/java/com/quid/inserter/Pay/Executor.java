package com.quid.inserter.Pay;

import com.quid.inserter.Pay.usecase.PlaceOrder;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class Executor {

    private final PlaceOrder placeOrder;

    public Executor(PlaceOrder placeOrder) {
        this.placeOrder = placeOrder;
    }

    @Scheduled(fixedDelay = 500)
    public void execute() {
        placeOrder.execute();
    }

}
