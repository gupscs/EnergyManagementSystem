package br.com.energymng.station;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "pump_status_history")
@Getter
@Setter
@NoArgsConstructor
public class PumpStatusHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pump_id", nullable = false)
    private Pump pump;

    @Column(name = "car_plugged_unique_id")
    private String carPluggedUniqueId;

    @Column(name = "plugged_at")
    private LocalDateTime pluggedAt;

    @Column(name = "unplugged_at")
    private LocalDateTime unpluggedAt;
}