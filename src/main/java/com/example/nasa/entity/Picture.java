package com.example.nasa.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "pictures")
public class Picture {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nasa_id")
    private Integer nasaId;

    @Column(name = "img_src")
    private String imgSrc;

    @ManyToOne
    @JoinColumn(name = "camera_id", referencedColumnName = "id")
    private Camera camera;

    public Picture(Integer nasaId, String imgSrc) {
        this.nasaId = nasaId;
        this.imgSrc = imgSrc;
    }
}
