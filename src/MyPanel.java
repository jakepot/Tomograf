import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;


public class MyPanel extends JPanel {
    public MyPanel(int x, int y, ArrayList<Integer> xs, ArrayList<Integer> ys, int detects) {
        setPreferredSize(new Dimension(400, 400));
        this.x = x;
        this.y = y;
        detXs = xs;
        detYs = ys;
        dets = detects;
    }

    int x;
    int y;
    int dets = 5;
    ArrayList<Integer> detXs;
    ArrayList<Integer> detYs;


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        g2d.drawLine(x, y, x, y);
        for (int i = 0; i < dets; i++) {
            g2d.drawLine(detXs.get(i), detYs.get(i), detXs.get(i), detYs.get(i));
        }
    }
}