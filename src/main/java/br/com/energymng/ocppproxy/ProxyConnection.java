package br.com.energymng.ocppproxy;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "proxy_connections")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ProxyConnection {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String stationSerialNumber;

    @Column(nullable = false)
    private String remoteAddress;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ConnectionStatus status;

    @Column(nullable = false)
    private String ocppVersion;

    private LocalDateTime connectedAt;
    private LocalDateTime disconnectedAt;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() { this.createdAt = LocalDateTime.now(); }
}
