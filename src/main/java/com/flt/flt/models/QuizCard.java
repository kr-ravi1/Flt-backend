package com.flt.flt.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class QuizCard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private long cardOrder;
    private String question;
    private String answer;
    @Enumerated(value = EnumType.STRING)
    private Difficulties difficulty = Difficulties.MEDIUM;
    @Enumerated(value = EnumType.STRING)
    private Categories category = Categories.MISC;
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
}
