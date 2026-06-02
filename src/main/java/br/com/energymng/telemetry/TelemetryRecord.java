package br.com.energymng.telemetry;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "telemetry_records")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class TelemetryRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long stationId;

    @Column(nullable = false)
    private Double currentPowerKw;

    @Column(nullable = false)
    private Double voltageV;

    @Column(nullable = false)
    private Double currentA;

    private Double temperatureCelsius;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TelemetryStatus status;

    @Column(nullable = false, updatable = false)
    private LocalDateTime recordedAt;

    @PrePersist
    protected void onCreate() { this.recordedAt = LocalDateTime.now(); }
}
