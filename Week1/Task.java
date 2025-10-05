// Takes the input image and does the following:
// Rescale intensities by 0.4
// Rescale intensities by 1.4
// Rescale intensities by 1.4 and clip overflows
// Flip images upsidedown

package Week1;

import java.io.IOException;

public class Task {
    public static void main(String[] args) throws IOException {
        String fileNameIn = args[0];
        ImageIntensityScaler scaler = new ImageIntensityScaler(fileNameIn);

        // remove the extension from the file name, we will reappend it
        String baseFilename = fileNameIn.substring(0, fileNameIn.lastIndexOf("."));

        // Task 1
        String taskOneFileName = baseFilename + "_taskone.pgm";
        Image scaledByPointFour = scaler.scaleByFactor(0.4f);
        scaledByPointFour.WritePGM(taskOneFileName);

        // Task 2
        String taskTwoFileName = baseFilename + "_tasktwo.pgm";
        Image scaledByOnePointFour = scaler.scaleByFactor(1.4f);
        scaledByOnePointFour.WritePGM(taskTwoFileName);

        // Task 3
        String taskThreeFileName = baseFilename + "_taskthree.pgm";
        Image scaledByOnePointFourAndClipped = scaler.scaleByFactor(1.4f, true);
        scaledByOnePointFourAndClipped.WritePGM(taskThreeFileName);

        // Task 4
        String taskFourFileName = baseFilename + "_taskfour.pgm";
        Image originalImage = new Image();
        originalImage.ReadPGM(fileNameIn);
        Image newImage = new Image(originalImage.depth, originalImage.width, originalImage.height);
        
        // Go reverse from bottom row
        for (int y = 0; y < originalImage.height; ++y) {
            int fromBottomY = originalImage.height - y - 1;
            for (int x = 0; x < originalImage.width; ++x) {
                newImage.pixels[x][y] = originalImage.pixels[x][fromBottomY];
            }
        }

        newImage.WritePGM(taskFourFileName);
    }
}
