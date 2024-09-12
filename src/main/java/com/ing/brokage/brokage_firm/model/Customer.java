package com.ing.brokage.brokage_firm.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "customer")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(length=50, nullable = false, unique = true)
    private String userName;

    private String passwordHash;

    @CreationTimestamp
    private LocalDateTime created;
}
