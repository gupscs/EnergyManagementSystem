package br.com.energymng.station;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "pump")
@Getter
@Setter
@NoArgsConstructor
public class  Pump extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "pump_unique_id", nullable = false, unique = true)
    private String pumpUniqueId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "station_id", nullable = false)
    private Station station;

    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "pump_status", nullable = false)
    private PumpStatus pumpStatus = PumpStatus.AVALIABLE;

    @Column(name = "car_plugged_unique_id")
    private String carPluggedUniqueId;

    @Column(name = "plugged_at")
    private LocalDateTime pluggedAt;

    @Column(nullable = false)
    private boolean deleted = false;
}