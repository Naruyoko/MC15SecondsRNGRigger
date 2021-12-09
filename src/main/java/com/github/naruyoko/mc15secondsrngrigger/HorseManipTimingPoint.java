package com.github.naruyoko.mc15secondsrngrigger;

import org.apache.commons.lang3.tuple.Pair;

import net.minecraft.util.Vec3;

public class HorseManipTimingPoint extends Pair<Float,Vec3> {
    /**
     * 
     */
    private static final long serialVersionUID = -1392911111550169401L;
    private final Float heightWhen;
    private final Vec3 dest;
    public HorseManipTimingPoint(float heightWhen,int destx,int desty,int destz) {
        this.heightWhen=heightWhen;
        this.dest=new Vec3(destx,desty,destz);
    }
    public HorseManipTimingPoint(float heightWhen,Vec3 dest) {
        this(heightWhen,(int)dest.xCoord,(int)dest.yCoord,(int)dest.zCoord);
    }
    @Override
    public Vec3 setValue(Vec3 value) {
        return null;
    }
    @Override
    public Float getLeft() {
        return heightWhen;
    }
    @Override
    public Vec3 getRight() {
        return dest;
    }
}
