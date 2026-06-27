package br.com.energymng.payment;

import br.com.energymng.common.event.notification.CarOwnerPaymentCalculationNotificationEvent;
import br.com.energymng.common.event.payment.PaymentCalculateAmountEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
class PaymentService {

    private final TariffRepository tariffRepository;
    private final ModelDetailRepository modelDetailRepository;
    private final ApplicationEventPublisher eventPublisher;
    private final CarOwnerWalletRepository carOwnerWalletRepository;
    private final CarOwnerWalletStatementRepository carOwnerWalletStatementRepository;

    void calculateAmount(PaymentCalculateAmountEvent event) {
        Tariff tariff = tariffRepository.findByPumpId(event.pumpId()).orElse(null);
        ModelDetail modelDetail = modelDetailRepository.findByModel(event.carModel()).orElse(null);

        Double batteryKwh = modelDetail != null ? modelDetail.getBateryKwh() : null;
        Double batteryLevel = event.batteryLevel();
        Double pumpKwh = event.pumpKwh();
        BigDecimal kwhPrice = tariff != null ? tariff.getKwhPrice() : null;
        BigDecimal iddlePricePerMin = tariff != null ? tariff.getIddlePricePerMin() : null;

        double estimatedMaxKwh = 0.0;
        BigDecimal estimatedMaxAmount = BigDecimal.ZERO;
        int estimatedMaxTimeInMin = 0;

        if (batteryKwh != null && batteryLevel != null) {
            estimatedMaxKwh = batteryKwh * (1 - batteryLevel);
        }

        if (kwhPrice != null && batteryKwh != null && batteryLevel != null) {
            estimatedMaxAmount = kwhPrice
                    .multiply(BigDecimal.valueOf(batteryKwh))
                    .multiply(BigDecimal.valueOf(1 - batteryLevel))
                    .setScale(2, RoundingMode.HALF_UP);
        }

        if (batteryKwh != null && pumpKwh != null && pumpKwh > 0) {
            estimatedMaxTimeInMin = (int) Math.round((batteryKwh / pumpKwh) * 60);
        }

        boolean hasEstimative = estimatedMaxAmount.compareTo(BigDecimal.ZERO) != 0
                || estimatedMaxTimeInMin != 0
                || estimatedMaxKwh != 0.0;

        CarOwnerPaymentCalculationNotificationEvent calculationEvent = new CarOwnerPaymentCalculationNotificationEvent(
                event.carOwnerPhone(),
                event.carOwnerIdentification(),
                batteryLevel,
                hasEstimative,
                estimatedMaxAmount,
                estimatedMaxTimeInMin,
                estimatedMaxKwh,
                kwhPrice != null ? kwhPrice : BigDecimal.ZERO,
                iddlePricePerMin != null ? iddlePricePerMin : BigDecimal.ZERO,
                event.pumpId()
        );

        log.info("PaymentCalculation pumpId={} hasEstimative={} phone={}",
                event.pumpId(), hasEstimative, event.carOwnerPhone());

        try {
            eventPublisher.publishEvent(calculationEvent);
        } catch (Exception e) {
            log.error("Failed to publish CarOwnerPaymentCalculationEvent pumpId={}", event.pumpId(), e);
            throw e;
        }
    }

    String createPaymentLink(CreatePaymentLinkRequest request) {
        log.info("createPaymentLink phone={} pumpId={} amountConfirmed={}",
                request.carOwnerPhone(), request.pumpId(), request.amountConfirmed());

        CarOwnerWallet wallet = carOwnerWalletRepository
                .findByCarOwnerIdentification(request.carOwnerIdentification())
                .orElseGet(() -> {
                    CarOwnerWallet newWallet = new CarOwnerWallet();
                    newWallet.setCarOwnerPhone(request.carOwnerPhone());
                    newWallet.setCarOwnerIdentification(request.carOwnerIdentification());
                    newWallet.setBalance(BigDecimal.ZERO);
                    return carOwnerWalletRepository.save(newWallet);
                });

        CarOwnerWalletStatement statement = new CarOwnerWalletStatement();
        statement.setCarOwnerWalletId(wallet.getId());
        statement.setChargeTransactionId(request.chargeTransactionId());
        statement.setAmount(request.amountConfirmed());
        statement.setPaymentMethod(request.paymentMethod());
        statement.setTransactionAt(LocalDateTime.now());
        carOwnerWalletStatementRepository.save(statement);

        log.info("CarOwnerWalletStatement created chargeTransactionId={} walletId={}",
                request.chargeTransactionId(), wallet.getId());

        // TODO: Implement Gateway Payment Integration
        return null;
    }

    void successPayment(SuccessPaymentRequest request) {
        log.info("successPayment chargeTransactionId={} amount={} paymentMethod={}",
                request.chargeTransactionId(), request.amount(), request.paymentMethod());

        CarOwnerWalletStatement statement = carOwnerWalletStatementRepository
                .findByChargeTransactionId(request.chargeTransactionId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "WalletStatement not found for chargeTransactionId=" + request.chargeTransactionId()));

        statement.setAmount(request.amount());
        statement.setGatewayTransactionId(request.gatewayTransactionId());
        statement.setPaymentMethod(request.paymentMethod());
        statement.setRemark(request.remark());
        carOwnerWalletStatementRepository.save(statement);

        CarOwnerWallet wallet = carOwnerWalletRepository.findById(statement.getCarOwnerWalletId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "CarOwnerWallet not found id=" + statement.getCarOwnerWalletId()));

        wallet.setBalance(wallet.getBalance().add(request.amount()));
        carOwnerWalletRepository.save(wallet);

        log.info("successPayment completed walletId={} newBalance={}",
                wallet.getId(), wallet.getBalance());

        enviar notificacao para o gateway iniciar o load
                gateway apos confirmar, enviar notificacao para o usuario avisando
        que iniciou e o tempo estimado para carga de acordo com os creditos e o preco do valor de idlle


    }
}