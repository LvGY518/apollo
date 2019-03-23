package com.clancey.apollo.common.utils;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * @author ChenShuai
 * @date 2018/8/1 15:46
 */
public class ImageUtil {
    public static BufferedImage getImageCut(BufferedImage image, Rectangle rectangle) {
        return getImageCut(image, rectangle.x, rectangle.y, rectangle.width, rectangle.height);
    }

    public static BufferedImage getImageCut(BufferedImage image, int x, int y, int width, int height) {
        BufferedImage subImage = image.getSubimage(x, y, width, height);
        BufferedImage newImage = new BufferedImage(width, height, image.getType());
        Graphics2D graphics2D = newImage.createGraphics();
        graphics2D.drawImage(subImage, 0, 0, null);
        graphics2D.dispose();
        return newImage;
    }
}
