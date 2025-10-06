package Week1;

public class ConvolutionFilterTask {
    public static void main(String[] args) {
        String imagePathIn = args[0];
        String baseFilename = imagePathIn.substring(0, imagePathIn.lastIndexOf("."));

        Image originalImage = new Image();
        originalImage.ReadPGM(imagePathIn);

        Image newImage = new Image(originalImage.depth, originalImage.width, originalImage.height);

        for (int y = 0; y < originalImage.height; ++y) {
            for (int x = 0; x < originalImage.width; ++x) {
                float pixelIntensity = 0.0f;

                for (int k = -1; k < 2; ++k) {
                    int actualK = k;
                    if (k == -1 && y == 0) {
                        actualK = 1;
                    } else if (k == 1 && y == originalImage.height - 1) {
                        actualK = -1;
                    }
                    for (int j = -1; j < 2; ++j) {
                        int actualJ = j;
                        if (j == -1 && x == 0) {
                            actualJ = 1;
                        } else if (j == 1 && x == originalImage.width - 1) {
                            actualJ = -1;
                        }

                        pixelIntensity += (1.0f/9.0f) * originalImage.pixels[x + actualJ][y + actualK];
                    }
                }

                newImage.pixels[x][y] = (int)pixelIntensity;
            }
        }

        newImage.WritePGM(baseFilename + "_convoluted.pgm");
    }
}
