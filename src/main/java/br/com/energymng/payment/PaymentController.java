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

    @PostMapping
    public ResponseEntity<Payment> process(@RequestParam Long userId,
                                           @RequestParam Long sessionId,
                                           @RequestParam BigDecimal amount) {
        return ResponseEntity.ok(paymentService.process(userId, sessionId, amount));
    }

    @GetMapping("/user/{userId}")
    public List<Payment> findByUser(@PathVariable Long userId) {
        return paymentService.findByUser(userId);
    }
}
