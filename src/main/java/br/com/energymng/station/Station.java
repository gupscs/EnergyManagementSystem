package br.com.energymng.station;

import br.com.energymng.common.entity.AuditableEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "station")
@Getter
@Setter
@NoArgsConstructor
public class Station extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(name = "station_code")
    private Integer stationCode;

    private String address;

    private String zipcode;

    @OneToMany(mappedBy = "station", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Pump> pumps = new ArrayList<>();

    @Column(precision = 10, scale = 7)
    private BigDecimal longitude;

    @Column(precision = 10, scale = 7)
    private BigDecimal latitude;

    @Column(nullable = false)
    private boolean deleted = false;
}