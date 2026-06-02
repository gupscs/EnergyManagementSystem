package br.com.energymng.ocppproxy;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/ocpp/proxy")
@RequiredArgsConstructor
public class OcppProxyController {

    private final OcppProxyService proxyService;

    @PostMapping("/{serialNumber}/connect")
    public ResponseEntity<ProxyConnection> connect(
            @PathVariable String serialNumber,
            @RequestParam String remoteAddress,
            @RequestParam(defaultValue = "1.6") String ocppVersion) {
        return ResponseEntity.ok(proxyService.connect(serialNumber, remoteAddress, ocppVersion));
    }

    @PostMapping("/{serialNumber}/disconnect")
    public ResponseEntity<ProxyConnection> disconnect(@PathVariable String serialNumber) {
        return ResponseEntity.ok(proxyService.disconnect(serialNumber));
    }

    @GetMapping("/active")
    public List<ProxyConnection> getActiveConnections() {
        return proxyService.findActiveConnections();
    }
}
