package com.github.bkassis.iconplus.image.util;

import com.github.bkassis.iconplus.image.ImageOverlayTask;

import java.io.File;

/**
 * Created by bassem on 21/04/2015.
 */
public class FileDrawableUtil {


    public static ImageOverlayTask.IconResolution fetchResolution(File inputFile) {
        String parentFolderName = inputFile.getParentFile().getName();
        ImageOverlayTask.IconResolution iconResolution = null;
        if (parentFolderName.startsWith("drawable-ldpi")) {
            iconResolution = ImageOverlayTask.IconResolution.LDPI;
        } else if (parentFolderName.startsWith("drawable-mdpi")) {
            iconResolution = ImageOverlayTask.IconResolution.MDPI;
        } else if (parentFolderName.startsWith("drawable-hdpi")) {
            iconResolution = ImageOverlayTask.IconResolution.HDPI;
        } else if (parentFolderName.contains("drawable-xhdpi")) {
            iconResolution = ImageOverlayTask.IconResolution.XHDPI;
        } else if (parentFolderName.contains("drawable-xxhdpi")) {
            iconResolution = ImageOverlayTask.IconResolution.XXHDPI;
        }

        return iconResolution;


    }
}
