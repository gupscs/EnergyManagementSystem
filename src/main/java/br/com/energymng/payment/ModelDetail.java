package br.com.energymng.payment;

import br.com.energymng.common.entity.AuditableEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "model_detail")
@Getter
@Setter
@NoArgsConstructor
public class ModelDetail extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String model;

    @Column(name = "batery_kwh")
    private Double bateryKwh;
}