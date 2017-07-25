package com.gmail.zendarva.scheduler.requests.info;

import com.gmail.zendarva.scheduler.ScheduledTask;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;

/**
 * Created by James on 7/24/2017.
 */
public class BlockStateRequest extends WorldInfoRequest {
    private final BlockPos pos;
    public IBlockState state;

    public BlockStateRequest(ScheduledTask parent, BlockPos pos) {
        super(parent);
        this.pos = pos;
    }

    @Override
    public void executeRequest() {
        World world = DimensionManager.getWorld(dimension);
        state = world.getBlockState(pos);
    }
}
