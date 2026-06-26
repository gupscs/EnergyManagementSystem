package br.com.energymng.station;

import br.com.energymng.common.entity.AuditableEntity;
import br.com.energymng.common.event.station.CarPluggedEvent;
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

    @Column(name = "pump_code")
    private Integer pumpCode;

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

    @Column(name = "pump_kwh")
    private Double pumpKwh;

    @Column(nullable = false)
    private boolean deleted = false;

    public CarPluggedEvent toCarPluggedEvent(Double batteryLevel) {
        Station s = this.getStation();
        return new CarPluggedEvent(
                this.getId(),
                s.getId(),
                s.getName(),
                s.getAddress(),
                s.getZipcode(),
                s.getLongitude(),
                s.getLatitude(),
                s.getStationCode(),
                this.getPumpUniqueId(),
                this.getName(),
                this.getPumpStatus(),
                this.getPumpCode(),
                this.getCarPluggedUniqueId(),
                this.getPluggedAt(),
                batteryLevel,
                this.getPumpKwh(),
                this.isDeleted()
        );
    }
}