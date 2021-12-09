package com.github.naruyoko.mc15secondsrngrigger;

import org.lwjgl.input.Keyboard;

import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.gameevent.InputEvent;

public class MC15SecondsRNGRiggerKeybinds {
    Minecraft mc=Minecraft.getMinecraft();
    @SuppressWarnings("unused")
    public void onKeyPress(InputEvent.KeyInputEvent event) {
        boolean isLShiftDown=Keyboard.isKeyDown(Keyboard.KEY_LSHIFT);
        boolean isLCtrlDown=Keyboard.isKeyDown(Keyboard.KEY_LCONTROL);
        boolean isLMenuDown=Keyboard.isKeyDown(Keyboard.KEY_LMENU);
        byte modifiers=(byte)((isLShiftDown?1:0)|(isLCtrlDown?2:0)|(isLMenuDown?4:0));
    }
}
