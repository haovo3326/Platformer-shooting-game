import CustomMath.Vector2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ResolutionDialog extends JFrame implements ActionListener {

    public ResolutionDialog() {
        setTitle("Settings");
        setSize(400, 400);
        setBackground(Color.LIGHT_GRAY);

        Font uiFont = new Font("Segoe UI", Font.BOLD, 13);
        UIManager.put("Label.font", uiFont);
        UIManager.put("Button.font", uiFont);
        UIManager.put("ComboBox.font", uiFont);
        UIManager.put("CheckBox.font", uiFont);
        splittingMainFrame();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }

    /**
     * Split main frame into the following layout
     * +----------------------+
     * |                      |
     * +                      +  <- bigger
     * |                      |
     * +----------------------+
     * |                      |  <- smaller
     * +----------------------+
     * */
    private void splittingMainFrame(){
        setLayout(new BorderLayout(0, 10));
        ((JComponent) getContentPane()).setBorder(
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        );
        JPanel bigPanel = new JPanel();

        JPanel smallPanel = new JPanel();
        smallPanel.setPreferredSize(new Dimension(0, 30));

        add(bigPanel, BorderLayout.CENTER);
        add(smallPanel, BorderLayout.SOUTH);

        splittingImageAndSetting(bigPanel);
        buildPlayQuit(smallPanel);
    }

    /**
     * Split (upper) big panel into the following layout
     * +----------------------+
     * |        Image         |
     * +----------------------+
     * |       Settings       |
     * +----------------------+
     * */
    private void splittingImageAndSetting(JPanel bigPanel){
        bigPanel.setLayout(new GridLayout(2, 1, 0, 10));
        JPanel imagePanel = new JPanel();
        imagePanel.setBackground(Color.WHITE);
        JPanel settingPanel = new JPanel();
        settingPanel.setBackground(Color.WHITE);
        bigPanel.add(imagePanel);
        bigPanel.add(settingPanel);

        buildSetting(settingPanel);
    }

    private JComboBox<String> resBox;
    private JCheckBox windowedCheckBox;
    private JComboBox<String> graphicsBox;

    private void buildSetting(JPanel settingPanel){
        settingPanel.setBorder(BorderFactory.createEmptyBorder(50, 30, 50, 30));
        settingPanel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0;
        gbc.gridy = 0;
        settingPanel.add(new JLabel("Resolution"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        String currentRes = screenSize.width + " x " + screenSize.height;
        List<String> resolutions = new ArrayList<>();
        resolutions.add("1920 x 1080");
        resolutions.add("1600 x 900");
        resolutions.add("1366 x 768");
        resolutions.add("1280 x 720");
        if (!resolutions.contains(currentRes)) {
            resolutions.add(currentRes);
        }
        Collections.sort(resolutions, (r1, r2) -> {
            int w1 = Integer.parseInt(r1.split(" x ")[0]);
            int w2 = Integer.parseInt(r2.split(" x ")[0]);
            return Integer.compare(w1, w2);
        });
        resBox = new JComboBox<>(resolutions.toArray(new String[0]));
        resBox.addActionListener(this);
        resBox.setSelectedItem(currentRes);
        settingPanel.add(resBox, gbc);

        gbc.gridx = 2;
        gbc.gridy = 0;
        JLabel windowed = new JLabel("Windowed");
        windowed.setHorizontalAlignment(SwingConstants.RIGHT);
        settingPanel.add(windowed, gbc);

        gbc.gridx = 3;
        gbc.gridy = 0;
        windowedCheckBox = new JCheckBox();
        windowedCheckBox.addActionListener(this);
        settingPanel.add(windowedCheckBox);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0;
        settingPanel.add(new JLabel("Quality"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        graphicsBox = new JComboBox<>(new String[]{
                "Low", "Medium", "High"
        });
        graphicsBox.addActionListener(this);
        graphicsBox.setSelectedItem("High");
        settingPanel.add(graphicsBox, gbc);
    }


    private JButton playButton;
    private JButton quitButton;

    private void buildPlayQuit(JPanel smallPanel){
        smallPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        playButton = new JButton("Play");
        quitButton = new JButton("Quit");
        playButton.addActionListener(this);
        quitButton.addActionListener(this);
        smallPanel.add(playButton);
        smallPanel.add(quitButton);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == playButton) {
            String selectedRes = (String) resBox.getSelectedItem();
            String[] parts = selectedRes.split(" x ");
            int width = Integer.parseInt(parts[0]);
            int height = Integer.parseInt(parts[1]);
            boolean isWindowed = windowedCheckBox.isSelected();
            new GameFrame(new Vector2(width, height), isWindowed);
            dispose();
        }

        if (e.getSource() == quitButton) {
            System.exit(0);
        }
    }

    static void main(String[] args) {
        new ResolutionDialog();
    }
}