package com.tutorial.events.services;

import java.util.ArrayList;

import com.tutorial.events.models.EventWorker;
import com.tutorial.events.models.Events;

import org.springframework.stereotype.Service;

@Service
public class EventsService {

    ArrayList<EventWorker> eventWorkerList = new ArrayList<>();
    public void init(Events event) {
       EventWorker e = eventWorkerList.get(eventWorkerList.size() - 1);
       if(!e.appendEvent(event)) {
           EventWorker newWorker = EventWorker.init(event);
           eventWorkerList.add(newWorker);
            
       } else {
           e.appendEvent(event);
       }
    }

   
}

