import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;


public class MyPanel extends JPanel {
    public MyPanel(int x, int y, ArrayList<Integer> xs, ArrayList<Integer> ys, int detects, int size,
                   BufferedImage img) {
        setPreferredSize(new Dimension(size, size));
        image = img;
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
    BufferedImage image;

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g.drawImage(image,0,0,this);
        g2d.drawLine(x, y, x, y);
        for (int i = 0; i < dets; i++) {
//            g2d.drawLine(detXs.get(i), detYs.get(i), detXs.get(i), detYs.get(i));
            g2d.drawLine(detXs.get(i), detYs.get(i), x, y);
        }
    }
}