package br.com.energymng.payment;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

interface TariffRepository extends JpaRepository<Tariff, Long> {
    Optional<Tariff> findByPumpId(Long pumpId);
}