package Week1;

public class ImageIntensityScaler {
    private String baseImageFileName;

    public ImageIntensityScaler(String fileName) {
        this.baseImageFileName = fileName;
    }

    private Image cloneBaseImage() {
        Image image = new Image();
        image.ReadPGM(baseImageFileName);
        return image;
    }

    public Image scaleByFactor(float factor, boolean clipOverflows) {
        Image newImage = cloneBaseImage();

        for (int y = 0; y < newImage.height; ++y) {
            for (int x = 0; x < newImage.width; ++x) {
                newImage.pixels[x][y] *= factor;

                if (clipOverflows && newImage.pixels[x][y] > 255) {
                    newImage.pixels[x][y] = 255;
                }
            }
        }

        return newImage;
    }

    public Image scaleByFactor(float factor) {
        return scaleByFactor(factor, false);
    }
}
