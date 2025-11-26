package com.example.Nasa_APOD_Assessment.Controller;


import com.example.Nasa_APOD_Assessment.Model.ApodEntity;
import com.example.Nasa_APOD_Assessment.Service.ApodService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/apod")
public class ApodController {

    private final ApodService apodService;

    public ApodController(ApodService apodService) {
        this.apodService = apodService;
    }

    @GetMapping("/today")
    public ResponseEntity<ApodEntity> today() {
        ApodEntity r = apodService.getToday();
        return ResponseEntity.ok(r);
    }

    @GetMapping
    public ResponseEntity<ApodEntity> byDate(@RequestParam String date) {
        ApodEntity r = apodService.getApodByDate(date);
        return ResponseEntity.ok(r);
    }

    @GetMapping("/range")
    public ResponseEntity<ApodEntity[]> range(@RequestParam String start, @RequestParam String end) {
        ApodEntity[] arr = apodService.getApodRange(start, end);
        return ResponseEntity.ok(arr);
    }

    @GetMapping("/recent")
    public ResponseEntity<List<ApodEntity>> recent(@RequestParam(defaultValue = "10") int count) {
        List<ApodEntity> arr = apodService.getRecent(count);
        return ResponseEntity.ok(arr);
    }
}







