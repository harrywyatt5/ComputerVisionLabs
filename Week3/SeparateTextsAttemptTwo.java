package Week3;

// For this attempt (as my previous one did not work)
// we are going to try the method which was outlined in the answers
// 1. Generate histogram
// 2. Smooth histogram with a box filter
// 3. Perform non-maximal suppression on the histogram
// 4. Get three biggest peaks in histogram
// 5. Create first image based on midpoint between highest value and second highest
// 6. Create second image based on midpoint between second highest and third highest

public class SeparateTextsAttemptTwo {
    public static void main(String[] args) {
        String fileName = args[0];
        String baseFileName = fileName.substring(0, fileName.lastIndexOf("."));

        Image originalImage = new Image();
        originalImage.ReadPGM(fileName);

        int[] histogram = new int[256];
        for (int y = 0; y < originalImage.height; ++y) {
            for (int x = 0; x < originalImage.width; ++x) {
                ++histogram[originalImage.pixels[x][y]];
            }
        }

        // Smooth the histogram :)
        float[] smoothedHistogram = new float[256];
        for (int i = 0; i < 256; ++i) {
            if (i == 0) {
                smoothedHistogram[0] = (histogram[0] + histogram[1]) / 2.f;
            } else if (i == 255) {
                smoothedHistogram[255] = (histogram[255] + histogram[254]) / 2.f;
            } else {
                smoothedHistogram[i] = (histogram[i - 1] + histogram[i] + histogram[i + 1]) / 3.f;
            }
        }

        // Non maximal suppression
        for (int i = 5; i <= 250; i += 5) {
            Peak tallestPeak = new Peak(0, 0.0f);
            for (int j = -5; j <= 5; ++j) {
                if (tallestPeak.getValue() < smoothedHistogram[i + j]) {
                    tallestPeak.setValue(smoothedHistogram[i+j]);
                    tallestPeak.setIndex(j);
                }
            }

            for (int j = -5; j <= 5; ++j) {
                if (j != tallestPeak.getIndex()) {
                    smoothedHistogram[i + j] = 0.0f;
                }
            }
        }

        // Get three biggest peaks
        Peak[] biggestPeaks = {
            new Peak(-1, 0.0f),
            new Peak(-1, 0.0f),
            new Peak(-1, 0.0f)
        };
        for (int i = 0; i < 256; ++i) {
            Peak currentValue = new Peak(i, smoothedHistogram[i]);

            for (int j = 0; j < 3; ++j) {
                if (currentValue.getValue() > biggestPeaks[j].getValue()) {
                    Peak temp = biggestPeaks[j];
                    biggestPeaks[j] = currentValue;
                    currentValue = temp;
                }
            }
        }

        int midpoint1 = (biggestPeaks[0].getIndex() + biggestPeaks[1].getIndex()) / 2; 
        int midpoint2 = (biggestPeaks[1].getIndex() + biggestPeaks[2].getIndex()) / 2;

        // Make split images
        Image firstText = new Image(originalImage.depth, originalImage.width, originalImage.height);
        Image secondText = new Image(originalImage.depth, originalImage.width, originalImage.height);
        for (int y = 0; y < originalImage.height; ++y) {
            for (int x = 0; x < originalImage.width; ++x) {
                if (originalImage.pixels[x][y] > midpoint1) {
                    firstText.pixels[x][y] = originalImage.pixels[x][y];
                    secondText.pixels[x][y] = 255;
                } else if (originalImage.pixels[x][y] > midpoint2) {
                    firstText.pixels[x][y] = 255;
                    secondText.pixels[x][y] = originalImage.pixels[x][y];
                } else {
                    firstText.pixels[x][y] = 255;
                    secondText.pixels[x][y] = 255;
                }
            }
        }

        firstText.WritePGM(baseFileName + "_firsttext.pgm");
        secondText.WritePGM(baseFileName + "_secondtext.pgm");
    }
}
