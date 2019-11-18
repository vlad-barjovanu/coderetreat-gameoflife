package com.vbarjovanu.coderetreat.gameoflife;

import com.vbarjovanu.coderetreat.gameoflife.world.*;

import java.io.IOException;
import java.text.NumberFormat;
import java.util.Random;

public class Main {
    public static void main(String[] args) throws InstantiationException, IllegalAccessException {
        World world;
        int count = 350;
        boolean shouldPrintWorld = true;
        world = initWorld(MatrixBasedWorld.class);
        playGame(world, count, shouldPrintWorld);
//        world = initWorld(ViewPortMatrixWorld.class);
//        playGame(world, count, shouldPrintWorld);
//        world = initWorld(ListBasedWorld.class);
//        playGame(world, count, shouldPrintWorld);
//        world = initWorld(MapBasedWorld.class);
//        playGame(world, count, shouldPrintWorld);
//        world = initWorld(GraphBasedWorld.class);
//        playGame(world, count, shouldPrintWorld);
    }

    private static void playGame(World world, int count, boolean shouldPrintWorld) {
        int index;
        long t1, t2;
        System.out.println("---------------------");
        System.out.printf("Playing game with world: %s\n", world.getClass().getSimpleName());
        t1 = System.nanoTime();
        index = 0;
//        if (shouldPrintWorld)
//            printWorld(world, index);
        for (; index < count; index++) {
            if (shouldPrintWorld)
                printWorld(world, index);
            world.nextGeneration();
            if (world.getAliveCells().size() == 0) {
                break;
            }
        }
        if (shouldPrintWorld)
            printWorld(world, index);
        t2 = System.nanoTime();

        System.out.printf("Results for playing game with world: %s: ", world.getClass().getSimpleName());
        System.out.printf("rounds: %d, time: %f\n", index, ((double) t2 - t1) / (1e+9f));
//        printMemoryConsumption();
    }

    private static void printMemoryConsumption() {
        Runtime runtime = Runtime.getRuntime();

        NumberFormat format = NumberFormat.getInstance();

        StringBuilder sb = new StringBuilder();
        long maxMemory = runtime.maxMemory();
        long allocatedMemory = runtime.totalMemory();
        long freeMemory = runtime.freeMemory();
        sb.append("free memory: ").append(format.format(freeMemory / 1024)).append("<br/>");
        sb.append("allocated memory: ").append(format.format(allocatedMemory / 1024)).append("<br/>");
        sb.append("max memory: ").append(format.format(maxMemory / 1024)).append("<br/>");
        sb.append("total free memory: ").append(format.format((freeMemory + (maxMemory - allocatedMemory)) / 1024)).append("<br/>");

        System.out.println(sb);
    }

    private static World initWorld(Class<? extends World> clazz) throws IllegalAccessException, InstantiationException {
        World world;
        world = clazz.newInstance();
        Random r = new Random(System.nanoTime());
        int cellsCount = r.nextInt(5000);
        for (int i = 0; i < cellsCount; i++) {
            int line = r.nextInt(30);
            int col = r.nextInt(30);
            world.setCellAlive(line, col, true);
        }
        return world;
    }

    private static void printWorld(World world, int index) {
//        for (int line = 0; line < world.getLinesCount(); line++) {
//            for (int col = 0; col < world.getColsCount(); col++) {
//                System.out.print((world.isCellAlive(line, col) ? "x" : ".") + " ");
//            }
//            System.out.println();
//        }
//        System.out.println("---");
        WorldToImage worldToImage = new WorldToImage();
        try {
            worldToImage.writeImage(world, String.format("/Users/vlad/GameOfLife/%s-%d.png", world.getClass().getSimpleName(), index));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
