import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import javax.swing.JButton;

public class PrettyButton extends JButton {
    private Color color;

    PrettyButton(String text, Color color) {
        super(text);
        this.color = color;
        setContentAreaFilled(false);
        setForeground(color);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setPaint(new GradientPaint(
                new Point(0, 0),
                getBackground(),
                new Point(0, getHeight() / 3),
                color));
        g2.fillRect(0, 0, getWidth(), getHeight() / 3);
        g2.setPaint(new GradientPaint(
                new Point(0, getHeight() / 3),
                color,
                new Point(0, getHeight()),
                getBackground()));
        g2.fillRect(0, getHeight() / 3, getWidth(), getHeight());
        g2.dispose();

        super.paintComponent(g);
    }
}

// Source:
// https://stackoverflow.com/questions/7115672/change-jbutton-gradient-color-but-only-for-one-button-not-all
// for some reason it changes the area under the button instead of the button itself
// came across this page just looking for how to change color of just the button
// without changing the area under it, but it didn't work for me.
// However, I left this code anyway because it looks cool