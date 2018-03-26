import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.awt.event.ActionListener;
import java.util.Hashtable;

public class MyPanel extends JPanel implements ActionListener {
    public MyPanel(Tomograf _tomo, int x, int y, ArrayList<Integer> xs, ArrayList<Integer> ys, int detects, int size,
                   BufferedImage img, BufferedImage _sin_img, BufferedImage _wyj_img, int detectorsNo, double detectorRange, double emitterStep, double error) {
        //setPreferredSize(new Dimension(size + 1100, size + 400));
        tomo = _tomo;
        image = img;
        wej_img = img;
        sin_img = _sin_img;
        wyj_img = _wyj_img;
        this.x = x;
        this.y = y;
        detXs = xs;
        detYs = ys;
        dets = detects;
        GroupLayout layout = new GroupLayout(this);
        setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);
        slider = new JSlider(JSlider.HORIZONTAL,
                10, 280, detectorsNo);
        slider.setMajorTickSpacing(20);
        slider.setMinorTickSpacing(5);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        slider2 = new JSlider(JSlider.HORIZONTAL,
                20, 360, (int)detectorRange);
        slider2.setMajorTickSpacing(20);
        slider2.setMinorTickSpacing(5);
        slider2.setPaintTicks(true);
        slider2.setPaintLabels(true);
        slider3 = new JSlider(JSlider.HORIZONTAL,
                30, 200, (int)(emitterStep * 100.0));
        Hashtable labelTable = new Hashtable();
        labelTable.put( new Integer( 30 ), new JLabel("0.3") );
        labelTable.put( new Integer( 50 ), new JLabel("0.5") );
        labelTable.put( new Integer( 75 ), new JLabel("0.75") );
        labelTable.put( new Integer( 100 ), new JLabel("1.0") );
        labelTable.put( new Integer( 125 ), new JLabel("1.25") );
        labelTable.put( new Integer( 150 ), new JLabel("1.5") );
        labelTable.put( new Integer( 175 ), new JLabel("1.75") );
        labelTable.put( new Integer( 200 ), new JLabel("2.0") );
        slider3.setLabelTable( labelTable );
        slider3.setMajorTickSpacing(25);
        slider3.setMinorTickSpacing(5);
        slider3.setPaintTicks(true);
        slider3.setPaintLabels(true);
        JButton button = new JButton("Calculate");
        button.setActionCommand("reset");
        button.addActionListener(this);
        /*add(slider);
        add(slider2);
        add(slider3);*/
        JLabel img_lab = new JLabel(new ImageIcon(image));
        JLabel wej_lab = new JLabel(new ImageIcon(image));
        JLabel sin_lab = new JLabel(new ImageIcon(sin_img));
        JLabel wyj_lab = new JLabel(new ImageIcon(wyj_img));
        Component comp = Box.createRigidArea(new Dimension(image.getWidth(), image.getHeight()));
        JLabel lab_slide_1 = new JLabel("Number of detectors");
        JLabel lab_slide_2 = new JLabel("Angle of detector cone");
        JLabel lab_slide_3 = new JLabel("Step of emitter");
        JLabel lab_img_1 = new JLabel("Input image:");
        JLabel lab_img_2 = new JLabel("Sinogram:");
        JLabel lab_img_3 = new JLabel("Reconstructed image:");

        JLabel err_lab = new JLabel("Error:");
        JLabel err_val_lab = new JLabel(String.valueOf(error));

        layout.setHorizontalGroup(
                layout.createParallelGroup()
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(comp)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(lab_slide_1)
                                        .addComponent(slider)
                                        .addComponent(lab_slide_2)
                                        .addComponent(slider2)
                                        .addComponent(lab_slide_3)
                                        .addComponent(slider3)
                                        .addComponent(button)
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(err_lab)
                                                .addComponent(err_val_lab))))
                        .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup()
                                        .addComponent(wej_lab)
                                        .addComponent(lab_img_1))
                                .addGroup(layout.createParallelGroup()
                                        .addComponent(sin_lab)
                                        .addComponent(lab_img_2))
                                .addGroup(layout.createParallelGroup()
                                        .addComponent(wyj_lab)
                                        .addComponent(lab_img_3)))
        );
        layout.setVerticalGroup(
                layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addGroup(layout.createSequentialGroup()
                                        .addComponent(lab_slide_1)
                                        .addComponent(slider)
                                        .addComponent(lab_slide_2)
                                        .addComponent(slider2)
                                        .addComponent(lab_slide_3)
                                        .addComponent(slider3)
                                        .addComponent(button)
                                        .addGroup(layout.createParallelGroup()
                                                .addComponent(err_lab)
                                                .addComponent(err_val_lab)))
                                .addComponent(comp))
                        .addGroup(layout.createParallelGroup()
                                .addComponent(lab_img_1)
                                .addComponent(lab_img_2)
                                .addComponent(lab_img_3))
                        .addGroup(layout.createParallelGroup()
                                .addComponent(wej_lab)
                                .addComponent(sin_lab)
                                .addComponent(wyj_lab))
        );

    }

    int x;
    int y;
    int dets = 5;
    ArrayList<Integer> detXs;
    ArrayList<Integer> detYs;
    Tomograf tomo;
    BufferedImage image;
    BufferedImage wej_img;
    BufferedImage sin_img;
    BufferedImage wyj_img;
    ActionListener actionListener;
    JSlider slider, slider2, slider3;

    @Override
    public void actionPerformed(ActionEvent e) {
        if ("reset".equals(e.getActionCommand())) {
            System.out.println("Click");
            ArrayList <Integer> list = new ArrayList<>();
            list.add(slider.getValue());
            list.add(slider2.getValue());
            list.add(slider3.getValue());
            tomo.caclutalte(image, list);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g.drawImage(image,10,10,this);
        g2d.drawLine(x, y, x, y);
        for (int i = 0; i < dets; i++) {
//            g2d.drawLine(detXs.get(i), detYs.get(i), detXs.get(i), detYs.get(i));
            g2d.drawLine(detXs.get(i) + 10, detYs.get(i) + 10, x + 10, y + 10);
        }
    }
}