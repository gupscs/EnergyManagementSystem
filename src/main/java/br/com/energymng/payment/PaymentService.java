package br.com.energymng.payment;

import br.com.energymng.common.event.notification.CarOwnerPaymentCalculationNotificationEvent;
import br.com.energymng.common.event.payment.PaymentCalculateAmountEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
class PaymentService {

    private final TariffRepository tariffRepository;
    private final ModelDetailRepository modelDetailRepository;
    private final ApplicationEventPublisher eventPublisher;

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
}