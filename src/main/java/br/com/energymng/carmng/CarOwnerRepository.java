package br.com.energymng.carmng;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

interface CarOwnerRepository extends JpaRepository<CarOwner, Long> {

    List<CarOwner> findAllByDeletedFalse();

    Optional<CarOwner> findByIdAndDeletedFalse(Long id);
}