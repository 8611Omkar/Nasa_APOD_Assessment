package com.example.Nasa_APOD_Assessment.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class ApodEntity {

        private String date;
        private String explanation;
        private String title;
        private String url;
        private String hdurl;
        private String media_type;
        private String service_version;
        private String copyright;

}
