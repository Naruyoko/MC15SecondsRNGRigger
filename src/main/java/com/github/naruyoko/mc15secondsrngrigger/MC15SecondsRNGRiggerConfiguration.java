package com.github.naruyoko.mc15secondsrngrigger;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.apache.commons.lang3.tuple.Pair;

import net.minecraft.client.Minecraft;
import net.minecraft.util.Vec3;

public class MC15SecondsRNGRiggerConfiguration {
    private static Minecraft mc=Minecraft.getMinecraft();
    
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
        if (file!=null) {
            try {
                Scanner scanner=new Scanner(file);
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
                        System.out.println("[15 Seconds RNG Rigger] [Level 15] "+message);
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
                        System.out.println("[15 Seconds RNG Rigger] [Level 15] "+message);
                        e.printStackTrace();
                        scanner.close();
                        return message;
                    }
                }
                if (!failure) horseManipPath=newPath;
                scanner.close();
            } catch (FileNotFoundException e) {
                String message="Path file not found";
                System.out.println("[15 Seconds RNG Rigger] [Level 15] "+message);
                e.printStackTrace();
                return message;
            }
        }
        return "";
    }
}
