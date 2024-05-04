package GUI.component;

import javax.swing.*;
import GUI.theme.Colors;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.geom.RoundRectangle2D;

public class InputField extends JTextField {
    private int cornerRadius = 10;
    private Color outlineColor = Colors.green600;
    private Color backgroundColor = Colors.green50;
    private Color textColor = Colors.green900;
    private Color focusedColor = Colors.green400;
    private int outlineThickness = 2;

    public InputField() {
        this(10);
    }

    public InputField(int columns) {
        super(columns);
        setCaretColor(Colors.green500);
        setOpaque(false);
        setForeground(textColor);
        setBackground(backgroundColor);
        setBorder(BorderFactory.createEmptyBorder(7, 10, 7, 10));

        addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                outlineColor = focusedColor;
                repaint();
            }

            @Override
            public void focusLost(FocusEvent e) {
                outlineColor = Colors.green600;
                repaint();
            }
        });
    }

    public void setCornerRadius(int cornerRadius) {
        this.cornerRadius = cornerRadius;
        repaint();
    }

    public void setOutlineColor(Color outlineColor) {
        this.outlineColor = outlineColor;
        repaint();
    }

    public void setOutlineThickness(int outlineThickness) {
        this.outlineThickness = outlineThickness;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        // Antialiasing for smoother corners
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Create a gradient from top to bottom
        GradientPaint gp = new GradientPaint(0, 0, backgroundColor, 0, getHeight(), outlineColor, true);

        // Paint the background with rounded corners
        g2d.setPaint(gp);
        g2d.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), cornerRadius, cornerRadius));

        // Paint the outline when in focus
        if (hasFocus()) {
            g2d.setColor(outlineColor);
            g2d.setStroke(new BasicStroke(outlineThickness));
            g2d.draw(new RoundRectangle2D.Float(0, 0, getWidth() - 1, getHeight() - 1, cornerRadius, cornerRadius));
        }

        g2d.dispose();

        super.paintComponent(g);
    }

    @Override
    protected void paintBorder(Graphics g) {
        // Do not paint the default border
    }
}
