package com.example.nasa.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "cameras")
public class Camera {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nasa_id")
    private Integer nasaId;

    @Column(name = "name")
    private String name;

    public Camera(Integer nasaId, String name) {
        this.nasaId = nasaId;
        this.name = name;
    }
}
