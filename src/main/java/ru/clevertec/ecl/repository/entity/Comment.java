package ru.clevertec.ecl.repository.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "comment")
public class Comment implements BaseEntity<Long>{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private String text;

    private Long userId;

    private Long newsId;

    public Comment(String text) {
        this.text = text;
    }

    @PrePersist
    public void onCreate(){
        createTime = LocalDateTime.now();
        updateTime = createTime;
    }

    @PreUpdate
    public void onUpdate(){
        updateTime = LocalDateTime.now();
    }
}
