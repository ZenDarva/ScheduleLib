package com.gmail.zendarva.scheduler.requests.change;

import com.gmail.zendarva.scheduler.ScheduledTask;
import com.gmail.zendarva.scheduler.requests.info.WorldInfoRequest;
import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;

/**
 * Created by James on 7/24/2017.
 */
public class BlockChangeRequest extends WorldInfoRequest {
    private final BlockPos pos;
    private final Block block;
    private final int meta;

    public BlockChangeRequest(ScheduledTask parent, BlockPos pos, Block block, int meta) {
        super(parent);
        this.pos = pos;
        this.block = block;
        this.meta = meta;
    }

    @Override
    public void executeRequest() {
        World world = DimensionManager.getWorld(dimension);
        world.setBlockState(pos,block.getStateFromMeta(meta));
    }
}
