package br.com.energymng.payment;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

interface PaymentRepository extends JpaRepository<Payment, Long> {
    List<Payment> findByUserId(Long userId);
}
