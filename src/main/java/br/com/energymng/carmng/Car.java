package br.com.energymng.carmng;

import br.com.energymng.common.entity.AuditableEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "car")
@Getter
@Setter
@NoArgsConstructor
public class Car extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "car_unique_id", nullable = false, unique = true)
    private String carUniqueId;

    private String plate;

    private String model;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "car_owner_id", nullable = false)
    private CarOwner carOwner;

    @Column(nullable = false)
    private boolean deleted = false;
}