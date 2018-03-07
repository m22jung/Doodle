import javax.swing.*;
import java.awt.*;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.Iterator;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by Max on 2016-02-25.
 */
class Canvas extends JPanel implements Observer {
    // the model that this view is showing
    private Model model;
    private int curX, curY, oldX, oldY;
    private boolean drawn;
    Image image;
    Graphics2D g2;

    Canvas(Model model_) {
        setBackground(Color.WHITE);
        setLayout(new FlowLayout(FlowLayout.LEFT));

        // set the model
        model = model_;
        drawn = false;
        addMouseMotionListener(new MouseMotionAdapter(){
            @Override
            public void mouseDragged(MouseEvent e) {
                drawn = true;
                curX = e.getX();
                curY = e.getY();
                // tick is not at the end.
                if (model.curtick != 100 && model.ticks != 0) {
                    Double sn = new Double(model.segnum);
                    Double ct = new Double(model.curtick);
                    Double doubleupto = sn * (ct / 100);
                    int intupto = doubleupto.intValue();
                    if (intupto == 0) {
                        model.segments.removeAllElements();
                    } else {
                        while (model.segments.get(intupto).segnum != model.segments.lastElement().segnum) {
                            model.segments.remove(intupto + 1);
                        }
                    }
                    model.segnum = intupto;
                    Double t = new Double(model.ticks);
                    Double doubleticks = t * ct/100;
                    int intticks = doubleticks.intValue();
                    model.ticks = intticks;
                }
                Segment seg = new Segment(model.color, model.thickness, oldX, oldY, curX, curY, model.segnum);
                model.segments.add(seg);
                model.segnum++;
                model.curtick = 100;
                model.update();
                oldX = curX;
                oldY = curY;
            }
        });
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                oldX = e.getX();
                oldY = e.getY();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (drawn) {
                    model.ticks++;
                    model.update();
                    drawn = false;
                }
            }
        });
    }

    protected void paintComponent(Graphics g) {
        image = createImage(800, 600);
        g2 = (Graphics2D)image.getGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setPaint(Color.WHITE);
        g2.fillRect(0, 0, 800, 600);

        // draw up to curtick
        Double sn = new Double(model.segnum);
        Double ct = new Double(model.curtick);
        Double doubleupto = sn * (ct / 100);
        int intupto = doubleupto.intValue();

        Iterator<Segment> i = model.segments.iterator();
        if (model.curtick == 100) {
            while (i.hasNext()) {
                i.next().paint(g2);
            }
        } else if (model.curtick != 0) {
            for (int j = 0; j <= intupto; ++j) {
                if (j < model.segments.size()) {
                    model.segments.get(j).paint(g2);
                }
            }
        }
        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(0, 0, getWidth(), getHeight());
        g.drawImage(image, 0, 0, null);
    }

    // Observer interface
    @Override
    public void update(Observable arg0, Object arg1) {
        repaint();
    }
}
