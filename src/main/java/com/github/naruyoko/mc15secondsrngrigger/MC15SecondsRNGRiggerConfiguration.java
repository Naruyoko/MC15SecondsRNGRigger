package com.github.naruyoko.mc15secondsrngrigger;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.apache.commons.lang3.tuple.Pair;

import net.minecraft.client.Minecraft;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;

public class MC15SecondsRNGRiggerConfiguration {
    private static Minecraft mc=Minecraft.getMinecraft();
    
    public static void init() {
        loadHorseManipPath();
        loadEnderPearlManipList();
    }
    
    //Level 15
    //Horse manipulation
    public static List<Pair<Float,Vec3>> horseManipPath=horseManipDefaultPath();
    public static List<Pair<Float,Vec3>> horseManipDefaultPath() {
        ArrayList<Pair<Float,Vec3>> result=new ArrayList<Pair<Float,Vec3>>();
        result.add(new HorseManipTimingPoint(13,1700,11,32));
        return result;
    }
    private static final String HORSE_MANIP_PATH_FILE_NAME="horseManipPath.txt";
    private static File horseManipPathFile() {
        return new File(mc.mcDataDir,HORSE_MANIP_PATH_FILE_NAME);
    }
    public static String loadHorseManipPath() {
        File file=horseManipPathFile();
        if (file==null||!file.exists()) {
            String message="Path file not found";
            MC15SecondsRNGRiggerMod.logger.error("[Level 15] "+message);
            return message;
        }
        Scanner scanner;
        try {
            scanner=new Scanner(file);
        } catch (FileNotFoundException e) {
            String message="Path file not found";
            MC15SecondsRNGRiggerMod.logger.error("[Level 15] "+message);
            e.printStackTrace();
            return message;
        }
        ArrayList<Pair<Float,Vec3>> newPath=new ArrayList<Pair<Float,Vec3>>();
        boolean failure=false;
        int linei=0;
        while (scanner.hasNext()) {
            String line=scanner.nextLine();
            linei++;
            if (line.isEmpty()) continue;
            String[] args=line.split(",");
            if (args.length!=4) {
                String message="Failed to parse at line "+linei;
                MC15SecondsRNGRiggerMod.logger.error("[Level 15] "+message);
                scanner.close();
                return message;
            }
            try {
                float heightWhen=Float.parseFloat(args[0]);
                int destx=Integer.parseInt(args[1]);
                int desty=Integer.parseInt(args[2]);
                int destz=Integer.parseInt(args[3]);
                newPath.add(new HorseManipTimingPoint(heightWhen,destx,desty,destz));
            } catch (NumberFormatException e) {
                String message="Failed to parse at line "+linei;
                MC15SecondsRNGRiggerMod.logger.error("[Level 15] "+message);
                e.printStackTrace();
                scanner.close();
                return message;
            }
        }
        if (!failure) horseManipPath=newPath;
        scanner.close();
        return "";
    }
    
    //Level 16
    //Ender pearl manipulation
    public static final double ENDER_PEARL_MIN_VALUE=-0.999;
    public static final double ENDER_PEARL_MAX_VALUE=+0.999;
    public static List<Vec3> enderPearlManipList=enderPearlManipDefaultList();
    public static List<Vec3> enderPearlManipDefaultList() {
        ArrayList<Vec3> result=new ArrayList<Vec3>();
        result.add(new Vec3(ENDER_PEARL_MAX_VALUE,ENDER_PEARL_MAX_VALUE,ENDER_PEARL_MAX_VALUE));
        result.add(new Vec3(ENDER_PEARL_MIN_VALUE,ENDER_PEARL_MIN_VALUE,ENDER_PEARL_MIN_VALUE));
        result.add(new Vec3(0,0,0));
        return result;
    }
    private static final String ENDER_PEARL_MANIP_LIST_FILE_NAME="enderPearlManipList.txt";
    private static File enderPearlManipListFile() {
        return new File(mc.mcDataDir,ENDER_PEARL_MANIP_LIST_FILE_NAME);
    }
    public static String loadEnderPearlManipList() {
        File file=enderPearlManipListFile();
        if (file==null||!file.exists()) {
            String message="Path file not found";
            MC15SecondsRNGRiggerMod.logger.error("[Level 16] "+message);
            return message;
        }
        Scanner scanner;
        try {
            scanner=new Scanner(file);
        } catch (FileNotFoundException e) {
            String message="Path file not found";
            MC15SecondsRNGRiggerMod.logger.error("[Level 16] "+message);
            e.printStackTrace();
            return message;
        }
        ArrayList<Vec3> newList=new ArrayList<Vec3>(); 
        boolean failure=false;
        int linei=0;
        while (scanner.hasNext()) {
            String line=scanner.nextLine();
            linei++;
            if (line.isEmpty()) continue;
            String[] args=line.split(",");
            if (args.length!=3) {
                String message="Failed to parse at line "+linei;
                MC15SecondsRNGRiggerMod.logger.error("[Level 16] "+message);
                scanner.close();
                return message;
            }
            try {
                double x=MathHelper.clamp_double(Double.parseDouble(args[0]),ENDER_PEARL_MIN_VALUE,ENDER_PEARL_MAX_VALUE);
                double y=MathHelper.clamp_double(Double.parseDouble(args[1]),ENDER_PEARL_MIN_VALUE,ENDER_PEARL_MAX_VALUE);
                double z=MathHelper.clamp_double(Double.parseDouble(args[2]),ENDER_PEARL_MIN_VALUE,ENDER_PEARL_MAX_VALUE);
                newList.add(new Vec3(x,y,z));
            } catch (NumberFormatException e) {
                String message="Failed to parse at line "+linei;
                MC15SecondsRNGRiggerMod.logger.error("[Level 16] "+message);
                e.printStackTrace();
                scanner.close();
                return message;
            }
        }
        if (!failure) enderPearlManipList=newList;
        scanner.close();
        return "";
    }
}
