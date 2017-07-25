package com.gmail.zendarva.scheduler;

import com.offbynull.coroutines.user.Continuation;
import com.offbynull.coroutines.user.Coroutine;
import net.minecraft.tileentity.TileEntity;

/**
 * Created by James on 7/24/2017.
 */
public abstract class ThreadedTileEntity extends TileEntity implements Coroutine {

    public abstract void offThreadUpdate();

    @Override
    public void run(Continuation continuation) throws Exception {
        continuation.suspend();
    }
}
