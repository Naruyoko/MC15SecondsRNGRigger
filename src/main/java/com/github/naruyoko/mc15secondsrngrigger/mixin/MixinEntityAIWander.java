package com.github.naruyoko.mc15secondsrngrigger.mixin;

import java.util.List;

import org.apache.commons.lang3.tuple.Pair;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.github.naruyoko.mc15secondsrngrigger.MC15SecondsRNGRiggerConfiguration;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;

@Mixin(EntityAIWander.class)
public abstract class MixinEntityAIWander extends EntityAIBase {
    @Shadow private EntityCreature entity;
    @Shadow private double xPosition;
    @Shadow private double yPosition;
    @Shadow private double zPosition;
    @Shadow private boolean mustUpdate;
    private Minecraft mc=Minecraft.getMinecraft();
    @Inject(method="shouldExecute",at=@At("HEAD"),cancellable=true)
    public void moveEvent(CallbackInfoReturnable<Boolean> ci) {
        if (!this.mustUpdate&&this.entity.getAge()>=100) {
            ci.setReturnValue(false);
            ci.cancel();
        }
        EntityPlayerSP player=mc.thePlayer;
        //Level 15
        if (entity instanceof EntityHorse&&1697<entity.posX&&entity.posX<1703&&player!=null&&1697<player.posX&&player.posX<1703) {
            EntityHorse horse=(EntityHorse)entity;
            boolean found=false;
            if (horse.riddenByEntity==null) {
                double playerY=player.posY;
                System.out.println("[15 Seconds RNG Rigger] [Level 15] Reading player Y: "+playerY);
                List<Pair<Float,Vec3>> path=MC15SecondsRNGRiggerConfiguration.horseManipPath;
                for (int i=0;i<path.size();i++) {
                    Pair<Float,Vec3> vertex=path.get(i);
                    if (MathHelper.epsilonEquals((float)playerY,vertex.getLeft())) {
                        System.out.println("[15 Seconds RNG Rigger] [Level 15] Walking horse by vertex "+(i+1));
                        this.xPosition=(int)vertex.getRight().xCoord;
                        this.yPosition=(int)vertex.getRight().yCoord;
                        this.zPosition=(int)vertex.getRight().zCoord;
                        this.mustUpdate=false;
                        found=true;
                        break;
                    }
                }
            }
            ci.setReturnValue(found);
            ci.cancel();
        }
    }
}
