package br.com.energymng.carmng;

import br.com.energymng.common.entity.AuditableEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "car_owner")
@Getter
@Setter
@NoArgsConstructor
public class CarOwner extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String identification;

    private String phone;

    private String email;

    @OneToMany(mappedBy = "carOwner", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Car> cars = new ArrayList<>();

    @Column(nullable = false)
    private boolean deleted = false;
}