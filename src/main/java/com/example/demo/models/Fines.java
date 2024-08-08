package com.example.demo.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "fines")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Fines {
    @Id
    private Long id;
    @OneToOne
    private User user;
    @Column(name = "amount", nullable = false)
    private Double amount;
    @Temporal(TemporalType.DATE)
    private LocalDate paymentDate;
    @Column(name = "description")
    private String description;
}
