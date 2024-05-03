package GUI.component;

import javax.swing.JRadioButton;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Color;

import GUI.theme.Colors;

public class RadioButton extends JRadioButton {
    public RadioButton(String text) {
        super(text);
        initialize();
    }

    private void initialize() {
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        setForeground(Colors.green400);  // Text color
        setOpaque(false);
        setAlignmentX(LEFT_ALIGNMENT);
        setFocusPainted(false);  // No focus ring around the button
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        try {
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            int ly = (getHeight() - 18) / 2;  // Center the circle vertically

            if (isSelected()) {
                drawSelected(g2, ly);
            } else {
                drawUnselected(g2, ly);
            }
        } finally {
            g2.dispose();
        }

        super.paintComponent(g);
    }

    private void drawSelected(Graphics2D g2, int ly) {
        g2.setColor(Colors.green400);  // Outer circle color
        g2.fillOval(1, ly, 18, 18);

        g2.setColor(Colors.green50);  // Inner circle color
        g2.fillOval(5, ly + 4, 10, 10);
    }

    private void drawUnselected(Graphics2D g2, int ly) {
        g2.setColor(Colors.green400);  // Outer circle color
        g2.fillOval(1, ly, 18, 18);

        g2.setColor(getBackground());  // This should be a different color, but for now, we use the component's background
        g2.fillOval(3, ly + 2, 14, 14);
    }
}
