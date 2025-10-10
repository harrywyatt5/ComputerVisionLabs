package Week1;

public class MedianFilterTask {
    public static void main(String[] args) {
        String imageIn = args[0];
        String baseImageName = imageIn.substring(0, imageIn.lastIndexOf('.'));

        Image originalImage = new Image();
        originalImage.ReadPGM(imageIn);
        
        Image newImage = new Image(originalImage.depth, originalImage.width, originalImage.height);
        
        for (int y = 0; y < originalImage.height; ++y) {
            for (int x = 0; x < originalImage.width; ++x) {
                int[] intensityFrequency = new int[256];
                int outOfBoundPixels = 0;

                for (int k = -1; k < 2; ++k) {
                    if (y + k < 0 || y + k > originalImage.height - 1) {
                        ++outOfBoundPixels;
                        continue;
                    }

                    for (int j = -1; j < 2; ++j) {
                        if (x + j < 0 || x + j > originalImage.width - 1) {
                            ++outOfBoundPixels;
                            continue;
                        }

                        intensityFrequency[originalImage.pixels[x + j][y + k]] += 1;
                    }
                }

                float currentFrequency = 0.0f;
                for (int i = 0; i < 256; ++i) {
                    currentFrequency += (float)intensityFrequency[i] / (9.0f - (float)outOfBoundPixels);

                    if (currentFrequency >= 0.5f) {
                        newImage.pixels[x][y] = i;
                        break;
                    }
                }

                // For debugging - check if we even reached it
                if (currentFrequency < 0.5f) {
                    System.out.println("Never reached currentFrequency...");
                }
            }
        }

        newImage.WritePGM(baseImageName + "_median.pgm");
    }
}
