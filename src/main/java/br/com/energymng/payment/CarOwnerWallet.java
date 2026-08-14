package br.com.energymng.payment;

import br.com.energymng.common.entity.AuditableEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "car_owner_wallet")
@Getter
@Setter
@NoArgsConstructor
public class CarOwnerWallet extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "car_owner_id")
    private Long carOwnerId;

    @Column(name = "car_owner_phone")
    private String carOwnerPhone;

    @Column(name = "car_owner_identification")
    private String carOwnerIdentification;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal balance = BigDecimal.ZERO;
}