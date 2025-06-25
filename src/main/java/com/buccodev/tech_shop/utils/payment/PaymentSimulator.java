package com.buccodev.tech_shop.utils.payment;

import java.math.BigDecimal;
import java.util.UUID;

public class PaymentSimulator {
    public static PaymentResult process(BigDecimal amount) {

        return new PaymentResult(true, UUID.randomUUID().toString(), "Pagamento simulado com sucesso.");
    }

    public record PaymentResult(boolean success, String transactionId, String message) {}
}
