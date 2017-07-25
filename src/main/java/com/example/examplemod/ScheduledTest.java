package com.example.examplemod;

import com.gmail.zendarva.scheduler.ScheduledTask;
import com.gmail.zendarva.scheduler.ThreadedWorldAccess;
import com.gmail.zendarva.scheduler.requests.info.BlockTypeRequest;
import com.offbynull.coroutines.user.Continuation;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;

/**
 * Created by James on 7/24/2017.
 */
public class ScheduledTest extends ScheduledTask {

    @Override
    public boolean isDone() {
        return false;
    }

    @Override
    public void run(Continuation continuation) throws Exception {
        ThreadedWorldAccess twa = new ThreadedWorldAccess(this,0);
        BlockPos target = new BlockPos(0,65,0);
        BlockTypeRequest request = twa.getBlockData(continuation,target);
        continuation.suspend();
        if (request.block != Blocks.STONE){
            twa.setBlock(continuation,target,Blocks.STONE,0);
        }
    }
}
