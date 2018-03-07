import java.awt.*;
import java.util.Observable;
import java.util.Vector;

/**
 * Created by Max on 2016-02-25.
 */
public class Model extends Observable {
    // the data in the model
    int ticks, curtick;
    Color color;
    int thickness;
    Vector<Segment> segments;
    int segnum;

    Model() {
        ticks = 0;
        curtick = 0;
        color = Color.BLACK;
        thickness = 2;
        segments = new Vector<Segment>();
        segnum = 1;
        setChanged();
    }

    public void update() {
        setChanged();
        notifyObservers();
    }
}
