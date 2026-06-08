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
@RequestMapping("/api/car-owners")
@RequiredArgsConstructor
class CarOwnerController {

    private final CarOwnerRepository carOwnerRepository;
    private final CarOwnerService carOwnerService;

    @GetMapping
    ResponseEntity<List<CarOwner>> findAll() {
        return ResponseEntity.ok(carOwnerRepository.findAllByDeletedFalse());
    }

    @GetMapping("/{id}")
    ResponseEntity<CarOwner> findById(@PathVariable Long id) {
        CarOwner carOwner = carOwnerRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> {
                    log.warn("CarOwner not found id={}", id);
                    return new ResponseStatusException(HttpStatus.NOT_FOUND, "Car owner not found");
                });
        return ResponseEntity.ok(carOwner);
    }

    @PostMapping
    ResponseEntity<CarOwner> create(@RequestBody CarOwner body) {
        return ResponseEntity.status(HttpStatus.CREATED).body(carOwnerService.create(body));
    }

    @PutMapping("/{id}")
    ResponseEntity<CarOwner> update(@PathVariable Long id, @RequestBody CarOwner body) {
        return ResponseEntity.ok(carOwnerService.update(id, body));
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Void> delete(@PathVariable Long id) {
        carOwnerService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
