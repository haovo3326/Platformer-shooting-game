import javax.swing.*;
import java.awt.*;

public class ResolutionDialog extends JFrame {

    public ResolutionDialog() {
        resolutionSettings();

        setTitle("Settings");
        setSize(300, 100);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }

    private void resolutionSettings() {
        String[] resolutions = {
                "1280 x 720",
                "1366 x 768",
                "1920 x 1080"
        };
        JComboBox<String> resolutionBox = new JComboBox<>(resolutions);
        // Force width to fit longest string
        resolutionBox.setPrototypeDisplayValue("1920 x 1080");
        JLabel label = new JLabel("Resolution");

        JPanel resolutionPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        resolutionPanel.add(label);
        resolutionPanel.add(resolutionBox);

        setLayout(new GridBagLayout());
        add(resolutionPanel);
    }


    static void main(String[] args) {
        new ResolutionDialog();
    }
}