package com.gmail.zendarva.scheduler.requests.info;

import com.gmail.zendarva.scheduler.ScheduledTask;

import java.util.UUID;

/**
 * Created by James on 7/24/2017.
 */
public abstract class WorldInfoRequest {
    public int dimension;
    public UUID requestedBy;

    public WorldInfoRequest(ScheduledTask parent){
        requestedBy = parent.id;
    }

    public abstract void executeRequest();
}
