package br.com.energymng.payment;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/link")
    public ResponseEntity<String> createPaymentLink(@RequestBody CreatePaymentLinkRequest request) {
        String paymentLink = paymentService.createPaymentLink(request);
        return ResponseEntity.ok(paymentLink);
    }
}
