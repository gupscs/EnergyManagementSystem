package br.com.energymng.payment;

import br.com.energymng.common.entity.AuditableEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "tariff")
@Getter
@Setter
@NoArgsConstructor
public class Tariff  extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "pump_id", nullable = false, unique = true)
    private Long pumpId;

    @Column(name = "kwh_price", nullable = false, precision = 10, scale = 4)
    private BigDecimal kwhPrice;

    @Column(name = "iddle_price_per_min", precision = 10, scale = 4)
    private BigDecimal iddlePricePerMin;
}