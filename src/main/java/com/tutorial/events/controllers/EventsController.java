package com.tutorial.events.controllers;
import com.tutorial.events.models.Events;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController()
@RequestMapping("/v1/events")
public class EventsController {
    
    @PostMapping
    public ResponseEntity<String> createEvent(@RequestBody Events event) {
        return null;
    }

    @GetMapping()
    public ResponseEntity<String> health() {
        return new ResponseEntity<>("Healthy", HttpStatus.OK);
    }
   
}
