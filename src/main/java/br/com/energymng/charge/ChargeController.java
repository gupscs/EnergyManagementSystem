package br.com.energymng.charge;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/charges")
@RequiredArgsConstructor
class ChargeController {

    private final ChargeTransactionService chargeTransactionService;

    @PostMapping("/start")
    ResponseEntity<Void> chargeTransactionStart(@RequestBody ChargeStartRequest request) {
        chargeTransactionService.startChargeTransaction(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
