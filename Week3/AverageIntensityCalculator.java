package Week3;

public class AverageIntensityCalculator {
    private Image originalImage;

    public AverageIntensityCalculator(Image originalImage) {
        this.originalImage = originalImage;
    }

    public float calculateMeanIntensity() {
        float numOfPixels = originalImage.width * originalImage.height;
        float rollingSum = 0.0f;

        for (int y = 0; y < originalImage.height; ++y) {
            for (int x = 0; x < originalImage.width; ++x) {
                rollingSum += originalImage.pixels[x][y];
            }
        }

        return rollingSum / numOfPixels;
    }
}
