package com.gmail.zendarva.scheduler;

import com.example.examplemod.ExampleMod;
import com.gmail.zendarva.scheduler.requests.change.BlockChangeRequest;
import com.gmail.zendarva.scheduler.requests.info.BlockStateRequest;
import com.gmail.zendarva.scheduler.requests.info.BlockTypeRequest;
import com.offbynull.coroutines.user.Continuation;
import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;

/**
 * Created by James on 7/24/2017.
 */
public class ThreadedWorldAccess {
    private int dimension;
    private ScheduledTask task;
    public ThreadedWorldAccess(ScheduledTask task, int dimension) {
        this.dimension=dimension;
        this.task=task;
    }

    public BlockTypeRequest getBlockData(Continuation c, BlockPos pos) {
        BlockTypeRequest request = new BlockTypeRequest(task,pos);
        ExampleMod.onThreadScheduler.addRequest(request);
        task.awaitingRequest =true;
        c.suspend();
        return request;
    }

    public void setBlock(Continuation c, BlockPos pos, Block block, int meta){
        BlockChangeRequest request = new BlockChangeRequest(task,pos,block,meta);
        ExampleMod.onThreadScheduler.addRequest(request);
        task.awaitingRequest = true;
        c.suspend();
    }

    public BlockStateRequest getBlockState (Continuation c, BlockPos pos){
        BlockStateRequest request = new BlockStateRequest(task,pos);
        ExampleMod.onThreadScheduler.addRequest(request);
        task.awaitingRequest=true;
        c.suspend();
        return request;
    }

}
