package Week3;

public class ThresholdBasedOnMean {
    public static void main(String[] args) {
        String fileName = args[0];
        String baseFileName = fileName.substring(0, fileName.lastIndexOf("."));

        Image originalImage = new Image();
        originalImage.ReadPGM(fileName);

        // Find the average intensity
        AverageIntensityCalculator calculator = new AverageIntensityCalculator(originalImage);
        float avgIntensity = calculator.calculateMeanIntensity();

        // Make new image and save it
        Image newImage = new Image(originalImage.depth, originalImage.width, originalImage.height);
        for (int y = 0; y < originalImage.height; ++y) {
            for (int x = 0; x < originalImage.width; ++x) {
                newImage.pixels[x][y] = originalImage.pixels[x][y] > avgIntensity ? 255 : 0;
            }
        }

        newImage.WritePGM(baseFileName + "_mask.pgm");
    }
}
