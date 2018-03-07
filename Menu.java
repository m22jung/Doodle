
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by Max on 2016-02-25.
 */
public class Menu extends JPanel implements Observer {
    private JFrame f;
    private Model model;
    private JMenuBar mb = new JMenuBar();
    private JMenu mnuFile = new JMenu("File");
    private JMenuItem mnuItemSave = new JMenuItem("Save");
    private JMenuItem mnuItemLoad = new JMenuItem("Load");
    private JMenuItem mnuItemCreate = new JMenuItem("Create New");
    private JMenuItem mnuItemExit = new JMenuItem("Exit");
    private JMenu mnuView = new JMenu("View");
    private JMenuItem mnuItemfullsize = new JMenuItem("Full-size");
    private JMenuItem mnuItemfit = new JMenuItem("Fit");

    // constructor
    public Menu(JFrame f_, Model model_) {
        f = f_;
        model = model_;
        f.setJMenuBar(mb);

        // Build Menus
        mnuFile.add(mnuItemCreate);
        mnuFile.add(mnuItemLoad);
        mnuFile.add(mnuItemSave);
        mnuFile.add(mnuItemExit);
        mnuView.add(mnuItemfullsize);
        mnuView.add(mnuItemfit);

        mb.add(mnuFile);
        mb.add(mnuView);

        // Allows the Swing App to be closed
        f.addWindowListener(new ListenCloseWdw());

        // Add Menu listener
        mnuItemExit.addActionListener(new ListenMenuQuit());

        mnuItemfullsize.setEnabled(false);
        mnuItemCreate.setEnabled(false);
        mnuItemSave.setEnabled(false);
        mnuItemLoad.setEnabled(false);
    }// end constructor

    public class ListenMenuQuit implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            System.exit(0);
        }
    }
    public class ListenCloseWdw extends WindowAdapter {

        public void windowClosing(WindowEvent e) {
            System.exit(0);
        }
    }

    @Override
    public void update(Observable o, Object arg) {

    }
}
