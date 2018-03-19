import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Tomograf {

    static Raster raster;

    int detectorsNo = 50;
    double detectorRange = 90.0;
    private double emitterStep = 2.0;

    int steps = (int) (360 / emitterStep);

    ArrayList<Integer> emitterPositionsX = new ArrayList<>();
    ArrayList<Integer> emitterPositionsY = new ArrayList<>();
    ArrayList<ArrayList<Integer>> detectorPositionsX = new ArrayList<>();
    ArrayList<ArrayList<Integer>> detectorPositionsY = new ArrayList<>();

    int[][] sinograph = new int[steps][detectorsNo]; //albo na odwrot

    private void generatePositions(int startingX, int startingY, int radius) {
        double step = Math.toRadians(emitterStep);
        double etdAngle = Math.toRadians(180.0 - (detectorRange / 2.0)); // emitter to 1st detector angle
        double detectorAngle = Math.toRadians(detectorRange / detectorsNo);
        for (int i = 0; i < 360.0 / emitterStep; i++) {
            emitterPositionsX.add((int) (startingX + (Math.cos(i * step) * radius))); //i + 1
            emitterPositionsY.add((int) (startingY + (Math.sin(i * step) * radius)));
            ArrayList<Integer> positionsX = new ArrayList<>();
            ArrayList<Integer> positionsY = new ArrayList<>();
            double firstDetectorAngle = ((i * step) + etdAngle);
            for (int j = 0; j < detectorsNo; j++) {
                positionsX.add((int) (startingX + (Math.cos(firstDetectorAngle + (j * detectorAngle)) * radius)));
                positionsY.add((int) (startingY + (Math.sin(firstDetectorAngle + (j * detectorAngle)) * radius)));
            }
            detectorPositionsX.add(positionsX);
            detectorPositionsY.add(positionsY);
        }
    }

    private void bresenham() {
        int d = 0;
        for (int i = 0; i < emitterPositionsX.size(); i++) {
            for (int j = 0; j < detectorPositionsX.get(i).size(); j++) {

                int x1 = emitterPositionsX.get(i);
                int y1 = emitterPositionsY.get(i);
                int x2 = detectorPositionsX.get(i).get(j);
                int y2 = detectorPositionsY.get(i).get(j);

                int dx = Math.abs(x2 - x1);
                int dy = Math.abs(y2 - y1);

                int dx2 = 2 * dx; // slope scaling factors to
                int dy2 = 2 * dy; // avoid floating point

                int ix = x1 < x2 ? 1 : -1; // increment direction
                int iy = y1 < y2 ? 1 : -1;

                int x = x1;
                int y = y1;

                int pixels = 0;

                if (dx >= dy) {
                    while (true) {
                        sinograph[i][j] += raster.getSample(x, y, 0);
                        pixels++;
                        if (x == x2)
                            break;
                        x += ix;
                        d += dy2;
                        if (d > dx) {
                            y += iy;
                            d -= dx2;
                        }
                    }
                } else {
                    while (true) {
                        sinograph[i][j] += raster.getSample(x, y, 0);
                        pixels++;
                        if (y == y2)
                            break;
                        y += iy;
                        d += dx2;
                        if (d > dy) {
                            x += ix;
                            d -= dy2;
                        }
                    }
                }

                sinograph[i][j] /= pixels;
            }
        }
    }

    public void writeImage() {
        String path = "res/output.png";
//        BufferedImage image = new BufferedImage(sinograph.length, sinograph[0].length, BufferedImage.TYPE_INT_RGB);
        BufferedImage image = new BufferedImage(sinograph.length, sinograph[0].length, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < steps; x++) {
            for (int y = 0; y < detectorsNo; y++) {
                image.setRGB(x, y, sinograph[x][y]);
            }
        }

        File ImageFile = new File(path);
        try {
            ImageIO.write(image, "png", ImageFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Zapisano obraz.");
    }

    public static void main(String[] args) throws IOException {
        Tomograf tomo = new Tomograf();
        tomo.generatePositions(200, 200, 199);

        File file = new File("./photo.bmp");
        BufferedImage image = ImageIO.read(file);
        raster = image.getData();

        tomo.bresenham();

        for (int i = 0; i < tomo.detectorsNo; i++) {
            System.out.println(tomo.sinograph[0][i]);
        }

        JFrame frame = new JFrame("Tomografia");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(new MyPanel(tomo.emitterPositionsX.get(0), tomo.emitterPositionsY.get(0),
                tomo.detectorPositionsX.get(0), tomo.detectorPositionsY.get(0), tomo.detectorsNo));
        frame.setSize(400, 400);
        frame.setVisible(true);

        tomo.writeImage();
    }

}
