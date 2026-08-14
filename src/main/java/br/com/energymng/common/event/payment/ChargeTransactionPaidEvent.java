package br.com.energymng.common.event.payment;

import br.com.energymng.payment.CarOwnerWallet;
import br.com.energymng.payment.PaymentMethod;
import br.com.energymng.payment.SuccessPaymentRequest;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ChargeTransactionPaidEvent(
        BigDecimal confirmChargeAmount,
        String paymentTransactionId,
        String paymentMethod,
        String paymentGateway,
        LocalDateTime paymentAt,
        String carOwnerPhone,
        String carOwnerIdentification,
        Long chargeTransactionId,
        String gatewayTransactionId,
        LocalDateTime transactionAt,
        BigDecimal balance,
        BigDecimal balanceInKwh
) {

    public static ChargeTransactionPaidEvent create(SuccessPaymentRequest request, CarOwnerWallet wallet, BigDecimal balanceInKwh){
        return new ChargeTransactionPaidEvent(
                request.amount(),
                request.gatewayTransactionId(),
                request.paymentMethod().name(),
                "PaymentGateway", // Assuming a default payment gateway name
                LocalDateTime.now(),
                wallet.getCarOwnerPhone(),
                wallet.getCarOwnerIdentification(),
                request.chargeTransactionId(),
                request.gatewayTransactionId(),
                LocalDateTime.now(),
                wallet.getBalance(),
                balanceInKwh
        );
    }
}