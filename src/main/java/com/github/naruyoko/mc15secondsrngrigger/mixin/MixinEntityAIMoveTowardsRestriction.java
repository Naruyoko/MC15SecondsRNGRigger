package com.github.naruyoko.mc15secondsrngrigger.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.passive.EntityHorse;

@Mixin(EntityAIMoveTowardsRestriction.class)
public abstract class MixinEntityAIMoveTowardsRestriction extends EntityAIBase {
    @Shadow private EntityCreature theEntity;
    @Inject(method="startExecuting",cancellable=true,at=@At("HEAD"))
    public void calcelAIForHorse(CallbackInfo ci) {
        //Level 15
        if (this.theEntity instanceof EntityHorse&&1697<this.theEntity.posX&&this.theEntity.posX<1703) {
            ci.cancel();
        }
    }
}
