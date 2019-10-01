package qqfly;

import java.awt.image.BufferedImage;

public class GetImages {

    private BufferedImage image;

    public GetImages(BufferedImage image) {
        this.image = image;
    }

    public BufferedImage grabImage(int col, int row, int width, int height) {
        BufferedImage img = image.getSubimage((col * 32) - 32, (row * 32) - 32, width, height);
        return img;
    }

}
