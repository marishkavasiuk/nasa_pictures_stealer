package com.example.nasa.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class CameraDto {

    @JsonProperty(value = "id")
    private Integer nasaId;

    @JsonProperty(value = "name")
    private String name;
}
