package br.com.energymng.carmng;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
class CarOwnerService {

    private final CarOwnerRepository carOwnerRepository;

    CarOwner create(CarOwner body) {
        body.setId(null);
        body.setDeleted(false);
        CarOwner saved = carOwnerRepository.save(body);
        log.info("CarOwner created id={} name={}", saved.getId(), saved.getName());
        return saved;
    }

    CarOwner update(Long id, CarOwner body) {
        CarOwner carOwner = carOwnerRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> {
                    log.warn("CarOwner not found id={}", id);
                    return new ResponseStatusException(HttpStatus.NOT_FOUND, "Car owner not found");
                });
        carOwner.setName(body.getName());
        carOwner.setIdentification(body.getIdentification());
        carOwner.setPhone(body.getPhone());
        carOwner.setEmail(body.getEmail());
        CarOwner saved = carOwnerRepository.save(carOwner);
        log.info("CarOwner updated id={} name={}", saved.getId(), saved.getName());
        return saved;
    }

    void delete(Long id) {
        CarOwner carOwner = carOwnerRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> {
                    log.warn("CarOwner not found id={}", id);
                    return new ResponseStatusException(HttpStatus.NOT_FOUND, "Car owner not found");
                });
        carOwner.setDeleted(true);
        carOwnerRepository.save(carOwner);
        log.info("CarOwner deleted id={}", id);
    }
}
