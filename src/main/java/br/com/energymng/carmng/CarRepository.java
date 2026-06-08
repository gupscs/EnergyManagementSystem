package br.com.energymng.carmng;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

interface CarRepository extends JpaRepository<Car, Long> {

    List<Car> findAllByDeletedFalse();

    Optional<Car> findByIdAndDeletedFalse(Long id);

    List<Car> findAllByCarOwnerIdAndDeletedFalse(Long carOwnerId);

    boolean existsByCarUniqueIdAndDeletedFalse(String carUniqueId);

    java.util.Optional<Car> findByCarUniqueIdAndDeletedFalse(String carUniqueId);
}