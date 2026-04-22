package com.senagust.helpdesk.model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "users")
@Data
public abstract class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(unique = true)
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private boolean isActive;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserType userType;
}

