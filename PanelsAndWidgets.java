package GuiExploration;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PanelsAndWidgets extends JFrame implements ActionListener, Runnable {
    public static final int WIDTH = 300;
    public static final int HEIGHT = 200;

    private JPanel pnlCenter;
    private JPanel pnlNorth;
    private JPanel pnlWest;
    private JPanel pnlEast;
    private JPanel pnlBtn;

    private JButton btnLoad;
    private JButton btnUnload;
    private JButton btnFind;
    private JButton btnSwitch;

    public PanelsAndWidgets(String title) throws HeadlessException {
        super(title);
    }

    private void createGUI() {
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(WIDTH, HEIGHT));

        BorderLayout mainLayout = new BorderLayout();
        setLayout(mainLayout);

        // create the main panel (which sizes automatically)
        pnlCenter = createPanel(Color.WHITE);
        getContentPane().add(pnlCenter, BorderLayout.CENTER);

        // create the panels around all four edges of the main panel (centered)
        pnlWest = createPanel(Color.LIGHT_GRAY);
        getContentPane().add(pnlWest, BorderLayout.WEST);
        pnlEast = createPanel(Color.LIGHT_GRAY);
        getContentPane().add(pnlEast, BorderLayout.EAST);
        pnlNorth = createPanel(Color.LIGHT_GRAY);
        getContentPane().add(pnlNorth, BorderLayout.NORTH);

        // create the panel for the buttons
        pnlBtn = createPanel(Color.LIGHT_GRAY);
        getContentPane().add(pnlBtn, BorderLayout.SOUTH);

        // create our four buttons with different labels
        btnLoad = createButton("Load");
        btnUnload = createButton("Unload");
        btnFind = createButton("Find");
        btnSwitch = createButton("Switch");

        // position the buttons in the panel
        layoutButtonPanel();

        setVisible(true);
    }

    private JPanel createPanel(Color color) {
        JPanel panel = new JPanel();
        panel.setBackground(color);
        return panel;
    }

    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.addActionListener(this);
        return button;
    }

    private void layoutButtonPanel() {
        GridBagLayout layout = new GridBagLayout();
        pnlBtn.setLayout(layout);

        addToPanel(pnlBtn, btnLoad, 0, 0, 2, 1);
        addToPanel(pnlBtn, btnUnload, 3, 0, 2, 1);
        addToPanel(pnlBtn, btnFind, 0, 2, 2, 1);
        addToPanel(pnlBtn, btnSwitch, 3, 2, 2, 1);
    }

    /**
     *
     * A convenience method to add a component to given grid bag
     * layout locations. Code due to Cay Horstmann
     *
     * @param c the component to add
     * @param x the x grid position
     * @param y the y grid position
     * @param w the grid width of the component
     * @param h the grid height of the component
     */
    private void addToPanel(JPanel jp, Component c, int x, int y, int w, int h) {
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.NONE;
        constraints.anchor = GridBagConstraints.CENTER;
        constraints.weightx = 100;
        constraints.weighty = 100;
        constraints.gridx = x;
        constraints.gridy = y;
        constraints.gridwidth = w;
        constraints.gridheight = h;
        constraints.insets = new Insets(5, 5, 5, 5);
        jp.add(c, constraints);
    }


    @Override
    public void actionPerformed(ActionEvent e) {

    }

    @Override
    public void run() {
        createGUI();
    }

    public static void main(String[] args) {
        PanelsAndWidgets framesAndPanels = new PanelsAndWidgets("BorderLayout");
        SwingUtilities.invokeLater(framesAndPanels);
    }
}
