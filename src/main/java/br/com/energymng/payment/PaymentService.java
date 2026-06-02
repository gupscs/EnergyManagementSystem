package br.com.energymng.payment;

import lombok.RequiredArgsConstructor;
import org.springframework.modulith.events.ApplicationModuleListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class PaymentService {

    private final PaymentRepository paymentRepository;

    public Payment process(Long userId, Long sessionId, BigDecimal amount) {
        Payment payment = Payment.builder()
            .userId(userId)
            .sessionId(sessionId)
            .amount(amount)
            .currency("BRL")
            .status(PaymentStatus.PENDING)
            .build();
        return paymentRepository.save(payment);
    }

    @Transactional(readOnly = true)
    public List<Payment> findByUser(Long userId) { return paymentRepository.findByUserId(userId); }
}
