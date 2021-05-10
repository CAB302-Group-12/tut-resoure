package GuiExploration;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WiringExample extends JFrame implements ActionListener, Runnable {
    public static final int WIDTH = 300;
    public static final int HEIGHT = 200;

    private JTextArea display;
    private JPanel pnlNorth;
    private JPanel pnlWest;
    private JPanel pnlEast;
    private JPanel pnlBtn;

    private JButton btnLoad;
    private JButton btnUnload;
    private JButton btnFind;
    private JButton btnSwitch;

    public WiringExample(String title) throws HeadlessException {
        super(title);
    }

    private void createGUI() {
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(WIDTH, HEIGHT));

        BorderLayout mainLayout = new BorderLayout();
        setLayout(mainLayout);

        // create the main panel (which sizes automatically)
        display = createTextArea();
        getContentPane().add(display, BorderLayout.CENTER);

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
        btnLoad.addActionListener((event) -> onLoadClicked());
        btnUnload = createButton("Unload");
        btnUnload.addActionListener((event) -> onUnloadClicked());
        btnFind = createButton("Find");
        btnFind.addActionListener((event) -> onFindClicked());
        btnSwitch = createButton("Switch");
        btnSwitch.addActionListener(e -> onSwitchClicked());

        // position the buttons in the panel
        layoutButtonPanel();

        setVisible(true);
    }

    private void onLoadClicked() {
        JOptionPane.showMessageDialog(this,
                "There is nothing to load",
                "Error", JOptionPane.ERROR_MESSAGE);
        display.setText("Load was pressed");
    }

    private void onUnloadClicked() {
        int choice = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to unload?",
                "Confirmation", JOptionPane.YES_NO_OPTION);

        if (choice == JOptionPane.NO_OPTION)
            return;

        display.setText("Unload was pressed");
    }

    private void onFindClicked() {
        display.setText("Find was pressed");
    }

    private void onSwitchClicked() {
        display.setText("Switch was pressed");
    }

    private JPanel createPanel(Color color) {
        JPanel panel = new JPanel();
        panel.setBackground(color);
        return panel;
    }

    private JButton createButton(String text) {
        JButton button = new JButton(text);
        return button;
    }

    private JTextArea createTextArea() {
        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setFont(new Font("Arial", Font.BOLD, 24));
        textArea.setBorder(BorderFactory.createEtchedBorder());
        return textArea;
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
        Object source = e.getSource();

        if (source == btnLoad || source == btnUnload ||
                source == btnFind || source == btnSwitch) {
            display.setText(((JButton)source).getText());
        }
    }

    @Override
    public void run() {
        createGUI();
    }

    public static void main(String[] args) {
        WiringExample framesAndPanels = new WiringExample("BorderLayout");
        SwingUtilities.invokeLater(framesAndPanels);
    }
}
