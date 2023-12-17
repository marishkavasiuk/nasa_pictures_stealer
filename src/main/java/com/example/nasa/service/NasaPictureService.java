package com.example.nasa.service;

import com.example.nasa.dto.CameraDto;
import com.example.nasa.dto.PictureDto;
import com.example.nasa.entity.Camera;
import com.example.nasa.entity.Picture;
import com.example.nasa.repository.CameraRepository;
import com.example.nasa.repository.PictureRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;


@Service
@AllArgsConstructor
@Slf4j
public class NasaPictureService {

    @Value("${nasa.url}")
    private final String URL;

    @Value("${nasa.api-key}")
    private final String NASA_API_KEY;

    private final CameraRepository cameraRepository;
    private final PictureRepository pictureRepository;

    private final RestTemplate restTemplate = new RestTemplate();

    public void steal(Integer sol) {
        String url = UriComponentsBuilder.fromHttpUrl(URL)
                .queryParam("sol", sol)
                .queryParam("api_key", NASA_API_KEY)
                .toUriString();

        JsonNode photos = restTemplate.getForEntity(url, JsonNode.class).getBody().findValue("photos");
        parsePictures(photos);
    }

    public void parsePictures(JsonNode jsonNode) {
        ObjectMapper objectMapper = new ObjectMapper();
        if (jsonNode.isArray()) {
            for (JsonNode item : jsonNode) {
                PictureDto pictureDto = null;
                try {
                    pictureDto = objectMapper.treeToValue(item, PictureDto.class);
                } catch (JsonProcessingException e) {
                    log.debug("Failed to parse picture: " + e.getMessage());
                }
                Camera camera = getOrCreateCamera(pictureDto.getCameraDto());
                Picture picture = new Picture(pictureDto.getNasaId(), pictureDto.getImgSrc());
                picture.setCamera(camera);
                pictureRepository.save(picture);
            }
        }
    }

    private Camera getOrCreateCamera(CameraDto cameraDto) {
        return cameraRepository.findCameraByNasaIdAndName(cameraDto.getNasaId(), cameraDto.getName())
                .orElseGet(() -> cameraRepository.save(new Camera(cameraDto.getNasaId(), cameraDto.getName())));
    }
}
