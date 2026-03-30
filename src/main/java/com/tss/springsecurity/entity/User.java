package com.tss.springsecurity.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long  userId;

    @Column(unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.REFRESH, CascadeType.MERGE, CascadeType.DETACH})
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;
}
