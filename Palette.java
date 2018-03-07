
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by Max on 2016-02-25.
 */
class Palette extends JPanel implements Observer {
    // the model that this view is showing
    private Model model;
    private JColorChooser jc;
    RectDraw selectedcolor;
    RectDrawE selectedstroke;

    Palette(Model model_) {
        // set the model
        model = model_;

        JPanel colour = new JPanel(new GridLayout(0, 2));
        colour.setBorder(BorderFactory.createTitledBorder("Colour"));

        // Add the components.
        colour.add(new RectDraw(Color.RED, model, false));
        colour.add(new RectDraw(Color.ORANGE, model, false));
        colour.add(new RectDraw(Color.YELLOW, model, false));
        colour.add(new RectDraw(Color.GREEN, model, false));
        colour.add(new RectDraw(Color.BLUE, model, false));
        colour.add(new RectDraw(Color.BLACK, model, false));
        colour.add(new RectDraw(Color.WHITE, model, false));
        jc = new JColorChooser();
        colour.add(new RectDrawC(model, jc));

        JPanel stroke = new JPanel(new GridLayout(0, 1));
        stroke.setBorder(BorderFactory.createTitledBorder("Stroke"));

        stroke.add(new RectDraw2(2, model));
        stroke.add(new RectDraw2(4, model));
        stroke.add(new RectDraw2(8, model));
        stroke.add(new RectDraw2(16, model));

        JPanel selected = new JPanel(new GridLayout(0, 2));

        selectedcolor = new RectDraw(model.color, model, true);
        selectedstroke = new RectDrawE(model.thickness, model.color);
        selected.add(selectedcolor);
        selected.add(selectedstroke);

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        colour.setMinimumSize(new Dimension(65, 120));
        colour.setMaximumSize(new Dimension(65, 120));
        stroke.setMinimumSize(new Dimension(65, 120));
        stroke.setMaximumSize(new Dimension(65, 120));
        selected.setMinimumSize(new Dimension(60, 30));
        selected.setMaximumSize(new Dimension(60, 30));
        this.add(colour);
        this.add(stroke);
        this.add(selected);
    }

    private static class RectDraw extends JComponent {
        Color color;
        Model model;
        RectDraw(Color color_, Model model_, boolean prev) {
            color = color_;
            model = model_;
            if (!prev) {
                addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        model.color = color;
                        model.update();
                    }
                });
            }
        }

        public void paintComponent(Graphics g) {
            g.setColor(color);
            g.fillRect(0, 0, 30, 30);
            g.setColor(Color.black);
            g.drawRect(0, 0, 30, 30);
        }
        public Dimension getPreferredSize() {
            return new Dimension(30, 30); // appropriate constants
        }
    }

    private static class RectDrawC extends JComponent {
        Model model;
        JColorChooser jc;

        RectDrawC(Model model_, JColorChooser jc_) {
            model = model_;
            jc = jc_;
            addMouseListener(new MouseAdapter(){
                @Override
                public void mouseClicked(MouseEvent e) {
                    Color newColor = JColorChooser.showDialog(RectDrawC.this, "Choose Colour", model.color);
                    if (newColor != null) {
                        model.color = newColor;
                        model.update();
                    }
                }
            });
        }

        public void paintComponent(Graphics g) {
            g.setColor(Color.WHITE);
            g.fillRect(0, 0, 30, 30);
            g.setColor(Color.black);
            g.drawRect(0, 0, 30, 30);
            g.setFont(new Font("TimesRoman", Font.BOLD, 22));
            g.drawString("C", 8, 24);
        }
        public Dimension getPreferredSize() {
            return new Dimension(30, 30); // appropriate constants
        }
    }

    private static class RectDraw2 extends JComponent {
        Model model;
        int thickness;

        RectDraw2(int thickness_, Model model_) {
            model = model_;
            thickness = thickness_;
            addMouseListener(new MouseAdapter(){
                @Override
                public void mouseClicked(MouseEvent e) {
                    model.thickness = thickness;
                    model.update();
                }
            });
        }

        public void paintComponent(Graphics g) {
            g.setColor(Color.black);
            g.drawRect(0, 0, 60, 30);
            g.fillRoundRect(2, (28-thickness)/2, 50, thickness, thickness, thickness);
        }
        public Dimension getPreferredSize() {
            return new Dimension(60, 30); // appropriate constants
        }
    }

    private static class RectDrawE extends JComponent {
        int stroke;
        Color color;
        RectDrawE(int stroke_, Color color_) {
            stroke = stroke_;
            color = color_;
        }

        public void paintComponent(Graphics g) {
            g.setColor(Color.BLACK);
            g.drawRect(0, 0, 30, 30);
            g.setColor(color);
            g.fillOval((30-stroke)/2, (30-stroke)/2, stroke, stroke);
        }
        public Dimension getPreferredSize() {
            return new Dimension(30, 30); // appropriate constants
        }
    }

    // Observer interface
    @Override
    public void update(Observable arg0, Object arg1) {
        selectedcolor.color = model.color;
        selectedstroke.stroke = model.thickness;
        selectedstroke.color = model.color;
        repaint();
    }
}

