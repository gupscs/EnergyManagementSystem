package br.com.energymng.carmng;

import br.com.energymng.station.CarPluggedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.modulith.events.ApplicationModuleListener;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class CarMngEventListener {

    private final CarService carService;

    @ApplicationModuleListener
    @Retryable(maxAttempts = 3, backoff = @Backoff(delay = 2000, multiplier = 2))
    void carPluggedEventListener(CarPluggedEvent event) {
        carService.carPlugged(event);
    }
}