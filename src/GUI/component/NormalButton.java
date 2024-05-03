package GUI.component;

import javax.swing.JButton;

import GUI.theme.Colors;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;

public class NormalButton extends JButton {
    Color defaultColor = Colors.green400;
    Color pressedColor = Colors.green600; // Darker shade when pressed
    Color hoverColor = Colors.green500; // Lighter shade when hovered
    Color textColor = Colors.green50; // High contrast text color
    int cornerRadius = 10;

    public NormalButton(String text) {
        super(text);
        addMouseListener(new MouseAdapter() {

            @Override
            public void mouseEntered(MouseEvent e) {
                setBackground(hoverColor);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setBackground(defaultColor);
            }

            @Override
            public void mousePressed(MouseEvent e) {
                setBackground(pressedColor);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                // Change color back on mouse release inside button area
                setBackground(isHover(e) ? hoverColor : defaultColor);
            }

            private boolean isHover(MouseEvent e) {
                return contains(e.getPoint());
            }
        });

        // Set initial button properties
        setForeground(textColor);
        setBackground(defaultColor);
        setContentAreaFilled(false);
        setOpaque(false);
        setBorderPainted(false);
    }

    public void setCornerRadius(int cornerRadius) {
        this.cornerRadius = cornerRadius;
        repaint();
    }

    public void setPressedColor(Color backgroundColor) {
        this.pressedColor = backgroundColor;
    }

    public void setHoverColor(Color hoverColor) {
        this.hoverColor = hoverColor;
    }

    public void setTextColor(Color textColor) {
        this.textColor = textColor;
        setForeground(textColor);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int width = getWidth();
        int height = getHeight();
        int moveDown = 2;

        // Create rounded rectangle with specified corner radius
        RoundRectangle2D roundedRectangle = new RoundRectangle2D.Float(0, moveDown, width - 1, height - 1 - moveDown, cornerRadius, cornerRadius);

        // Paint the background
        g2d.setColor(getBackground());
        g2d.fill(roundedRectangle);

        // Paint the text
        g2d.setColor(getForeground());
        FontMetrics fm = g2d.getFontMetrics();
        int x = (width - fm.stringWidth(getText())) / 2;
        int y = (height - fm.getHeight()) / 2 + fm.getAscent();
        g2d.drawString(getText(), x, y);

        g2d.dispose();
    }
}
