package br.com.energymng.payment;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

interface CarOwnerWalletRepository extends JpaRepository<CarOwnerWallet, Long> {

    Optional<CarOwnerWallet> findByCarOwnerIdentification(String carOwnerIdentification);
}