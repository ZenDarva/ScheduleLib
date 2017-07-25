package com.gmail.zendarva.scheduler;

import com.offbynull.coroutines.user.Continuation;
import com.offbynull.coroutines.user.Coroutine;

import java.util.UUID;

/**
 * Created by James on 7/24/2017.
 */
public abstract class ScheduledTask implements Coroutine {

    public abstract boolean isDone();
    public UUID id;
    protected Long sleepTil = -1l;
    protected boolean awaitingRequest = false;

    public ScheduledTask(){
        id = UUID.randomUUID();
    }

    protected void sleep(Continuation continuation, int ticks) {
        sleepTil = System.currentTimeMillis()+ (ticks*50);
        continuation.suspend();
    }

}
