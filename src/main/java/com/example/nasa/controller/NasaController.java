package com.example.nasa.controller;

import com.example.nasa.service.NasaPictureService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/picture")
public class NasaController {

    private final NasaPictureService nasaPictureService;

    @PostMapping(produces = MediaType.IMAGE_JPEG_VALUE)
    public void steal(@RequestParam Integer sol) {
        nasaPictureService.steal(sol);
    }
}
