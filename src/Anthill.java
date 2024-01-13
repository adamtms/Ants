import java.awt.Point;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JLayeredPane;
import javax.swing.*;

/**
 * Represents an abstract class for an anthill.
 */
public abstract class Anthill extends Vertex {
    private int countLarvae = 0;
    private JLabel larvaeNumberLabel = new JLabel("0");

    protected Anthill(Point point, JLayeredPane layeredPane) {
        super(point, layeredPane);
        setDifficulty(1);

        createLarvaeLabel();
    }

    private void createLarvaeLabel() {
        larvaeNumberLabel = new JLabel(String.valueOf(countLarvae));
        Font boldFont = new Font(larvaeNumberLabel.getFont().getName(), Font.BOLD,
                larvaeNumberLabel.getFont().getSize());
        larvaeNumberLabel.setFont(boldFont);
        larvaeNumberLabel.setForeground(Color.WHITE);
        Point point = getPoint();
        larvaeNumberLabel.setBounds(point.x - Vertex.radius, point.y - Vertex.radius, 100, 20);
        getLayeredPane().add(larvaeNumberLabel, Integer.valueOf(5));
    }

    private void recreateLarvaeLabel() {
        getLayeredPane().remove(larvaeNumberLabel);
        createLarvaeLabel();
    }

    protected void putLarvae(Larvae larvae) {
        synchronized (getLarvaeLock()) {
            countLarvae += 1;
            recreateLarvaeLabel();
            synchronized (larvae) {
                getLayeredPane().remove(larvae.getLabel());
            }
        }
    }

    protected int getCountLarvae() {
        return countLarvae;
    }
}