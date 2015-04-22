package com.github.bkassis.iconplus.image;

import com.github.bkassis.iconplus.image.LauncherIconAttribute;
import com.github.bkassis.iconplus.image.util.ColorUtil;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.font.TextAttribute;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.AttributedString;

import javax.imageio.ImageIO;

/**
 * Created by bassem on 21/04/2015.
 */
public class ImageOverlayTask {


    private File mImageFile;

    public enum IconResolution {LDPI, MDPI, HDPI, XHDPI, XXHDPI}


    public ImageOverlayTask(File imageFile) {
        mImageFile = imageFile;
    }


    public void executeIconLauncherOverlay(IconResolution iconResolution, LauncherIconAttribute launcherIconAttribute) throws IOException {
        BufferedImage inputFile = ImageIO.read(mImageFile);

        int x = getCoordinate(DefaultXHDPILauncher.X, iconResolution);
        int y = getCoordinate(DefaultXHDPILauncher.Y, iconResolution);
        int width = getCoordinate(DefaultXHDPILauncher.WIDTH, iconResolution);
        int height = getCoordinate(DefaultXHDPILauncher.HEIGHT, iconResolution);

        int xText = getCoordinate(DefaultXHDPILauncher.X_TEXT, iconResolution);
        int yText = getCoordinate(DefaultXHDPILauncher.Y_TEXT, iconResolution);
        int fontSizeText = getCoordinate(DefaultXHDPILauncher.FONT_SIZE_TEXT, iconResolution);
        Graphics2D g2d = inputFile.createGraphics();

        Font serifFont = new Font("Serif", Font.BOLD, fontSizeText);


        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.rotate(Math.toRadians(-45));


        AlphaComposite alphaComposite = AlphaComposite.getInstance(
                AlphaComposite.SRC_OVER, 0.75f);
        g2d.setComposite(alphaComposite);

        Color backgroundColor = ColorUtil.stringToColor(launcherIconAttribute.backgroundColor);
        g2d.setColor(backgroundColor);
        g2d.fillRect(x, y, width, height);
        AttributedString attributedString = new AttributedString(launcherIconAttribute.text);
        attributedString.addAttribute(TextAttribute.FOREGROUND, ColorUtil.stringToColor(launcherIconAttribute.textColor));
        attributedString.addAttribute(TextAttribute.FONT, serifFont);
        g2d.drawString(attributedString.getIterator(), xText, yText);
        g2d.dispose();
        ImageIO.write(inputFile, "png", mImageFile);
    }


    private int getCoordinate(int value, IconResolution iconResolution) {
        int coordinate = 0;

        switch (iconResolution) {
            case LDPI: {
                coordinate = (int) (value / ((1.5) * (1.5) * (1.5)));
            }
            break;
            case MDPI: {
                coordinate = (int) (value / ((1.5) * (1.5)));
            }
            break;
            case HDPI: {
                coordinate = (int) (value / (1.5));
            }
            break;
            case XHDPI: {
                coordinate = value;
            }
            break;
            case XXHDPI: {
                coordinate = (int) (value * (1.5));
            }
            break;

        }
        return coordinate;
    }


    class DefaultXHDPILauncher {
        public static final int X = -65;
        public static final int Y = 30;
        public static final int WIDTH = 150;
        public static final int HEIGHT = 30;

        public static final int X_TEXT = -30;
        public static final int Y_TEXT = 50;
        public static final int FONT_SIZE_TEXT = 16;
    }
}
