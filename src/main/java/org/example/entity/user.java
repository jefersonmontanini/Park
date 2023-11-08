package org.example.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")
public class user implements Serializable {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100, nullable = false, unique = true)
    private String user;

    @Column(length = 200, nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(length = 100, nullable = false)
    private Role role;

    @Column
    private LocalDateTime createDate;

    @Column
    private LocalDateTime changeDate;

    @Column
    private String createBy;

    @Column
    private String changeBy;

    public enum Role {
        ROLE_ADMIN, ROLE_CLIENT
    }
}
