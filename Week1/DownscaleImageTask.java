package Week1;

public class DownscaleImageTask {
    public static void main(String[] args) {
        String fileName = args[0];
        String baseFilename = fileName.substring(0, fileName.lastIndexOf("."));

        Image originalImage = new Image();
        originalImage.ReadPGM(fileName);

        int newWidth = originalImage.width / 8;
        int newHeight = originalImage.height / 8;

        Image sampledImage = new Image(originalImage.depth, newWidth, newHeight);
        for (int y = 0; y < newHeight; y++) {
            for (int x = 0; x < newWidth; x++) {
                sampledImage.pixels[x][y] = originalImage.pixels[x*8][y*8];
            }
        }

        sampledImage.WritePGM(baseFilename + "_subsampled.pgm");
    }
}
