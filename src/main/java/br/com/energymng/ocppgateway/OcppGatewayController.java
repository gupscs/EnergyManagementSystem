package br.com.energymng.ocppgateway;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/ocpp/gateway")
@RequiredArgsConstructor
public class OcppGatewayController {

    private final OcppGatewayService gatewayService;

    @PostMapping("/{serialNumber}/message")
    public ResponseEntity<OcppMessage> receive(
            @PathVariable String serialNumber,
            @RequestParam OcppAction action,
            @RequestBody String payload) {
        return ResponseEntity.ok(gatewayService.handleInbound(serialNumber, action, payload));
    }

    @GetMapping("/{serialNumber}/messages")
    public List<OcppMessage> findByStation(@PathVariable String serialNumber) {
        return gatewayService.findByStation(serialNumber);
    }
}
