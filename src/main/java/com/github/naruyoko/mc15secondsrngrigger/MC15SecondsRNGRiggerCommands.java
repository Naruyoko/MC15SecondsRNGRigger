package com.github.naruyoko.mc15secondsrngrigger;

import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;

public class MC15SecondsRNGRiggerCommands extends CommandBase {
    private static Minecraft mc=Minecraft.getMinecraft();
    @Override
    public void processCommand(ICommandSender sender,String[] args) throws CommandException {
        if (!(sender instanceof EntityPlayer)) return;
        if (!mc.isSingleplayer()) return;
        if (args==null||args.length==0) {
            sender.addChatMessage(new ChatComponentText(EnumChatFormatting.RED+"Too few arguments."));
        } else if (args[0].equals("loadHorsePath")) {
            String r=MC15SecondsRNGRiggerConfiguration.loadHorseManipPath();
            if (r.isEmpty()) sender.addChatMessage(new ChatComponentText(EnumChatFormatting.GREEN+"Horse path loaded successfully."));
            else sender.addChatMessage(new ChatComponentText(EnumChatFormatting.RED+"Error: "+r));
        } else if (args[0].equals("loadEnderPearlList")) {
            String r=MC15SecondsRNGRiggerConfiguration.loadEnderPearlManipList();
            if (r.isEmpty()) sender.addChatMessage(new ChatComponentText(EnumChatFormatting.GREEN+"Ender pearl manipulation list successfully."));
            else sender.addChatMessage(new ChatComponentText(EnumChatFormatting.RED+"Error: "+r));
        } else if (args[0].equals("resetEnderPearlCount")) {
            MC15SecondsRNGRiggerMod.resetEnderPearlCount();
        } else {
            sender.addChatMessage(new ChatComponentText(EnumChatFormatting.RED+"Unknown command"));
        }
    }
    @Override
    public List<String> addTabCompletionOptions(ICommandSender sender,String[] args,BlockPos pos) {
        if (args.length<1) return null;
        if (args.length<=2) return getListOfStringsMatchingLastWord(args,"loadHorsePath","loadEnderPearlList","resetEnderPearlCount");
        return null;
    }
    @Override
    public String getCommandName() {
        return MC15SecondsRNGRiggerMod.MODID;
    }
    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "command."+MC15SecondsRNGRiggerMod.MODID+".usage";
    }
}
