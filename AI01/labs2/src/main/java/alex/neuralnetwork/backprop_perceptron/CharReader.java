package alex.neuralnetwork.backprop_perceptron;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;

public class CharReader {
    public static final String IMAGE_PATH_TEMPLATE = "ocr-data/%c.jpg";

    public byte[][] readImage(char readChar, boolean echo) {
        BufferedImage image = loadCharImage(readChar);
        PrintStream echoStream = echo ? System.out: new PrintStream(OutputStream.nullOutputStream());

        if (image != null) {
            byte[][] pixels = new byte[image.getWidth()][];
            for(int i=0;i<pixels.length;i++){
                pixels[i]=new byte[image.getHeight()];
            }
            echoStream.print("new int[] {\n");
            for (int Y = 0; Y < image.getWidth(); Y++) {
                for (int x = 0; x < image.getHeight(); x++) {
                    var argb=image.getRGB( x,Y);
                    pixels[x][Y] = (byte) (isWhite(argb) ? 0 : 1);
                    echoStream.print(pixels[x][Y]);//+",");
                }
                echoStream.println();
            }
            echoStream.print("}\n");
            return pixels;
        }
        throw new IllegalArgumentException("Cannot load char: " + readChar);
    }

    private boolean isWhite(int argb) {
        // Components will be in the range of 0..255:
        int blue = argb & 0xff;
        int green = (argb & 0xff00) >> 8;
        int red = (argb & 0xff0000) >> 16;

        return (blue+green+red)>255*3/2;
    }

    private BufferedImage loadCharImage(char readChar) {
        String charPath = String.format(IMAGE_PATH_TEMPLATE, readChar);
        try (InputStream is = this.getClass().getClassLoader().getResourceAsStream(charPath)) {
            return ImageIO.read(is);
        } catch (IOException e) {
            return null;
        }

    }

}

