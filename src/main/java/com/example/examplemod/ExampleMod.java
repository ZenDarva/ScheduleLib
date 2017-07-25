package com.example.examplemod;

import com.gmail.zendarva.scheduler.OffThreadScheduler;
import com.gmail.zendarva.scheduler.OnThreadScheduler;
import com.gmail.zendarva.scheduler.ScheduledTask;
import com.offbynull.coroutines.user.CoroutineRunner;
import net.minecraft.init.Blocks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.ArrayList;
import java.util.List;

@Mod(modid = ExampleMod.MODID, version = ExampleMod.VERSION)
public class ExampleMod
{
    public static final String MODID = "examplemod";
    public static final String VERSION = "1.0";

    public static OffThreadScheduler offThreadScheduler;
    public static OnThreadScheduler onThreadScheduler;

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        // some example code
        onThreadScheduler = new OnThreadScheduler();
        MinecraftForge.EVENT_BUS.register(onThreadScheduler);
        offThreadScheduler = new OffThreadScheduler();
        offThreadScheduler.start();

    }

    @EventHandler
    public void onWorldLoad(FMLServerStartedEvent event) {
        offThreadScheduler.addTask(new ScheduledTest());
    }
}
