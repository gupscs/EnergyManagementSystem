package br.com.energymng.payment;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

interface CarOwnerWalletStatementRepository extends JpaRepository<CarOwnerWalletStatement, Long> {

    Optional<CarOwnerWalletStatement> findByChargeTransactionId(Long chargeTransactionId);
}