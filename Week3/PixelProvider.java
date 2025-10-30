package Week3;

// Version 1 of the pixel provider. Will expand this when I need other
// techniques of getting out-of-bound pixels

public class PixelProvider {
    private Image baseImage;
    private PixelStrategy defaultStrategy;

    public PixelProvider(Image image, PixelStrategy strategy) {
        baseImage = image;
        defaultStrategy = strategy;
    }

    public int getPixel(int x, int y) {
        return getPixelWithStrategy(x, y, defaultStrategy);
    }

    public int getPixelWithStrategy(int x, int y, PixelStrategy strategy) {
        if (x >= 0 && x < baseImage.width && y >= 0 && y < baseImage.height) {
            return baseImage.pixels[x][y];
        }

        if (strategy == PixelStrategy.USE_BLACK) {
            return 0;
        } else if (strategy == PixelStrategy.USE_WHITE) {
            return 255;
        }

        throw new UnsupportedOperationException("This PixelStrategy is not supported");
    }
}
