package com.vbarjovanu.coderetreat.gameoflife;

import com.vbarjovanu.coderetreat.gameoflife.world.World;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;

class WorldToImage {

    void writeImage(World world, String filepath) throws IOException {
        //noinspection PointlessBitwiseExpression
        int pLive = (128 << 24) | (0 << 16) | (255 << 8) | 0;
        //noinspection PointlessBitwiseExpression
        int pDead = (128 << 24) | (255 << 16) | (0 << 8) | 0;
        int pBorder = (128 << 24) | (50 << 16) | (50 << 8) | 50;
        byte[] byteArray;
        int colsCount = world.getColsCount();
        int linesCount = world.getLinesCount();
        int imageH, imageW;
        int liveCellsCount = 0;
        PixelSize pixelSize = this.computePixelSize(world);
        imageH = pixelSize.height * linesCount;
        imageW = pixelSize.width * colsCount;

        BufferedImage img = new BufferedImage(imageW, imageH, BufferedImage.TYPE_INT_ARGB);

        for (int line = 0; line < linesCount; line++) {
            for (int col = 0; col < colsCount; col++) {
                boolean cellIsAlive = world.isCellAlive(line, col);
                if (cellIsAlive)
                    liveCellsCount++;

                for (int ph = 0; ph < pixelSize.height; ph++) {
                    for (int pw = 0; pw < pixelSize.width; pw++) {
                        int pixel = (cellIsAlive ? pLive : pDead);
                        if (pixelSize.isBorderWidth(pw) || pixelSize.isBorderHeight(ph)) {
                            pixel = pBorder;
                        }
                        img.setRGB(col * pixelSize.width + pw, line * pixelSize.height + ph, pixel);
                    }
                }
            }
        }
        Graphics2D graphics = img.createGraphics();
        graphics.setPaint(Color.black);
        graphics.setFont(new Font("Serif", Font.BOLD, 60));
        int totalCellsCount = linesCount * colsCount;
        NumberFormat nf = new DecimalFormat();
        nf.setGroupingUsed(true);

        graphics.drawString(String.format("Cells live / total %s / %s", nf.format(liveCellsCount), nf.format(totalCellsCount)), 0, graphics.getFontMetrics().getHeight());
        graphics.dispose();
        ImageIO.write(img, "png", new File(filepath));
    }

    private PixelSize computePixelSize(World world) {

        int h = 1024 / world.getLinesCount();
        int w = 1024 / world.getColsCount();
        return new PixelSize(h, w);
    }

    private static class PixelSize {
        private final int height, width;
        private final int borderHeight, borderWidth;

        PixelSize(int height, int width) {
            this.height = height;
            this.width = width;
            if (height < 10)
                this.borderHeight = -1;//cancel border
            else
                this.borderHeight = height / 10;
            if (width < 10)
                this.borderWidth = -1;//cancel border
            else
                this.borderWidth = width / 10;
        }

        public int getHeight() {
            return height;
        }

        public int getWidth() {
            return width;
        }

        boolean isBorderWidth(int w) {
            return (w <= borderWidth) || (w >= this.width - borderWidth);
        }

        boolean isBorderHeight(int h) {
            return (h <= borderHeight) || (h >= this.height - borderHeight);
        }
    }
}
