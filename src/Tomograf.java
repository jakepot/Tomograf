import javax.swing.*;
import java.util.ArrayList;

public class Tomograf {

    int detectorsNo = 20;
    int detectorRange = 90;
    private int emitterStep = 2; // math.toradians
    ArrayList<Integer> emitterPositionsX = new ArrayList<>();
    ArrayList<Integer> emitterPositionsY = new ArrayList<>();
    ArrayList<ArrayList<Integer>> detectorPositionsX = new ArrayList<>();
    ArrayList<ArrayList<Integer>> detectorPositionsY = new ArrayList<>();

    private void generatePositions (int startingX, int startingY, int radius) {
        double step = Math.toRadians(emitterStep);
        double etdAngle = Math.toRadians(180 - (detectorRange/2)); // emitter to 1st detector angle
        double detectorAngle = Math.toRadians(detectorRange/detectorsNo);
        for (int i = 0; i < 360/emitterStep; i++) {
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

    public static void main(String[] args) {
        Tomograf tomo = new Tomograf();
        tomo.generatePositions(200, 200, 100);

        JFrame frame = new JFrame("Tomografia");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(new MyPanel(tomo.emitterPositionsX.get(0), tomo.emitterPositionsY.get(0),
                tomo.detectorPositionsX.get(0), tomo.detectorPositionsY.get(0), tomo.detectorsNo));
        frame.setSize(400,400);
        frame.setVisible(true);
    }
}
