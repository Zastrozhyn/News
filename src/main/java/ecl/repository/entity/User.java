package ecl.repository.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User implements BaseEntity<Long>{

    @Id
    private Long id;

    private String name;

    @Enumerated(EnumType.STRING)
    private UserRole role;

}
