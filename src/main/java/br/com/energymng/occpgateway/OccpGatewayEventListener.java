package br.com.energymng.occpgateway;

import br.com.energymng.common.event.ocppgateway.PumpLoadStartEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.modulith.events.ApplicationModuleListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class OccpGatewayEventListener {

    @ApplicationModuleListener
    void onPumpLoadStart(PumpLoadStartEvent event) {
        // to-do send the occp command
    }
}