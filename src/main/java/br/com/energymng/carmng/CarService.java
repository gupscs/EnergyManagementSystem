package br.com.energymng.carmng;

import br.com.energymng.charge.ChargeTransactionStartByCarPluggedEvent;
import br.com.energymng.station.CarPluggedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
class CarService {

    private final CarRepository carRepository;
    private final CarOwnerRepository carOwnerRepository;
    private final ApplicationEventPublisher eventPublisher;

    Car create(Long carOwnerId, Car body) {
        CarOwner carOwner = carOwnerRepository.findByIdAndDeletedFalse(carOwnerId)
                .orElseThrow(() -> {
                    log.warn("CarOwner not found carOwnerId={}", carOwnerId);
                    return new ResponseStatusException(HttpStatus.NOT_FOUND, "Car owner not found");
                });
        if (carRepository.existsByCarUniqueIdAndDeletedFalse(body.getCarUniqueId())) {
            log.warn("Car uniqueId already in use uniqueId={}", body.getCarUniqueId());
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Car unique ID already in use");
        }
        body.setId(null);
        body.setDeleted(false);
        body.setCarOwner(carOwner);
        Car saved = carRepository.save(body);
        log.info("Car created uniqueId={} carOwnerId={}", saved.getCarUniqueId(), carOwnerId);
        return saved;
    }

    Car update(Long id, Car body) {
        Car car = carRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> {
                    log.warn("Car not found id={}", id);
                    return new ResponseStatusException(HttpStatus.NOT_FOUND, "Car not found");
                });
        if (!car.getCarUniqueId().equals(body.getCarUniqueId())
                && carRepository.existsByCarUniqueIdAndDeletedFalse(body.getCarUniqueId())) {
            log.warn("Car uniqueId already in use uniqueId={}", body.getCarUniqueId());
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Car unique ID already in use");
        }
        car.setCarUniqueId(body.getCarUniqueId());
        car.setPlate(body.getPlate());
        car.setModel(body.getModel());
        Car saved = carRepository.save(car);
        log.info("Car updated id={} uniqueId={}", id, saved.getCarUniqueId());
        return saved;
    }

    void delete(Long id) {
        Car car = carRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> {
                    log.warn("Car not found id={}", id);
                    return new ResponseStatusException(HttpStatus.NOT_FOUND, "Car not found");
                });
        car.setDeleted(true);
        carRepository.save(car);
        log.info("Car deleted id={}", id);
    }

    void carPlugged(CarPluggedEvent event) {
        if (event.carPluggedUniqueId() == null || event.carPluggedUniqueId().isBlank()) {
            log.info("Car plugged with no registered carUniqueId pumpUniqueId={}", event.pumpUniqueId());
            try {
                eventPublisher.publishEvent(ChargeTransactionStartByCarPluggedEvent.from(event));
            } catch (Exception e) {
                log.error("Failed to publish ChargeTransactionStartEvent pumpUniqueId={}", event.pumpUniqueId(), e);
                throw e;
            }
            return;
        }

        carRepository.findByCarUniqueIdAndDeletedFalse(event.carPluggedUniqueId())
                .ifPresentOrElse(
                        car -> {
                            log.info("Car plugged with registered car carUniqueId={} pumpUniqueId={}", car.getCarUniqueId(), event.pumpUniqueId());
                            try {
                                CarOwner owner = car.getCarOwner();
                                eventPublisher.publishEvent(ChargeTransactionStartByCarPluggedEvent.fromEntityAndEvent(owner, car, event));
                            } catch (Exception e) {
                                log.error("Failed to publish ChargeTransactionStartEvent carUniqueId={} pumpUniqueId={}", car.getCarUniqueId(), event.pumpUniqueId(), e);
                                throw new RuntimeException(e);
                            }
                        },
                        () -> {
                            log.warn("Car not found in registry carUniqueId={} pumpUniqueId={}", event.carPluggedUniqueId(), event.pumpUniqueId());
                            try {
                                eventPublisher.publishEvent(ChargeTransactionStartByCarPluggedEvent.from(event));
                            } catch (Exception e) {
                                log.error("Failed to publish ChargeTransactionStartEvent carUniqueId={} pumpUniqueId={}", event.carPluggedUniqueId(), event.pumpUniqueId(), e);
                                throw new RuntimeException(e);
                            }
                        }
                );
    }
}
