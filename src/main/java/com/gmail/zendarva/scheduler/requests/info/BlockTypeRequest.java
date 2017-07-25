package com.gmail.zendarva.scheduler.requests.info;

import com.gmail.zendarva.scheduler.ScheduledTask;
import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;

/**
 * Created by James on 7/24/2017.
 */
public class BlockTypeRequest extends WorldInfoRequest {
    public Block block;
    private BlockPos pos;
    public int metadata;

    public BlockTypeRequest(ScheduledTask parent, int x, int y, int z) {
        super(parent);
        pos = new BlockPos(x,y,z);
    }
    public BlockTypeRequest(ScheduledTask parent, BlockPos pos) {
        super(parent);
        this.pos = pos;
    }

    public void executeRequest() {
        World world = DimensionManager.getWorld(dimension);
        block = world.getBlockState(pos).getBlock();
        metadata = block.getMetaFromState(world.getBlockState(pos));
    }
}
