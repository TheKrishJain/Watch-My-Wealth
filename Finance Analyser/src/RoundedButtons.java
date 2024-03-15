

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;

 public class RoundedButtons extends JButton {
    private Color bgColor;
    private Color textColor;

      public RoundedButtons(String text, Color bgColor, Color textColor) {
        super(text);
        this.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
       
        this.bgColor = bgColor;
        this.textColor = textColor;
        setContentAreaFilled(false); // Make button transparent
        setFocusPainted(false); // Remove focus border
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    
        // Paint background
        g2d.setColor(bgColor);
        g2d.fill(new RoundRectangle2D.Double(0, 0, getWidth() - 1, getHeight() - 1, 20, 20));
    
        // Calculate text position
        FontMetrics fm = g2d.getFontMetrics();
        Rectangle2D textBounds = fm.getStringBounds(getText(), g2d);
        int textX = (int) ((getWidth() - textBounds.getWidth()) / 2);
        int textY = (int) ((getHeight() - textBounds.getHeight()) / 2 + fm.getAscent());
    
        // Paint text
        g2d.setColor(textColor);
        g2d.drawString(getText(), textX, textY);
    
        g2d.dispose();
    }
    
  
}
