package com.example.demo.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "user_book")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserBook{
    @Id
    private Long id;

    @ManyToOne
    private Book book;

    @ManyToOne
    private User user;

    @Temporal(TemporalType.DATE)
    private LocalDate borrowedDate;

    @Temporal(TemporalType.DATE)
    private LocalDate returnedDate;

    private Integer overDueDays;
}
