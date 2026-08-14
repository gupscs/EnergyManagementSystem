package br.com.energymng.payment;

import br.com.energymng.common.entity.AuditableEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "car_owner_wallet_statement")
@Getter
@Setter
@NoArgsConstructor
public class CarOwnerWalletStatement extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "car_owner_wallet_id", nullable = false)
    private Long carOwnerWalletId;

    @Column(precision = 10, scale = 2)
    private BigDecimal amount;

    @Column(name = "charge_transaction_id", nullable = false)
    private Long chargeTransactionId;

    @Column(name = "gateway_transaction_id")
    private String gatewayTransactionId;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_method")
    private PaymentMethod paymentMethod;

    @Column(name = "transaction_at")
    private LocalDateTime transactionAt;

    @Column(name = "pump_id")
    private Long pumpId;

    private String remark;
}