package com.tutorial.events.models;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ArrayBlockingQueue;
import com.tutorial.events.services.RequestService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.CollectionUtils;

import lombok.AccessLevel;
import lombok.Getter;

/**
 * workerId: string, //generated by api, used to identify an event worker.
    createdAt: timestamp, //generated by api, used to identify when a worker was created.
    lastCompletedTask: eventId, //generated by api, used to identify last completed task
    tasksRemaining: int, //generated by api, used to identify remaining tasks.
    willTerminate: boolean, //generated by api, used to determine if an event worker is full and will shutdown when all tasks in queue are complete.
    tasksCompleted: int, //generated by api, used to figure out completed tasks
    tasksFailed: int, //generated by api, used to figure out tasks which failed.
    maxReferenceCount: int, //used to determine the max number of resources an event worker can manage. 
    isDone: boolean, //internal use only, used to determine if the event service should create this worker.
 */

 @Getter
public class EventWorker implements Runnable {
    @Value("${events.worker.maxReferenceCount")
    private  int maxReferenceCount;
    private UUID workerId;
    private boolean stayAlive = true;
    private Set<String> referenceIds = Collections.synchronizedSet(new HashSet<String>());

    private RequestService requestService;
    //minimum amount of references to keep the event worker alive for. Consider this flag before terminating.
    @Value("${events.worker.minReferenceCount}")
    private int minReferenceCount;
    @Getter(AccessLevel.NONE)
    private final ArrayBlockingQueue<Events> queue;
    private Thread thread;
    private  EventWorker(Events event) {
        thread = new Thread(this);
        this.workerId = UUID.randomUUID();
        this.queue = new ArrayBlockingQueue<>(maxReferenceCount);
        this.queue.add(event);
        thread.start();
    }



    public static EventWorker init(Events event) {
        EventWorker e = new EventWorker(event);
        return e;
    }


 


    public String getWorkerId() {
        return workerId.toString();
    }


    //if the maxReferenceCount is reached, then add no more values to the queue and return false.

    public boolean appendEvent(Events event) {
        if(referenceIds.size() >= maxReferenceCount) {
            this.stayAlive = false;
            return false;
        }
        queue.add(event);
        return true;
   
    }



    
   


    @Override
    public void run() {

        while(true) {

            //stop running the application if the stayAlive flag is set to false and the queue is empty.
            if(!stayAlive && CollectionUtils.isEmpty(queue)) {
                //set continue running
                break;
            }
           Events event =  queue.poll();
           if (event != null) {
               requestService.makeRequest(event);
           }

        
        }

        
    }

    



  
}
