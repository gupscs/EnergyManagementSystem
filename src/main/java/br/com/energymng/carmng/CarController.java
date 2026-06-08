package br.com.energymng.carmng;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/cars")
@RequiredArgsConstructor
class CarController {

    private final CarRepository carRepository;
    private final CarOwnerRepository carOwnerRepository;
    private final CarService carService;

    @GetMapping
    ResponseEntity<List<Car>> findAll() {
        return ResponseEntity.ok(carRepository.findAllByDeletedFalse());
    }

    @GetMapping("/{id}")
    ResponseEntity<Car> findById(@PathVariable Long id) {
        Car car = carRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> {
                    log.warn("Car not found id={}", id);
                    return new ResponseStatusException(HttpStatus.NOT_FOUND, "Car not found");
                });
        return ResponseEntity.ok(car);
    }

    @GetMapping("/owner/{carOwnerId}")
    ResponseEntity<List<Car>> findByCarOwner(@PathVariable Long carOwnerId) {
        carOwnerRepository.findByIdAndDeletedFalse(carOwnerId)
                .orElseThrow(() -> {
                    log.warn("CarOwner not found carOwnerId={}", carOwnerId);
                    return new ResponseStatusException(HttpStatus.NOT_FOUND, "Car owner not found");
                });
        return ResponseEntity.ok(carRepository.findAllByCarOwnerIdAndDeletedFalse(carOwnerId));
    }

    @PostMapping("/owner/{carOwnerId}")
    ResponseEntity<Car> create(@PathVariable Long carOwnerId, @RequestBody Car body) {
        return ResponseEntity.status(HttpStatus.CREATED).body(carService.create(carOwnerId, body));
    }

    @PutMapping("/{id}")
    ResponseEntity<Car> update(@PathVariable Long id, @RequestBody Car body) {
        return ResponseEntity.ok(carService.update(id, body));
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Void> delete(@PathVariable Long id) {
        carService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
