package br.com.energymng.payment;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

interface ModelDetailRepository extends JpaRepository<ModelDetail, Long> {
    Optional<ModelDetail> findByModel(String model);
}