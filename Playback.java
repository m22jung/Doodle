import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;
import java.util.TimerTask;

/**
 * Created by Max on 2016-02-25.
 */
class Playback extends JPanel implements Observer {
    // the model that this view is showing
    private Model model;
    private ImageIcon playIcon = new ImageIcon(getClass().getResource("4.gif"));
    private JButton play = new JButton(playIcon);
    private ImageIcon startIcon = new ImageIcon(getClass().getResource("5.gif"));
    private JButton start = new JButton(startIcon);
    private ImageIcon endIcon = new ImageIcon(getClass().getResource("6.gif"));
    private JButton end = new JButton(endIcon);
    private JSlider slider = new JSlider();

    Playback(Model model_) {
        // set the model
        model = model_;

        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        this.add(play);
        this.add(slider);
        this.add(start);
        this.add(end);
        slider.setValue(100);

        play.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                if (model.ticks != 0) {
                    model.curtick = 0;
                    slider.setValue(0);

                    java.util.Timer timer = new java.util.Timer();
                    timer.scheduleAtFixedRate(new TimerTask() {
                        @Override
                        public void run() {
                            if (slider.getValue() < 100) {
                                slider.setValue(slider.getValue() + 1);
                                model.curtick = model.curtick + 1;
                            } else {
                                model.curtick -= 1;
                                timer.cancel();
                                timer.purge();
                            }
                        }
                    }, model.segnum/10, model.segnum/10);
                }
            }
        });

        start.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                if (model.ticks != 0) {
                    slider.setValue(slider.getMinimum());
                }
            }
        });

        end.addActionListener(new  ActionListener(){
            public void actionPerformed(ActionEvent e) {
                if (model.ticks != 0) {
                    slider.setValue(slider.getMaximum());
                }
            }
        });

        slider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                model.curtick = slider.getValue();
                model.update();
            }
        });
    }

    // Observer interface
    @Override
    public void update(Observable arg0, Object arg1) {
        if (model.ticks != 0) {
            slider.setEnabled(true);
            play.setEnabled(true);
            start.setEnabled(true);
            end.setEnabled(true);
            slider.setMajorTickSpacing(100 / model.ticks);
            slider.setPaintTicks(true);
        } else {
            slider.setEnabled(false);
            play.setEnabled(false);
            start.setEnabled(false);
            end.setEnabled(false);
        }

        if (model.curtick == 100) {
            slider.setValue(100);
        }
    }
}
