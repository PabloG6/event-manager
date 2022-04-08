package com.tutorial.events.services;
import com.tutorial.events.models.Events;
import com.tutorial.events.models.EventsResponse;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class RequestService {
    private final RestTemplate restTemplate;
    public RequestService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }


    public EventsResponse makeRequest(Events events) {
        HttpEntity<String> httpEntity = new HttpEntity<>(events.getPayload(), this.getHeaders());
        this.getHeaders();
        ResponseEntity<EventsResponse> eventsResponse = this.restTemplate.postForEntity(events.getUrl(), httpEntity, EventsResponse.class);
        return eventsResponse.getBody();
       
    }



    private HttpHeaders getHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }
    
    
}
