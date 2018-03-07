
import java.awt.*;

/**
 * Created by Max on 2016-02-27.
 */
public class Segment {
    Color col;
    int strk;
    int oldX, oldY, curX, curY;
    int segnum;

    Segment(Color col_, int strk_, int oldX_, int oldY_, int curX_, int curY_, int segnum_) {
        col = col_;
        strk = strk_;
        oldX = oldX_;
        oldY = oldY_;
        curX = curX_;
        curY = curY_;
        segnum = segnum_;
    }

    protected void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setPaint(col);
        g2.setStroke(new BasicStroke(strk, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g2.drawLine(oldX, oldY, curX, curY);
    }
}
