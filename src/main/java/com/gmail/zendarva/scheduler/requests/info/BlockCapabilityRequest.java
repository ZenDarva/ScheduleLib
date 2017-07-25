package com.gmail.zendarva.scheduler.requests.info;

import com.gmail.zendarva.scheduler.ScheduledTask;
import com.gmail.zendarva.scheduler.capabilities.ThreadedCapability;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.capabilities.Capability;

/**
 * Created by James on 7/24/2017.
 */
public class BlockCapabilityRequest extends WorldInfoRequest{
    private final BlockPos pos;
    private final Capability<?> capability;
    private final EnumFacing facing;

    public BlockCapabilityRequest(ScheduledTask parent, BlockPos pos, Capability<?> capability, EnumFacing facing) {
        super(parent);
        this.pos = pos;
        this.capability = capability;
        this.facing = facing;
    }

    @Override
    public void executeRequest() {
        World world = DimensionManager.getWorld(dimension);
        world.getCapability(capability,facing);
    }

    public Capability<?> getCapability(){
        if (!(capability instanceof ThreadedCapability))
            throw new RuntimeException("Attempt to use a non-threaded capability offthread.");
        return capability;
    }
}
