
import javax.swing.*;
import java.awt.*;

/**
 * Created by Max on 2016-02-23.
 */
public class Doodle {
    public static void main(String[] args){
        JFrame frame = new JFrame("Doodle");

        // create Model and initialize it
        Model model = new Model();
        Menu mn = new Menu(frame, model);

        Palette pal = new Palette(model);
        model.addObserver(pal);

        Playback play = new Playback(model);
        model.addObserver(play);

        Canvas canvas = new Canvas(model);
        model.addObserver(canvas);

        // let all the views know that they're connected to the model
        model.notifyObservers();

        // create the window
        JPanel p = new JPanel(new BorderLayout());

        canvas.setPreferredSize(new Dimension(800,600));
        JScrollPane scroller = new JScrollPane(canvas);

        p.add(pal, BorderLayout.WEST);
        p.add(play, BorderLayout.SOUTH);
        p.add(scroller, BorderLayout.CENTER);

        frame.getContentPane().add(p);
        frame.setMinimumSize(new Dimension(480, 420));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}