package com.example.nasa.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class PictureDto {

    @JsonProperty(value = "id")
    private Integer nasaId;

    @JsonProperty(value = "img_src")
    private String imgSrc;

    @JsonProperty(value = "camera")
    private CameraDto cameraDto;
}
