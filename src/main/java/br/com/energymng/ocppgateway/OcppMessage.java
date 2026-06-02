package br.com.energymng.ocppgateway;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "ocpp_messages")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class OcppMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String messageId;

    @Column(nullable = false)
    private String stationSerialNumber;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OcppAction action;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OcppDirection direction;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String payload;

    @Column(nullable = false, updatable = false)
    private LocalDateTime receivedAt;

    @PrePersist
    protected void onCreate() { this.receivedAt = LocalDateTime.now(); }
}
