package Week3;

public class SeparateTwoTextSamples {
    public static void main(String[] args) {
        String fileName = args[0];
        String baseName = fileName.substring(0, fileName.lastIndexOf("."));

        Image originalImage = new Image();
        originalImage.ReadPGM(fileName);

        float avgIntensity = new AverageIntensityCalculator(originalImage).calculateMeanIntensity();
        Image thresholdedMask = new Image(originalImage.depth, originalImage.width, originalImage.height);
        for (int y = 0; y < originalImage.height; ++y) {
            for (int x = 0; x < originalImage.width; ++x) {
                thresholdedMask.pixels[x][y] = originalImage.pixels[x][y] > avgIntensity ? 255 : 0;
            }
        }

        // Create an x-directed image and y-directed image based on kernels
        PixelProvider pixelProvider = new PixelProvider(thresholdedMask, PixelStrategy.USE_WHITE);
        Image xDirectedImage = new Image(originalImage.depth, originalImage.width, originalImage.height);
        Image yDirectedImage = new Image(originalImage.depth, originalImage.width, originalImage.height);
        for (int y = 0; y < originalImage.height; ++y) {
            for (int x = 0; x < originalImage.width; ++x) {
                for (int j = x - 5; j < x + 5; ++j) {
                    xDirectedImage.pixels[x][y] += pixelProvider.getPixel(j, y);
                }
                for (int k = y - 5; k < y + 5; ++k) {
                    yDirectedImage.pixels[x][y] += pixelProvider.getPixel(x, k);
                }

                // Correct the pixel intensity to an inbound value
                xDirectedImage.pixels[x][y] = xDirectedImage.pixels[x][y] > 255 ? 255 : xDirectedImage.pixels[x][y];
                yDirectedImage.pixels[x][y] = yDirectedImage.pixels[x][y] > 255 ? 255 : yDirectedImage.pixels[x][y];
            }
        }

        xDirectedImage.WritePGM(baseName + "_firsttext.pgm");
        yDirectedImage.WritePGM(baseName + "_secondtext.pgm");
    }
}
