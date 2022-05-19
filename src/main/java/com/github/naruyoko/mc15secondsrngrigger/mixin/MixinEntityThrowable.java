package com.github.naruyoko.mc15secondsrngrigger.mixin;

import java.util.Random;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import com.github.naruyoko.mc15secondsrngrigger.MC15SecondsRNGRiggerConfiguration;
import com.github.naruyoko.mc15secondsrngrigger.MC15SecondsRNGRiggerMod;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IProjectile;
import net.minecraft.entity.item.EntityEnderPearl;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

@Mixin(EntityThrowable.class)
public abstract class MixinEntityThrowable extends Entity implements IProjectile {
    public MixinEntityThrowable(World worldIn) {
        super(worldIn);
        this.setSize(1,1);
    }
    @Shadow private EntityLivingBase thrower;
    @Redirect(method="setThrowableHeading",at=@At(value="INVOKE",target="Ljava/util/Random;nextGaussian()D"),require=3)
    public double overrideRandomVelocity(Random rand) {
        //Level 16
        double r=rand.nextGaussian(); //Original value, digested regardless
        int c=MC15SecondsRNGRiggerMod.enderPearlCoordCount;
        int n=MC15SecondsRNGRiggerMod.enderPearlCount;
        MC15SecondsRNGRiggerMod.logger.info("[Level 16] Checking thrown item:");
        MC15SecondsRNGRiggerMod.logger.info(String.format("[Level 16]] %s %s",this,this.thrower));
        if ((Entity)this instanceof EntityEnderPearl&&this.thrower instanceof EntityPlayerMP&&n<MC15SecondsRNGRiggerConfiguration.enderPearlManipList.size()) {
            MC15SecondsRNGRiggerMod.logger.info("[Level 16] Overriding random velocity "+(c+1)+" for throw "+(n+1));
            if (c<0||c>=MC15SecondsRNGRiggerMod.COORDINATE_PER_THROW) MC15SecondsRNGRiggerMod.logger.error("[Level 16] Error: Attempted to modify coordinate "+(c+1)+", which does not exist");
            Vec3 vec=MC15SecondsRNGRiggerConfiguration.enderPearlManipList.get(n);
            double v=c==0?vec.xCoord:c==1?vec.yCoord:c==2?vec.zCoord:Double.NaN;
            if (!Double.isNaN(v)) r=v;
            MC15SecondsRNGRiggerMod.enderPearlCoordCount++;
            if (MC15SecondsRNGRiggerMod.enderPearlCoordCount>=MC15SecondsRNGRiggerMod.COORDINATE_PER_THROW) {
                MC15SecondsRNGRiggerMod.enderPearlCoordCount=0;
                MC15SecondsRNGRiggerMod.enderPearlCount++;
            }
        }
        return r;
    }
}
