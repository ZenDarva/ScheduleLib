package com.gmail.zendarva.scheduler;

import com.gmail.zendarva.scheduler.requests.info.WorldInfoRequest;
import com.offbynull.coroutines.user.CoroutineRunner;

import java.util.*;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Created by James on 7/24/2017.
 */
public class OffThreadScheduler extends Thread{


    private List<ScheduledTask> tasks;
    private ReadWriteLock taskLock;
    private Map<UUID, WorldInfoRequest> worldInfoRequests;
    private ReadWriteLock worldInfoRequestsLock;

    public OffThreadScheduler(){
        tasks = new ArrayList<ScheduledTask>();
        taskLock = new ReentrantReadWriteLock();
        worldInfoRequestsLock = new ReentrantReadWriteLock();
        worldInfoRequests = new HashMap<>();
    }

    @Override
    public void run() {
        Map<UUID, CoroutineRunner> coroutines = new HashMap<>();
        while(true){
            try {
                sleep(50);
                taskLock.readLock().lock();
                try {
                    for (ScheduledTask task : tasks) {
                        if (!coroutines.containsKey(task.id)) {
                            CoroutineRunner cont = new CoroutineRunner(task);
                            coroutines.put(task.id,cont);
                        }
                        if (task.isDone()){
                            coroutines.remove(task.id);
                            continue;
                        }
                        if (task.sleepTil <= System.currentTimeMillis()) {
                            if ((task.awaitingRequest && worldInfoRequests.containsKey(task.id)) || !task.awaitingRequest) {
                                task.awaitingRequest=false;
                                CoroutineRunner runner = coroutines.get(task.id);
                                runner.execute();
                                worldInfoRequests.remove(task.id);
                            }
                            task.sleepTil=-1l;

                        }
                    }
                }
                finally{
                    taskLock.readLock().unlock();
                }


            } catch (InterruptedException e) {
                return;
            }
        }
    }


    public void addTask(ScheduledTask task){
        taskLock.writeLock().lock();
        try {
            tasks.add(task);
        }
        finally {
            taskLock.writeLock().unlock();
        }
    }

    public void addCompletedWorldInfoRequest(WorldInfoRequest request){
        worldInfoRequestsLock.writeLock().lock();
        try {
            worldInfoRequests.put(request.requestedBy,request);
        }
        finally{
            worldInfoRequestsLock.writeLock().unlock();
        }
    }
}
