package ru.clevertec.ecl.repository.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * User entity for managing with database
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User implements BaseEntity<Long>{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Enumerated(EnumType.STRING)
    private UserRole role;

}
