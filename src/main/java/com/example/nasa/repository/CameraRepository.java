package com.example.nasa.repository;

import com.example.nasa.entity.Camera;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface CameraRepository extends JpaRepository<Camera, Integer> {
    Optional<Camera> findCameraByNasaIdAndName(Integer nasaId, String name);
}
