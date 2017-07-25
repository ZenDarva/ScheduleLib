package com.gmail.zendarva.scheduler;

import com.example.examplemod.ExampleMod;
import com.gmail.zendarva.scheduler.requests.info.WorldInfoRequest;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Created by James on 7/24/2017.
 */
public class OnThreadScheduler {

    private List<WorldInfoRequest> requests;
    private ReadWriteLock requestLock;

    public OnThreadScheduler() {
        requestLock = new ReentrantReadWriteLock();
        requests= new LinkedList<>();
    }

    @SubscribeEvent
    public void startTick(TickEvent.ServerTickEvent event){
        process();
    }


    private void process(){
        requestLock.readLock().lock();
        try{
            for(WorldInfoRequest request : requests)
            {
                processWorldRequest(request);
            }
            requests.clear();
        }
        finally {
            requestLock.readLock().unlock();
        }
    }

    public void addRequest(WorldInfoRequest request){
        requestLock.writeLock().lock();
        try {
            requests.add(request);
        }
        finally {
            requestLock.writeLock().unlock();
        }
    }

    private void processWorldRequest(WorldInfoRequest request){
        request.executeRequest();
        ExampleMod.offThreadScheduler.addCompletedWorldInfoRequest(request);
    }
}
