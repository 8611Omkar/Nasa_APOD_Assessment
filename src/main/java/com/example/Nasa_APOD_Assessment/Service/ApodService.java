package com.example.Nasa_APOD_Assessment.Service;

import com.example.Nasa_APOD_Assessment.Model.ApodEntity;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDate;
import java.util.*;

@Service
public class ApodService {

    private final RestTemplate restTemplate = new RestTemplate();

    private LocalDate maxAvailableDate;

    private static final LocalDate MIN_DATE = LocalDate.of(1995, 6, 16);

    @Value("${nasa.apod.url}")
    private  String apodBase;

    @Value("${nasa.api.key}")
    private  String apiKeyEnv; // fallback to env var

    private String getApiKey() {
        String k = System.getenv("nasa.api.key");
        if (k != null && !k.isBlank()) return k;
        return apiKeyEnv != null ? apiKeyEnv : "";
    }

    @Cacheable(cacheNames = "apodByDate", key = "#date")
    public ApodEntity getApodByDate(String date) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(apodBase)
                .queryParam("date", date)
                .queryParam("api_key", getApiKey());
        ResponseEntity<ApodEntity> resp = restTemplate.getForEntity(builder.toUriString(), ApodEntity.class);
        return resp.getBody();
    }

    @Cacheable(cacheNames = "apodRange", key = "#start + '-' + #end")
    public ApodEntity[] getApodRange(String start, String end) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(apodBase)
                .queryParam("start_date", start)
                .queryParam("end_date", end)
                .queryParam("nasa.api.key", getApiKey());
        ResponseEntity<ApodEntity[]> resp = restTemplate.getForEntity(builder.toUriString(), ApodEntity[].class);
        return resp.getBody();
    }

    public ApodEntity getToday() {
        LocalDate today = LocalDate.now();
        today = clampDate(today);
        return getApodByDate(today);
    }

    public List<ApodEntity> getRecent(int days) {
        List<ApodEntity> list = new ArrayList<>();

        LocalDate today = clampDate(LocalDate.now());

        for (int i = 0; i < days; i++) {
            LocalDate d = clampDate(today.minusDays(i));
            list.add(getApodByDate(d));
        }

        return list;
    }

    @PostConstruct
    public void init() {

        try {
            String url = "https://api.nasa.gov/planetary/apod?api_key=" + apiKeyEnv;
            ApodEntity latest = restTemplate.getForObject(url, ApodEntity.class);

            if (latest != null && latest.getDate() != null) {
                maxAvailableDate = LocalDate.parse(latest.getDate());
            } else {
                maxAvailableDate = LocalDate.now().minusDays(1);
            }

            System.out.println("🚀 NASA latest APOD date set to: " + maxAvailableDate);

        } catch (Exception e) {
            maxAvailableDate = LocalDate.now().minusDays(1);
        }
    }

    private LocalDate clampDate(LocalDate date) {
        if (date.isBefore(MIN_DATE)) return MIN_DATE;
        if (date.isAfter(maxAvailableDate)) return maxAvailableDate;
        return date;
    }

    public ApodEntity getApodByDate(LocalDate date) {
        LocalDate finalDate = clampDate(date);

        String url = String.format(
                "https://api.nasa.gov/planetary/apod?api_key=%s&date=%s",
                apiKeyEnv,
                finalDate.toString()
        );

        try {
            return restTemplate.getForObject(url, ApodEntity.class);
        } catch (HttpClientErrorException e) {
            throw new RuntimeException("NASA API error: " + e.getResponseBodyAsString());
        }
    }

}
