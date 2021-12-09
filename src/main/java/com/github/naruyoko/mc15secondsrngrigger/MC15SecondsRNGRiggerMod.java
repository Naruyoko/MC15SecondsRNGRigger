package com.github.naruyoko.mc15secondsrngrigger;

import org.apache.logging.log4j.Logger;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;

@Mod(modid=MC15SecondsRNGRiggerMod.MODID,name=MC15SecondsRNGRiggerMod.NAME,version=MC15SecondsRNGRiggerMod.VERSION)
public class MC15SecondsRNGRiggerMod {
    public static final String MODID="mc15secondsrngrigger";
    public static final String NAME="RNG Rigger for 15 Seconds";
    public static final String VERSION="${version}";
    public static final String MCVERSION="${mcversion}";
    private static Minecraft mc=Minecraft.getMinecraft();
    public static Logger logger=null;
    public static MC15SecondsRNGRiggerKeybinds keybinds;
    public static MC15SecondsRNGRiggerEventHandler eventHandler;
    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        logger=event.getModLog();
    }
    @EventHandler
    public void init(FMLServerStartingEvent event)
    {
        keybinds=new MC15SecondsRNGRiggerKeybinds();
        eventHandler=new MC15SecondsRNGRiggerEventHandler();
        MinecraftForge.EVENT_BUS.register(eventHandler);
        event.registerServerCommand(new MC15SecondsRNGRiggerCommands());
    }
    public static void onWorldUnload(WorldEvent.Unload event) {
        MinecraftForge.EVENT_BUS.unregister(eventHandler);
    }
    public static void outputErrorToChatAndLog(String message) {
        mc.thePlayer.addChatMessage(new ChatComponentText(EnumChatFormatting.RED+message));
        logger.error(message);
    }
}
