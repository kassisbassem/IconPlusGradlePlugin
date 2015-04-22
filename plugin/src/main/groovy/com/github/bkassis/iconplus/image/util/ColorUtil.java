package com.github.bkassis.iconplus.image.util;

import java.awt.Color;
import java.lang.reflect.Field;

/**
 * Created by bassem on 21/04/2015.
 */
public class ColorUtil {

    public static Color stringToColor(String value) {
        if (value == null) {
            return Color.black;
        }
        try {
            // get color by hex or octal value
            return Color.decode(value);
        } catch (NumberFormatException nfe) {
            // if we can't decode lets try to get it by name
            try {
                // try to get a color by name using reflection
                final Field f = Color.class.getField(value);

                return (Color) f.get(null);
            } catch (Exception ce) {
                // if we can't get any color return black
                return Color.black;
            }
        }
    }
}
