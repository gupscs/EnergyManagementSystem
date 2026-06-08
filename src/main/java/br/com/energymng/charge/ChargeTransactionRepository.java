package br.com.energymng.charge;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

interface ChargeTransactionRepository extends JpaRepository<ChargeTransaction, Long> {

    Optional<ChargeTransaction> findTopByPumpIdAndChargeStatusInOrderByCreatedAtDesc(
            Long pumpId, List<ChargeStatus> statuses);
}