package com.example.demo.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "middle_name")
    private String middleName;

    @Column(name="last_name")
    private String lastName;

    @Column(name="email")
    private String email;

    @Column(name="password")
    private String password;

    @Column(name="phone")
    private String phone;

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    private List<Address> address;

    @Column(name="is_active")
    private boolean isActive;

    @PrePersist
    private void prePersist() {
        isActive = true;
    }
}
