package com.example.Nasa_APOD_Assessment.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
public class HealthCheck {

        @GetMapping
        public String healthCheck(){
            return "ok";
        }


}
