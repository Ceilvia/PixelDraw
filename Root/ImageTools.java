package Root;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

public class ImageTools {
    public static BufferedImage pixelate(BufferedImage imageToPixelate, int pixelSize) {
        BufferedImage pixelateImage = new BufferedImage(
                imageToPixelate.getWidth(),
                imageToPixelate.getHeight(),
                imageToPixelate.getType());
        System.out.println("Width is: " + imageToPixelate.getWidth());
        for (int h = 0; h < imageToPixelate.getHeight(); h += pixelSize) {
            for (int w = 0; w < imageToPixelate.getWidth(); w += pixelSize) {
                BufferedImage croppedImage = getCroppedImage(imageToPixelate, w, h, pixelSize);
                Color dominantColor = getDominantColor(croppedImage);
                for (int yd = h; (yd < h + pixelSize) && (yd < pixelateImage.getHeight()); yd++) {
                    for (int xd = w; (xd < w + pixelSize) && (xd < pixelateImage.getWidth()); xd++) {
                        pixelateImage.setRGB(xd, yd, dominantColor.getRGB());
                    }
                }
            }
        }

        return pixelateImage;
    }
    /*
        Get the pixels that will be combined into a single pixel
        @param:
     */
    public static BufferedImage getCroppedImage(BufferedImage image, int startx, int starty, int pixsize) {
        // In case startx,y go out of range
        if (startx < 0) startx = 0;
        if (starty < 0) starty = 0;
        if (startx > image.getWidth()) startx = image.getWidth();
        if (starty > image.getHeight()) starty = image.getHeight();
        int width = pixsize;
        if (startx + width > image.getWidth()) width = image.getWidth() - startx;
        if (starty + pixsize > image.getHeight()) pixsize = image.getHeight() - starty;
        return image.getSubimage(startx, starty, width, pixsize);
    }

    public static Color getDominantColor(BufferedImage image) {
        int sumR = 0, sumB = 0, sumG = 0;
        int sum2 = 0;
        int color = 0;
        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                color = image.getRGB(x, y);
                Color c = new Color(color);
                sumR += c.getRed();
                sumB += c.getBlue();
                sumG += c.getGreen();
                sum2++;
            }
        }
        return new Color(sumR/sum2, sumG/sum2, sumB/sum2);
        /*
        Map<Integer, Integer> colorCounter = new HashMap<>(100);
        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                int currentRGB = image.getRGB(x, y);
                int count = colorCounter.getOrDefault(currentRGB, 0);
                colorCounter.put(currentRGB, count + 1);
            }
        }
        return getDominantColor(colorCounter);

         */
    }

    private static Color getDominantColor(Map<Integer, Integer> colorCounter) {
        int dominantRGB = colorCounter.entrySet().stream()
                .max((entry1, entry2) -> entry1.getValue() > entry2.getValue() ? 1 : -1)
                .get()
                .getKey();
        return new Color(dominantRGB);
    }
}
