package com.github.naruyoko.mc15secondsrngrigger;

import net.minecraft.client.Minecraft;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;

public class MC15SecondsRNGRiggerEventHandler {
    @SuppressWarnings("unused")
    private static Minecraft mc=Minecraft.getMinecraft();
    @SubscribeEvent
    public void onKeyPress(InputEvent.KeyInputEvent event) {
        MC15SecondsRNGRiggerMod.keybinds.onKeyPress(event);
    }
    @SubscribeEvent
    public void onWorldLoad(WorldEvent.Load event) {
        MC15SecondsRNGRiggerMod.resetEnderPearlCount();
    }
    @SubscribeEvent
    public void onWorldUnload(WorldEvent.Unload event) {
        MC15SecondsRNGRiggerMod.onWorldUnload(event);
    }
}
