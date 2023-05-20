package ru.roslyackov.springboot.business.entity;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.roslyackov.springboot.auth.entity.User;

import java.util.Collection;

@Entity
@Table(name = "priority")
@EqualsAndHashCode
@NoArgsConstructor
@Getter
@Setter
public class Priority {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "color")
    private String color;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
}
